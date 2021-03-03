package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import model.MemberDao;

public class LoginFrame extends JFrame implements ActionListener{
	JScrollPane jScrollPane;
	ImageIcon icon;
	JTextField tfId;
	JPasswordField passwordField;
	JButton loginBtn,signUpBtn,accountBtn;	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="login") {
			String id = tfId.getText().trim();
			String pass = String.valueOf(passwordField.getPassword()).trim();
			System.out.println("id : " + id);
			System.out.println("pass : " + pass);
			if(id.length()==0||pass.length()==0) {
				JOptionPane.showMessageDialog(LoginFrame.this, "Id Or PassCheck");
				return;
			}
			if(MemberDao.idPassCheck(id, pass)) {
				new GameFrame(id);
				LoginFrame.this.dispose();
			}else {
				resetField();
			}
		}
	}

	public void resetField() {
		System.out.println("로그인 실패");
		JOptionPane.showMessageDialog(this, "Id Or PassFail");
		tfId.setText("");
		passwordField.setText("");
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
		passwordField = new JPasswordField();
		loginBtn = new JButton("Login");
		signUpBtn = new JButton("Sign Up");
		accountBtn = new JButton("Account");
		
		label_id.setBounds(10,50,250,30);
		label_id.setForeground(Color.white);
		label_id.setFont(new Font("ID",Font.BOLD,20)); 
		tfId.setBounds(10,80,250,30);
		tfId.registerKeyboardAction(this, "login", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), JComponent.WHEN_FOCUSED);

		label_pw.setBounds(10,130,250,30);
		label_pw.setForeground(Color.white);
		label_pw.setFont(new Font("PW",Font.BOLD,20)); 
		passwordField.setBounds(10,160,250,30);
		passwordField.registerKeyboardAction(this, "login", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), JComponent.WHEN_FOCUSED);

		loginBtn.setBounds(10, 220, 250, 30);
		signUpBtn.setBounds(10, 280, 250, 30);
		accountBtn.setBounds(10, 340, 250, 30);
		
		loginBtn.setActionCommand("login");
		loginBtn.addActionListener(this);

		signUpBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new SignUpFrame();
				LoginFrame.this.dispose();
			}			
		});
		
		accountBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AccountUpdate();
				LoginFrame.this.dispose();
			}
		});
		
		panel.add(label_id);
		panel.add(tfId);
		panel.add(label_pw);
		panel.add(passwordField);
		panel.add(loginBtn);
		panel.add(signUpBtn);
		panel.add(accountBtn);
		jScrollPane = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setPreferredSize(new Dimension(300,500));
		this.add(jScrollPane);
	}
	
	LoginFrame(){ 
		super("Login");
		init();
		this.setBounds(100, 100, 300, 500);
		this.setLayout(new FlowLayout());
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
