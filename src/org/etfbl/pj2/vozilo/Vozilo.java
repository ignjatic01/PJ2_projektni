package org.etfbl.pj2.vozilo;

import org.etfbl.pj2.putnik.Putnik;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public abstract class Vozilo extends Thread
{
    protected int maxBrojPutnika;
    protected int brojPutnika;
    protected List<Putnik> putnici;
    protected static int brojVozila = 0;
    protected int idVozilo;
    protected long stopTime;

    public static Handler handler;
    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "Vozilo.log");
            Logger.getLogger(Vozilo.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Vozilo(int maxBrojPutnika)
    {
        Random random = new Random();
        this.maxBrojPutnika = maxBrojPutnika;
        brojPutnika = random.nextInt(maxBrojPutnika) + 1;
        putnici = new ArrayList<>();
        this.idVozilo = ++brojVozila;
    }

    public int getMaxBrojPutnika() {
        return maxBrojPutnika;
    }

    public void setMaxBrojPutnika(int maxBrojPutnika) {
        this.maxBrojPutnika = maxBrojPutnika;
    }

    public int getBrojPutnika() {
        return brojPutnika;
    }

    public void setBrojPutnika(int brojPutnika) {
        this.brojPutnika = brojPutnika;
    }

    public List<Putnik> getPutnici() {
        return putnici;
    }

    public void setPutnici(List<Putnik> putnici) {
        this.putnici = putnici;
    }

    public static int getBrojVozila() {
        return brojVozila;
    }

    public static void setBrojVozila(int brojVozila) {
        Vozilo.brojVozila = brojVozila;
    }

    public int getIdVozilo() {
        return idVozilo;
    }

    public void setIdVozilo(int idVozilo) {
        this.idVozilo = idVozilo;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    protected boolean policijskaLogika()
    {
        return true;
    }

    protected boolean carinskaLogika()
    {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vozilo vozilo = (Vozilo) o;

        return idVozilo == vozilo.idVozilo;
    }

    @Override
    public int hashCode() {
        return idVozilo;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName() + " sa " + this.getBrojPutnika() + " putnika.");
        return sb.toString();
    }

}
