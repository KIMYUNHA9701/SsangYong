package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.MemberBean;
import model.MemberDao;

public class SignUpFrame extends JFrame{

	ImageIcon icon;
	JTextField tfId;
	JPasswordField tfPw;
	JButton signUpBtn,homeBtn;
	
	public void resetField() {
		tfId.setText("");
		tfPw.setText("");
	}
	
	public void backImage() {
		icon = new ImageIcon("Image\\minigame.jpg");
		Image iconImg = icon.getImage();
		iconImg = iconImg.getScaledInstance(300, 500, Image.SCALE_SMOOTH);
		icon.setImage(iconImg);
	}
	
	public void init() {	
		backImage();
		JPanel panel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
			}
		};
		
		JLabel label_id = new JLabel("ID");
		JLabel label_pw = new JLabel("PW");
		tfId = new JTextField(20);
		tfPw = new JPasswordField(20); 
		signUpBtn = new JButton("Sign Up");
		homeBtn = new JButton("Home");
		
		label_id.setBounds(10,100,250,30);
		label_id.setForeground(Color.white);
		label_id.setFont(new Font("ID",Font.BOLD,20)); 
		tfId.setBounds(10,130,250,30);
		
		label_pw.setBounds(10,180,250,30);
		label_pw.setForeground(Color.white);
		label_pw.setFont(new Font("PW",Font.BOLD,20)); 
		tfPw.setBounds(10,210,250,30);
		
		signUpBtn.setBounds(10, 280, 250, 30);
		
		signUpBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String id = tfId.getText().trim();
				String pass = String.valueOf(tfPw.getPassword()).trim();
				System.out.println("id : " + id);
				System.out.println("pass : " + pass);
				if(id.length()==0||pass.length()==0) {
					JOptionPane.showMessageDialog(SignUpFrame.this, "Check ID or Password");
					return;
				}
				if(!MemberDao.idCheck(id)) {
					MemberBean bean = new MemberBean();
					bean.setId(id);
					bean.setPw(pass);
					MemberDao.addMember(bean);
					new LoginFrame();
					SignUpFrame.this.dispose();
				}else {
					JOptionPane.showMessageDialog(SignUpFrame.this, "Duplicated");
					resetField();
				}
			}
		});
		
		homeBtn.setBounds(10, 350, 250, 30);
		
		homeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
					new LoginFrame();
					SignUpFrame.this.dispose();
			}
		});
		
		panel.add(label_id);
		panel.add(tfId);
		panel.add(label_pw);
		panel.add(tfPw);
		panel.add(signUpBtn);
		panel.add(homeBtn);
		this.add(panel);
	}
	
	SignUpFrame(){
		super("SignUp");
		init();
		this.setBounds(100, 100, 300, 500);
		this.setResizable(false);
		this.setVisible(true);
	}
}
