package org.etfbl.pj2.util;

import org.etfbl.pj2.simulacija.Simulacija;
import org.etfbl.pj2.terminal.Terminal;
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

    public static List<Vozilo> prvih5()
    {
        List<Vozilo> vozila = new ArrayList<>();
        Iterator<Vozilo> iterator = Simulacija.vozila.iterator();
        for(int i = 0; i < 5; i++)
        {
            if(iterator.hasNext())
            {
                vozila.add(iterator.next());
            }
        }
        return vozila;
    }
}
