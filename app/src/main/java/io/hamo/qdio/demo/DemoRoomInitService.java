package io.hamo.qdio.demo;

import android.arch.lifecycle.MutableLiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Queue;

import io.hamo.qdio.communication.Communicator;
import io.hamo.qdio.communication.JsonUtil;
import io.hamo.qdio.communication.entity.CommandMessage;
import io.hamo.qdio.room.MasterRoom;
import io.hamo.qdio.room.Room;
import io.hamo.qdio.room.SlaveRoom;

/**
 * Class for testing the domain w/o dependency on android hardware (nearby communications depends on bluetooth, which doesn't work in emulator)
 * <p>
 * This class may receive commands via intents sent with Intent.ACTION_SEND action and Intent.EXTRA_TEXT as a json string containing the command message
 */
public class DemoRoomInitService {
    private static final String DEMO_INTENT_ACTION = "io.hamo.qdio.demo_intent";
    private static final String DEMO_INTENT_EXTRA_KEY = "DATA";

    private final Context context;
    private final MutableLiveData<Queue<CommandMessage>> incomingMsgQueue;

    public DemoRoomInitService(Context context) {
        this.context = context.getApplicationContext();
        incomingMsgQueue = new MutableLiveData<>();
        incomingMsgQueue.setValue(new ArrayDeque<CommandMessage>());
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(getClass().getSimpleName(), "Broadcast received!, data: " + intent.toString());
                String json = intent.getStringExtra(DEMO_INTENT_EXTRA_KEY);
                CommandMessage commandMessage = JsonUtil.getInstance().deserializeCommandMessage(json);
                Log.i(getClass().getSimpleName(), "received commandmessage: " + commandMessage.toString());
                Queue<CommandMessage> value = incomingMsgQueue.getValue();
                value.add(commandMessage);
                incomingMsgQueue.postValue(value);
            }
        };
        IntentFilter intentFilter = new IntentFilter(DEMO_INTENT_ACTION);
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    private Communicator getCommunicator() {
        return new DemoCommunicator(incomingMsgQueue);
    }

    /**
     * Creates a slave room with a mock communicator, can receive commandmessages through intents
     *
     * @return the room created
     */
    public Room createDemoSlaveRoom() {
        return new SlaveRoom(getCommunicator());
    }

    /**
     * Creates a master room with a mock communicator, can receive commandmessages through intents
     *
     * @return the room created
     */
    public Room createDemoMasterRoom() {
        return new MasterRoom(getCommunicator());
    }
}
