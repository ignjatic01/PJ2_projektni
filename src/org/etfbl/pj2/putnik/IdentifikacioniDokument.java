package org.etfbl.pj2.putnik;

import org.etfbl.pj2.dokumentacija.CarinskaDokumentacija;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class IdentifikacioniDokument implements Serializable
{
    private String serijskiBroj;
    private boolean ispravan;

    public static Handler handler;

    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "IdentifikacioniDokument.log");
            Logger.getLogger(IdentifikacioniDokument.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public IdentifikacioniDokument()
    {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 8; i++)
        {
            sb.append((char) ('A' + rand.nextInt(26)));
        }
        serijskiBroj = sb.toString();
        ispravan = (rand.nextDouble() < 0.97);
    }

    public String getSerijskiBroj()
    {
        return serijskiBroj;
    }

    public void setSerijskiBroj(String serijskiBroj)
    {
        this.serijskiBroj = serijskiBroj;
    }

    public boolean isIspravan()
    {
        return ispravan;
    }

    public void setIspravan(boolean ispravan)
    {
        this.ispravan = ispravan;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdentifikacioniDokument that = (IdentifikacioniDokument) o;

        return Objects.equals(serijskiBroj, that.serijskiBroj);
    }

    @Override
    public int hashCode()
    {
        return serijskiBroj != null ? serijskiBroj.hashCode() : 0;
    }

    @Override
    public String toString()
    {
        return "IdentifikacioniDokument: " + serijskiBroj + " ispravan: " + ispravan;
    }
}
