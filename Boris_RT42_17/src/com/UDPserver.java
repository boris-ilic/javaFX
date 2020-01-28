package com;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPserver {

    public static void main(String[] args) {


        try {
            DatagramSocket datagramSoketServer = new DatagramSocket(9001);
            byte[] bafer = new byte[1000];
            DatagramPacket paketZahtev = new DatagramPacket(bafer, bafer.length);
            DatagramPacket paketOdgovor;
            System.out.println("Server pokrenut, ceka zahtev klijenta...");
            String odgovor = "";
            String porukaK = "";

            while (!porukaK.equals("kraj")) { //prijem poruke od klijenta
                 datagramSoketServer.receive(paketZahtev);
                 //pretvaranje niza bajtova u string. Metoda getData() vraca sadrzaj poruke iz paketa.

                porukaK = new String(paketZahtev.getData());
                porukaK = porukaK.trim();

                System.out.println("Server primio poruku od klijenta (port=" + paketZahtev.getPort() + ")" + porukaK);

                odgovor =new StringBuilder(porukaK).reverse().toString();


                byte[] odgovorB = odgovor.getBytes();
                //kreiranje odgovora. Ip adresa i port se uzimaju iz pristiglog paketa.
                paketOdgovor = new DatagramPacket(odgovorB, odgovor.length(), paketZahtev.getAddress(), paketZahtev.getPort());
                System.out.println("Server salje odgovor :" + odgovor);
                datagramSoketServer.send(paketOdgovor); //ciscenje bafera paketZahtev.setData(new byte[1000]); //brisanje praznina
                 }
            System.out.println("Server zavrsio sa radom...");

        } catch (IOException e) { System.out.println("Greska prilikom slanja ili primanja paketa."); }


        }
}
