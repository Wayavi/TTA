package leeme.tta.intel.ehu.eus.leeme.presentacion.Business;

import org.json.JSONObject;

import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.HttpClient;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Palabra;

public class Vocabulario
{
    private HttpClient cliente;

    public Vocabulario(HttpClient cl)
    {
        this.cliente = cl;
    }

    public Palabra[] getPalabrasByCategory(String fullPath)
    {
        Palabra[] palabras = null;
        try
        {
            JSONObject json = cliente.getJson(fullPath);
            palabras = new Palabra[json.length()];

            for(int i=0;i<json.length();i++)
            {
                palabras[i].setCastellano(json.getJSONObject(String.valueOf(i+1)).getString("palabra"));
                palabras[i].setEuskera(json.getJSONObject(String.valueOf(i + 1)).getString("hitza"));
                palabras[i].setVideo(json.getJSONObject(String.valueOf(i + 1)).getString("video"));
                palabras[i].setBideo(json.getJSONObject(String.valueOf(i + 1)).getString("bideoa"));
                palabras[i].setImagen(json.getJSONObject(String.valueOf(i+1)).getString("imagen"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return palabras;
    }
}
