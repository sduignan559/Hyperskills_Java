package server;

public class JsonObject {
    private final String type;
    private final String key;
    private final String value;


    public JsonObject(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
