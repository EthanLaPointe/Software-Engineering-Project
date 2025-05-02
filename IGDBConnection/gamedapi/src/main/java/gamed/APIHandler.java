package gamed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.request.TwitchAuthenticator;
import com.api.igdb.utils.TwitchToken;

import proto.Game;
import proto.Genre;

public class APIHandler 
{
    String clientID = "";
    String clientSecret = "";
    IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
    HashMap<String, Long> genreDictionary = new HashMap<>();
    

    public APIHandler(String clientID, String clientSecret) 
    {
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        Initialize();   
    }

    private void Initialize() 
    {
        TwitchAuthenticator auth = TwitchAuthenticator.INSTANCE;
        TwitchToken token = auth.requestTwitchToken(clientID, clientSecret);

        String authenticationToken = token.getAccess_token();
        wrapper.setCredentials(clientID, authenticationToken);

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
        APICalypse apicalypse = new APICalypse().search(genre).fields("*").limit(20);
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

    //Used for finding all genres to place in the genre dictionary for searching
    public HashMap<Long, String> getAllGenres()
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
}
