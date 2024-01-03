package org.etfbl.pj2.vozilo;

import org.etfbl.pj2.dokumentacija.CarinskaDokumentacija;
import org.etfbl.pj2.putnik.Putnik;
import org.etfbl.pj2.simulacija.Simulacija;
import org.etfbl.pj2.terminal.PolicijskiTerminal;
import org.etfbl.pj2.util.Reporter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Kamion extends Vozilo
{
    public static final int MAX_BROJ_PUTNIKA = 3;
    private double deklarisanaTezina;
    private double stvarnaTezina;
    private CarinskaDokumentacija dokumentacija;

    public Kamion()
    {
        super(MAX_BROJ_PUTNIKA);
        Random random = new Random();
        for(int i = 0; i < brojPutnika; i++)
        {
            putnici.add(new Putnik("Putnik" + i, "Vozilo" + this.getIdVozilo(), i == 0));
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

    @Override
    public String toString()
    {
        return super.toString() + "Deklarisana tezina: " + this.getDeklarisanaTezina() + ", tezina: " + this.getStvarnaTezina() +
                "\nCarinska dokumentacija: " + ((this.getDokumentacija() == null)? " postoji" : " ne postoji");
    }

    public void run()
    {
//        try {
//            Simulacija.policijskiTerminaliKamion.acquire();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        Random random = new Random();
        boolean validnoVozilo = true;


        Simulacija.pk.setSlobodan(false);
        Simulacija.pk.setVozilo(this);
        System.out.println("Obrada kamiona " + this.getIdVozilo() + " na terminalu 3");
        validnoVozilo = this.policijskaLogika();
        if(!validnoVozilo)
        {
            System.out.println("VOZILO " + this.getIdVozilo() + "JE IZBACENO!!!");
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
                        throw new RuntimeException(e);
                    }
                }
            }
            if(Simulacija.ck.isSlobodan() && Simulacija.ck.isRadi())
            {
                Simulacija.pk.setVozilo(null);
                Simulacija.pk.setSlobodan(true);
                synchronized (Simulacija.startedKamions)
                {
                    Simulacija.startedKamions--;
                }

                Simulacija.ck.setSlobodan(false);
                Simulacija.ck.setVozilo(this);
                System.out.println("Obrada kamiona " + this.getIdVozilo() + " na carinskom terminalu");
                validnoVozilo = this.carinskaLogika();
                if(!validnoVozilo)
                {
                    System.out.println("VOZILO " + this.getIdVozilo() + "JE IZBACENO!!!");
                    //return;
                }
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

//        Simulacija.policijskiTerminaliKamion.release();
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
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(this.getStvarnaTezina() +  " > " + this.getDeklarisanaTezina());
        if(this.getStvarnaTezina() > this.getDeklarisanaTezina())
        {
            Reporter.upisiNeispravnogVozila("Vozilo iskljuceno zbog pretovara", this);
            return false;
        }
        return true;
    }

    public static void main(String args[])
    {
        List<Kamion> list = new ArrayList<>();
        for(int i = 0; i < 10; i++)
        {
            list.add(new Kamion());
        }
        for(Kamion k : list)
        {
            System.out.println(k);
            System.out.println();
        }
    }
}
