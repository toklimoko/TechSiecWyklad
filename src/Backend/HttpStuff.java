package Backend;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpStuff {

    public static JSONObject doPost(String mail, String password) throws Exception {

        URL url = new URL("http://smieszne-koty.herokuapp.com/oauth/token" + "?grant_type=password&email=" + mail + "&password=" + password);
        HttpURLConnection connection;
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setReadTimeout(30000);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = input.readLine()) != null)
            response.append(inputLine);
        input.close();

        JSONObject autoryzacja = new JSONObject(response.toString());

        return autoryzacja;
    }

    public static List<Kitten> doGet(JSONObject jsonObject, int pageNumber) throws Exception {
        String token = jsonObject.getString("access_token");
        URL url = new URL("http://smieszne-koty.herokuapp.com/api/kittens" + "?access_token=" + token + "&page=" + pageNumber);
        HttpURLConnection connection;

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(30000);

        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = input.readLine()) != null)
            response.append(inputLine);
        input.close();

        JSONArray kotki = new JSONArray(response.toString());
        List<Kitten> kittens = new ArrayList<>();

        for (int i = 0; i < kotki.length(); i++) {

            JSONObject kotek = kotki.getJSONObject(i);
            Kitten kitten = new Kitten();
            kitten.setName(kotek.getString("name"));
            kitten.setURL(kotek.getString("url"));
            kitten.setVotes(kotek.getInt("vote_count"));
            kittens.add(kitten);
        }

        return kittens;
    }

}
