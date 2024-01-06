package org.etfbl.pj2.simulacija;

import javafx.scene.control.Button;
import org.etfbl.pj2.gui.Log;
import org.etfbl.pj2.gui.Main;
import org.etfbl.pj2.putnik.IdentifikacioniDokument;
import org.etfbl.pj2.putnik.Putnik;
import org.etfbl.pj2.terminal.CarinskiTerminal;
import org.etfbl.pj2.terminal.PolicijskiTerminal;
import org.etfbl.pj2.util.Initializator;
import org.etfbl.pj2.util.Reporter;
import org.etfbl.pj2.util.Stoperica;
import org.etfbl.pj2.util.Watcher;
import org.etfbl.pj2.vozilo.Autobus;
import org.etfbl.pj2.vozilo.Kamion;
import org.etfbl.pj2.vozilo.LicnoVozilo;
import org.etfbl.pj2.vozilo.Vozilo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulacija
{
    public static PolicijskiTerminal p1 = new PolicijskiTerminal(false, true, 1);
    public static PolicijskiTerminal p2 = new PolicijskiTerminal(false, true, 2);
    public static PolicijskiTerminal pk = new PolicijskiTerminal(true, true, 3);
    public static CarinskiTerminal c1 = new CarinskiTerminal(false, true, 4);
    public static CarinskiTerminal ck = new CarinskiTerminal(true, true, 5);
    public static LinkedList<Vozilo> vozila = new LinkedList<>();
    public static Integer startedKamions = 0;
    public static Integer startedOther = 0;
    public static Integer blockedPoliceTerminals = 0;

    public static Handler handler;

    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "Simulacija.log");
            Logger.getLogger(Simulacija.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static void main(String args[])
    {
        initializeVozila();
        List<Vozilo> vozilos = new ArrayList<>();
        initializeFiles();

        Main gui = new Main();
        Thread guiThread = new Thread(gui);
        guiThread.start();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long pocetak = System.currentTimeMillis();
//        Initializator.prvih5().stream().forEach(e -> System.out.println(e.getIdVozilo()));
        Stoperica stoperica = new Stoperica();
        stoperica.start();
        Initializator.prvih5();
        kretanjeVozila(vozilos);

        for(Vozilo v : vozilos)
        {
            try {
                v.join();
            } catch (InterruptedException e) {
                Logger.getLogger(Simulacija.class.getName()).log(Level.WARNING, "Greska kod poziva join() metode nad vozilima");
            }
        }

        Stoperica.setAktivna(false);
        System.out.println("KRAJ SIMULACIJE");
        Log.logMessage("KRAJ SIMULACIJE");
        Reporter.serijalizacijaKaznjenih();
        System.out.println(System.currentTimeMillis() - pocetak);
    }

    public static void kretanjeVozila(List<Vozilo> vozilos)
    {
        while (!vozila.isEmpty()) {
            if (vozila.peek() instanceof Kamion) {
                if (pk.isSlobodan() && startedKamions == 0 && pk.isRadi()) {
                    Vozilo v = vozila.poll();
//                    Initializator.prvih5().stream().forEach(e -> System.out.println(e.getIdVozilo()));
                    System.out.println("Izlazi vozilo: " + v.getIdVozilo());
                    Log.logMessage("Izlazi vozilo: " + v.getIdVozilo());
                    v.start();
                    Initializator.prvih5();
                    synchronized (startedKamions)
                    {
                        startedKamions++;
                    }
                    vozilos.add(v);
                } else {
                    // Ako terminal nije dostupan, ostavlja se vozilo u redu
                }
            } else if (vozila.peek() instanceof LicnoVozilo || vozila.peek() instanceof Autobus) {
                if ((p1.isSlobodan() || p2.isSlobodan()) && startedOther + blockedPoliceTerminals <= 1) {
                    Vozilo v = vozila.poll();
//                    Initializator.prvih5().stream().forEach(e -> System.out.println(e.getIdVozilo()));
                    System.out.println("Izlazi vozilo: " + v.getIdVozilo());
                    Log.logMessage("Izlazi vozilo: " + v.getIdVozilo());
                    v.start();
                    Initializator.prvih5();
                    synchronized (startedOther)
                    {
                        startedOther++;
                    }
                    vozilos.add(v);
                } else {
                    // Ako terminal nije dostupan, ostavlja se vozilo u redu
                }
            }
        }
    }

    public static void initializeVozila()
    {
        vozila.add(new Kamion());
        vozila.add(new Kamion());
        vozila.add(new Kamion());
        vozila.add(new Kamion());
        vozila.add(new LicnoVozilo());
        vozila.add(new Autobus());
        vozila.add(new Autobus());
        vozila.add(new LicnoVozilo());
        vozila.add(new LicnoVozilo());
        vozila.add(new Autobus());
        vozila.add(new Autobus());
        vozila.add(new Autobus());
        vozila.add(new LicnoVozilo());
        vozila.add(new LicnoVozilo());
        vozila.add(new Kamion());
        vozila.add(new LicnoVozilo());
        vozila.add(new LicnoVozilo());
        vozila.add(new LicnoVozilo());
        vozila.add(new Kamion());
        vozila.add(new LicnoVozilo());
        vozila.add(new Autobus());
        vozila.add(new Kamion());
        vozila.add(new LicnoVozilo());
        vozila.add(new LicnoVozilo());
        vozila.add(new LicnoVozilo());
        vozila.add(new LicnoVozilo());
        vozila.add(new LicnoVozilo());
    }

    public static void initializeFiles()
    {
        Initializator.obrisiSvePutnike();
        Initializator.obrisiZapiseVozila();
        Watcher.ucitajTerminale();
        System.out.println(p1.isRadi() + " " + p2.isRadi() + " " + pk.isRadi() + " " + c1.isRadi() + " " + ck.isRadi());
        Watcher watcher = new Watcher();
        watcher.start();
    }
}
