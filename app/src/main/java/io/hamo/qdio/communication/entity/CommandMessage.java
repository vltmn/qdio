package io.hamo.qdio.communication.entity;

/**
 * format for sending requests like adding song to Queue
 */
public class CommandMessage {
    private final CommandAction action;
    private final String value;

    public CommandMessage(CommandAction action, String value) {
        this.action = action;
        this.value = value;
    }

    public CommandAction getAction() {
        return action;
    }

    public String getValue() {
        return value;
    }
}
