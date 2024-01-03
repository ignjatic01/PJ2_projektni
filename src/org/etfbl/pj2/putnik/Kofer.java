package org.etfbl.pj2.putnik;

import java.io.Serializable;
import java.util.Random;

public class Kofer implements Serializable
{
    private boolean nedozvoljeneStvari;

    public Kofer()
    {
        Random rand = new Random();
        nedozvoljeneStvari = (rand.nextDouble() < 0.10);
    }

    public boolean isNedozvoljeneStvari()
    {
        return nedozvoljeneStvari;
    }

    public void setNedozvoljeneStvari(boolean nedozvoljeneStvari)
    {
        this.nedozvoljeneStvari = nedozvoljeneStvari;
    }
}
