package org.etfbl.pj2.util;

import org.etfbl.pj2.gui.Main;
import org.etfbl.pj2.simulacija.Simulacija;
import org.etfbl.pj2.terminal.Terminal;
import org.etfbl.pj2.vozilo.Kamion;
import org.etfbl.pj2.vozilo.LicnoVozilo;
import org.etfbl.pj2.vozilo.Vozilo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Initializator
{
    private static final String PUTNICI_PATH = "evidencije" + File.separator +  "putnici" + File.separator;
    private static final String VOZILA_PATH = "evidencije" + File.separator +  "vozila" + File.separator;

    public static Handler handler;
    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "Initializator.log");
            Logger.getLogger(Initializator.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void obrisiSvePutnike()
    {
        File folder = new File(PUTNICI_PATH);
        if(folder.exists() && folder.isDirectory())
        {
            File[] fajlovi = folder.listFiles();
            if(fajlovi != null)
            {
                Arrays.stream(fajlovi).forEach(File::delete);
            }
        }
    }

    public static void obrisiZapiseVozila()
    {
        File folder = new File(VOZILA_PATH);
        if(folder.exists() && folder.isDirectory())
        {
            File[] fajlovi = folder.listFiles();
            if(fajlovi != null)
            {
                Arrays.stream(fajlovi).forEach(File::delete);
            }
        }
    }

    public static void stopirajSimulaciju()
    {
        Simulacija.p1.setRadi(false);
        Simulacija.p2.setRadi(false);
        Simulacija.pk.setRadi(false);
        Simulacija.c1.setRadi(false);
        Simulacija.ck.setRadi(false);
    }

    public static void pokreniSimulaciju()
    {
        Watcher.ucitajTerminale();
    }

    public static List<Vozilo> prvih5()
    {
        List<Vozilo> vozila = new ArrayList<>();
        Iterator<Vozilo> iterator = Simulacija.vozila.iterator();
//        for(int i = 0; i < 5; i++)
//        {
//            if(iterator.hasNext())
//            {
//                vozila.add(iterator.next());
//
//            }
//        }
        if(iterator.hasNext())
        {
            Vozilo v = iterator.next();
            vozila.add(v);
            if(v instanceof Kamion)
            {
                Main.setBtn1Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn1Color("-fx-background-color: #5FAEEA;");
            }
            else if (v instanceof LicnoVozilo)
            {
                Main.setBtn1Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn1Color("-fx-background-color: #28CB6F;");
            }
            else
            {
                Main.setBtn1Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn1Color("-fx-background-color: #2D58E4;");
            }
        }
        else
        {
            Main.setBtn1Text("Prazno");
            Main.setBtn1Color("-fx-background-color: #eeeeee;");
        }

        if(iterator.hasNext())
        {
            Vozilo v = iterator.next();
            vozila.add(v);
            if(v instanceof Kamion)
            {
                Main.setBtn2Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn2Color("-fx-background-color: #5FAEEA;");
            }
            else if (v instanceof LicnoVozilo)
            {
                Main.setBtn2Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn2Color("-fx-background-color: #28CB6F;");
            }
            else
            {
                Main.setBtn2Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn2Color("-fx-background-color: #2D58E4;");
            }
        }
        else
        {
            Main.setBtn2Text("Prazno");
            Main.setBtn2Color("-fx-background-color: #eeeeee;");
        }

        if(iterator.hasNext())
        {
            Vozilo v = iterator.next();
            vozila.add(v);
            if(v instanceof Kamion)
            {
                Main.setBtn3Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn3Color("-fx-background-color: #5FAEEA;");
            }
            else if (v instanceof LicnoVozilo)
            {
                Main.setBtn3Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn3Color("-fx-background-color: #28CB6F;");
            }
            else
            {
                Main.setBtn3Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn3Color("-fx-background-color: #2D58E4;");
            }
        }
        else
        {
            Main.setBtn3Text("Prazno");
            Main.setBtn3Color("-fx-background-color: #eeeeee;");
        }

        if(iterator.hasNext())
        {
            Vozilo v = iterator.next();
            vozila.add(v);
            if(v instanceof Kamion)
            {
                Main.setBtn4Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn4Color("-fx-background-color: #5FAEEA;");
            }
            else if (v instanceof LicnoVozilo)
            {
                Main.setBtn4Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn4Color("-fx-background-color: #28CB6F;");
            }
            else
            {
                Main.setBtn4Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn4Color("-fx-background-color: #2D58E4;");
            }
        }
        else
        {
            Main.setBtn4Text("Prazno");
            Main.setBtn4Color("-fx-background-color: #eeeeee;");
        }

        if(iterator.hasNext())
        {
            Vozilo v = iterator.next();
            vozila.add(v);
            if(v instanceof Kamion)
            {
                Main.setBtn5Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn5Color("-fx-background-color: #5FAEEA;");
            }
            else if (v instanceof LicnoVozilo)
            {
                Main.setBtn5Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn5Color("-fx-background-color: #28CB6F;");
            }
            else
            {
                Main.setBtn5Text(v.getClass().getSimpleName() + ": " + v.getIdVozilo());
                Main.setBtn5Color("-fx-background-color: #2D58E4;");
            }
        }
        else
        {
            Main.setBtn5Text("Prazno");
            Main.setBtn5Color("-fx-background-color: #eeeeee;");
        }
        return vozila;
    }
}
