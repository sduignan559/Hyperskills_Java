package metro;

import com.google.gson.*;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.*;


public class Main {
    static LinkedHashMap<String, LinkedList<Stations>> metroMap = new LinkedHashMap<>();
    static LinkedList<Stations> depoList = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();
        File file = new File(args[0]);
        JsonObject elem = null;

        if (!file.isFile()) {
            System.out.println("Error! Such a file doesn't exist!");
            System.exit(0);
        }

        try {
            Reader reader = Files.newBufferedReader(file.toPath());
            try {
                elem = JsonParser.parseReader(reader).getAsJsonObject();
            } catch (Exception e) {
                System.out.println("Incorrect file");
                System.exit(0);
            }

            System.out.println(elem.entrySet());
            String[] lineName = elem.keySet().toString().replaceAll("[\\[\\]]", "").split(", ");
            for (String line : lineName) {
                LinkedList<Stations> depoList = new LinkedList<>();
                JsonArray jsonOuter = elem.getAsJsonArray(line);
                for(JsonElement jsoninner : jsonOuter){
                    Stations stations = new Stations();

                    stations.line = line;
                    stations.name = jsoninner.getAsJsonObject().get("name").getAsString();
                    try {
                        stations.time = jsoninner.getAsJsonObject().get("time").getAsInt();
                    } catch (Exception e){
                        stations.time= 0;
                    }

                    JsonArray jsonarrayprev = jsoninner.getAsJsonObject().get("prev").getAsJsonArray();
                    if (jsonarrayprev.size() > 0) {
                            ArrayList<String> prevArray = new ArrayList<>();
                            for(int i =0; i< jsonarrayprev.size(); i++) {
                                prevArray.add(jsonarrayprev.get(i).getAsString());
                            }
                            stations.prevStation = prevArray;
                        }

                    JsonArray jsonarraynext = jsoninner.getAsJsonObject().get("next").getAsJsonArray();
                    if (jsonarraynext.size() > 0) {
                        ArrayList<String> nextArray = new ArrayList<>();
                        for (int i = 0; i < jsonarraynext.size(); i++) {
                            nextArray.add(jsonarraynext.get(i).getAsString());
                        }
                        stations.nextStation = nextArray;
                    }

                    JsonArray jsonarraytrans = jsoninner.getAsJsonObject().get("transfer").getAsJsonArray();
                    String transferLine = null;
                    String transferStation = null;

                    if (jsonarraytrans.size() > 0) {
                        for (int i = 0; i < jsonarraytrans.size(); i++) {
                            JsonObject transferObj = jsonarraytrans.get(i).getAsJsonObject();
                            transferLine = transferObj.get("line").toString().replaceAll("\"", "");
                            transferStation = transferObj.get("station").toString().replaceAll("\"", "");
                        }
                    }

                    ArrayList<String> currentStation = new ArrayList<>();
                    currentStation.add(transferStation);
                    stations.transfers.put(transferLine, currentStation);
                    depoList.add(stations);
                    metroMap.put(line, depoList);
                }
            }
            System.out.println(metroMap.entrySet());

        } catch (IOException e) {
            e.printStackTrace();
        }
        UI ui = new UI();
        ui.processInput(metroMap);
    }
}

