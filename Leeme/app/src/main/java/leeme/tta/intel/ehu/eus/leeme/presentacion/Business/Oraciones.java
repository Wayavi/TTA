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

    public Frase[] getFrasesByCategory(String path)
    {
        Frase[] frases = null;
        try
        {
            JSONObject json = cliente.getJson(path);
            int numPalabras = json.length();
            frases = new Frase[numPalabras];

            for(int i = 0; i < numPalabras; i++)
            {
                int index = i + 1;
                JSONObject jsonRow = json.getJSONObject(String.valueOf(index));
                Frase frase = new Frase();

                frase.setCastellano(jsonRow.getString("oracion"));
                frase.setEuskera(jsonRow.getString("esaldia"));
                frase.setVideo(jsonRow.getString("video"));
                frase.setBideo(jsonRow.getString("bideoa"));
                frases[i] = frase;
            }
            return frases;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return frases;
    }

    public String[] getFrasesStrings(Frase[] frases, String idioma)
    {
        String cadenas[] = new String[frases.length];
        int cont = 0;
        for(Frase f : frases)
        {
            if(idioma.contains("esp"))
            {
                cadenas[cont] = f.getCastellano();
            }
            else
            {
                cadenas[cont] = f.getEuskera();
            }
            cont++;
        }
        return cadenas;
    }


}
