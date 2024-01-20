package org.etfbl.pj2.vozilo;

import org.etfbl.pj2.gui.Log;
import org.etfbl.pj2.gui.Main;
import org.etfbl.pj2.putnik.Putnik;
import org.etfbl.pj2.simulacija.Simulacija;
import org.etfbl.pj2.terminal.PolicijskiTerminal;
import org.etfbl.pj2.util.Reporter;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LicnoVozilo extends Vozilo
{
    public static final int MAX_BROJ_PUTNIKA = 5;

    public static Handler handler;
    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "LicnoVozilo.log");
            Logger.getLogger(LicnoVozilo.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public LicnoVozilo()
    {
        super(MAX_BROJ_PUTNIKA);
        for(int i = 0; i < brojPutnika; i++)
        {
            putnici.add(new Putnik("Putnik" + i, "Prezime" + i, i == 0));
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
        while (petlja && validnoVozilo)
        {

            if (Simulacija.p1.isSlobodan() && Simulacija.p1.isRadi()) {
                Simulacija.p1.setSlobodan(false);
                Simulacija.p1.setVozilo(this);
                Main.setP1btnText(this.getClass().getSimpleName() + ": " + this.getIdVozilo());
                Main.setP1btnColor("-fx-background-color: #28CB6F;");
                Main.setP1btnOnAction(this);
                System.out.println("Obrada licnog vozila " + this.getIdVozilo() + " na terminalu 1");
                Log.logMessage("Obrada licnog vozila " + this.getIdVozilo() + " na terminalu 1");
                validnoVozilo = this.policijskaLogika();
                if(!validnoVozilo)
                {
                    System.out.println("VOZILO " + this.getIdVozilo() + "JE IZBACENO!!!");
                    Log.logMessage("VOZILO " + this.getIdVozilo() + "JE IZBACENO!!!");
                    Main.setP1btnText("P1");
                    Main.setP1btnColor("-fx-background-color: #ABFFAC;");
                    Main.setP1btnOnAction(null);
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
            } else if (Simulacija.p2.isSlobodan() && Simulacija.p2.isRadi()) {
                Simulacija.p2.setSlobodan(false);
                Simulacija.p2.setVozilo(this);
                Main.setP2btnText(this.getClass().getSimpleName() + ": " + this.getIdVozilo());
                Main.setP2btnColor("-fx-background-color: #28CB6F;");
                Main.setP2btnOnAction(this);
                System.out.println("Obrada licnog vozila " + this.getIdVozilo() + " na terminalu 2");
                Log.logMessage("Obrada licnog vozila " + this.getIdVozilo() + " na terminalu 2");
                validnoVozilo = this.policijskaLogika();
                if(!validnoVozilo)
                {
                    System.out.println("VOZILO " + this.getIdVozilo() + "JE IZBACENO!!!");
                    Log.logMessage("VOZILO " + this.getIdVozilo() + "JE IZBACENO!!!");
                    Main.setP2btnText("P2");
                    Main.setP2btnColor("-fx-background-color: #ABFFAC;");
                    Main.setP2btnOnAction(null);
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
                        Logger.getLogger(LicnoVozilo.class.getName()).log(Level.WARNING, "Interrupted prilikom cekanja na oslobadjanje terminala");
                    }
                }
            }
            if(Simulacija.c1.isSlobodan() && Simulacija.c1.isRadi())
            {
                if(this.equals(Simulacija.p2.getVozilo()))
                {
                    Main.setP2btnText("P2");
                    Main.setP2btnColor("-fx-background-color: #ABFFAC;");
                    Main.setP2btnOnAction(null);
                    Simulacija.p2.setVozilo(null);
                    Simulacija.p2.setSlobodan(true);
                }
                else
                {
                    Main.setP1btnText("P1");
                    Main.setP1btnColor("-fx-background-color: #ABFFAC;");
                    Main.setP1btnOnAction(null);
                    Simulacija.p1.setVozilo(null);
                    Simulacija.p1.setSlobodan(true);
                }
                synchronized (Simulacija.startedOther)
                {
                    Simulacija.startedOther--;
                }


                Simulacija.c1.setSlobodan(false);
                Simulacija.c1.setVozilo(this);
                Main.setc1btnText(this.getClass().getSimpleName() + ": " + this.getIdVozilo());
                Main.setc1btnColor("-fx-background-color: #28CB6F;");
                Main.setc1btnOnAction(this);
                System.out.println("Obrada licnog vozila " + this.getIdVozilo() + " na carinskom terminalu");
                Log.logMessage("Obrada licnog vozila " + this.getIdVozilo() + " na carinskom terminalu");
                validnoVozilo = this.carinskaLogika();
                if(!validnoVozilo)
                {
                    System.out.println("VOZILO " + this.getIdVozilo() + "JE IZBACENO!!!");
                    Log.logMessage("VOZILO " + this.getIdVozilo() + "JE IZBACENO!!!");
                    //return;
                }
                Main.setc1btnText("C1");
                Main.setc1btnColor("-fx-background-color: #A7F9CB;");
                Main.setc1btnOnAction(null);
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
                Logger.getLogger(LicnoVozilo.class.getName()).log(Level.WARNING, "Interrupted prilikom stanja spavanja (Policijska logika)");
            }
            if(!p.getId().isIspravan())
            {
                PolicijskiTerminal.addKaznjeniPutnik(p);
                Reporter.upisNeispravnogPutnika("Ima neispravne dokumente", this, p);
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
            Logger.getLogger(LicnoVozilo.class.getName()).log(Level.WARNING, "Interrupted prilikom stanja spavanja (Carinska logika)");
        }
        return true;
    }

}
