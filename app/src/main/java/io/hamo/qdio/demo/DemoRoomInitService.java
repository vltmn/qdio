package io.hamo.qdio.demo;

import android.arch.lifecycle.MutableLiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Queue;

import io.hamo.qdio.communication.JsonUtil;
import io.hamo.qdio.communication.entity.CommandMessage;
import io.hamo.qdio.room.Room;

public class DemoRoomInitService {
    private final Context context;

    public DemoRoomInitService(Context context) {
        this.context = context.getApplicationContext();
        final MutableLiveData<Queue<CommandMessage>> incomingMsgQueue = new MutableLiveData<>();
        incomingMsgQueue.setValue(new ArrayDeque<CommandMessage>());
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String json = intent.getStringExtra(Intent.EXTRA_TEXT);
                CommandMessage commandMessage = JsonUtil.getInstance().deSerializeMessage(json);
                Queue<CommandMessage> value = incomingMsgQueue.getValue();
                value.add(commandMessage);
                incomingMsgQueue.postValue(value);
            }
        };
        IntentFilter intentFilter = null;

        try {
            intentFilter = new IntentFilter(Intent.ACTION_SEND, Intent.EXTRA_TEXT);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            Log.e(getClass().getSimpleName(), "Could net instantiate intentfilter", e);
        }
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    public Room createDemoSlaveRoom() {

        return null;
    }
}
