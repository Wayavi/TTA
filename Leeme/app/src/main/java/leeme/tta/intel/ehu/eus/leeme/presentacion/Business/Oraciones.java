package leeme.tta.intel.ehu.eus.leeme.presentacion.Business;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Frase;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.HttpClient;

public class Oraciones
{
    private HttpClient cliente;

    public Oraciones(HttpClient cl)
    {
        this.cliente = cl;
    }

    public Frase[] getFrasesByCategory(String fullPath)
    {
        Frase[] frases = null;
        try
        {
            JSONObject json = cliente.getJson(fullPath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return frases;
    }


}
