package factory;

import org.json.simple.JSONObject;
import player.Player;
import strategies.standard.AlwaysCooperatePlayer;
import strategies.standard.AlwaysDefectPlayer;
import strategies.standard.GrudgerPlayer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class PopulationConfigWriter {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        HashMap<Player, Integer> playerCountByType = designCountByPlayerType();
        JSONObject jsonObject = new JSONObject();
        for (Player player: playerCountByType.keySet()) {
            jsonObject.put(player.getPlayerType(), playerCountByType.get(player));
        }
        dumpJsonToFile(jsonObject, new File(FilePath.TrainingPhase.getPath()));
    }

    private static HashMap<Player, Integer> designCountByPlayerType() {

        HashMap<Player, Integer> playerCountByType = new HashMap<>();

        playerCountByType.put(new GrudgerPlayer(), 2);
        playerCountByType.put(new AlwaysCooperatePlayer(), 2);
        playerCountByType.put(new AlwaysDefectPlayer(), 1);

        return playerCountByType;
    }

    private static void dumpJsonToFile(JSONObject jsonToDumpInFile, File newFile) throws IOException {
        FileWriter writer = new FileWriter(newFile);
        writer.write(jsonToDumpInFile.toJSONString());
        writer.flush();
    }

}
