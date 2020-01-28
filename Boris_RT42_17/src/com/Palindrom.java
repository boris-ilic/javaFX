package com;

public class Palindrom {

    private String rec;


    public Palindrom(String rec) {
        this.rec = rec;
    }


    public boolean proveriPalindrom(){
        boolean odgovor=false;
        StringBuilder str=new StringBuilder(this.rec);

        str.reverse();

        String rec2=str.toString();

        if (this.rec.equals(rec2)){
            odgovor=true;
        }

        return odgovor;
    }
}
