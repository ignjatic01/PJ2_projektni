package org.etfbl.pj2.terminal;

import org.etfbl.pj2.putnik.Putnik;
import org.etfbl.pj2.simulacija.Simulacija;
import org.etfbl.pj2.vozilo.Vozilo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PolicijskiTerminal extends Terminal
{
    private static List<Putnik> kaznjeniPutnici = new ArrayList<>();
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
        return vrijednost;
    }
}
