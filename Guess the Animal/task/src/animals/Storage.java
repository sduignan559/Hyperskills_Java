package animals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;

public class Storage {
    private static final String ANIMAL_FILE_NAME = "animals";

    // Get storage type from command line.
    public static String getStorageType(String[] args) {
        final String exceptionMessage = "Only \"-type [json|xml|yaml]\" is supported";

        String storageType;

        if (args.length == 0) {
            storageType = "json";
        } else if (args.length == 2) {
            if (args[0].equals("-type")) {
                switch (args[1]) {
                    case "json", "xml", "yaml" -> {
                        storageType = args[1];
                    }
                    default -> {
                        throw new IllegalArgumentException(exceptionMessage);
                    }
                }
            } else {
                throw new IllegalArgumentException(exceptionMessage);
            }
        } else {
            throw new IllegalArgumentException(exceptionMessage);
        }

        return storageType;
    }

    // Read animal data.
    public static Node readAnimalData(String storageType) {
        // Get Jackson object mapper.
        ObjectMapper objectMapper = getObjectMapper(storageType);

        // Does file not exist?
        File file = new File(ANIMAL_FILE_NAME + "." + storageType);
        if (!file.exists()) {
            return null;
        }

        // Read animal data from file.
        Node root = null;
        try {
            root = objectMapper.readValue(file, Node.class);
        } catch (IOException e) {
            System.out.println(e.toString());
            System.exit(1);
        }

        return root;
    }

    // Write animal data.
    public static void writeAnimalData(String storageType, Node rootNode) {
        // Get Jackson object mapper.
        ObjectMapper objectMapper = getObjectMapper(storageType);

        // Write animal data to file.
        File file = new File(ANIMAL_FILE_NAME + "." + storageType);
        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(file, rootNode);
        } catch (IOException e) {
            System.out.println(e.toString());
            System.exit(1);
        }
    }

    // Get Jackson object mapper for storage type.
    private static ObjectMapper getObjectMapper(String storageType) {
        return switch (storageType) {
            case "json" -> {
                yield new JsonMapper();
            }
            case "xml" -> {
                yield new XmlMapper();
            }
            case "yaml" -> {
                yield new YAMLMapper();
            }
            default -> {
                yield null;
            }
        };
    }
}