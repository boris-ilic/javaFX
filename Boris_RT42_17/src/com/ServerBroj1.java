package com;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBroj1 {



    public static void main(String[] args) {
        int bytesRead; int current = 0;

        try {
            ServerSocket servsock=new ServerSocket(12345);

            while(true) {

                System.out.println("Cekam fajl...");
                Socket sock = servsock.accept();
                System.out.println("..........");



                byte[] mybytearray = new byte[1000000];
                InputStream is = sock.getInputStream();

                DataInputStream clientData = new DataInputStream(is);
                String imeFajla = clientData.readUTF();


                System.out.println("Stigao je falj: "+imeFajla);


                FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "\\src\\com\\server\\" + imeFajla);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bytesRead = is.read(mybytearray, 0, mybytearray.length);
                current = bytesRead;
                do {
                    bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
                    if (bytesRead > 0)
                        current += bytesRead;
                }
                while (bytesRead > -1);
                bos.write(mybytearray, 0, current);
                bos.flush();
                fos.close();
                bos.close();
                sock.close();
                System.out.println("Fajl je prebacen");
//                imeFajla+="1";
            }



        } catch (IOException e) { System.out.println("Greska prilikom slanja ili primanja paketa.");
            e.printStackTrace();
        }


        }
}
