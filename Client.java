package homework.homewok10;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Client{
    public static void main(String[] args) throws IOException{
        Socket ss = new Socket("127.0.0.1", 8888);
        InputStream is = ss.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader br = new BufferedReader(isr);

        System.out.println(br.readLine());
/////////////////////////////////////////////////////////////////////
        OutputStream os = ss.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        File file = new File("1.png");
        String name = file.getName().split("\\.")[1];
        name = name + "" + name.length();
        byte[] nameArray = name.getBytes();
        System.out.println("name = " + name);
        int len = 0;
        int newLen = 0;
        byte[] bytearray = new byte[1024 * 100];//受限于这里数组大小
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        while ((len = bis.read(bytearray)) != -1) {//这里要循环全部的数据
                                                    // long skip​(long n) 跳过并从输入流中丢弃 n个字节的数据。
                                                    //新思路 将 type+内容全部上传到服务器的.td!文件中,解析此文件 然后本地复制skip前面几个
            newLen = len;
        }
        byte[] newArray = new byte[newLen + nameArray.length];
        System.out.println("newArray.length = " + newArray.length);
        System.out.println("nameArray = " + nameArray.length);
        System.arraycopy(bytearray, 0, newArray, 0, newLen);
        System.out.println("Arrays.toString(newArray) = " + Arrays.toString(newArray));
        System.arraycopy(nameArray, 0, newArray, newArray.length - nameArray.length, nameArray.length);
        System.out.println("Arrays.toString(newArray) = " + Arrays.toString(newArray));

        bos.write(newArray);
        bos.flush();
        ss.shutdownOutput();

    }
}
