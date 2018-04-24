package factory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import player.Action;
import strategies.genetic.GeneticAlgorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StrategyWriter {

    static void populateNewFileWithStrategyData(GeneticAlgorithm geneticAlgorithm) throws IOException, ParseException {
        JSONObject jsonToDumpInFile = new JSONObject();
        JSONArray encodedStrategy = buildEncodedStrategyJsonArray(geneticAlgorithm);
        populateJsonWithStrategyData(geneticAlgorithm, jsonToDumpInFile, encodedStrategy);
        File newFile = createNewJsonFileInANewDirectory();
        dumpJsonToFile(jsonToDumpInFile, newFile);
    }

    private static void dumpJsonToFile(JSONObject jsonToDumpInFile, File newFile) throws IOException {
        FileWriter writer = new FileWriter(newFile);
        writer.write(jsonToDumpInFile.toJSONString());
        writer.flush();
    }

    @SuppressWarnings("unchecked")
    private static JSONArray buildEncodedStrategyJsonArray(GeneticAlgorithm geneticAlgorithm) throws IOException, ParseException {
        List<Action> genes = geneticAlgorithm.buildStrategy().getGenes();
        JSONArray encodedStrategy = new JSONArray();
        for (Action currentAction : genes) {
            encodedStrategy.add(currentAction.toString());
        }
        return encodedStrategy;
    }

    @SuppressWarnings("unchecked")
    private static void populateJsonWithStrategyData(GeneticAlgorithm geneticAlgorithm, JSONObject jsonObject, JSONArray encodedStrategy) throws IOException, ParseException {
        jsonObject.put(StrategyJsonColumnName.EncodedStrategy.getColumnName(), encodedStrategy);
        jsonObject.put(StrategyJsonColumnName.PopulationSize.getColumnName(), geneticAlgorithm.getPopulationSize());
        jsonObject.put(StrategyJsonColumnName.NumberOfGenerations.getColumnName(), geneticAlgorithm.getNumberOfGenerations());
        jsonObject.put(StrategyJsonColumnName.CrossoverProbability.getColumnName(), geneticAlgorithm.getCrossoverProbability());
        jsonObject.put(StrategyJsonColumnName.MutationProbability.getColumnName(), geneticAlgorithm.getMutationProbability());
        jsonObject.put(StrategyJsonColumnName.NumberOfRoundsPerMatch.getColumnName(), geneticAlgorithm.getNumberOfRoundsPerMatch());
        jsonObject.put(StrategyJsonColumnName.FitnessScore.getColumnName(), geneticAlgorithm.getBestChromosomeFitnessScore());
        jsonObject.put(StrategyJsonColumnName.TrainingPopulationConfiguration.getColumnName(), PopulationConfigReader.getJsonObjectFromConfigFile(FilePath.TrainingPhase.getPath()));
    }

    private static File createNewJsonFileInANewDirectory() throws IOException {
        String epoch = String.valueOf(System.currentTimeMillis());
        String rootPath =  "src/resources/";
        String parentDirectoryPath = rootPath + "/from.epoch." + epoch;
        String jsonFilePath = parentDirectoryPath + "/strategy.json";
        File newDirectory = new File(parentDirectoryPath);
        newDirectory.mkdir();
        File newJsonFile = new File(jsonFilePath);
        newJsonFile.createNewFile();
        return newJsonFile;
    }

}
