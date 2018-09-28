package io.hamo.qdio.communication;

import com.google.android.gms.nearby.connection.Strategy;
import com.google.gson.Gson;

import io.hamo.qdio.communication.entity.CommandMessage;

/**
 * Converts CommandMessage using Gson to Java object when communicating between Slave and Master
 */
public class JsonUtil {
    private Gson gson = new Gson();
    private static JsonUtil instance;

    private JsonUtil(Gson gson) {
        this.gson = gson;
    }


    public CommandMessage deSerializeMessage(String msg) {
        return gson.fromJson(msg, CommandMessage.class);
    }

    public String serialize(CommandMessage msg) {
        return gson.toJson(msg);
    }

    public static JsonUtil getInstance(){
        if (instance == null) {
            instance = new JsonUtil(new Gson());
        }
        return instance;
    }
}
