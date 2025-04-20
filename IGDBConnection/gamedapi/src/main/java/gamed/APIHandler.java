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

public class APIHandler 
{
    String clientID = "";
    String clientSecret = "";
    IGDBWrapper wrapper = IGDBWrapper.INSTANCE;

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
