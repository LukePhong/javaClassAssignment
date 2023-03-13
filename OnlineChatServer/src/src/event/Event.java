package event;

public enum Event {
    START_SERVER,SET_CLIENT,SEND_MESSAGE,GET_MESSAGE,NO_MESSAGE;
    //public Event event;


    public static Event getEvent(String str){
        if(str.equals("START_SERVER")){
            return START_SERVER;
        }else if(str.equals("SET_CLIENT")){
            return SET_CLIENT;
        }else if(str.equals("SEND_MESSAGE")){
            return SEND_MESSAGE;
        }else if(str.equals("GET_MESSAGE")){
            return GET_MESSAGE;
        }
        return null;
    }
}
