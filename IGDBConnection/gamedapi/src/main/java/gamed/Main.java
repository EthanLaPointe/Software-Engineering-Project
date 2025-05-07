package gamed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
        DBConnector dbConnector = DBConnector.INSTANCE;

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
            System.out.println("6. DB TEST");
            System.out.println("7. Exit");
            System.out.println("8. Test DB connection");

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
                    Connection connection = createConnection();
                    ResultSet dbWishlist = retrieveWishlistDB(connection);
                    ArrayList<String> dbWishlistIDs = null;
                    try 
                    {
                        dbWishlistIDs = resToArr(dbWishlist);
                    } 
                    catch (SQLException e) 
                    {
                        e.printStackTrace();
                    }
                    
                    for (String id : dbWishlistIDs) 
                    {
                        Game wishlistGame = handler.RetrieveGameByID(id);
                        if (wishlistGame != null) 
                        {
                            printGameDetails(wishlistGame);
                        }
                        else
                        {
                            System.out.println("Game not found with ID: " + id);
                        }
                    }
                    
                    //System.out.println("Wishlist IDs from DB: " + dbWishlistIDs);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scan.close();
                    return;
                case 8:
                    ArrayList<String> wishIDs = null;
                    System.out.println("Enter user ID: ");
                    int userID = scan.nextInt();
                    
                    try 
                    {
                        wishIDs = dbConnector.retrieveUserWishlist(userID);
                    } 
                    catch (SQLException e) 
                    {
                        e.printStackTrace();
                    }
                    System.out.println("Wishlist IDs from DB: " + wishIDs);
                case 9:
                    ArrayList<String> genreIDs = handler.RetrieveGameGenres("7346");
                    System.out.println(genreIDs);
                    break;
                case 10:
                    System.out.println("Enter game ID: ");
                    gameID = scan.next();
                    game = handler.RetrieveGameByID(gameID);

                    ArrayList<String> genres = handler.RetrieveGameGenres(game);
                    System.out.println(genres);
                    break;
                case 11:
                    System.out.println("Enter game name: ");
                    String gameName = scan.next();
                    List<Game> searchResults = handler.SearchGameByName(gameName);
                    if (searchResults != null) 
                    {
                        for(Game g : searchResults) 
                        {
                            System.out.println("Name: " + g.getName());
                        }
                    }
                    break;
                case 12:
                    System.out.println("Enter Genre to search: ");
                    String genre = scan.next();
                    List<Game> genreSearchResults = handler.SearchGameByGenre(genre);
                    if (genreSearchResults != null) 
                    {
                        for(Game g : genreSearchResults) 
                        {
                            System.out.println("Name: " + g.getName());
                        }
                    }
                    break;
                case 13:
                    System.out.println("Getting all platforms and IDs...");
                    HashMap<Long, String> platforms = handler.getAllPlatforms();
                    for (Long key : platforms.keySet()) 
                    {
                        System.out.println("Platform ID: " + key + ", Name: " + platforms.get(key));
                    }
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

    public static  Connection createConnection()
    {
        Connection connection;
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/gamed_db", "root", "Raznian86");
            System.out.println("Successfully connected");
            return connection;
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet retrieveWishlistDB(Connection connection)
    {
        try 
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT game_id FROM WishLists");
            return resultSet;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> resToArr(ResultSet resultSet) throws SQLException
    {
        ArrayList<String> ids = new ArrayList<>();
        while (resultSet.next()) 
        {
            ids.add(resultSet.getString("game_id"));
        }
        return ids;
    }
}