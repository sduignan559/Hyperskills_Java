package server;

import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;
import java.io.*;
import java.net.*;

public class ClientThread extends Thread {

    static Map<String, String> Jasondb = new LinkedHashMap<String, String>();

    ServerSocket server;
    Socket socket;
    DataOutputStream output;
    DataInputStream input;

    public ClientThread(Socket socket, DataInputStream input, DataOutputStream output, ServerSocket server) {
        this.socket =socket;
        this.input =input;
        this.output = output;
        this.server = server;
    }

    @Override
    public void run() {

        try {
            String msg = input.readUTF(); // reading a message
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(msg, JsonObject.class);

            Map<String, String> result = getSetDelete(jsonObject);
            String outputMsg = gson.toJson(result);
            output.writeUTF(outputMsg); // sent result to the client

            if (jsonObject.getType().equals("exit")) {
                server.close();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

        private static Map<String,String> getSetDelete(JsonObject jason) {
            Map<String, String> argsMap = new LinkedHashMap<>();
            String path = System.getProperty("user.dir") + "/src/server/data/db.json";
            File dbFile = new File(path);
            String type = jason.getType();
            String key = jason.getKey();
            String value = jason.getValue();

            if (type.equals("exit")) {

                argsMap.put("response","OK");
                return argsMap;
            }

            if (type.equals("set"))
            {
                if(!jason.getKey().equals("")) {
                    Jasondb.put(key, value);
                    argsMap.put("response", "OK");
                    return argsMap;
                }
                else
                {
                    argsMap.put("response","ERROR");
                    argsMap.put("reason","No such key");
                    return argsMap;
                }
            }

            if (type.equals("get"))
            {
                if(Jasondb.containsKey(key))
                {
                    try
                    {
                        argsMap.put("response","OK");
                        argsMap.put("value",Jasondb.get(key));
                        return argsMap;
                    }
                    catch(Exception e)
                    {
                        argsMap.put("response","ERROR");
                        argsMap.put("reason","No such key");
                        return argsMap;
                    }
                }
                else
                {
                    argsMap.put("response","ERROR");
                    argsMap.put("reason","No such key");
                    return argsMap;
                }
            }

            if (type.equals("delete"))
            {

                if(Jasondb.containsKey(key))
                {
                    try
                    {
                        Jasondb.remove(key);
                        {
                            argsMap.put("response", "OK");
                            return argsMap;
                        }
                    }
                    catch (Exception e)
                    {
                        argsMap.put("response", "ERROR");
                        argsMap.put("reason", "No such key");
                        return argsMap;
                    }
                }
                else
                {
                    argsMap.put("response","ERROR");
                    argsMap.put("reason","No such key");
                    return argsMap;
                }
            }

            else
            {
                argsMap.put("response","ERROR");
                argsMap.put("reason","No such key");
                return argsMap;
            }
        }
}
