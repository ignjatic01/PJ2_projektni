package org.etfbl.pj2.vozilo;

import org.etfbl.pj2.putnik.Putnik;
import org.etfbl.pj2.simulacija.Simulacija;
import org.etfbl.pj2.terminal.PolicijskiTerminal;
import org.etfbl.pj2.util.Reporter;

import java.io.File;
import java.util.Iterator;
import java.util.Random;

public class LicnoVozilo extends Vozilo
{
    public static final int MAX_BROJ_PUTNIKA = 5;

    public LicnoVozilo()
    {
        super(MAX_BROJ_PUTNIKA);
        for(int i = 0; i < brojPutnika; i++)
        {
            putnici.add(new Putnik("Putnik" + i, "Vozilo" + this.getIdVozilo(), i == 0));
        }
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    @Override
    public void run()
    {
        Random random = new Random();
        boolean petlja = true;
        boolean validnoVozilo = true;
//        try {
//            Simulacija.policijskiTerminali.acquire();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        while (petlja && validnoVozilo)
        {

            if (Simulacija.p1.isSlobodan() && Simulacija.p1.isRadi()) {
                Simulacija.p1.setSlobodan(false);
                Simulacija.p1.setVozilo(this);
                System.out.println("Obrada licnog vozila " + this.getIdVozilo() + " na terminalu 1");
                validnoVozilo = this.policijskaLogika();
                if(!validnoVozilo)
                {
                    System.out.println("VOZILO " + this.getIdVozilo() + "JE IZBACENO!!!");
                    if(this.equals(Simulacija.p2.getVozilo()))
                    {
                        Simulacija.p2.setVozilo(null);
                        Simulacija.p2.setSlobodan(true);
                    }
                    else
                    {
                        Simulacija.p1.setVozilo(null);
                        Simulacija.p1.setSlobodan(true);
                    }
                    synchronized (Simulacija.startedOther)
                    {
                        Simulacija.startedOther--;
                    }
                }
                this.setStopTime(System.currentTimeMillis());
                petlja = false;
//                Simulacija.policijskiTerminali.release();
            } else if (Simulacija.p2.isSlobodan() && Simulacija.p2.isRadi()) {
                Simulacija.p2.setSlobodan(false);
                Simulacija.p2.setVozilo(this);
                System.out.println("Obrada licnog vozila " + this.getIdVozilo() + " na terminalu 2");
                validnoVozilo = this.policijskaLogika();
                if(!validnoVozilo)
                {
                    System.out.println("VOZILO " + this.getIdVozilo() + "JE IZBACENO!!!");
                    if(this.equals(Simulacija.p2.getVozilo()))
                    {
                        Simulacija.p2.setVozilo(null);
                        Simulacija.p2.setSlobodan(true);
                    }
                    else
                    {
                        Simulacija.p1.setVozilo(null);
                        Simulacija.p1.setSlobodan(true);
                    }
                    synchronized (Simulacija.startedOther)
                    {
                        Simulacija.startedOther--;
                    }
                }
                this.setStopTime(System.currentTimeMillis());
                petlja = false;

            }
        }
        boolean carinskaPetlja = true;
        while(carinskaPetlja && validnoVozilo)
        {
            synchronized (this)
            {
                while (!Simulacija.c1.isSlobodan())
                {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if(Simulacija.c1.isSlobodan() && Simulacija.c1.isRadi())
            {
                if(this.equals(Simulacija.p2.getVozilo()))
                {
                    Simulacija.p2.setVozilo(null);
                    Simulacija.p2.setSlobodan(true);
                }
                else
                {
                    Simulacija.p1.setVozilo(null);
                    Simulacija.p1.setSlobodan(true);
                }
                synchronized (Simulacija.startedOther)
                {
                    Simulacija.startedOther--;
                }

                Simulacija.c1.setSlobodan(false);
                Simulacija.c1.setVozilo(this);
                System.out.println("Obrada licnog vozila " + this.getIdVozilo() + " na carinskom terminalu");
                validnoVozilo = this.carinskaLogika();
                if(!validnoVozilo)
                {
                    System.out.println("VOZILO " + this.getIdVozilo() + "JE IZBACENO!!!");
                    //return;
                }
                Simulacija.c1.setSlobodan(true);
                Simulacija.c1.setVozilo(null);
                carinskaPetlja = false;
                if(Simulacija.p2.getVozilo() != null && Simulacija.p1.getVozilo() != null)
                {
                    long currTime = System.currentTimeMillis();
                    if(System.currentTimeMillis() - Simulacija.p2.getVozilo().getStopTime() > System.currentTimeMillis() - Simulacija.p1.getVozilo().getStopTime())
                    {
                        synchronized (Simulacija.p2.getVozilo())
                        {
                            Simulacija.p2.getVozilo().notify();
                        }
                    }
                    else
                    {
                        synchronized (Simulacija.p1.getVozilo())
                        {
                            Simulacija.p1.getVozilo().notify();
                        }
                    }
                }
                else if(Simulacija.p2.getVozilo() != null && Simulacija.p1.getVozilo() == null)
                {
                    synchronized (Simulacija.p2.getVozilo())
                    {
                        Simulacija.p2.getVozilo().notify();
                    }
                }
                else if(Simulacija.p1.getVozilo() != null && Simulacija.p2.getVozilo() == null)
                {
                    synchronized (Simulacija.p1.getVozilo())
                    {
                        Simulacija.p1.getVozilo().notify();
                    }
                }
                else if(Simulacija.p2.getVozilo() != null)
                {
                    synchronized (Simulacija.p2.getVozilo())
                    {
                        Simulacija.p2.getVozilo().notify();
                    }
                }
                else if(Simulacija.p1.getVozilo() != null)
                {
                    synchronized (Simulacija.p1.getVozilo())
                    {
                        Simulacija.p1.getVozilo().notify();
                    }
                }
            }
        }
    }



    public static boolean provjeriPolicijskiTerminal(PolicijskiTerminal pt)
    {
        synchronized (pt)
        {
            return  pt.isSlobodan();
        }
    }

    @Override
    protected boolean policijskaLogika()
    {
        Iterator<Putnik> iterator = putnici.iterator();
        while(iterator.hasNext())
        {
            Putnik p = iterator.next();
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(!p.getId().isIspravan())
            {
                PolicijskiTerminal.addKaznjeniPutnik(p);
                iterator.remove();
                if(p.isVozac())
                {
                    Reporter.upisiNeispravnogVozila("Vozac iskljucen na policijskom terminalu", this);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected boolean carinskaLogika()
    {
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}
