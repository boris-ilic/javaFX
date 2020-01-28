package com;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UDPklijent {
    public UDPklijent() {


        try {
            InetAddress ipAdresaServera = InetAddress.getByName("127.0.0.1");
            //prosledjeni string treba da odgovara vrednosti ip adrese
            int portServera = 9001;
            DatagramSocket datagramSocketKlijent = new DatagramSocket();
            DatagramPacket paketZahtev; //kreiranje bafera za skladistenje sadrzaja paketa.
            byte[] bafer = new byte[1000];
            DatagramPacket odgovor = new DatagramPacket(bafer, bafer.length);
            Scanner unos = new Scanner(System.in);
            String poruka = "";
            String odgovorServera = ""; //slanje poruka i primanje odgovora od servera sve dok korisnik ne unese sadrzaj poruke "kraj"
            while (!poruka.equals("kraj")) {
                System.out.print("Unesite poruku za server(\"kraj\" za prekid):");
                poruka = unos.nextLine();
                System.out.println("Klijent salje poruku:" + poruka); //pretvaranje poruke u niz bajtova
                byte[] porukaB = poruka.getBytes();
                paketZahtev = new DatagramPacket(porukaB, porukaB.length, ipAdresaServera, portServera); //slanje paketa
                datagramSocketKlijent.send(paketZahtev);
                System.out.println("Ceka se odgovor servera..."); //primanje paketa od servera
                datagramSocketKlijent.receive(odgovor); //pretvaranje niza bajtova u string
                odgovorServera = new String(odgovor.getData());
                System.out.println("Odgovor servera:" + odgovorServera);
            }
            System.out.println("Klijent prestaje sa radom.");
        } catch (UnknownHostException e) {
            System.out.println("Desila se greska prilikom kreiranja ip adrese.");
        } catch (
                IOException e) {
            System.out.println("Doslo je do greske prilikom slanja ili primanja paketa.");
        }
    }
}
