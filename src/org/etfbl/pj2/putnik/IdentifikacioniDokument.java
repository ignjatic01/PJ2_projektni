package org.etfbl.pj2.putnik;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public class IdentifikacioniDokument implements Serializable
{
    private String serijskiBroj;
    private boolean ispravan;

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
}
