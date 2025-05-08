package gamed.gamedtestproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.URISyntaxException;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.apicalypse.Sort;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.request.TwitchAuthenticator;
import com.api.igdb.utils.ImageBuilderKt;
import com.api.igdb.utils.ImageSize;
import com.api.igdb.utils.ImageType;
import com.api.igdb.utils.TwitchToken;

import proto.Game;
import proto.Genre;
import proto.Platform;
import proto.Cover;

public enum APIHandler 
{
    INSTANCE;
    String clientID = "";
    String clientSecret = "";
    String authToken = "";
    IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
    HashMap<String, Long> genreDictionary = new HashMap<>();
    URL coverURL;

    private APIHandler() 
    {

    }

    public void Initialize() 
    {
        try
        {
            coverURL = new URI("https://api.igdb.com/v4/covers/").toURL();
        }
        catch (MalformedURLException | URISyntaxException e)
        {
            e.printStackTrace();
        }
        
        TwitchAuthenticator auth = TwitchAuthenticator.INSTANCE;
        TwitchToken token = auth.requestTwitchToken(clientID, clientSecret);

        String authenticationToken = token.getAccess_token();
        authToken = authenticationToken;
        wrapper.setCredentials(clientID, authenticationToken);

        //Genre dictionary
        genreDictionary.put("Point-and-click", 2L);
        genreDictionary.put("Fighting", 4L);
        genreDictionary.put("Shooter", 5L);
        genreDictionary.put("Music", 7L);
        genreDictionary.put("Platform", 8L);
        genreDictionary.put("Puzzle", 9L);
        genreDictionary.put("Racing", 10L);
        genreDictionary.put("Real Time Strategy", 11L);
        genreDictionary.put("Role-playing", 12L);
        genreDictionary.put("Simulator", 13L);
        genreDictionary.put("Sport", 14L);
        genreDictionary.put("Strategy", 15L);
        genreDictionary.put("Turn-based", 16L);
        genreDictionary.put("Tactical", 24L);
        genreDictionary.put("Hack and slash", 25L);
        genreDictionary.put("Quiz/Trivia", 26L);
        genreDictionary.put("Pinball", 30L);
        genreDictionary.put("Adventure", 31L);
        genreDictionary.put("Indie", 32L);
        genreDictionary.put("Arcade", 33L);
        genreDictionary.put("Visual Novel", 34L);
        genreDictionary.put("Card & Board Game", 35L);
        genreDictionary.put("MOBA", 36L);
    }

    public void SetClientID(String clientID) 
    {
        this.clientID = clientID;
    }
    public void SetClientSecret(String clientSecret) 
    {
        this.clientSecret = clientSecret;
    }

    
    public String GetGameImageURL(String gameID) 
    {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.igdb.com/v4/covers"))
            .header("Client-ID", clientID)
            .header("Authorization", "Bearer " + authToken)
            .method("POST", HttpRequest.BodyPublishers.ofString("fields image_id; where game = " + gameID + ";"))
            .build();
        HttpResponse<String> response = null;
        try
        {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            String image_id = response.body().substring(response.body().indexOf("image_id") + 12, response.body().indexOf("}") - 4);
            return "https://images.igdb.com/igdb/image/upload/t_cover_big/" + image_id + ".jpg";
        }
        catch (InterruptedException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    

    public List<Game> RetrieveFeaturedGames() 
    {
        APICalypse apicalypse = new APICalypse().fields("*").limit(10).sort("rating", Sort.DESCENDING);
        try 
        {
            List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);
            if (games.size() > 0) 
            {
                return games;
            } 
            else 
            {
                System.out.println("No featured games found.");
                return null;
            }
        } 
        catch (RequestException e) 
        {
            System.out.println("Error retrieving featured games: " + e.getMessage());
            return null;
        }
    }

    public Game RetrieveGameByID(String gameID) 
    {
        APICalypse apicalypse = new APICalypse().fields("*").where("id = " + gameID);
        try 
        {
            List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);
            if (games.size() > 0) 
            {
                return games.get(0);
            } 
            else 
            {
                System.out.println("Game not found with ID: " + gameID);
                return null;
            }
        } 
        catch (RequestException e) 
        {
            System.out.println("Error retrieving game: " + e.getMessage());
            return null;
        }
    }

    public List<Game> SearchGameByName(String gameName) 
    {
        APICalypse apicalypse = new APICalypse().search(gameName).fields("*").limit(20);
        try 
        {
            List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);
            if (games.size() > 0) 
            {
                return games;
            } 
            else 
            {
                System.out.println("Game not found with name: " + gameName);
                return null;
            }
        } 
        catch (RequestException e) 
        {
            System.out.println("Error retrieving game: " + e.getMessage());
            return null;
        }
    }

    public List<Game> SearchGameByGenre(String genre) 
    {
        Long genreID = genreDictionary.get(genre);

        APICalypse apicalypse = new APICalypse().fields("*").where("genres = " + genreID).limit(20);
        try 
        {
            List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);

            if (games.size() > 0) 
            {
                return games;
            } 
            else 
            {
                System.out.println("Game not found with specified genre: " + genre);
                return null;
            }
        } 
        catch (RequestException e) 
        {
            System.out.println("Error retrieving game: " + e.getMessage());
            return null;
        }
    }

    public List<Game> SearchGameByPlatform(String platform) 
    {
        Long platformID = genreDictionary.get(platform);

        APICalypse apicalypse = new APICalypse().fields("*").where("platforms = " + platformID).limit(20);
        try 
        {
            List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);

            if (games.size() > 0) 
            {
                return games;
            } 
            else 
            {
                System.out.println("Game not found with specified platform: " + platform);
                return null;
            }
        } 
        catch (RequestException e) 
        {
            System.out.println("Error retrieving game: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<String> RetrieveGameGenres(String gameID)
    {
        ArrayList<String> genres = new ArrayList<>();
        List<Genre> genreIDList = new ArrayList<>();
        APICalypse apicalypse = new APICalypse().fields("*").where("id = " + gameID);
        try 
        {
            List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);
            if (games.size() > 0) 
            {
                Game game = games.get(0);
                genreIDList = game.getGenresList();


                for(Genre genre : genreIDList) 
                {   
                    List<Genre> genreList = ProtoRequestKt.genres(wrapper, new APICalypse().fields("*").where("id = " + genre.getId()));
                    genres.add(genreList.get(0).getName());
                }
                return genres;
            } 
            else 
            {
                System.out.println("Game not found with ID: " + gameID);
                return null;
            }
        } 
        catch (RequestException e) 
        {
            System.out.println("Error retrieving game genres: " + e.getMessage());
            return null;
        }
    }
    
    public ArrayList<String> RetrieveGameGenres(Game game)
    {
        ArrayList<String> genres = new ArrayList<>();
        List<Genre> genreIDList = new ArrayList<>();
        try 
        {
            genreIDList = game.getGenresList();

            for(Genre genre : genreIDList) 
            {   
                List<Genre> genreList = ProtoRequestKt.genres(wrapper, new APICalypse().fields("*").where("id = " + genre.getId()));
                genres.add(genreList.get(0).getName());
            }
            return genres;
        } 
        catch (RequestException e) 
        {
            System.out.println("Error retrieving game genres: " + e.getMessage());
            return null;
        }
    }

    //Used for finding all platforms to place in the genre dictionary for searching
    public HashMap<Long, String> getAllPlatforms()
    {
        HashMap<Long, String> platforms = new HashMap<>();
        Long platformID = 2l;

        while(platformID < 171)
        {
            try
            {
                List<Platform> platformList = ProtoRequestKt.platforms(wrapper, new APICalypse().fields("*").where("id = " + platformID));

                if(platformList.size() > 0)
                {
                    platforms.put(platformList.get(0).getId(), platformList.get(0).getName());
                }
                else
                {
                    System.out.println("Platform not found with ID: " + platformID);
                }
            }
            catch (RequestException e) 
            {
                System.out.println("Error retrieving game platforms: " + e.getMessage());
            }
            platformID++;
        }
        return platforms;
        
    }

    private HashMap<Long, String> getAllGenres()
    {
        HashMap<Long, String> genres = new HashMap<>();
        Long genreID = 2l;

        try
        {
            while(genreID < 59)
            {
                List<Genre> genreList = ProtoRequestKt.genres(wrapper, new APICalypse().fields("*").where("id = " + genreID));

                if(genreList.size() > 0)
                {
                    genres.put(genreList.get(0).getId(), genreList.get(0).getName());
                }
                else
                {
                    System.out.println("Genre not found with ID: " + genreID);
                }
                genreID++;
            }
            return genres;
        }
        catch (RequestException e) 
        {
            System.out.println("Error retrieving game genres: " + e.getMessage());
        }
        return null;
    }

    public String getPlatformByID(Long platformID)
    {
        try
        {
            List<Platform> platformList = ProtoRequestKt.platforms(wrapper, new APICalypse().fields("*").where("id = " + platformID));
            if(platformList.size() > 0)
            {
                return platformList.get(0).getName();
            }
            else
            {
                System.out.println("Platform not found with ID: " + platformID);
                return null;
            }
        } 
        catch (RequestException e) 
        {
            System.out.println("Error retrieving game platforms: " + e.getMessage());
            return null;
        }
           
    }

    public String getGenreByID(Long genreID)
    {
        try
        {
            List<Genre> genreList = ProtoRequestKt.genres(wrapper, new APICalypse().fields("*").where("id = " + genreID));
            if(genreList.size() > 0)
            {
                return genreList.get(0).getName();
            }
            else
            {
                System.out.println("Genre not found with ID: " + genreID);
                return null;
            }
        } 
        catch (RequestException e) 
        {
            System.out.println("Error retrieving game genres: " + e.getMessage());
            return null;
        }
    }

    public List<Game> RetrieveWishlist(List<String> gameIDs) 
    {
        APICalypse apicalypse = new APICalypse().fields("*").where("id = (" + String.join(",", gameIDs.stream().map(String::valueOf).toArray(String[]::new)) + ")");
        try 
        {
            return ProtoRequestKt.games(wrapper, apicalypse);
        } 
        catch (RequestException e) 
        {
            System.out.println("Error retrieving wishlist: " + e.getMessage());
            return null;
        }
    }

    public String getAuthToken()
    {
        return authToken;
    }
}
