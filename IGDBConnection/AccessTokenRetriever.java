package IGDBConnection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URI;
import javax.net.ssl.HttpsURLConnection;
import java.nio.charset.StandardCharsets;

public class AccessTokenRetriever
{
    public static void main (String[] args) throws IOException
    {
        String clientId = "86hpmu9gws96n5ipkekcq715bq77tj";
        String clientSecret = "zhmdic84egb7xi2wfwttuwwvq8uiql";

        String token = getAccessToken(clientId, clientSecret);
        String accessToken = token.substring(token.indexOf("access_token") + 15, token.indexOf("expires_in") - 3);
        String expireTime = token.substring(token.indexOf("expires_in") + 12, token.indexOf("token_type") - 2);
        System.out.println("Access Token: " + accessToken);
        System.out.println("Expires In: " + expireTime);
        System.out.println(retrieveGameInfo(1942, accessToken));
        //System.out.println("Access Token: " + token);
    }

    public static String retrieveGameInfo(int gameId, String accessToken) throws IOException
    {
        String url = "https://api.igdb.com/v4/games";
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        connection.setRequestProperty("Client-ID", "86hpmu9gws96n5ipkekcq715bq77tj");
        //connection.setRequestProperty("Body", "fields *; where id = " + gameId + ";");
        connection.setRequestProperty("Body", "fields *; limit 5;");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) 
        {
            return new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } 
        else 
        {
            throw new IOException("Failed to retrieve game info: " + responseCode);
        }
    }

    public static String getAccessToken(String clientId, String clientSecret) throws IOException
    {
        String url = "https://id.twitch.tv/oauth2/token";
        String params = "client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=client_credentials";

        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("access_token", "application/json");
        connection.setDoOutput(true);
        

        try (OutputStream os = connection.getOutputStream()) 
        {
            os.write(params.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) 
        {
            return new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } 
        else 
        {
            throw new IOException("Failed to retrieve access token: " + responseCode);
        }
    }
}