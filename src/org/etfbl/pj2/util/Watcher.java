package org.etfbl.pj2.util;

import org.etfbl.pj2.simulacija.Simulacija;

import java.io.*;
import java.nio.file.*;

public class Watcher extends Thread
{
    Path directoryPath = Paths.get("terminali");

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
            throw new RuntimeException(e);
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
//                        ucitajTerminale();
                    }
                }
                key.reset();
                ucitajTerminale();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[])
    {
        Watcher watcher = new Watcher();
        watcher.start();
    }
}
