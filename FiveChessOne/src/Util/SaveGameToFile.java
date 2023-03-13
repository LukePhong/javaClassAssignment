package Util;

import Bean.Chess;
import Bean.PlayerOnline;
import Map.Mapper;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class SaveGameToFile {
    //单例模式
    private static SaveGameToFile instance = null;
    private SaveGameToFile() {
    }
    public static SaveGameToFile getInstance() {
        if (instance == null) {
            instance = new SaveGameToFile();
        }
        return instance;
    }

    private String path="src/Util/";
    private String fileName="";

    //save 2*PlayerOnline+Mapper.chesses to file
    public void saveGame(PlayerOnline playerOnline1,PlayerOnline playerOnline2, Vector<Chess> chesses) {
        try {
            fileName=playerOnline1.getName()+"_"+playerOnline2.getName()+".txt";
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path+fileName));

            //save playerOnline
            oos.writeObject(playerOnline1);
            oos.writeObject(playerOnline2);

            //save chesses
            oos.writeObject(chesses);
            oos.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
