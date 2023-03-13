package Bean;

import java.io.Serializable;

public class PlayerOnline extends PlayerLocal implements Serializable {
    private String ip;
    private int port;

    public PlayerOnline(){}
    public PlayerOnline(String name, ChessCondition condition, String ip, int port) {
        super(name, condition);
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }


}
