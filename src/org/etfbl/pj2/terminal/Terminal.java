package org.etfbl.pj2.terminal;

import org.etfbl.pj2.vozilo.Vozilo;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Terminal /*extends Thread*/
{
    protected boolean zaKamione;
    protected boolean radi;
    protected boolean slobodan;
    protected Vozilo vozilo;
    public int id;

    public static Handler handler;
    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "Terminal.log");
            Logger.getLogger(Terminal.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Terminal(boolean zaKamione, boolean radi, int id)
    {
        this.zaKamione = zaKamione;
        this.radi = radi;
        this.slobodan = true;
        this.id = id;
    }

    public boolean isZaKamione() {
        return zaKamione;
    }

    public void setZaKamione(boolean zaKamione) {
        this.zaKamione = zaKamione;
    }

    public boolean isRadi() {
        return radi;
    }

    public void setRadi(boolean radi) {
        this.radi = radi;
    }

    public synchronized boolean isSlobodan() {
        return slobodan;
    }

    public synchronized void setSlobodan(boolean slobodan) {
        this.slobodan = slobodan;
    }

    public synchronized Vozilo getVozilo()
    {
        return vozilo;
    }

    public synchronized void setVozilo(Vozilo vozilo) {
        this.vozilo = vozilo;
    }
}
