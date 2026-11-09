package jjie.msgqueue.server.mapper.Manager;

import jjie.msgqueue.common.BinaryTool;
import jjie.msgqueue.common.MqException;
import jjie.msgqueue.server.core.Message;
import jjie.msgqueue.server.core.MsgQueue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class DataFileManager {
    private static final int MAX_MSG_BYTES = 64 * 1024 * 1024;
    private final ConcurrentHashMap<String, Object> queueLocks = new ConcurrentHashMap<>();

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
    public void writeSta(String queueName,Sta sta) throws IOException {
        File tmp = new File(getQueueStaPath(queueName) + ".tmp");
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(tmp))) {
            pw.write(sta.totalCount + "\t" + sta.validCount);
            pw.flush();
        }
        Files.move(tmp.toPath(), Paths.get(getQueueStaPath(queueName)),
                StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
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
    public void destroyQueueFiles(String queueName) throws IOException{
        File queueDataPath=new File(getQueueDataPath(queueName));
        boolean ret1=queueDataPath.delete();
        File queueStaPath=new File(getQueueStaPath(queueName));
        boolean ret2=queueStaPath.delete();
        File queueFileDir=new File(getQueueDir(queueName));
        boolean ret3=queueFileDir.delete();

        if(!ret1 || !ret2 || !ret3) throw new IOException("删除文件失败");
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
    public void sendMessage(MsgQueue queue, Message message) throws IOException, MqException {
        if(!checkFilesExists(queue.getName())){
            throw new MqException("[DataFileManager] 队列对应的文件不存在! queueName=" + queue.getName());
        }
        synchronized (lockOf(queue.getName())) {
            //先序列化这个message
            File queueDataFile=new File(getQueueDataPath(queue.getName()));
            byte[] msg= BinaryTool.toBinary(message);
            long s=queueDataFile.length()+4,
                 e=s+msg.length;
            message.setOffsetS(s);message.setOffsetE(e);
            //追加写写到文件尾
            try(OutputStream outputStream=new FileOutputStream(queueDataFile,true)){
                try(DataOutputStream dataOutputStream=new DataOutputStream(outputStream)){
                    dataOutputStream.writeInt(msg.length);//专门写四个字节的int
                    dataOutputStream.write(msg);
                }
            }
            //更新sta.txt文件
            Sta sta=(Sta)readSta(queue.getName());
            sta.validCount++;
            sta.totalCount++;
            writeSta(queue.getName(),sta);
        }
    }
    //删除某一个消息
    public void deleteMessage(MsgQueue queue,Message message) throws IOException, ClassNotFoundException, MqException {
        synchronized (lockOf(queue.getName())) {
            //采取随机读写的方式
            try(RandomAccessFile randomAccessFile=new RandomAccessFile(
                    getQueueDataPath(queue.getName()),"rw")){
                long offsetStart=message.getOffsetS(),offsetEnd=message.getOffsetE();
                byte[] msg=new byte[(int)(offsetEnd-offsetStart)];
                //移动文件光标
                randomAccessFile.seek(offsetStart);
                randomAccessFile.read(msg);
                Message tem= (Message)BinaryTool.from(msg);

                if (tem.getIsValid() != 0x1) {
                    return;                      // 已删过, 幂等返回, 不动 sta
                }
                if (msg.length != (offsetEnd - offsetStart)) {
                    throw new MqException("[DataFileManager] 序列化长度变化, 拒绝回写: " + queue.getName());
                }

                tem.setIsValid((byte) 0x0);
                msg=BinaryTool.toBinary(tem);
                randomAccessFile.seek(offsetStart);
                randomAccessFile.write(msg);
                //更新sta.txt文件
                Sta sta=(Sta)readSta(queue.getName());
                if(sta.validCount>0) sta.validCount--;
                writeSta(queue.getName(),sta);
            }
        }
    }
    //返回所有有效的消息
    public LinkedList<Message> getAllMessages(String queueName) throws IOException,MqException,ClassNotFoundException {
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
                    byte[] message=new byte[length];
                    if (length == 0 || length > MAX_MSG_BYTES)
                        throw new MqException("[DataFileManager] 消息长度非法: " + length + ", queue=" + queueName);

                    try {
                        dataInputStream.readFully(message);            // EOF 在这里 = 文件损坏
                    } catch (EOFException e) {
                        throw new MqException("[DataFileManager] 消息文件损坏, 尾部记录不完整: " + queueName);
                    }
                    //由于是在程序开始时调用 要把offset等属性重新加回内存对象中
                    Message tem=(Message) (BinaryTool.from(message));
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
