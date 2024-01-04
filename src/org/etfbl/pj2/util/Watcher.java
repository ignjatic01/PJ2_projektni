package org.etfbl.pj2.util;

import org.etfbl.pj2.simulacija.Simulacija;

import java.io.*;
import java.nio.file.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Watcher extends Thread
{
    Path directoryPath = Paths.get("terminali");

    public static Handler handler;
    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "Watcher.log");
            Logger.getLogger(Watcher.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Watcher()
    {
        this.setDaemon(true);
    }
    public void run()
    {
        WatchService watchService = null;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            directoryPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            Logger.getLogger(Watcher.class.getName()).log(Level.WARNING, "Greska prilikom registracije Watch servisa");
        }
        System.out.println("Watcher je pokrenut. Pratim promjene u direktorijumu: " + directoryPath);
        while (true)
        {
            try {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents())
                {
                    if(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY)
                    {
                        Path modifiedFile = (Path) event.context();
                        System.out.println("Fajl je izmijenjen: " + modifiedFile);
                    }
                }
                key.reset();
                ucitajTerminale();
            } catch (InterruptedException e) {
                Logger.getLogger(Watcher.class.getName()).log(Level.WARNING, "Interrupted izuzetak na Watcher niti prilikom uzimanja WatchKey iz WatchService");
            }
        }
    }

    public static void ucitajTerminale()
    {
        try {
            sleep(1000);
            BufferedReader br = new BufferedReader(new FileReader(new File("terminali/terminali.txt")));
            String linija = "";
            while((linija = br.readLine()) != null )
            {
                System.out.println(linija);
                String[] params = linija.split("#");
                if("p1".equals(params[0]))
                {
                    if(!Simulacija.p1.isRadi() && "true".equals(params[1]))
                    {
                        Simulacija.blockedPoliceTerminals--;
                    }
                    else if(Simulacija.p1.isRadi() && "false".equals(params[1]))
                    {
                        Simulacija.blockedPoliceTerminals++;
                    }
                    Simulacija.p1.setRadi(Boolean.parseBoolean(params[1]));
                }
                else if("p2".equals(params[0]))
                {
                    if(!Simulacija.p2.isRadi() && "true".equals(params[1]))
                    {
                        Simulacija.blockedPoliceTerminals--;
                    }
                    else if(Simulacija.p2.isRadi() && "false".equals(params[1]))
                    {
                        Simulacija.blockedPoliceTerminals++;
                    }
                    Simulacija.p2.setRadi(Boolean.parseBoolean(params[1]));
                }
                else if("pk".equals(params[0]))
                {
                    Simulacija.pk.setRadi(Boolean.parseBoolean(params[1]));
                }
                else if("c1".equals(params[0]))
                {
                    Simulacija.c1.setRadi(Boolean.parseBoolean(params[1]));
                }
                else if("ck".equals(params[0]))
                {
                    Simulacija.ck.setRadi(Boolean.parseBoolean(params[1]));
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(Watcher.class.getName()).log(Level.WARNING, "Nije pronadjen fajl na putanji: terminali/terminali.txt");
        } catch (IOException e) {
            Logger.getLogger(Watcher.class.getName()).log(Level.WARNING, "Greska tokom pokusaja citanja reda iz fajla");
        } catch (InterruptedException e) {
            Logger.getLogger(Watcher.class.getName()).log(Level.WARNING, "Interrupted tokom stanja spavanja");
        }
    }
}
