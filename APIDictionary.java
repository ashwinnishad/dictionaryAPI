// USES OXFORD DICTIONARY API TO RETRIEVE THE DEFINITION OF A WORD

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Scanner;

public class APIDictionary
{
    public static void main(String args[])
    {
        String lan = "en";
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a word to display it's definition.");
        String word = input.nextLine();
        String word_id = word.toLowerCase();
        wbeRequest(lan,word_id);
    }

    static String wbeRequest(String language, String word_id)
    {
        final String app_id = ""; // Get Application ID from Oxford dictionary API
        final String app_key = ""; // Get Application Key from Oxford dictionary API
        String dictionaryURL = "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id ;
        StringBuilder result = new StringBuilder();
        try
        {
            URL url = new URL(dictionaryURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept","application/json");
            conn.setRequestProperty("app_id",app_id);
            conn.setRequestProperty("app_key",app_key);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while( (line = rd.readLine()) != null)
            {
                result.append(line);
            }
            rd.close();
            String definition = parseJson(result.toString());
            System.out.print("The definition of the word " + word_id + " is: ");
            System.out.println(definition + ".");
            return result.toString();
        }
        catch(Exception e)
        {
            return "Error! Exception: " + e;
        }
    } // end of webRequest function

    static String parseJson(String json)
    {
        String definition = null;
      try
      {
          JSONObject main = new JSONObject(json);
          JSONArray results = main.getJSONArray("results");
          JSONObject lexical = results.getJSONObject(0);
          JSONArray la = lexical.getJSONArray("lexicalEntries");
          JSONObject entries = la.getJSONObject(0);
          JSONArray e = entries.getJSONArray("entries");
          JSONObject senses = e.getJSONObject(0);
          JSONArray s = senses.getJSONArray("senses");
          JSONObject d = s.getJSONObject(0);
          JSONArray de = d.getJSONArray("definitions");
          definition = de.getString(0);
      }
      catch (JSONException e)
      {
          e.printStackTrace();
      }
      return definition;
    }
} // end of the class
