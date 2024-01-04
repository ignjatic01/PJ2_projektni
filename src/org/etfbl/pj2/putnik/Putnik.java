package org.etfbl.pj2.putnik;

import org.etfbl.pj2.vozilo.Vozilo;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Putnik implements Serializable
{
    private String ime;
    private String prezime;
    private IdentifikacioniDokument id;
    private Kofer kofer;
    private boolean vozac;

    public static Handler handler;

    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "Putnik.log");
            Logger.getLogger(Putnik.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Putnik(String ime, String prezime, boolean vozac)
    {
        this.ime = ime;
        this.prezime = prezime;
        this.id = new IdentifikacioniDokument();
        this.kofer = null;
        this.vozac = vozac;
    }

    public Putnik()
    {
        super();
    }

    public Putnik(String ime, String prezime, Kofer kofer, boolean vozac)
    {
        this.ime = ime;
        this.prezime = prezime;
        this.id = new IdentifikacioniDokument();
        this.kofer = kofer;
        this.vozac = vozac;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public IdentifikacioniDokument getId() {
        return id;
    }

    public void setId(IdentifikacioniDokument id) {
        this.id = id;
    }

    public Kofer getKofer() {
        return kofer;
    }

    public void setKofer(Kofer kofer) {
        this.kofer = kofer;
    }

    public boolean isVozac() {
        return vozac;
    }

    public void setVozac(boolean vozac) {
        this.vozac = vozac;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Putnik putnik = (Putnik) o;

        if (!Objects.equals(ime, putnik.ime)) return false;
        return Objects.equals(prezime, putnik.prezime);
    }

    @Override
    public int hashCode() {
        int result = ime != null ? ime.hashCode() : 0;
        result = 31 * result + (prezime != null ? prezime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "Putnik: " + this.getIme() + " " + this.getPrezime() + ", Kofer " + ((this.getKofer() == null) ? "nema " : "ima ") +
                ", Vozac: " + (this.isVozac()? " da." : " ne.");
    }
}
