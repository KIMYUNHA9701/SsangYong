package first;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Puzzle extends JFrame implements ActionListener{
	
	JPanel panel;
	JLabel label,label2;
	RoundedButton[] button = new RoundedButton[3];
	String[] buttonText = {"<",">","게임시작"};
	String[] easy_hard = {"3x3","4x4","5x5"};
	int select = 0;
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == button[0]) {
			select =  --select % 3;
			if(select<0) select = 2;
			label2.setText(easy_hard[select]);
		}
		
		if(e.getSource() == button[1]) {
			select =  ++select % 3;
			label2.setText(easy_hard[select]);
		}
		
		if(e.getSource() == button[2]) {
			String easyhard = label2.getText().trim();
			switch(easyhard) {
			case "3x3":
				new Game(3);
				break;
			case "4x4":
				new Game(4);
				break;
			case "5x5":
				new Game(5);
				break;
			default:
				System.out.println("?????");
				break;
			}
			this.dispose();
		}
	}

	public void initPanel() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		ImageIcon icon = new ImageIcon("\\PICTURE\\1.jpg");
		Image img = icon.getImage();
		img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
		icon = new ImageIcon(img);
		label = new JLabel(icon);
		label2 = new JLabel(easy_hard[select]);
		for(int i=0; i < buttonText.length; i++) {
			button[i] = new RoundedButton(buttonText[i]);
			button[i].addActionListener(this);
		}
		Box box1 = Box.createHorizontalBox();
		Box box2 = Box.createHorizontalBox();
		Box box3 = Box.createHorizontalBox();
		box1.add(label);
		box2.add(button[0]);
		box2.add(Box.createRigidArea(new Dimension(20,0)));	
		box2.add(label2);
		box2.add(Box.createRigidArea(new Dimension(20,0)));	
		box2.add(button[1]);
		box3.add(button[2]);
		
		panel.add(Box.createRigidArea(new Dimension(0,30)));
		panel.add(box1);
		panel.add(Box.createRigidArea(new Dimension(0,100)));
		panel.add(box2);
		panel.add(Box.createRigidArea(new Dimension(0,100)));
		panel.add(box3);
		panel.setBackground(new Color(255,204,102));
		
		this.add(panel);
	}
	
	public Puzzle(){
		super("2048");
		initPanel();
		this.setBounds(100, 100, 600, 600);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new Puzzle();
	}
	
}
