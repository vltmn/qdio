package io.hamo.qdio.room;


/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 * <p>
 * <p>
 *
 * returns the current room instance or throws an exception if no room is yet instantiated.
 */
public class RoomInstanceHolder {
    private static Room roomInstance;

    public static Room getRoomInstance() {
        if (roomInstance == null) {
            throw new RuntimeException("No room exists");
        } else {
            return roomInstance;
        }
    }

    public static void setRoomInstance(Room room) {
        roomInstance = room;
    }

}
