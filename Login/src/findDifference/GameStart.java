package findDifference;
	
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

	public class GameStart extends JFrame implements ActionListener {

		ImageIcon start;
		JLabel imgbox, userInfo;
		JPanel jPanel, jPanel2;
		RoundedButton jButton;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == jButton) {
				this.dispose();
				 new Difference();
			}
		}

		public GameStart() {
			this.setTitle("틀린 그림 찾기");
			this.setBounds(100, 100, 984, 580);
//			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setResizable(false);
			this.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					// TODO Auto-generated method stub
					dispose();
				}
			});

			this.setLayout(new BorderLayout());
			jPanel = new JPanel(new BorderLayout());
			jPanel.add("West", userInfo = new JLabel("userId: " + DifferenceDao.id));
			jPanel.setBackground(new Color(0xbbada0)); 
			userInfo.setFont(new Font("굴림", Font.BOLD, 30));
			this.add("North", jPanel);

			Panel panel = new Panel("FDImage\\sss.png");
			
			this.add("Center", panel);
			jPanel2 = new JPanel();
			jPanel2.add(jButton = new RoundedButton("start"));
			jButton.addActionListener(this);
			jPanel2.setBackground(new Color(255,204,102));
			this.add("South", jPanel2);
		}

		public class Panel extends JPanel {

	         public Panel(String imgFile) {
	            this.setLayout(new GridLayout(1,1));
	            start = new ImageIcon(imgFile);
	            
	            imgbox = new JLabel(start);
	            this.add(imgbox);
	           
	         }

		}

	}
