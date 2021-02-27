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

public class AccountUpdate extends JFrame {

	ImageIcon icon;
	JTextField tfId;
	JPasswordField tfPw,tfUpPw;
	JButton updateBtn,deleteBtn,homeBtn;
	
	public void resetField() {
		tfId.setText("");
		tfPw.setText("");
		tfUpPw.setText("");
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
		JLabel label_uppw = new JLabel("UPDATE - PW");
		tfId = new JTextField(20);
		tfPw = new JPasswordField(20); 
		tfUpPw = new JPasswordField(20); 
		updateBtn = new JButton("Update");
		deleteBtn = new JButton("Delete Account");
		homeBtn = new JButton("Home");
		
		label_id.setBounds(10,50,250,30);
		label_id.setForeground(Color.white);
		label_id.setFont(new Font("ID",Font.BOLD,20)); 
		tfId.setBounds(10,80,250,30);
		
		label_pw.setBounds(10,130,250,30);
		label_pw.setForeground(Color.white);
		label_pw.setFont(new Font("PW",Font.BOLD,20)); 
		tfPw.setBounds(10,160,250,30);
		
		label_uppw.setBounds(10,210,250,30);
		label_uppw.setForeground(Color.white);
		label_uppw.setFont(new Font("PW",Font.BOLD,20)); 
		tfUpPw.setBounds(10,240,250,30);
		
		updateBtn.setBounds(10, 290, 250, 30);
		
		updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String id = tfId.getText().trim();
				String pass = String.valueOf(tfPw.getPassword()).trim();
				String uppass = String.valueOf(tfUpPw.getPassword()).trim();
				System.out.println("id : " + id);
				System.out.println("pass : " + pass);
				if(id.length()==0||pass.length()==0) {
					JOptionPane.showMessageDialog(AccountUpdate.this, "Check ID or Password");
					return;
				}
				if(MemberDao.idPassCheck(id,pass)) {
					MemberDao.updateMember(id,uppass);
					new LoginFrame();
					AccountUpdate.this.dispose();
				}else {
					JOptionPane.showMessageDialog(AccountUpdate.this, "Check ID or Password");
					resetField();
				}
			}
		});
		
		deleteBtn.setBounds(10, 340, 250, 30);
		
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String id = tfId.getText().trim();
				String pass = String.valueOf(tfPw.getPassword()).trim();
				System.out.println("id : " + id);
				System.out.println("pass : " + pass);
				if(id.length()==0||pass.length()==0) {
					JOptionPane.showMessageDialog(AccountUpdate.this, "Check ID or Password");
					return;
				}
				if(MemberDao.idPassCheck(id,pass)) {
					MemberDao.deleteMember(id);
					new LoginFrame();
					AccountUpdate.this.dispose();
				}else {
					JOptionPane.showMessageDialog(AccountUpdate.this, "Check ID or Password");
					resetField();
				}
			}
		});
		
		homeBtn.setBounds(10, 390, 250, 30);
		
		homeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
					new LoginFrame();
					AccountUpdate.this.dispose();
			}
		});
		
		panel.add(label_id);
		panel.add(tfId);
		panel.add(label_pw);
		panel.add(tfPw);
		panel.add(label_uppw);
		panel.add(tfUpPw);
		panel.add(updateBtn);
		panel.add(deleteBtn);
		panel.add(homeBtn);
		this.add(panel);
	}
	
	AccountUpdate(){
		super("Account");
		init();
		this.setBounds(100, 100, 300, 500);
		this.setResizable(false);
		this.setVisible(true);
	}
}
