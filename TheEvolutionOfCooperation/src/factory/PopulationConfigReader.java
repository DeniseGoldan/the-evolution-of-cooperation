package factory;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import player.Player;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PopulationConfigReader {

    public static List<Player> getPlayersFromConfigFile(final String filePath) throws IOException, ParseException {
        JSONObject jsonObject = getJsonObjectFromConfigFile(filePath);
        List<Player> players = new ArrayList<>();
        for (Object playerType : jsonObject.keySet()) {
            Long count = (Long) jsonObject.get(playerType);
            for (int i = 0; i < count; i++) {
                players.add(Player.getNewPlayerOfType((String) playerType));
            }
        }
        return players;
    }

    static JSONObject getJsonObjectFromConfigFile(final String filePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object parsingResultObject = parser.parse(new FileReader(filePath));
        return (JSONObject) parsingResultObject;
    }

}
