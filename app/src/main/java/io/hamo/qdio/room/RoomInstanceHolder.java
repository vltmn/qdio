package io.hamo.qdio.room;

public class RoomInstanceHolder {
    private static Room roomInstance;

    public static Room getRoomInstance() {
        if (roomInstance == null) {
            throw new RuntimeException("No room exists");
        }
        else {
            return roomInstance;
        }
    }

    public static void setRoomInstance(Room room) {
        roomInstance = room;
    }

}
