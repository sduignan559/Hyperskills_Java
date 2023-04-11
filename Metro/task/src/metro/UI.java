package metro;

import java.util.*;
import static metro.Main.depoList;

public class UI {
    void processInput(LinkedHashMap<String, LinkedList<Stations>> laneMap) {

        while (true) {
            Scanner scanner = new Scanner(System.in);
            List<String> options = getCommandTokens(scanner.nextLine());
            String line = "";
            String station = "";
            String connectStation = "";
            String connectLine = "";

            String command = options.get(0);
            if (options.size() > 1) {
                line = options.get(1);
            }
            if (options.size() > 2) {
                station = options.get(2).trim();
            }
            if (options.size() > 3) {
                connectLine = options.get(3).trim();
            }
            if (options.size() > 4) {
                connectStation = options.get(4).trim();
            }

            if (command.equals("/append")) {
                LinkedList<Stations> depoListtemp = laneMap.get(line);
                Stations stations = new Stations();
                stations.name = station;
                depoListtemp.addLast(stations);
                laneMap.replace(line, depoListtemp);

            } else if (command.equals("/add-head")) {
                LinkedList<Stations> depoListtemp = laneMap.get(line);
                Stations stations = new Stations();
                stations.name = station;
                depoListtemp.addFirst(stations);
                laneMap.replace(line, depoListtemp);

            } else if (command.equals("/remove")) {
                LinkedList<Stations> depoListtemp = laneMap.get(line);
                Stations stations = new Stations();
                stations.name = station;
                depoListtemp.remove(stations);
                laneMap.replace(line, depoListtemp);

            } else if (command.equals("/output")) {
                if (laneMap.containsKey(line)) {
                    printLineStations(laneMap, line);
                } else {
                    System.out.println("Error: invalid station name");
                }

            } else if (command.equals("/connect")) {
                connectStations(laneMap, connectLine, connectStation, line, station);

            } else if (command.equals("/fastest-route")) {
                FastestRoutes fastestRoutes = new FastestRoutes();
                fastestRoutes.getAdjanceymatrix(laneMap);
                fastestRoutes.findWays(line,station,connectLine,connectStation,laneMap);

            } else if (command.equals("/route")) {
                ShortestRoutes routes = new ShortestRoutes();
                routes.getAdjanceymatrix(laneMap);
                routes.findWays(line,station,connectLine,connectStation,laneMap);

            } else if (command.equals("/exit")) {
                System.exit(0);
            }
        }
    }

    public void printLineStations(LinkedHashMap<String, LinkedList<Stations>> metroData, String lineName) {
        if (metroData.containsKey(lineName)) {
            LinkedList<Stations> depoList = new LinkedList<Stations>(metroData.get(lineName));

            System.out.println("depot");
            for (Stations eachStation : depoList) {
                if (eachStation.transfers.isEmpty()) {
                    System.out.println(eachStation.name);
                } else

                {
                    System.out.print(eachStation.name);
                    for (String lines : eachStation.transfers.keySet()) {
                        for (String station : eachStation.transfers.get(lines)) {
                            if (station == null) {
                                continue;
                            } else {
                                System.out.printf(String.valueOf(eachStation.transfers) + eachStation.transfers.size());
                            }
                        }
                    }
                    System.out.println();
                }
            }
        }
        else {
            System.out.println("Error: invalid line name");
        }
        System.out.println("depot");
    }

    private static List<String> getCommandTokens(String command) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean hasFoundQuotes = false;
        for (char c : command.toCharArray()) {
            if (c == '"' && !hasFoundQuotes) {
                hasFoundQuotes = true;
            } else if (c == '"' && hasFoundQuotes) {
                result.add(sb.toString());
                sb.setLength(0);
                hasFoundQuotes = false;
            } else if (c == ' ' && !hasFoundQuotes && sb.length() > 0) {
                result.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        if (sb.length() > 0) {
            result.add(sb.toString());
        }
        return result;
    }

    public HashMap<String, LinkedList<Stations>> connectStations(HashMap<String, LinkedList<Stations>> laneMap, String connectLine, String connectStation, String line,
                                                                 String station) {

        LinkedList<Stations > oldStationList = laneMap.get(connectLine);
        LinkedList<Stations > newStationList = new LinkedList<>();
        for(Stations  station1 : oldStationList) {
            if (station1.name.equals(connectStation)) {
                HashMap<String, ArrayList<String>> updatedTransfersMap = station1.transfers;
                ArrayList<String> updatedTransfervalues = new ArrayList<>();
                updatedTransfervalues.add(station);
                station1.transfers.put(line, updatedTransfervalues);
                newStationList.add(station1);
            } else {
                newStationList.add(station1);
            }
        }
        laneMap.replace(connectLine,newStationList);

        LinkedList<Stations > oldStationList2 = new LinkedList<>();
        oldStationList2 = laneMap.get(line);
        LinkedList<Stations > newStationList2 = new LinkedList<>();
        for(Stations  station2 : oldStationList2)
        {
            if (station2.name.equals(station)) {
                HashMap<String, ArrayList<String>> updatedTransfersMap = station2.transfers;
                ArrayList<String> updatedTransfervalues = new ArrayList<>();
                updatedTransfervalues.add(station);
                station2.transfers.put(connectLine,updatedTransfervalues);
                newStationList2.add(station2);
            }
            else {
                newStationList2.add(station2);
            }
        }
        laneMap.replace(station,newStationList2);
        return laneMap;
    }
}