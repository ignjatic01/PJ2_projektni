package org.etfbl.pj2.simulacija;

import org.etfbl.pj2.putnik.Putnik;
import org.etfbl.pj2.terminal.CarinskiTerminal;
import org.etfbl.pj2.terminal.PolicijskiTerminal;
import org.etfbl.pj2.util.Initializator;
import org.etfbl.pj2.util.Reporter;
import org.etfbl.pj2.util.Watcher;
import org.etfbl.pj2.vozilo.Autobus;
import org.etfbl.pj2.vozilo.Kamion;
import org.etfbl.pj2.vozilo.LicnoVozilo;
import org.etfbl.pj2.vozilo.Vozilo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Simulacija
{
    public static PolicijskiTerminal p1 = new PolicijskiTerminal(false, true, 1);
    public static PolicijskiTerminal p2 = new PolicijskiTerminal(false, true, 2);
    public static PolicijskiTerminal pk = new PolicijskiTerminal(true, true, 3);
    public static CarinskiTerminal c1 = new CarinskiTerminal(false, true, 4);
    public static CarinskiTerminal ck = new CarinskiTerminal(true, true, 5);
    public static Semaphore policijskiTerminali = new Semaphore(2);
    public static Semaphore policijskiTerminaliKamion = new Semaphore(1);
    public static LinkedList<Vozilo> vozila = new LinkedList<>();
    public static Integer startedKamions = 0;
    public static Integer startedOther = 0;
    public static Integer blockedPoliceTerminals = 0;


    public static void main(String args[])
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

        List<Vozilo> vozilos = new ArrayList<>();
        Initializator.obrisiSvePutnike();
        Initializator.obrisiZapiseVozila();
        Watcher.ucitajTerminale();
        System.out.println(p1.isRadi() + " " + p2.isRadi() + " " + pk.isRadi() + " " + c1.isRadi() + " " + ck.isRadi());
        Watcher watcher = new Watcher();
        watcher.start();

        long pocetak = System.currentTimeMillis();
//        Initializator.prvih5().stream().forEach(e -> System.out.println(e.getIdVozilo()));
        while (!vozila.isEmpty()) {
            if (vozila.peek() instanceof Kamion) {
                if (pk.isSlobodan() && policijskiTerminaliKamion.availablePermits() > 0 && startedKamions == 0 && pk.isRadi()) {
                    Vozilo v = vozila.poll();
//                    Initializator.prvih5().stream().forEach(e -> System.out.println(e.getIdVozilo()));
                    System.out.println("Izlazi vozilo: " + v.getIdVozilo());
                    v.start();
                    synchronized (startedKamions)
                    {
                        startedKamions++;
                    }
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                    vozilos.add(v);
                } else {
                    // Ako terminal nije dostupan, ostavi vozilo u redu
                    //break;
                }
            } else if (vozila.peek() instanceof LicnoVozilo || vozila.peek() instanceof Autobus) {
                if ((p1.isSlobodan() || p2.isSlobodan()) && (policijskiTerminali.availablePermits() > 0) && startedOther + blockedPoliceTerminals <= 1) {
                    Vozilo v = vozila.poll();
//                    Initializator.prvih5().stream().forEach(e -> System.out.println(e.getIdVozilo()));
                    System.out.println("Izlazi vozilo: " + v.getIdVozilo());
                    v.start();
                    synchronized (startedOther)
                    {
                        startedOther++;
                    }
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                    vozilos.add(v);
                } else {
                    // Ako terminal nije dostupan, ostavi vozilo u redu
                    //break;
                }
            }
        }


        for(Vozilo v : vozilos)
        {
            try {
                v.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }




        System.out.println("KRAJ SIMULACIJE");
        Reporter.serijalizacijaKaznjenih();
        System.out.println(p1.isRadi() + " " + p2.isRadi() + " " + pk.isRadi() + " " + c1.isRadi() + " " + ck.isRadi());
        System.out.println(pocetak - System.currentTimeMillis());
        List<Putnik> kaznjeniPutnici = Reporter.deserijalizacijaKaznjenih();
        kaznjeniPutnici.stream().forEach(System.out::println);
    }
}
