package org.etfbl.pj2.util;

import org.etfbl.pj2.gui.Main;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Stoperica extends Thread
{
    private long pocetakVremena;
    private long trajanjePauze;
    private static boolean aktivna;

    public static Handler handler;
    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "Stoperica.log");
            Logger.getLogger(Stoperica.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Stoperica()
    {
        this.setDaemon(true);
        this.trajanjePauze = 0;
        this.aktivna = true;
    }

    public static synchronized void setAktivna(boolean akt) {
        aktivna = akt;
    }

    @Override
    public void run()
    {
        this.pocetakVremena = System.currentTimeMillis();
        while (aktivna)
        {
            try {
                sleep(1000);
            }
            catch (InterruptedException e)
            {
                Logger.getLogger(Stoperica.class.getName()).log(Level.WARNING, "Interrupted izuzetak na Stoperica niti prilikom mjerenja vremena");
            }
            if(!Main.isPauziran())
            {
                long prolaznoVrijeme = System.currentTimeMillis() - pocetakVremena - trajanjePauze;
                final String vrijeme = String.format("Vrijeme: %d s", prolaznoVrijeme / 1000);
                Main.setVrijeme(vrijeme);
            }else
            {
                trajanjePauze += 1000;
            }
        }
    }
}
