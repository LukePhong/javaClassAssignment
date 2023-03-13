package event;

public class sendMessage extends Beans{
    //private int port;
    private String message;

    public sendMessage(String message) {

        this.message = message;
        this.event = Event.SEND_MESSAGE;

    }

    public String getMessage() {
        return message;
    }
}
