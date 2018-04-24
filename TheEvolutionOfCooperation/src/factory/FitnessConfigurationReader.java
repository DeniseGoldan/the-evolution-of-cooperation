package factory;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import player.Player;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FitnessConfigurationReader {

    private static final String CONFIG_FILE_PATH = "src/factory/fitness.config.json";

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(getPlayerListFromConfigurationFile());
    }

    public static List<Player> getPlayerListFromConfigurationFile() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object parsingResultObject = parser.parse(new FileReader(CONFIG_FILE_PATH));
        JSONObject jsonObject = (JSONObject) parsingResultObject;
        List<Player> players = new ArrayList<>();
        for (Object playerType : jsonObject.keySet()) {
            Long count = (Long) jsonObject.get(playerType);
            for (int i = 0; i < count; i++) {
                players.add(Player.getNewPlayerOfType((String) playerType));
            }
        }
        return players;
    }

}
