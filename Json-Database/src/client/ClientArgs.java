package client;

import com.beust.jcommander.Parameter;

public class ClientArgs {

    @Parameter (names = {"--type","-t"})
    String type = "";

    @Parameter (names = {"--key","-k"})
    String key = "";

    @Parameter(names = {"--value", "-v"})
    String value = "";

    @Parameter(names = {"--input", "-in"})
    String fileName;

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getFilename() {
        return fileName;
    }
}
