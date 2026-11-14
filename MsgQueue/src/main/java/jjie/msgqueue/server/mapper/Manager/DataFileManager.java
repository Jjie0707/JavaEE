package jjie.msgqueue.server.mapper.Manager;

import jjie.msgqueue.common.BinaryTool;
import jjie.msgqueue.common.MqException;
import jjie.msgqueue.server.core.Message;
import jjie.msgqueue.server.core.MsgQueue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class DataFileManager {
    private static final int MAX_MSG_BYTES = 64 * 1024 * 1024;
    private final ConcurrentHashMap<String, Object> queueLocks = new ConcurrentHashMap<>();
    // 记录统计文件需要重新计算的队列。
    private final Set<String> queuesNeedingStatsRebuild =
            ConcurrentHashMap.newKeySet();

    private Object lockOf(String queueName) {
        return queueLocks.computeIfAbsent(queueName, k -> new Object());
    }
    public void init() {
        //null
    }

    public static class Sta{
        public int totalCount;
        public int validCount;
        public Sta(){}
        public Sta(int totalCount,int validCount){
            this.totalCount=totalCount;
            this.validCount=validCount;
        }
    }
    //获取各文件名的方法
    public String getQueueDir(String queueName){
        return "./data/"+queueName;
    }
    public String getQueueDataPath(String queueName){
        return getQueueDir(queueName)+"/data.txt";
    }
    public String getQueueStaPath(String queueName){
        return getQueueDir(queueName)+"/sta.txt";
    }

    //sta文件写入
    public void writeSta(String queueName, Sta sta) throws IOException {
        Path temporaryPath = Paths.get(getQueueStaPath(queueName) + ".tmp");
        Path targetPath = Paths.get(getQueueStaPath(queueName));

        Files.writeString(
                temporaryPath,
                sta.totalCount + "\t" + sta.validCount
        );

        Files.move(
                temporaryPath,
                targetPath,
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE
        );
    }
    //sta文件读取
    public Object readSta(String queueName) throws IOException {
        Sta sta=new Sta();
        try(InputStream inputStream=new FileInputStream(
                getQueueStaPath(queueName) )
        ){
            try (Scanner scanner = new Scanner(new FileInputStream(getQueueStaPath(queueName)))) {
                if (!scanner.hasNextInt()) throw new IOException("[DataFileManager] sta.txt 损坏: " + queueName);
                sta.totalCount = scanner.nextInt();
                if (!scanner.hasNextInt()) throw new IOException("[DataFileManager] sta.txt 损坏: " + queueName);
                sta.validCount = scanner.nextInt();
            }
            return sta;
        }
    }

    //用一个总方法完成整个队列消息文件的目录以及子文件的创建
    public void createQueueFiles(String queueName) throws IOException{
        File queueFileDir=new File(getQueueDir(queueName));
        queueFileDir.mkdirs();
        File queueDataPath=new File(getQueueDataPath(queueName));
        queueDataPath.createNewFile();
        File queueStaPath=new File(getQueueStaPath(queueName));
        boolean ret=queueStaPath.createNewFile();
        //完成sta.txt文件的初始化
        if(ret) {
            Sta sta = new Sta(0, 0);
            writeSta(queueName, sta);
        }
    }
    //由下至上销毁一个队列的消息文件
    public void destroyQueueFiles(String queueName) throws IOException {
        synchronized (lockOf(queueName)) {
            Path dir = Paths.get(getQueueDir(queueName));

            if (Files.exists(dir)) {
                try (Stream<Path> walk = Files.walk(dir)) {
                    for (Path path : walk.sorted(Comparator.reverseOrder()).toList()) {
                        Files.deleteIfExists(path);
                    }
                }
            }

            // 队列文件已删除，对应的统计重建标记也一起移除。
            queuesNeedingStatsRebuild.remove(queueName);
        }
    }
    public boolean checkFilesExists(String queueName) {
        // 判定队列的数据文件和统计文件是否都存在!!
        File queueDataFile = new File(getQueueDataPath(queueName));
        if (!queueDataFile.exists()) {
            return false;
        }
        File queueStaFile = new File(getQueueStaPath(queueName));
        if (!queueStaFile.exists()) {
            return false;
        }
        return true;
    }
    //把一个消息存储到文件中
    public void sendMessage(MsgQueue queue, Message message)
            throws IOException, MqException {

        String queueName = queue.getName();

        synchronized (lockOf(queueName)) {
            File queueDataFile = new File(getQueueDataPath(queueName));

            // 数据文件必须存在。统计文件缺失时，可以根据数据文件重建。
            if (!queueDataFile.isFile()) {
                throw new MqException(
                        "[DataFileManager] 队列数据文件不存在! queueName="
                                + queueName
                );
            }

            byte[] data = BinaryTool.toBinary(message);

            long start = queueDataFile.length() + Integer.BYTES;
            long end = start + data.length;

            try (DataOutputStream output = new DataOutputStream(
                    new FileOutputStream(queueDataFile, true))) {

                output.writeInt(data.length);
                output.write(data);
            }

            message.setOffsetS(start);
            message.setOffsetE(end);

            // 新增一条消息，总数和有效数都增加 1。
            updateStatsAfterMessageChange(queueName, 1, 1);
        }
    }
    //删除某一个消息
    public void deleteMessage(MsgQueue queue, Message message)
            throws IOException, ClassNotFoundException, MqException {

        String queueName = queue.getName();

        synchronized (lockOf(queueName)) {
            Long start = message.getOffsetS();
            Long end = message.getOffsetE();

            if (start == null || end == null
                    || start < Integer.BYTES || end <= start) {
                throw new MqException(
                        "[DataFileManager] 消息偏移非法! queueName=" + queueName
                );
            }

            File dataFile = new File(getQueueDataPath(queueName));
            if (!dataFile.isFile()) {
                throw new IOException(
                        "[DataFileManager] 队列文件不存在! queueName=" + queueName
                );
            }

            long length = end - start;

            try (RandomAccessFile file = new RandomAccessFile(dataFile, "rw")) {
                if (length > MAX_MSG_BYTES || end > file.length()) {
                    throw new MqException(
                            "[DataFileManager] 消息范围非法! queueName=" + queueName
                    );
                }

                // 消息前面的 4 个字节存储序列化数据的长度。
                file.seek(start - Integer.BYTES);

                if (file.readInt() != length) {
                    throw new MqException(
                            "[DataFileManager] 消息长度与偏移不符! queueName="
                                    + queueName
                    );
                }

                byte[] record = new byte[(int) length];
                file.readFully(record);

                Message stored = (Message) BinaryTool.from(record);
                String expectedId = message.getBasicProperties().getId();

                // 必须先核对 ID，才能修改这条磁盘记录。
                if (expectedId == null
                        || stored.getBasicProperties() == null
                        || !expectedId.equals(stored.getBasicProperties().getId())) {
                    throw new MqException(
                            "[DataFileManager] 磁盘消息 ID 不匹配! queueName="
                                    + queueName
                    );
                }

                // 同一条消息已经删除，重试时直接成功。
                if (stored.getIsValid() == 0x0) {
                    return;
                }

                if (stored.getIsValid() != 0x1) {
                    throw new MqException(
                            "[DataFileManager] 消息有效标识非法! messageId="
                                    + expectedId
                    );
                }

                stored.setIsValid((byte) 0x0);
                byte[] rewritten = BinaryTool.toBinary(stored);

                if (rewritten.length != length) {
                    throw new MqException(
                            "[DataFileManager] 序列化长度变化! messageId="
                                    + expectedId
                    );
                }

                file.seek(start);
                file.write(rewritten);
            }

            // 逻辑删除不改变总记录数，只让有效消息数减少 1。
            updateStatsAfterMessageChange(queueName, 0, -1);
        }
    }
    private void updateStatsAfterMessageChange(
            String queueName,
            int totalDelta,
            int validDelta) {

        // 统计可信时，直接根据本次操作调整数量。
        if (!queuesNeedingStatsRebuild.contains(queueName)) {
            try {
                Sta sta = (Sta) readSta(queueName);

                sta.totalCount += totalDelta;
                sta.validCount += validDelta;

                // 发现统计异常，也转入重新扫描。
                if (sta.totalCount < 0
                        || sta.validCount < 0
                        || sta.validCount > sta.totalCount) {
                    throw new IOException(
                            "[DataFileManager] 统计数量非法! queueName="
                                    + queueName
                    );
                }

                writeSta(queueName, sta);
                return;
            } catch (IOException e) {
                queuesNeedingStatsRebuild.add(queueName);
            }
        }

        // 数据文件已经包含本次写入或删除的结果。
        // 扫描得到的就是最新数量，不需要再加 totalDelta、validDelta。
        try {
            Sta rebuilt = new Sta(0, 0);

            getAllMessages(queueName, rebuilt);
            writeSta(queueName, rebuilt);

            // 只有重建并写入成功，才能清除标记。
            queuesNeedingStatsRebuild.remove(queueName);
        } catch (IOException | ClassNotFoundException | MqException e) {
            queuesNeedingStatsRebuild.add(queueName);

            System.err.println(
                    "[DataFileManager] 统计重建失败，保留重建标记! queueName="
                            + queueName
            );
            e.printStackTrace();
        }
    }

    //返回所有有效的消息
    public LinkedList<Message> getAllMessages(String queueName)
            throws IOException, MqException, ClassNotFoundException {

        synchronized (lockOf(queueName)) {
            Sta rebuilt = new Sta(0, 0);

            // 读取有效消息的同时，重新统计全部记录。
            LinkedList<Message> messages =
                    getAllMessages(queueName, rebuilt);

            try {
                writeSta(queueName, rebuilt);
                queuesNeedingStatsRebuild.remove(queueName);
            } catch (IOException e) {
                queuesNeedingStatsRebuild.add(queueName);

                System.err.println(
                        "[DataFileManager] 消息读取成功，统计写入失败! queueName="
                                + queueName
                );
                e.printStackTrace();
            }

            // 统计写入失败时，已经读出的消息仍然可以恢复。
            return messages;
        }
    }
    private LinkedList<Message> getAllMessages(String queueName, Sta stats)
            throws IOException, MqException, ClassNotFoundException {
        LinkedList<Message> ret=new LinkedList<>();
        long index=0;
        long fileLen = new File(getQueueDataPath(queueName)).length();
        try(InputStream inputStream=new FileInputStream(getQueueDataPath(queueName))){
            try(DataInputStream dataInputStream=new DataInputStream(inputStream)){
                while (true){
                    int length;
                    try {
                        length = dataInputStream.readInt();            // EOF 在这里 = 正常读完
                    } catch (EOFException e) {
                        // 读到的位置必须正好是文件尾, 否则尾部有半截记录
                        if (index != fileLen) {
                            throw new MqException("[DataFileManager] 文件尾部残留 "
                                    + (fileLen - index) + " 字节: " + queueName);
                        }
                        System.out.println("[DataFileManager] 加载全部消息完毕!");
                        break;
                    }
                    if (length <= 0 || length > MAX_MSG_BYTES) {
                        throw new MqException("[DataFileManager] 消息长度非法: " + length + ", queue=" + queueName);
                    }
                    byte[] message = new byte[length];
                    try {
                        dataInputStream.readFully(message);            // EOF 在这里 = 文件损坏
                    } catch (EOFException e) {
                        throw new MqException("[DataFileManager] 消息文件损坏, 尾部记录不完整: " + queueName);
                    }
                    //由于是在程序开始时调用 要把offset等属性重新加回内存对象中
                    Message tem=(Message) (BinaryTool.from(message));
                    if (stats != null) {
                        stats.totalCount++;

                        if (tem.getIsValid() == 0x1) {
                            stats.validCount++;
                        }
                    }
                    if(tem.getIsValid()!=((byte) 0x01)){
                        index=index+4+length;
                        continue;
                    }
                    tem.setOffsetS(index+4);
                    tem.setOffsetE(index+4+length);
                    index=index+4+length;
                    ret.addLast(tem);
                }
            }
            return ret;
        }
    }
    //判断是否要进行垃圾回收
    public boolean checkGc(String queueName) throws IOException {
        Sta sta=(Sta)readSta(queueName);
        // 有效消息数量小于一半时触发回收机制
        if(sta.totalCount>=2000 && (sta.validCount)/(0.0+ sta.totalCount)<0.5){
            return true;
        }
        return false;
    }
    //获取临时文件的名字
    public String getNewQueueDataPath(String queueName){
        return getQueueDir(queueName)+"/" +
                "data_new.txt";
    }
    //进行垃圾回收(gc)
    public void gc(MsgQueue queue) throws MqException, IOException, ClassNotFoundException {
        synchronized(lockOf(queue.getName())) {
            long startTime=System.currentTimeMillis();
            // 新的data文件
            File newDataFile=new File(getNewQueueDataPath(queue.getName()));
             //本来不应该存在 存在说明出错
            if(newDataFile.exists()){
                throw new MqException("[DataFileManager] :垃圾回收失败,上次回收被中断....");
            }
            //创建新文件
            newDataFile.createNewFile();
            LinkedList<Message> messages=getAllMessages(queue.getName());
            try(OutputStream outputStream =new FileOutputStream(newDataFile)){
                try(DataOutputStream dataOutputStream=new DataOutputStream(outputStream)){
                    for(Message tem:messages){
                        byte[] message=BinaryTool.toBinary(tem);
                        dataOutputStream.writeInt(message.length);
                        dataOutputStream.write(message);
                    }
                }
            }
            //完成文件复制,重命名新文件
            File dataFile=new File(getQueueDataPath(queue.getName()));
            dataFile.delete();
            newDataFile.renameTo(dataFile);
            //更新sta文件
            Sta sta=(Sta)readSta(queue.getName());
            sta.validCount=messages.size();sta.totalCount=messages.size();
            writeSta(queue.getName(),sta);
            long endTime=System.currentTimeMillis();
            System.out.println("[DataFileManager] :gc进行完成,共耗时"+(endTime-startTime)+"ms");
        }
    }
}
