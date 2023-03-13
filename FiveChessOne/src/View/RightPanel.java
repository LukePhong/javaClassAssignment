package View;

import Bean.GameInfo;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    private static InfoPanel infoPanel;
    private static ControlPanel controlPanel;
    private static ChatPanel chatPanel;

    public RightPanel() {
        setLayout(new GridLayout(3,1));
        controlPanel = new ControlPanel();

        add(controlPanel);

        setVisible(true);
    }

    public RightPanel(GameInfo gameInfo) {
        setLayout(new GridLayout(3,1));
        infoPanel = new InfoPanel(gameInfo);
        controlPanel = new ControlPanel();
        chatPanel = new ChatPanel();
        add(infoPanel);
        add(controlPanel);
        add(chatPanel);
        setVisible(true);
    }

    public static InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public static ControlPanel getControlPanel() {
        return controlPanel;
    }

    public static ChatPanel getChatPanel() {
        return chatPanel;
    }

    public static void setOnlineMode() {
        controlPanel.setOnlineMode();
    }

    public static void setOfflineMode() {
        controlPanel.setOfflineMode();
        chatPanel.setVisible(false);
    }

    public void setLocalMode() {
        chatPanel.setVisible(false);
        infoPanel.setVisible(false);
    }
}
