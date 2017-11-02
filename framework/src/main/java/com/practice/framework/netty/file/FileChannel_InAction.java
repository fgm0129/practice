package com.practice.framework.netty.file;

import io.netty.buffer.ByteBuf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by fgm on 2017/8/27.
 */
public class FileChannel_InAction {


    public static void main(String[] args) throws Exception {


        long start=System.currentTimeMillis();
        for(int i=0;i<100000;i++){
            readFIleOfBIO(); //882ms
            //readFileOfNIO();//1173ms
        }
        System.out.println("cost "+(System.currentTimeMillis()-start)+"ms");


    }


    public static void  readFileOfNIO() throws Exception{
        RandomAccessFile randomAccessFile=new RandomAccessFile(new File("/Users/fgm/Desktop/temp"),"rw");
        FileChannel fileChannel=randomAccessFile.getChannel();
        //read from ByteBuffer
        ByteBuffer buf = ByteBuffer.allocate(1024);
        int read=fileChannel.read(buf);
        //if(read>-1){
           // buf.flip();
           // Charset cs=Charset.defaultCharset();
            //String s=cs.decode(buf).toString();
            //System.out.println(s);
       // }

        //write into bytBuffer
//        fileChannel.position(fileChannel.size());
//        fileChannel.write(ByteBuffer.wrap("Hello World".getBytes()));

        fileChannel.close();
        randomAccessFile.close();
    }


    public static void readFIleOfBIO() throws Exception{
        FileInputStream fis=new FileInputStream("/Users/fgm/Desktop/temp");
        byte[] buf = new byte[1024];
        int read=fis.read(buf);
//        if(read!=-1){
//           String s=new String(buf,"UTF-8");
//        }
        fis.close();


    }


}
