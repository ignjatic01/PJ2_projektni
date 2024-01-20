package org.etfbl.pj2.vozilo;

import org.etfbl.pj2.dokumentacija.CarinskaDokumentacija;
import org.etfbl.pj2.gui.Log;
import org.etfbl.pj2.gui.Main;
import org.etfbl.pj2.putnik.Putnik;
import org.etfbl.pj2.simulacija.Simulacija;
import org.etfbl.pj2.terminal.PolicijskiTerminal;
import org.etfbl.pj2.util.Reporter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kamion extends Vozilo
{
    public static final int MAX_BROJ_PUTNIKA = 3;
    private double deklarisanaTezina;
    private double stvarnaTezina;
    private CarinskaDokumentacija dokumentacija;

    public static Handler handler;
    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "Kamion.log");
            Logger.getLogger(Kamion.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Kamion()
    {
        super(MAX_BROJ_PUTNIKA);
        Random random = new Random();
        for(int i = 0; i < brojPutnika; i++)
        {
            putnici.add(new Putnik("Putnik" + i, "Prezime" + i, i == 0));
        }
        deklarisanaTezina = random.nextDouble() * 10;
        stvarnaTezina = initializeTeret(deklarisanaTezina);
        if(random.nextBoolean())
        {
            dokumentacija = new CarinskaDokumentacija();
        }
        else
        {
            dokumentacija = null;
        }
    }

    public double getDeklarisanaTezina() {
        return deklarisanaTezina;
    }

    public void setDeklarisanaTezina(double deklarisanaTezina) {
        this.deklarisanaTezina = deklarisanaTezina;
    }

    public double getStvarnaTezina() {
        return stvarnaTezina;
    }

    public void setStvarnaTezina(double stvarnaTezina) {
        this.stvarnaTezina = stvarnaTezina;
    }

    public CarinskaDokumentacija getDokumentacija() {
        return dokumentacija;
    }

    public void setDokumentacija(CarinskaDokumentacija dokumentacija) {
        this.dokumentacija = dokumentacija;
    }

    public static double initializeTeret(double deklarisana)
    {
        Random random = new Random();
        double param1 = random.nextDouble();
        if(param1 <= 0.2)
        {
            int param2 = random.nextInt(31);
            return (1.0 + param2/100.0) * deklarisana;
        }
        else
        {
            return deklarisana;
        }
    }

    public String toString() {
        return String.format("%s%nDeklarisana tezina: %.3f t, tezina: %.3f t%nCarinska dokumentacija: %s",
                super.toString(),
                this.getDeklarisanaTezina(),
                this.getStvarnaTezina(),
                (this.getDokumentacija() == null) ? " postoji" : " ne postoji");
    }


    public void run()
    {
        Random random = new Random();
        boolean validnoVozilo = true;


        Simulacija.pk.setSlobodan(false);
        Simulacija.pk.setVozilo(this);
        Main.setPkbtnText(this.getClass().getSimpleName() + ": " + this.getIdVozilo());
        Main.setPkbtnColor("-fx-background-color: #5FAEEA;");
        Main.setPkbtnOnAction(this);
        System.out.println("Obrada kamiona " + this.getIdVozilo() + " na terminalu 3");
        Log.logMessage("Obrada kamiona " + this.getIdVozilo() + " na terminalu 3");
        validnoVozilo = this.policijskaLogika();
        if(!validnoVozilo)
        {
            System.out.println("VOZILO " + this.getIdVozilo() + " JE IZBACENO!!!");
            Log.logMessage("VOZILO " + this.getIdVozilo() + " JE IZBACENO!!!");
            Main.setPkbtnText("PK");
            Main.setPkbtnColor("-fx-background-color: #ABFFAC;");
            Main.setPkbtnOnAction(null);
            Simulacija.pk.setVozilo(null);
            Simulacija.pk.setSlobodan(true);
            synchronized (Simulacija.startedKamions)
            {
                Simulacija.startedKamions--;
            }
        }
        this.setStopTime(System.currentTimeMillis());

        boolean carinskaPetlja = true;
        while(carinskaPetlja && validnoVozilo)
        {
            synchronized (this)
            {
                while (!Simulacija.ck.isSlobodan())
                {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Logger.getLogger(Kamion.class.getName()).log(Level.WARNING, "Interrupted tokom cekanja na oslobadjanje carinskog terminala");
                    }
                }
            }
            if(Simulacija.ck.isSlobodan() && Simulacija.ck.isRadi())
            {
                Main.setPkbtnText("PK");
                Main.setPkbtnColor("-fx-background-color: #ABFFAC;");
                Main.setPkbtnOnAction(null);
                Simulacija.pk.setVozilo(null);
                Simulacija.pk.setSlobodan(true);
                synchronized (Simulacija.startedKamions)
                {
                    Simulacija.startedKamions--;
                }

                Simulacija.ck.setSlobodan(false);
                Simulacija.ck.setVozilo(this);
                Main.setCkbtnText(this.getClass().getSimpleName() + ": " + this.getIdVozilo());
                Main.setCkbtnColor("-fx-background-color: #5FAEEA;");
                Main.setCkbtnOnAction(this);
                System.out.println("Obrada kamiona " + this.getIdVozilo() + " na carinskom terminalu");
                Log.logMessage("Obrada kamiona " + this.getIdVozilo() + " na carinskom terminalu");
                validnoVozilo = this.carinskaLogika();
                if(!validnoVozilo)
                {
                    System.out.println("VOZILO " + this.getIdVozilo() + " JE IZBACENO!!!");
                    Log.logMessage("VOZILO " + this.getIdVozilo() + " JE IZBACENO!!!");
                    //return;
                }
                Main.setCkbtnText("CK");
                Main.setCkbtnColor("-fx-background-color: #A7F9CB;");
                Main.setCkbtnOnAction(null);
                Simulacija.ck.setSlobodan(true);
                Simulacija.ck.setVozilo(null);
                carinskaPetlja = false;
                if(Simulacija.pk.getVozilo() != null)
                {
                    synchronized (Simulacija.pk.getVozilo())
                    {
                        Simulacija.pk.getVozilo().notify();
                    }
                }
            }
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
                Logger.getLogger(Kamion.class.getName()).log(Level.WARNING, "Interrupted tokom stanja spavanja niti (Policijska logika)");
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
            sleep(500);
        } catch (InterruptedException e) {
            Logger.getLogger(Kamion.class.getName()).log(Level.WARNING, "Interrupted tokom stanja spavanja niti (Policijska logika)");
        }
        if(this.getStvarnaTezina() > this.getDeklarisanaTezina())
        {
            Reporter.upisiNeispravnogVozila("Vozilo iskljuceno zbog pretovara", this);
            return false;
        }
        return true;
    }

}
