package org.etfbl.pj2.dokumentacija;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class CarinskaDokumentacija
{
    public static Handler handler;

    static
    {
        try
        {
            handler = new FileHandler("evidencije" + File.separator +  "log" + File.separator + "CarinskaDokumentacija.log");
            Logger.getLogger(CarinskaDokumentacija.class.getName()).addHandler(handler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
