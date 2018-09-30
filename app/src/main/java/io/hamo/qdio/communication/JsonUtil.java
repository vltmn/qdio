package io.hamo.qdio.communication;

import com.google.android.gms.nearby.connection.Strategy;
import com.google.gson.Gson;

import io.hamo.qdio.communication.entity.CommandMessage;
import io.hamo.qdio.room.SerializableRoom;

/**
 * Converts CommandMessage using Gson to Java object when communicating between Slave and Master
 */
public class JsonUtil {
    private Gson gson = new Gson();
    private static JsonUtil instance;

    private JsonUtil(Gson gson) {
        this.gson = gson;
    }


    public CommandMessage deserializeCommandMessage(String msg) {
        return gson.fromJson(msg, CommandMessage.class);
    }

    public String serializeCommandMessage(CommandMessage msg) {
        return gson.toJson(msg);
    }

    public SerializableRoom deserializeRoom(String msg) {
        return gson.fromJson(msg, SerializableRoom.class);
    }

    public String serializeRoom(SerializableRoom serializableRoom) {
        return gson.toJson(serializableRoom);
    }

    public static JsonUtil getInstance(){
        if (instance == null) {
            instance = new JsonUtil(new Gson());
        }
        return instance;
    }
}
