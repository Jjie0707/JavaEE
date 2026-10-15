package jjie.msgqueue.common;

import java.io.*;

public class BinaryTool {
    public static byte[] toBinary(Object object) throws IOException {
        try(ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream()){
            try(ObjectOutputStream objectOutputStream=
                        new ObjectOutputStream(byteArrayOutputStream)){
                objectOutputStream.writeObject(object);
            }
            return  byteArrayOutputStream.toByteArray();
        }
    }
    public static Object from(byte[] bytes) throws IOException, ClassNotFoundException {
        Object object=null;
        try(ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes)){
            try(ObjectInputStream objectInputStream=
                    new ObjectInputStream(byteArrayInputStream)){
                object=objectInputStream.readObject();
            }
            return object;
        }
    }
}
