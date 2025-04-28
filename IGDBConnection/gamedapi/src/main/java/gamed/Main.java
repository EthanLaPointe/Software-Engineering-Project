package gamed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.api.igdb.utils.ImageBuilderKt;
import com.api.igdb.utils.ImageSize;
import com.api.igdb.utils.ImageType;

import proto.Game;

public class Main 
{
    public static void main(String[] args) 
    {
        String clientID = "86hpmu9gws96n5ipkekcq715bq77tj";
        String clientSecret = "zhmdic84egb7xi2wfwttuwwvq8uiql";

        String wishlistFile = "IGDBConnection\\gamedapi\\src\\main\\resources\\Wishlist.csv";
        String favoritesFile = "IGDBConnection\\gamedapi\\src\\main\\resources\\Favorites.csv";

        APIHandler handler = new APIHandler(clientID, clientSecret);

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
                        String imageURL = GetImageURL(game);
                        System.out.println("Image URL: " + imageURL);
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
        String image_id = game.getCover().getImageId();
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