package Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class AddressBattle {
    //单例模式
    private static AddressBattle addressBattle = null;
    private AddressBattle(){}
    public static AddressBattle getInstance(){
        if(addressBattle == null){
            addressBattle = new AddressBattle();
        }
        return addressBattle;
    }

    private static ImmutablePair<String, Integer> ADDRESS_LOCAL;
    private static ImmutablePair<String, Integer> ADDRESS_REMOTE;

    public static ImmutablePair<String, Integer> getAddressLocal() {
        return ADDRESS_LOCAL;
    }

    public static void setAddressLocal(ImmutablePair<String, Integer> addressLocal) {
        ADDRESS_LOCAL = addressLocal;
    }

    public static ImmutablePair<String, Integer> getAddressRemote() {
        return ADDRESS_REMOTE;
    }

    public static void setAddressRemote(ImmutablePair<String, Integer> addressRemote) {
        ADDRESS_REMOTE = addressRemote;
    }

    public void setAddressLocal(String ip, int port) {
        ADDRESS_LOCAL = new ImmutablePair<String, Integer>(ip, port);
    }

    public void setAddressRemote(String ip, int port) {
        ADDRESS_REMOTE = new ImmutablePair<String, Integer>(ip, port);
    }
}
