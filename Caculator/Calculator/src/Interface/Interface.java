package Interface;

import cacu.Caculor;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class Interface extends JFrame{
	
	public Interface(String title) {
		super(title);

		GridLayout textLayout = new GridLayout(2,1);
		JPanel textPanel = new JPanel(textLayout);
		JTextField preTextField = new JTextField("0");
		preTextField.setEditable(false);
		preTextField.setHorizontalAlignment(JTextField.RIGHT);
		preTextField.setFont(new Font("宋体", Font.BOLD, 14));
		preTextField.setBounds(0, 0, 500, 20);

		JTextField numlineField=new JTextField("0");
		numlineField.setBounds(22,22,500,50);
		numlineField.setHorizontalAlignment(JTextField.RIGHT);
		numlineField.setFont(new Font("Times New Roman",Font.BOLD,40));

		textPanel.add(preTextField);
		textPanel.add(numlineField);

		GridLayout butGridLayout=new GridLayout(6,4);
		JPanel butPanel=new JPanel(butGridLayout);
		butPanel.setPreferredSize(new Dimension(600,600));
		{
			butPanel.add(new JButton("+/-"));
			butPanel.add(new JButton("C"));
			butPanel.add(new JButton("CE"));
			butPanel.add(new JButton("backspace"));
			butPanel.add(new JButton("("));
			butPanel.add(new JButton(")"));
			butPanel.add(new JButton("^"));
			butPanel.add(new JButton("/"));
			butPanel.add(new JButton("7"));
			butPanel.add(new JButton("8"));
			butPanel.add(new JButton("9"));
			butPanel.add(new JButton("*"));
			butPanel.add(new JButton("4"));
			butPanel.add(new JButton("5"));
			butPanel.add(new JButton("6"));
			butPanel.add(new JButton("-"));
			butPanel.add(new JButton("1"));
			butPanel.add(new JButton("2"));
			butPanel.add(new JButton("3"));
			butPanel.add(new JButton("+"));
			butPanel.add(new JButton("%"));
			butPanel.add(new JButton("0"));
			butPanel.add(new JButton("."));
			butPanel.add(new JButton("="));
		}

		class buttonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e){
				String what=((JButton) e.getSource()).getText();

				preTextField.setText(numlineField.getText()+"=");
				switch(what){
					case "CE":
						int tmp=numlineField.getText().length()-1;
						while(tmp>0){
							if(numlineField.getText().charAt(tmp)<'0'||numlineField.getText().charAt(tmp)>'9'){
								break;
							}
							tmp--;
						}
						numlineField.setText(numlineField.getText().substring(0,tmp+1));
						break;
					case "C":
						numlineField.setText("0");
						break;
					case"+/-":
						numlineField.setText(numlineField.getText().equals("0") ? "0" : "-"+numlineField.getText());
						break;
					case "=":
						numlineField.setText(new Caculor().caculate(numlineField.getText()));
						break;
					case "backspace":
						if(numlineField.getText().length()==1) {
							numlineField.setText("0");
							break;
						}
						numlineField.setText(numlineField.getText().equals("0") ? "0" : numlineField.getText().substring(0,numlineField.getText().length()-1));
						break;
					default:
						numlineField.setText(numlineField.getText().equals("0") ? what : numlineField.getText() + what);
				}

			}
		}


		
		this.add(BorderLayout.NORTH,textPanel);
		this.add(BorderLayout.SOUTH,butPanel);

		for (int i=0;i<24;i++) {
			((JButton) butPanel.getComponent(i)).addActionListener(new buttonListener());
		}
		
	}

	public static void main(String[] args) {
	    Interface startInterface=new Interface("Calculator");
		startInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startInterface.setSize(600, 800);
		startInterface.setUndecorated(true); // 去掉窗口的装饰
		startInterface.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);//采用指定的窗口装饰风格

		startInterface.setVisible(true);
	}
}
