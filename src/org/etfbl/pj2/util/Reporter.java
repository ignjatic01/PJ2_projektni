package org.etfbl.pj2.util;

import org.etfbl.pj2.putnik.Putnik;
import org.etfbl.pj2.terminal.PolicijskiTerminal;
import org.etfbl.pj2.vozilo.Vozilo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reporter {
    private static long currTime = System.currentTimeMillis();
    private static final String PUTNICI_PATH = "evidencije" + File.separator + "putnici" + File.separator;
    private static final String VOZILA_PATH = "evidencije" + File.separator + "vozila" + File.separator;

    public static Handler handler;
    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "Reporter.log");
            Logger.getLogger(Reporter.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void serijalizacijaKaznjenih()
    {
        PolicijskiTerminal.serijalizujKaznjene(currTime, PUTNICI_PATH);
    }

    public static List<Putnik> deserijalizacijaKaznjenih()
    {
        return PolicijskiTerminal.ucitajKaznjenePutnike(currTime, PUTNICI_PATH);
    }

    public static void upisNeispravnogPutnika(String razlog, Vozilo vozilo, Putnik putnik)
    {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(new File(PUTNICI_PATH + "neispravniPutnici.txt"), true));
            pw.println(putnik.getClass().getSimpleName() + "#" + vozilo.getIdVozilo() + "#" + putnik.getIme() + "#" + putnik.getPrezime() + "#" + razlog);
            pw.close();
        } catch (IOException e) {
            Logger.getLogger(Reporter.class.getName()).log(Level.WARNING, "Greska prilikom upisa putnika u tekstualni fajl: neispravniPutnici.txt");
        }
    }

    public static void upisiNeispravnogVozila(String razlog, Vozilo vozilo)
    {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(new File(VOZILA_PATH + "vozila.txt"), true));
            pw.println(vozilo.getClass().getSimpleName() + "#" + vozilo.getIdVozilo() + "#" + razlog);
            pw.close();
        } catch (IOException e) {
            Logger.getLogger(Reporter.class.getName()).log(Level.WARNING, "Greska prilikom upisa vozila u tekstualni fajl: vozila.txt");
        }
    }

}
