package Map;

import Bean.BattleMessage;

public class MessageBuffer {
    //单例模式
    private static MessageBuffer instance = null;
    private MessageBuffer(){}
    public static MessageBuffer getInstance(){
        if(instance == null){
            instance = new MessageBuffer();
        }
        return instance;
    }

    private static BattleMessage battleMessage = null;
    public BattleMessage getBattleMessage(){
        return battleMessage;
    }
    public void setBattleMessage(BattleMessage battleMessage){
        this.battleMessage = battleMessage;
    }
    //清空
    public void clear(){
        battleMessage = null;
    }
}
