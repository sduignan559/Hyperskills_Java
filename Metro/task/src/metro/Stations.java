package metro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Stations {
    String line;
    int time;
    int nodeIndex;
    ArrayList <String> prevStation;
    ArrayList<String> nextStation;
    String name;
    HashMap<String, ArrayList<String>> transfers = new HashMap<>();

    public Stations(int time, String name, HashMap<String, ArrayList<String>> transfers) {
        this.time = time;
        this.name = name;
        this.transfers = transfers;
    }

    public Stations(String name) {
        this.name = name;
    }

    public Stations(){
    }
}
