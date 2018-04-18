package factory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import player.Action;
import strategies.genetic.GeneticAlgorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StrategyWriter {

    static void populateNewFileWithStrategyData(GeneticAlgorithm geneticAlgorithm) throws IOException {
        JSONObject jsonToDumpInFile = new JSONObject();
        JSONArray encodedStrategy = buildEncodedStrategyJsonArray(geneticAlgorithm);
        populateJsonWithStrategyData(geneticAlgorithm, jsonToDumpInFile, encodedStrategy);
        File newFile = createNewJsonFile();
        dumpJsonToFile(jsonToDumpInFile, newFile);

    }

    private static void dumpJsonToFile(JSONObject jsonToDumpInFile, File newFile) throws IOException {
        FileWriter writer = new FileWriter(newFile);
        writer.write(jsonToDumpInFile.toJSONString());
        writer.flush();
    }

    @SuppressWarnings("unchecked")
    private static JSONArray buildEncodedStrategyJsonArray(GeneticAlgorithm geneticAlgorithm) {
        List<Action> genes = geneticAlgorithm.buildStrategy().getGenes();
        JSONArray encodedStrategy = new JSONArray();
        for (Action currentAction : genes) {
            encodedStrategy.add(currentAction.toString());
        }
        return encodedStrategy;
    }

    @SuppressWarnings("unchecked")
    private static void populateJsonWithStrategyData(GeneticAlgorithm geneticAlgorithm, JSONObject jsonObject, JSONArray encodedStrategy) {
        jsonObject.put(JsonFileColumnName.EncodedStrategy.getColumnName(), encodedStrategy);
        jsonObject.put(JsonFileColumnName.PopulationSize.getColumnName(), geneticAlgorithm.getPopulationSize());
        jsonObject.put(JsonFileColumnName.NumberOfGenerations.getColumnName(), geneticAlgorithm.getNumberOfGenerations());
        jsonObject.put(JsonFileColumnName.CrossoverProbability.getColumnName(), geneticAlgorithm.getCrossoverProbability());
        jsonObject.put(JsonFileColumnName.MutationProbability.getColumnName(), geneticAlgorithm.getMutationProbability());
        jsonObject.put(JsonFileColumnName.NumberOfRoundsPerMatch.getColumnName(), geneticAlgorithm.getNumberOfRoundsPerMatch());
        jsonObject.put(JsonFileColumnName.FitnessScore.getColumnName(), geneticAlgorithm.getBestChromosomeFitnessScore());
    }

    private static File createNewJsonFile() throws IOException {
        String path = "src/resources/strategy_" + System.currentTimeMillis() + ".json";
        File newFile = new File(path);
        newFile.createNewFile();
        return newFile;
    }

}