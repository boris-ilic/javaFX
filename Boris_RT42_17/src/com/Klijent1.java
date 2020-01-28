package com;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Klijent1 {

    static boolean odgovor;
//    public static String imeFajla;

    public Klijent1(File fajl) {

                String imeFajla=fajl.getName();
//                ServerBroj1.velicinaFajla=(int)fajl.length();
                System.out.println("Prebacujem fajl: "+imeFajla);
            try {
                Socket sock=  new Socket("127.0.0.1", 12345);
                File myFile = new File(fajl.getPath());
                byte[] mybytearray = new byte[(int) myFile.length()];
                FileInputStream fis=new FileInputStream(myFile);
                BufferedInputStream bis= new BufferedInputStream(fis);

                DataInputStream dis = new DataInputStream(bis);
                dis.readFully(mybytearray, 0, mybytearray.length);
//                bis.read(mybytearray, 0, mybytearray.length);

                OutputStream os = sock.getOutputStream();

                //Sending file name and file size to the server
                DataOutputStream dos = new DataOutputStream(os);
                dos.writeUTF(myFile.getName());
                dos.writeLong(mybytearray.length);
                dos.write(mybytearray, 0, mybytearray.length);
                dos.flush();



                os.write(mybytearray, 0, mybytearray.length);

                os.flush();
                fis.close();
                os.close();
                sock.close();

                System.out.println("Mislim da sam poslao fajl");

            } catch (UnknownHostException e) {
                System.out.println("Desila se greska prilikom kreiranja ip adrese.");
                e.printStackTrace();
            } catch (
                    IOException e) {
                System.out.println("Doslo je do greske prilikom slanja ili primanja paketa.");
                e.printStackTrace();
            }

    }



}
