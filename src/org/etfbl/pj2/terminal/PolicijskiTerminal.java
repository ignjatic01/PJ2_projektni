package org.etfbl.pj2.terminal;

import org.etfbl.pj2.putnik.Putnik;
import org.etfbl.pj2.simulacija.Simulacija;
import org.etfbl.pj2.vozilo.Vozilo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PolicijskiTerminal extends Terminal
{
    private static List<Putnik> kaznjeniPutnici = new ArrayList<>();

    public static Handler handler;
    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "PolicijskiTerminal.log");
            Logger.getLogger(PolicijskiTerminal.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public PolicijskiTerminal(boolean zaKamione, boolean radi, int id)
    {
        super(zaKamione, radi, id);
    }

    public static synchronized void addKaznjeniPutnik(Putnik p)
    {
        kaznjeniPutnici.add(p);
    }

    public static void serijalizujKaznjene(Long time, String path)
    {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path + time + ".ser")));
            oos.writeObject(kaznjeniPutnici);
            oos.close();
        } catch (IOException e) {
            Logger.getLogger(PolicijskiTerminal.class.getName()).log(Level.WARNING, "Izuzetak pri pokusaju serijalizacije kaznjenih putnika");
        }
    }

    public static List<Putnik> ucitajKaznjenePutnike(Long time, String path)
    {
        List<Putnik> vrijednost = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path + time + ".ser")));
            vrijednost = (List<Putnik>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(PolicijskiTerminal.class.getName()).log(Level.WARNING, "Izuzetak pri pokusaju deserijalizacije kaznjenih putnika");
        }
        return vrijednost;
    }
}
