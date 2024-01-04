package org.etfbl.pj2.terminal;

import org.etfbl.pj2.simulacija.Simulacija;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class CarinskiTerminal extends Terminal
{
    public static Handler handler;
    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "CarinskiTerminal.log");
            Logger.getLogger(CarinskiTerminal.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public CarinskiTerminal(boolean zaKamione, boolean radi, int id)
    {
        super(zaKamione, radi, id);
    }
}
