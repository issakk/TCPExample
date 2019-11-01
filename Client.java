package homework.homewok10;

import java.io.*;
import java.net.Socket;

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
        File file = new File("a.txt123");
        String name = file.getName().split("\\.")[1];
        name = name + ".";
        int len = 0;
        byte[] byteArray = new byte[1024];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bos.write(name.getBytes());
        while ((len = bis.read(byteArray)) != -1) {
            bos.write(byteArray, 0, len);
        }
        bos.flush();
        ss.shutdownOutput();

    }
}
