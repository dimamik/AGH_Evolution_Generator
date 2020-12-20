package logic.statistics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;


public class MapStatisticsWriter {

    private final MapStatistics mapStatistics;
    private final MapStatisticsPropertyGetter mapStatisticsPropertyGetter;
    private final double[] averageDominantGenome;
    private long plantsOnMapSum;
    private long animalsOnMapSum;
    private double averageEnergyForAllDaysSum;
    private double averageLiveDurationSum;
    private double averageKidsNumberSum;

    public MapStatisticsWriter(MapStatisticsPropertyGetter mapStatisticsPropertyGetter) {
        this.mapStatisticsPropertyGetter = mapStatisticsPropertyGetter;
        this.mapStatistics = mapStatisticsPropertyGetter.getMapStatistics();
        averageDominantGenome = new double[8];
    }


    private Optional<JSONArray> getJsonArrayIfPresent(String path) {
        JSONParser jsonParser = new JSONParser();
        JSONArray statisticsArray;
        try (FileReader reader = new FileReader(path)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            statisticsArray = (JSONArray) obj;


        } catch (IOException | ParseException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(statisticsArray);
    }


    public void updateStatisticsWriter() {
        ObservableList<Double> var = mapStatisticsPropertyGetter.getAverageGenomeTypesList();
        double dominantGenome = Collections.max(var);
        for (int i = 0; i < 8; i++) {
            if (var.get(i) == dominantGenome) {
                averageDominantGenome[i] += 1;
                break;
            }
        }
        this.plantsOnMapSum += (mapStatisticsPropertyGetter.getSumGrass());
        this.animalsOnMapSum += (mapStatisticsPropertyGetter.getSumAnimalsAlive());
        this.averageEnergyForAllDaysSum += (mapStatisticsPropertyGetter.getAverageEnergyForAliveAnimals());
        this.averageKidsNumberSum += (mapStatisticsPropertyGetter.getAverageKidsNumberForAliveAnimals());
        this.averageLiveDurationSum += (mapStatisticsPropertyGetter.getAverageLiveDurationForDeadAnimals());
    }


    private String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now));
    }

    @SuppressWarnings("unchecked")
    private JSONObject convertStatisticsToJson() {


        JSONObject jsonObject = new JSONObject();

        jsonObject.put("Date of adding:", getCurrentDate());

        jsonObject.put("Day in Animation:", mapStatistics.getDayOfAnimation());


        double defaultValue = 0;
        if (mapStatistics.getDayOfAnimation() != 0) {
            defaultValue = animalsOnMapSum / (double) mapStatistics.getDayOfAnimation();
        }
        jsonObject.put("Average alive animals in all days:", RoundDoubleValue.round(defaultValue, 2));

        if (mapStatistics.getDayOfAnimation() != 0) {
            defaultValue = plantsOnMapSum / (double) mapStatistics.getDayOfAnimation();
        }
        jsonObject.put("Average grass on map in all days:", RoundDoubleValue.round(defaultValue, 2));

        if (mapStatistics.getDayOfAnimation() != 0) {
            defaultValue = averageEnergyForAllDaysSum / (double) mapStatistics.getDayOfAnimation();
        }
        jsonObject.put("Average energy level in all days:", RoundDoubleValue.round(defaultValue, 2));

        if (mapStatistics.getDayOfAnimation() != 0) {
            defaultValue = averageLiveDurationSum / (double) mapStatistics.getDayOfAnimation();
        }
        jsonObject.put("Average live duration in all days:", RoundDoubleValue.round(defaultValue, 2));

        if (mapStatistics.getDayOfAnimation() != 0) {
            defaultValue = averageKidsNumberSum / (double) mapStatistics.getDayOfAnimation();
        }
        jsonObject.put("Average kids number in all days:", RoundDoubleValue.round(defaultValue, 2));

        double max_value = Double.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < 8; i++) {
            if (max_value < averageDominantGenome[i]) {
                max_value = averageDominantGenome[i];
                index = i;
            }
        }
        jsonObject.put("Average dominant genome in all days (number) is:",
                index
        );


        return jsonObject;

    }


    @SuppressWarnings("unchecked")
    public void writeStatistics() {
        String path = "src/main/resources/statisticsOut/statistics.json";

        JSONArray statisticsArray = new JSONArray();
        if (getJsonArrayIfPresent(path).isPresent())
            statisticsArray = getJsonArrayIfPresent(path).get();
        statisticsArray.add(convertStatisticsToJson());

        try (FileWriter file = new FileWriter(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement je = JsonParser.parseString(statisticsArray.toJSONString());
            String prettyJsonString = gson.toJson(je);
            file.write(prettyJsonString);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
