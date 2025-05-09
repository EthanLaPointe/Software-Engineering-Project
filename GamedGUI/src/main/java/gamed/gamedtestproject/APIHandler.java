package gamed.gamedtestproject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.apicalypse.Sort;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.request.TwitchAuthenticator;
import com.api.igdb.utils.TwitchToken;

import proto.Game;
import proto.Genre;
import proto.Platform;

public enum APIHandler 
{
    INSTANCE;
    String clientID = "";
    String clientSecret = "";
    String authToken = "";
    IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
    HashMap<String, Long> genreDictionary = new HashMap<>();
    HashMap<String, Long> platformMap = new HashMap<>();
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
                //Platforms ids  
                    platformMap.put("Linux", 3L);
                    platformMap.put("Nintendo 64", 4L);
                    platformMap.put("Wii", 5L);
                    platformMap.put("PC (Microsoft Windows)", 6L);
                    platformMap.put("PlayStation", 7L);
                    platformMap.put("PlayStation 2", 8L);
                    platformMap.put("PlayStation 3", 9L);
                    platformMap.put("Xbox", 11L);
                    platformMap.put("Xbox 360", 12L);
                    platformMap.put("DOS", 13L);
                    platformMap.put("Mac", 14L);
                    platformMap.put("Commodore C64/128/MAX", 15L);
                    platformMap.put("Amiga", 16L);
                    platformMap.put("Nintendo Entertainment System", 18L);
                    platformMap.put("Super Nintendo Entertainment System", 19L);
                    platformMap.put("Nintendo DS", 20L);
                    platformMap.put("Nintendo GameCube", 21L);
                    platformMap.put("Game Boy Color", 22L);
                    platformMap.put("Dreamcast", 23L);
                    platformMap.put("Game Boy Advance", 24L);
                    platformMap.put("Amstrad CPC", 25L);
                    platformMap.put("ZX Spectrum", 26L);
                    platformMap.put("MSX", 27L);
                    platformMap.put("Sega Mega Drive/Genesis", 29L);
                    platformMap.put("Sega 32X", 30L);
                    platformMap.put("Sega Saturn", 32L);
                    platformMap.put("Game Boy", 33L);
                    platformMap.put("Android", 34L);
                    platformMap.put("Sega Game Gear", 35L);
                    platformMap.put("Nintendo 3DS", 37L);
                    platformMap.put("PlayStation Portable", 38L);
                    platformMap.put("iOS", 39L);
                    platformMap.put("Wii U", 41L);
                    platformMap.put("N-Gage", 42L);
                    platformMap.put("Tapwave Zodiac", 44L);
                    platformMap.put("PlayStation Vita", 46L);
                    platformMap.put("PlayStation 4", 48L);
                    platformMap.put("Xbox One", 49L);
                    platformMap.put("3DO Interactive Multiplayer", 50L);
                    platformMap.put("Family Computer Disk System", 51L);
                    platformMap.put("Arcade", 52L);
                    platformMap.put("MSX2", 53L);
                    platformMap.put("Legacy Mobile Device", 55L);
                    platformMap.put("WonderSwan", 57L);
                    platformMap.put("Super Famicom", 58L);
                    platformMap.put("Atari 2600", 59L);
                    platformMap.put("Atari 7800", 60L);
                    platformMap.put("Atari Lynx", 61L);
                    platformMap.put("Atari Jaguar", 62L);
                    platformMap.put("Sega Master System/Mark III", 64L);
                    platformMap.put("Atari 8-bit", 65L);
                    platformMap.put("Atari 5200", 66L);
                    platformMap.put("Intellivision", 67L);
                    platformMap.put("ColecoVision", 68L);
                    platformMap.put("BBC Microcomputer System", 69L);
                    platformMap.put("Vectrex", 70L);
                    platformMap.put("Commodore VIC-20", 71L);
                    platformMap.put("Ouya", 72L);
                    platformMap.put("BlackBerry OS", 73L);
                    platformMap.put("Windows Phone", 74L);
                    platformMap.put("Sharp X1", 77L);
                    platformMap.put("Sega CD", 78L);
                    platformMap.put("Neo Geo MVS", 79L);
                    platformMap.put("Neo Geo AES", 80L);
                    platformMap.put("Web browser", 82L);
                    platformMap.put("SG-1000", 84L);
                    platformMap.put("Donner Model 30", 85L);
                    platformMap.put("TurboGrafx-16/PC Engine", 86L);
                    platformMap.put("Microvision", 89L);
                    platformMap.put("Commodore PET", 90L);
                    platformMap.put("Bally Astrocade", 91L);
                    platformMap.put("Commodore 16", 93L);
                    platformMap.put("Commodore Plus/4", 94L);
                    platformMap.put("PDP-1", 95L);
                    platformMap.put("PDP-10", 96L);
                    platformMap.put("DEC GT40", 98L);
                    platformMap.put("Family Computer", 99L);
                    platformMap.put("Analogue electronics", 100L);
                    platformMap.put("Ferranti Nimrod Computer", 101L);
                    platformMap.put("EDSAC", 102L);
                    platformMap.put("PDP-7", 103L);
                    platformMap.put("HP 2100", 104L);
                    platformMap.put("HP 3000", 105L);
                    platformMap.put("SDS Sigma 7", 106L);
                    platformMap.put("Call-A-Computer time-shared mainframe computer system", 107L);
                    platformMap.put("CDC Cyber 70", 109L);
                    platformMap.put("PLATO", 110L);
                    platformMap.put("Imlac PDS-1", 111L);
                    platformMap.put("Microcomputer", 112L);
                    platformMap.put("OnLive Game System", 113L);
                    platformMap.put("Amiga CD32", 114L);
                    platformMap.put("Apple IIGS", 115L);
                    platformMap.put("Acorn Archimedes", 116L);
                    platformMap.put("Philips CD-i", 117L);
                    platformMap.put("Sharp X68000", 121L);
                    platformMap.put("Nuon", 122L);
                    platformMap.put("WonderSwan Color", 123L);
                    platformMap.put("SwanCrystal", 124L);
                    platformMap.put("TRS-80", 126L);
                    platformMap.put("Fairchild Channel F", 127L);
                    platformMap.put("PC Engine SuperGrafx", 128L);
                    platformMap.put("Acorn Electron", 134L);
                    platformMap.put("Hyper Neo Geo 64", 135L);
                    platformMap.put("Neo Geo CD", 136L);
                    platformMap.put("New Nintendo 3DS", 137L);
                    platformMap.put("VC 4000", 138L);
                    platformMap.put("1292 Advanced Programmable Video System", 139L);
                    platformMap.put("AY-3-8500", 140L);
                    platformMap.put("AY-3-8610", 141L);
                    platformMap.put("PC-50X Family", 142L);
                    platformMap.put("AY-3-8760", 143L);
                    platformMap.put("AY-3-8710", 144L);
                    platformMap.put("AY-3-8605", 146L);
                    platformMap.put("AY-3-8606", 147L);
                    platformMap.put("AY-3-8607", 148L);
                    platformMap.put("PC-9800 Series", 149L);
                    platformMap.put("Turbografx-16/PC Engine CD", 150L);
                    platformMap.put("TRS-80 Color Computer", 151L);
                    platformMap.put("FM-7", 152L);
                    platformMap.put("Amstrad PCW", 154L);
                    platformMap.put("Tatung Einstein", 155L);
                    platformMap.put("Thomson MO5", 156L);
                    platformMap.put("NEC PC-6000 Series", 157L);
                    platformMap.put("Commodore CDTV", 158L);
                    platformMap.put("Windows Mixed Reality", 161L);
                    platformMap.put("SteamVR", 163L);
                    platformMap.put("Daydream", 164L);
                    platformMap.put("Pokémon mini", 166L);
                    
                    List<String> platformNames = new ArrayList<>();
                    platformNames.add("PlayStation 5");
                    platformNames.add("PC");
                    platformNames.add("PlayStation");
                    platformNames.add("PlayStation 2");
                    platformNames.add("PlayStation 3");
                    platformNames.add("PlayStation 4");
                    platformNames.add("PlayStation 5");
                    platformNames.add("PlayStation Portable");
                    platformNames.add("PlayStation Vita");
                    platformNames.add("Xbox");
                    platformNames.add("Xbox 360");
                    platformNames.add("Xbox One");
                    platformNames.add("Xbox Series X|S");
                    platformNames.add("Family Computer Disk System");
                    platformNames.add("NES");
                    platformNames.add("Super Nintendo");
                    platformNames.add("Super Famicom");
                    platformNames.add("Nintendo 64");
                    platformNames.add("Game Boy");
                    platformNames.add("Game Boy Color");
                    platformNames.add("Game Boy Advance");
                    platformNames.add("Nintendo DS");
                    platformNames.add("Nintendo 3DS");
                    platformNames.add("Nintendo GameCube");
                    platformNames.add("Wii");
                    platformNames.add("WiiU");
                    platformNames.add("Nintendo Switch");
                    platformNames.add("Sega Genesis");
                    platformNames.add("Sega 32X");
                    platformNames.add("Sega Saturn");
                    platformNames.add("Sega Dreamcast");
                    platformNames.add("Sega Game Gear");
                    platformNames.add("Atari 2600");
                    platformNames.add("Commodore C64/128/MAX");
                    platformNames.add("Amiga");
                    platformNames.add("iOS");
                    platformNames.add("Android");
                    platformNames.add("Arcade");
                    
                    // Remove entries from platformMap whose keys are not in platformNames
                    platformMap.keySet().retainAll(platformNames);

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

    
    public String GetGameCoverImageURL(String gameID) 
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
            if(response.body().contains("image_id") == false)
            {
                return null;
            }
            String image_id = response.body().substring(response.body().indexOf("image_id") + 12, response.body().indexOf("}") - 4);
            return "https://images.igdb.com/igdb/image/upload/t_cover_big/" + image_id + ".jpg";
        }
        catch (InterruptedException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> GetGameScreenshotURLS(String gameID)
    {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.igdb.com/v4/screenshots"))
            .header("Client-ID", clientID)
            .header("Authorization", "Bearer " + authToken)
            .method("POST", HttpRequest.BodyPublishers.ofString("fields image_id; where game = " + gameID + "; limit 5;"))
            .build();
        HttpResponse<String> response = null;
        try
        {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            List<String> screenshotUrls = new ArrayList<>();
            String body = response.body().trim();

            // Remove leading and trailing brackets if present.
            if (body.startsWith("[") && body.endsWith("]")) {
                body = body.substring(1, body.length() - 1);
            }

            // Split the result string by "}," to separate each object
            String[] items = body.split("},");
            for (String item : items) 
            {
                // Append closing bracket if it was removed during splitting
                if (!item.endsWith("}")) 
                {
                    item = item + "}";
                }
                // Locate the image_id value
                int imageIndex = item.indexOf("image_id");
                if (imageIndex != -1) {
                    int colonIndex = item.indexOf(":", imageIndex);
                    int firstQuote = item.indexOf("\"", colonIndex);
                    int secondQuote = item.indexOf("\"", firstQuote + 1);
                    if (firstQuote != -1 && secondQuote != -1) {
                        String imageId = item.substring(firstQuote + 1, secondQuote);
                        // Build screenshot URL and add to list  
                        screenshotUrls.add("https://images.igdb.com/igdb/image/upload/t_screenshot_med/" + imageId + ".jpg");
                    }
                }
            }
            return screenshotUrls;
            //String image_id = response.body().substring(response.body().indexOf("image_id") + 12, response.body().indexOf("}") - 4);
        }
        catch (InterruptedException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    

    public List<Game> RetrieveFeaturedGames() 
    {
        APICalypse apicalypse = new APICalypse().fields("*").limit(12).sort("rating", Sort.DESCENDING);
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

    public String GetGameDescription(String gameID)
    {
        APICalypse apicalypse = new APICalypse().fields("summary").where("id = " + gameID);
        try 
        {
            List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);
            if (games.size() > 0) 
            {
                return games.get(0).getSummary();
            } 
            else 
            {
                System.out.println("Game not found with ID: " + gameID);
                return null;
            }
        } 
        catch (RequestException e) 
        {
            System.out.println("Error retrieving game description: " + e.getMessage());
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

    public String GetGameIDByName(String gameName) 
    {
        APICalypse apicalypse = new APICalypse().search(gameName).fields("id").limit(1);
        try 
        {
            List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);
            if (games.size() > 0) 
            {
                return String.valueOf(games.get(0).getId());
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

        APICalypse apicalypse = new APICalypse().fields("*").where("genres = " + genreID).sort("rating", Sort.DESCENDING).limit(20);
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
        Long platformID = platformMap.get(platform);

        APICalypse apicalypse = new APICalypse().fields("*").where("platforms = " + platformID).sort("rating", Sort.DESCENDING).limit(20);
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
            System.out.println("Error retrieving list: " + e.getMessage());
            return null;
        }
    }

    public String getAuthToken()
    {
        return authToken;
    }
}
