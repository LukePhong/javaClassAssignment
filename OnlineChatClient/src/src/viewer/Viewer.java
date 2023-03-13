package viewer;

import event.*;
import mainWindow.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import static java.lang.Integer.parseInt;

public class Viewer extends JFrame {
    private Manager m;

    private JPanel panel;
    private JLabel label1,label2;
    private JTextField inputAddr;
    private JComboBox portSelectorBox;
    //private JComboBox clientSelectorBox;
    private JTextArea textArea;
    private JTextArea logArea;
    private JTextField statusField;
    private JButton sendButton;
    //private JButton startButton;
    private JScrollPane scrollPanel1,scrollPanel2;
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
        lowTextBuffer.append("Client"+((noResponse)b).getWho()+1+" Sending No Message\n");
        logArea.setText(String.valueOf(lowTextBuffer));
    }

    public void getMessage(Beans b) {
        lowTextBuffer.append("Recieve: "+((TextResponse)b).getRes()+"\n");
        logArea.setText(String.valueOf(lowTextBuffer));
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setTitle("Client");
        panel = new JPanel();
        panel.setLayout(new GridLayout(5,1));
        add(panel);

        panel.add(new JPanel());
        ((JPanel)panel.getComponent(0)).setLayout(new FlowLayout());;
        label1=new JLabel();
        label1.setText("请输入地址 ");
        ((JPanel)panel.getComponent(0)).add(label1);
        inputAddr=new JTextField();
        inputAddr.setEditable(true);
        inputAddr.setText("localhost");
        inputAddr.setPreferredSize(new Dimension(80,20));
        ((JPanel)panel.getComponent(0)).add(inputAddr);
        label2=new JLabel();
        label2.setText("端口 ");
        ((JPanel)panel.getComponent(0)).add(label2);
        portSelectorBox = new JComboBox();
        //portSelectorBox.addItem("Select port");
        portSelectorBox.addItem("10000");
        portSelectorBox.addItem("8080");
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
//        clientSelectorBox = new JComboBox();
//        clientSelectorBox.addItem("Select client");
//        clientSelectorBox.addItem("Client 1");
//        clientSelectorBox.addItem("Client 2");
//        ((JPanel)panel.getComponent(2)).add(clientSelectorBox);

        logArea = new JTextArea("Log");
//        scrollPanel1 = new JScrollPane(logArea);
//        scrollPanel1.setPreferredSize(new Dimension(400, 200));
//        scrollPanel1.add(logArea);
//        scrollPanel1.setVisible(true);
        panel.add(logArea);

        panel.add(new JPanel());
        ((JPanel)panel.getComponent(4)).setLayout(new FlowLayout(FlowLayout.LEFT));
        statusField = new JTextField("Status");
        ((JPanel)panel.getComponent(4)).add(statusField);

        sendButton = new JButton("Send");
        sendButton.setEnabled(true);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                m.sendMessage(new sendMessage(textArea.getText()));
            }

        });
        ((JPanel)panel.getComponent(2)).add(sendButton);

        m.startService(new startService(parseInt(portSelectorBox.getSelectedItem().toString())));
    }

}
