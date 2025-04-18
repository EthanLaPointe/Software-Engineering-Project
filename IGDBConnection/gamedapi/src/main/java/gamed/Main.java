package gamed;

import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.TwitchAuthenticator;
import com.api.igdb.utils.TwitchToken;
import com.api.igdb.utils.Endpoints;
import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.apicalypse.Sort;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.JsonRequestKt;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.utils.ImageBuilderKt;
import com.api.igdb.utils.ImageSize;
import com.api.igdb.utils.ImageType;
import proto.Cover;
import proto.Game;
import proto.Search;
import proto.GameResult;

import java.util.List;

import com.api.igdb.*;

public class Main 
{
    public static void main(String[] args) 
    {
        String clientID = "86hpmu9gws96n5ipkekcq715bq77tj";
        String clientSecret = "zhmdic84egb7xi2wfwttuwwvq8uiql";

        TwitchAuthenticator auth = TwitchAuthenticator.INSTANCE;
        TwitchToken token = auth.requestTwitchToken(clientID, clientSecret);

        String authenticationToken = token.getAccess_token();

        IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
        wrapper.setCredentials(clientID, authenticationToken);
        /*
        APICalypse apicalypse = new APICalypse().fields("*").sort("release_dates.date", Sort.DESCENDING);
        try
        {
            List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);

            for(int i = 0; i < 10; i++)
            {
                System.out.println("Game " + i + ": " + games.get(i).getName() + " ID: " + games.get(i).getId());
            }
        } 
        catch(RequestException e) 
        {
            System.out.println("Error");
        }*/

        Game game = retrieveGameByID(wrapper, 133236);

        printGameDetails(game);
    }

    private static Game retrieveGameByID(IGDBWrapper wrapper, int gameID) 
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

    private static void printGameDetails(Game game) 
    {
        System.out.println("Game ID: " + game.getId());
        System.out.println("Name: " + game.getName());
        System.out.println("Summary: " + game.getSummary());
        System.out.println("Rating: " + game.getRating());
        System.out.println("Genres: " + game.getGenresList());
        System.out.println("Platforms: " + game.getPlatformsList());
        String image_id = game.getCover().getImageId();
        String imageURL = ImageBuilderKt.imageBuilder(image_id, ImageSize.SCREENSHOT_HUGE, ImageType.PNG);
        System.out.println("Cover Image URL: " + imageURL);
    }
}