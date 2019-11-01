package homework.homewok10;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
                    byte[] allArray = new byte[1024];
                    System.out.println("len = " + len);
                    int randomName = new Random().nextInt(10000);
                    FileOutputStream fos = new FileOutputStream(randomName + ".change");
                    while ((len = bis.read(allArray)) != -1) {
                        fos.write(allArray, 0, len);//测试git合拼
                    }
                    fos.close();
                    BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(randomName + ".change"));
                    byte[] bytes10 = new byte[10];
                    bis2.read(bytes10);
                    String temp = new String(bytes10).split("\\.")[0];
                    System.out.println(temp);
                    bis2.close();
                    BufferedInputStream bis3 = new BufferedInputStream(new FileInputStream(randomName + ".change"));
                    FileOutputStream fos2 = new FileOutputStream(randomName + "." + temp);
                    BufferedOutputStream bos2 = new BufferedOutputStream(fos2);
                    int len2 = 0;
                    byte[] outputArray = new byte[1024 * 8];
                    bis3.skip(4);
                    while ((len2 = bis3.read(outputArray)) != -1) {
                        bos2.write(outputArray, 0, len2);
                    }
                    System.out.println("写入了 " + randomName + "." + temp);
                    File file3 = new File(randomName + "." + "change");
                    bis3.close();
                    bos2.close();
                    file3.delete();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

        }

    }
}
