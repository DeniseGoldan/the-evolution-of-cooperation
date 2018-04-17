package factory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import player.Action;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class StrategyReader {

    @SuppressWarnings("unchecked")
    static List<Action> getStrategyFromJsonFile(String filepath) throws IOException, ParseException {

        JSONArray encodedStrategy = extractEncodedStrategyKeyValuePair(filepath);

        List<Action> genes = new ArrayList<>();
        for (String currentAction : (Iterable<String>) encodedStrategy) {
            if (currentAction.equals("Cooperate")) {
                genes.add(Action.Cooperate);
            } else {
                genes.add(Action.Defect);
            }
        }
        return genes;
    }

    private static JSONArray extractEncodedStrategyKeyValuePair(String filepath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader(filepath));
        JSONObject loadedJson = (JSONObject) object;
        return (JSONArray) loadedJson.get(JsonFileColumnName.EncodedStrategy.getColumnName());
    }

}
