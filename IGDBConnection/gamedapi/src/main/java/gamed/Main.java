package gamed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;

import com.api.igdb.utils.ImageBuilderKt;
import com.api.igdb.utils.ImageSize;
import com.api.igdb.utils.ImageType;

import proto.Game;
import proto.Cover;
import proto.CoverOrBuilder;

public class Main 
{
    public static void main(String[] args) 
    {
        String clientID = "86hpmu9gws96n5ipkekcq715bq77tj";
        String clientSecret = "zhmdic84egb7xi2wfwttuwwvq8uiql";

        String wishlistFile = "IGDBConnection\\gamedapi\\src\\main\\resources\\Wishlist.csv";
        String favoritesFile = "IGDBConnection\\gamedapi\\src\\main\\resources\\Favorites.csv";

        APIHandler handler = APIHandler.INSTANCE;
        handler.SetClientID(clientID);
        handler.SetClientSecret(clientSecret);
        handler.Initialize();

        int choice = 0;
        Scanner scan = new Scanner(System.in);

        while(choice != 6) 
        {
            choice = 0;
            System.out.println("Enter your choice: ");
            System.out.println("1. Enter a game ID to retrieve its details:");
            System.out.println("2. Retrieve games from wishlist");
            System.out.println("3. Add games to wishlist");
            System.out.println("4. Retrieve games from favorites");
            System.out.println("5. Add games to favorites");
            System.out.println("6. Exit");
            choice = scan.nextInt();

            switch (choice)
            {
                case 1:
                    System.out.println("Enter the game ID: ");
                    String gameID = scan.next();
                    Game game = handler.RetrieveGameByID(gameID);
                    if (game != null) 
                    {
                        printGameDetails(game);
                        Long gameId = game.getId();
                        String imageURL = handler.GetGameCoverImageURL(gameId.toString());
                        String fullImageURL = ImageBuilderKt.imageBuilder(imageURL, ImageSize.COVER_SMALL, ImageType.PNG);
                        System.out.println("Image URL: " + fullImageURL);
                    }
                    else
                    {
                        System.out.println("Game not found with ID: " + gameID);
                    }
                    break;
                case 2:
                    System.out.println("Retrieving games from wishlist...");
                    List<String> wishlistIDs = RetrieveIDList(wishlistFile);
                    List<Game> wishlist = handler.RetrieveWishlist(wishlistIDs);
                    for (Game g : wishlist) 
                    {
                        System.out.println("Game ID: " + g.getId() + ", Name: " + g.getName());
                    }
                    break;
                case 3:
                    System.out.println("Enter ID of the game to add to wishlist: ");
                    String idToAdd = scan.next();
                    if (handler.RetrieveGameByID(idToAdd) != null) 
                    {
                        AddIDToList(wishlistFile, idToAdd);
                    }
                    else
                    {
                        System.out.println("Game not found with ID: " + idToAdd);
                    }
                    break;
                case 4:
                    System.out.println("Retrieving games from favorites...");
                    List<String> favoritesIDs = RetrieveIDList(favoritesFile);
                    List<Game> favorites = handler.RetrieveWishlist(favoritesIDs);
                    for (Game g : favorites) 
                    {
                        System.out.println("Game ID: " + g.getId() + ", Name: " + g.getName());
                    }
                    break;
                case 5:
                    System.out.println("Enter ID of the game to add to favorites: ");
                    idToAdd = scan.next();
                    if (handler.RetrieveGameByID(idToAdd) != null) 
                    {
                        AddIDToList(favoritesFile, idToAdd);
                    }
                    else
                    {
                        System.out.println("Game not found with ID: " + idToAdd);
                    }
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                case 7:
                    //System.out.println("Enter the game ID to retrieve its cover image:");
                    //String gameIDToRetrieve = scan.next();
                    //Game gameToRetrieve = handler.RetrieveGameByID(gameIDToRetrieve);
                    URL coverURL;

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("https://api.igdb.com/v4/covers"))
                            .header("Client-ID", clientID)
                            .header("Authorization", "Bearer " + handler.getAuthToken())
                            .method("POST", HttpRequest.BodyPublishers.ofString("fields image_id; where game = 8945;"))
                            .build();
                    HttpResponse<String> response = null;
                    try
                        {
                            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                            System.out.println(response.body());
                            String image_id = response.body().substring(response.body().indexOf("image_id") + 12, response.body().indexOf("}") - 4);
                            System.out.println("Image ID: " + image_id);
                        }
                        catch (InterruptedException | IOException e)
                        {
                            e.printStackTrace();
                        }
                    break;
                case 8:
                    System.out.println("Access token: " + handler.getAuthToken());
                    break;
                case 9:
                    
                    HttpRequest scRequest = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.igdb.com/v4/screenshots"))
                        .header("Client-ID", clientID)
                        .header("Authorization", "Bearer " + handler.getAuthToken())
                        .method("POST", HttpRequest.BodyPublishers.ofString("fields image_id; where game = " + "7346" + ";"))
                        .build();
                    HttpResponse<String> scResponse = null;
                    try
                    {
                        response = HttpClient.newHttpClient().send(scRequest, HttpResponse.BodyHandlers.ofString());
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
                                    screenshotUrls.add("https://images.igdb.com/igdb/image/upload/t_screenshot_big/" + imageId + ".jpg");
                                    System.out.println("Screenshot URL: " + screenshotUrls.get(screenshotUrls.size() - 1));
                                }
                            }
                        }
                        //String image_id = response.body().substring(response.body().indexOf("image_id") + 12, response.body().indexOf("}") - 4);
                    }
                    catch (InterruptedException | IOException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case 10:
                    System.out.println("Enter game genre: ");
                    String genre = scan.next();
                    System.out.println("Retrieving games by genre...");
                    List<Game> genreGames = handler.SearchGameByGenre(genre);
                    for (Game g : genreGames) 
                    {
                        System.out.println("Game ID: " + g.getId() + ", Name: " + g.getName());
                    }
                    break;
                case 11:
                    System.out.println("Getting all platforms...");
                    HashMap<Long, String> platforms = handler.getAllPlatforms();
                    for (Long id : platforms.keySet()) 
                    {
                        System.out.println("Platform ID: " + id + ", Name: " + platforms.get(id));
                    }
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void printGameDetails(Game game) 
    {
        System.out.println("Game ID: " + game.getId());
        System.out.println("Name: " + game.getName());
        System.out.println("Summary: " + game.getSummary());
        System.out.println("Rating: " + game.getRating());
        System.out.println("Genres: " + game.getGenresList());
        System.out.println("Platforms: " + game.getPlatformsList());
        System.out.println("Userscore: " + game.getAggregatedRating());
    }

    private static String GetImageURL(Game game) 
    {
        CoverOrBuilder cover = game.getCoverOrBuilder();
        String image_id = game.getArtworks(1).getImageId();
        System.out.println("Image id:" + cover.getUrl());
        String imageURL = ImageBuilderKt.imageBuilder(image_id, ImageSize.SCREENSHOT_HUGE, ImageType.PNG);
        return imageURL;
    }

    private static List<String> RetrieveIDList(String fileName) 
    {
        String line = "";
        String csvSplitBy = ",";

        List<String> gameIDs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) 
        {
            while ((line = br.readLine()) != null) 
            {
                String[] data = line.split(csvSplitBy);
                for (String value : data) 
                {
                    gameIDs.add(value);
                }
            }

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        return gameIDs;
    }

    private static void AddIDToList(String fileName, String gameID) 
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) 
        {
            bw.write("\n" + gameID);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}