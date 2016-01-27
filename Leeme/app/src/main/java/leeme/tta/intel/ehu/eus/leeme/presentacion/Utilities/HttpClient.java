package leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient
{
    public String urlConexion;

    public HttpClient(String url)
    {
        this.urlConexion=url;
    }

    private HttpURLConnection getConnection(String path) throws IOException
    {
        URL url=new URL(String.format("%s/%s",urlConexion,path));
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        return conn;
    }

    public String getString(String path) throws IOException
    {
        HttpURLConnection conn=null;
        try
        {
            conn=getConnection(path);
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String linea=br.readLine();
            return linea;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if(conn !=null)
            {
                conn.disconnect();
            }
        }
    }

    public JSONObject getJson(String path) throws IOException, JSONException
    {
        JSONObject json=new JSONObject(getString(path));
        System.out.println(json);
        return json;
    }

    /**
    public  Bitmap downloadImage(String imagen)
    {
        Bitmap loadedImage=null;
        try {
            HttpURLConnection conn = getConnection(imagen);
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedImage;
    }
     **/
}
