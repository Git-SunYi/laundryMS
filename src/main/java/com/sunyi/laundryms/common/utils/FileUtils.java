package com.sunyi.laundryms.common.result.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class FileUtils {
    //写入
    public static void WriterText(String fileContent,String writerPathName) throws IOException {
        FileWriter fw = null;
        try {
            // 如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File(writerPathName);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(new String(fileContent.getBytes(), "utf-8")); //解决乱码
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //读取
    public static String readText(String readPathName) throws IOException {
                                        //pathName: 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        String fileContent = "";
        String second="";
        File f = new File(readPathName);
        if (f.isFile() && f.exists()) {
            InputStreamReader read = new InputStreamReader(new FileInputStream(f), "UTF-8");
            BufferedReader reader = new BufferedReader(read);
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent += line+"\n";
            }
            read.close();
        }
        return fileContent;
    }

    /**
     * 保存文件
     *
     * @throws Exception
     */
    public static void saveFile(InputStream inStream,String savePathName) throws Exception {
                                //inStream通过输入流获取图片数据
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);
        //new一个文件对象用来保存图片
        File file = new File(savePathName);
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(file);
        //写入数据
        outStream.write(data);
        //关闭输出流
        outStream.close();
        log.info("保存文件成功");

    }

    /**
     * 得到图片的二进制数据，以二进制封装得到数据，具有通用性
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    public static void main(String[] args) {
        String fileContent="123456";
        try {
            WriterText(fileContent,"D:\\Idea\\laundryMS\\src\\main\\resources\\static\\img\\dd.txt");
            System.out.println(readText("D:\\Idea\\laundryMS\\src\\main\\resources\\static\\img\\dd.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
