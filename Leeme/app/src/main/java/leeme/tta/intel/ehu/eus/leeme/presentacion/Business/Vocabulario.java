package leeme.tta.intel.ehu.eus.leeme.presentacion.Business;

import org.json.JSONObject;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.HttpClient;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Palabra;

public class Vocabulario
{
    private HttpClient cliente;
    private String CONTENT_PATH = "vocabularioPorCategoria.php";

    public Vocabulario(HttpClient cl)
    {
        this.cliente = cl;
    }

    public Palabra[] getPalabrasByCategory(String path)
    {
        Palabra[] palabras = null;
        try
        {
            JSONObject json = cliente.getJson(CONTENT_PATH + path);
            int numPalabras = json.length();
            palabras = new Palabra[numPalabras];

            for(int i = 0; i < numPalabras; i++)
            {
                int index = i + 1;
                JSONObject jsonRow = json.getJSONObject(String.valueOf(index));
                Palabra palabra = new Palabra();

                palabra.setCastellano(jsonRow.getString("palabra"));
                palabra.setEuskera(jsonRow.getString("hitza"));
                palabra.setImagen(jsonRow.getString("imagen"));
                palabra.setVideo(jsonRow.getString("video"));
                palabra.setBideo(jsonRow.getString("bideoa"));

                palabras[i] = palabra;
            }

            return palabras;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getPalabrasStrings(Palabra[] palabras, String idioma)
    {
        String cadenas[] = new String[palabras.length];
        int cont = 0;
        for(Palabra p : palabras)
        {
            if(idioma.equals("Castellano"))
            {
                cadenas[cont] = p.getCastellano();
            }
            else
            {
                cadenas[cont] = p.getEuskera();
            }
            cont++;
        }
        return cadenas;
    }

    public String[] getVideoUrls(Palabra[] palabras, String idioma)
    {
        String urls[] = new String[palabras.length];
        int cont = 0;
        for(Palabra p : palabras)
        {
            if(idioma.equals("Castellano"))
            {
                urls[cont] = p.getVideo();
            }
            else
            {
                urls[cont] = p.getBideo();
            }
            cont++;
        }
        return urls;
    }
}
