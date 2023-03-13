package viewer;

import event.*;
import mainWindow.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Integer.parseInt;

public class Viewer extends JFrame {
    private Manager m;

    private JPanel panel;
    private JComboBox portSelectorBox;
    private JComboBox clientSelectorBox;
    private JTextArea textArea;
    private JTextArea logArea;
    private JTextField statusField;
    private JButton sendButton;
    private JButton startButton;
    private StringBuilder lowTextBuffer=new StringBuilder();


    @Override
    protected void processWindowEvent(WindowEvent e) {
        if(e.getID()==WindowEvent.WINDOW_CLOSING) {
            m.closing();
        }
        super.processWindowEvent(e);
    }

    public void start(Manager manager) {
        m=manager;
        init();
        setVisible(true);

    }

    public void noResponse(Beans b) {
        lowTextBuffer.append("Client"+(((noResponse)b).getWho()+1)+" Sending No Message\n");
        logArea.setText(String.valueOf(lowTextBuffer));
    }

    public void getMessage(Beans b) {
        lowTextBuffer.append("Client"+(((TextResponse)b).getWho()+1)+" "+((TextResponse)b).getRes()+"\n");
        logArea.setText(String.valueOf(lowTextBuffer));
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setTitle("Server");
        panel = new JPanel();
        panel.setLayout(new GridLayout(5,1));
        add(panel);

        panel.add(new JPanel());
        ((JPanel)panel.getComponent(0)).setLayout(new FlowLayout());;
        portSelectorBox = new JComboBox();
        portSelectorBox.addItem("Select port");
        portSelectorBox.addItem("8080");
        portSelectorBox.addItem("10000");
        ((JPanel)panel.getComponent(0)).add(portSelectorBox);

        textArea = new JTextArea("Please select a port and a client");
        //textArea.setEditable(true);
//        JScrollPane scrollPane = new JScrollPane(textArea);
//        scrollPane.setPreferredSize(new Dimension(400, 200));
        //scrollPane.add(textArea);
        //scrollPane.setVisible(true);
        panel.add(textArea);
        //((JPanel)panel.getComponent(1)).add(scrollPane);

        panel.add(new JPanel());
        ((JPanel)panel.getComponent(2)).setLayout(new FlowLayout());;
        clientSelectorBox = new JComboBox();
        clientSelectorBox.addItem("Select client");
        clientSelectorBox.addItem("Client 1");
        clientSelectorBox.addItem("Client 2");
        ((JPanel)panel.getComponent(2)).add(clientSelectorBox);

        logArea = new JTextArea("Log");
//        JScrollPane scrollPane = new JScrollPane(logArea);
//        scrollPane.setPreferredSize(new Dimension(400, 200));
//        scrollPane.add(logArea);
//        scrollPane.setVisible(true);
        panel.add(logArea);

        panel.add(new JPanel());
        ((JPanel)panel.getComponent(4)).setLayout(new FlowLayout(FlowLayout.LEFT));
        statusField = new JTextField("Status");
        ((JPanel)panel.getComponent(4)).add(statusField);

        sendButton = new JButton("Send");
        sendButton.setEnabled(true);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                m.sendMessage(new sendMessage(textArea.getText()));

            }

        });
        ((JPanel)panel.getComponent(2)).add(sendButton);

        startButton = new JButton("Start");
        startButton.setEnabled(true);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
               m.startService(new startService((parseInt(portSelectorBox.getSelectedItem().toString())),clientSelectorBox.getSelectedIndex()));
               //sendButton.setEnabled(true);
//                try {
//                    ServerSocket ss=new ServerSocket(parseInt(portSelectorBox.getSelectedItem().toString()));
//                    Socket s=ss.accept();
//
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }

            }
        });
        ((JPanel)panel.getComponent(0)).add(startButton);

    }


}
