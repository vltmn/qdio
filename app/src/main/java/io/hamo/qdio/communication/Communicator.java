package io.hamo.qdio.communication;

import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.Strategy;

import javax.security.auth.callback.Callback;

import io.hamo.qdio.communication.entity.CommandMessage;

public interface Communicator {
    public static final String SERVICE_ID = "Qdio";
    public static final Strategy STRATEGY = Strategy.P2P_STAR;
    public static final String roomAdmin = "admin";


    void sendCommand(CommandMessage commandMessage);
    void onReceiveCommand(Callback callback);
    IncomingMessageQueue getIncomingMessages();

}
