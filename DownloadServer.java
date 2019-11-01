package homework.homewok10;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class DownloadServer{
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(8888);

        while (true) {
            Socket accept = serverSocket.accept();//等待连接 有反应就是连接上
            new Thread(() -> {
                String hostAddress = accept.getInetAddress().getHostAddress();
                String name = Thread.currentThread().getName();
                try {
                    OutputStream os = accept.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);

                    BufferedWriter bw = new BufferedWriter(osw);

                    DateFormat instance = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String format = instance.format(new Date());
                    System.out.println(hostAddress + " 登录 " + format + " 线程: " + name);
                    bw.write("欢迎登录 " + format);
                    bw.newLine();
                    bw.flush();

                    InputStream is = accept.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    int len = 0;
                    int newLen = 0;
                    byte[] allArray = new byte[1024 * 100];//受限于这里数组大小
                    //len = bis.read(allArray);
                    System.out.println("len = " + len);
                    while ((len = bis.read(allArray)) != -1) {
                        newLen = len;
                    }
                    //客户端要shutdownoutpu 没结束符
                    System.out.println("Arrays.toString(allArray) = " + Arrays.toString(allArray));
                    byte[] newArray = new byte[newLen];
                    System.arraycopy(allArray, 0, newArray, 0, newLen);
                    char temp = (char) newArray[newArray.length - 1];
                    int num = Integer.parseInt(String.valueOf(temp));
                    System.out.println("num = " + num);
                    String type = new String(newArray, newArray.length - num - 1, num);
                    System.out.println("type = " + type);
                    byte[] outArray = new byte[newLen - num - 1];
                    System.out.println("outArray.length = " + outArray.length);
                    System.arraycopy(newArray, 0, outArray, 0, outArray.length);
                    System.out.println("Arrays.toString(outArray) = " + Arrays.toString(outArray));
                    FileOutputStream fos = new FileOutputStream(System.currentTimeMillis() + "." + type);
                    fos.write(outArray);
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

        }

    }
}
