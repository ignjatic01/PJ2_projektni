package org.etfbl.pj2.putnik;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Kofer implements Serializable
{
    private boolean nedozvoljeneStvari;
    public static Handler handler;

    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "Kofer.log");
            Logger.getLogger(Kofer.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

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
