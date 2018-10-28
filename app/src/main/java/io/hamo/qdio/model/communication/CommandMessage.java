package io.hamo.qdio.model.communication;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 * <p>
 * <p>
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
