package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

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

public class LoginFrame extends JFrame implements ActionListener {
	JScrollPane jScrollPane;
	ImageIcon icon;
	JTextField tfId;
	JPasswordField passwordField;
	JButton loginBtn, signUpBtn, accountBtn;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "login") {
			String id = tfId.getText().trim();
			String pass = String.valueOf(passwordField.getPassword()).trim();
			System.out.println("id : " + id);
			System.out.println("pass : " + pass);
			if (id.length() == 0 || pass.length() == 0) {
				JOptionPane.showMessageDialog(LoginFrame.this, "Id Or PassCheck");
				return;
			}
			if (MemberDao.idPassCheck(id, pass)) {
				new GameFrame(id);
				LoginFrame.this.dispose();
			} else {
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

		tfId = new JTextField(20);
		passwordField = new JPasswordField();
		loginBtn = new JButton("Login");
		signUpBtn = new JButton("Sign Up");
		accountBtn = new JButton("Account");

		tfId.setBounds(10, 180, 250, 30);
		tfId.registerKeyboardAction(this, "login", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_FOCUSED);
		tfId.setText("  아이디");
		tfId.setForeground(Color.gray);
		tfId.setFocusable(false);
		tfId.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(tfId.getText().equals("  아이디")) {
					tfId.setText("");
					tfId.setForeground(Color.BLACK);
				}else {
					tfId.setText(tfId.getText());
					tfId.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(tfId.getText().equals("  아이디")||tfId.getText().length()==0) {
					tfId.setText("  아이디");
					tfId.setForeground(Color.GRAY);
				}else {
					tfId.setText(tfId.getText());
					tfId.setForeground(Color.BLACK);
				}
			}
			
		});
		passwordField.setBounds(10, 220, 250, 30);
		passwordField.registerKeyboardAction(this, "login", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_FOCUSED);
		passwordField.setEchoChar((char)0);
		passwordField.setText("  비밀번호");
		passwordField.setForeground(Color.GRAY);
		passwordField.setFocusable(false);
		passwordField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(passwordField.getText().equals("  비밀번호")) {
					passwordField.setText("");
					passwordField.setEchoChar('*');
					passwordField.setForeground(Color.BLACK);
				}else {
					passwordField.setEchoChar((char)0);
					passwordField.setText(passwordField.getText());
					passwordField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(passwordField.getText().equals("  비밀번호")||passwordField.getText().length()==0) {
					passwordField.setEchoChar((char)0);
					passwordField.setText("  비밀번호");
					passwordField.setForeground(Color.GRAY);
				}else {
					passwordField.setEchoChar('*');
					passwordField.setText(tfId.getText());
					passwordField.setForeground(Color.BLACK);
				}
			}
			
		});
		loginBtn.setBounds(10, 290, 250, 30);
		signUpBtn.setBounds(10, 330, 250, 30);
		accountBtn.setBounds(10, 370, 250, 30);
		

		loginBtn.setActionCommand("login");
		loginBtn.addActionListener(this);

		signUpBtn.addActionListener(new ActionListener() {

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
		

		panel.add(tfId);
		panel.add(passwordField);
		panel.add(loginBtn);
		panel.add(signUpBtn);
		panel.add(accountBtn);
		jScrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setPreferredSize(new Dimension(300, 500));
		this.add(jScrollPane);
	}

	LoginFrame() {
		super("Login");
		init();
		this.setBounds(100, 100, 300, 500);
		this.setLayout(new FlowLayout());
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

