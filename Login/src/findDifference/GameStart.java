package findDifference;
	
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

	public class GameStart extends JFrame implements ActionListener {

		ImageIcon start;
		JLabel imgbox, userInfo;
		JPanel jPanel, jPanel2;
		JButton jButton;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == jButton) {
				this.dispose();
				 new Difference();
			}
		}

		public GameStart() {
			this.setTitle("Ʋ�� �׸� ã��");
			this.setSize(984, 600);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setResizable(false);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			this.setLayout(new BorderLayout());
			jPanel = new JPanel(new BorderLayout());
			jPanel.add("West", userInfo = new JLabel("userId: " + DifferenceDao.id));
			jPanel.setBackground(Color.ORANGE);
			userInfo.setFont(new Font("����", Font.BOLD, 20));
			this.add("North", jPanel);

			Panel panel = new Panel("FDImage\\sss.png");
			
			this.add("Center", panel);
			jPanel2 = new JPanel();
			jPanel2.add(jButton = new JButton("start"));
			jButton.addActionListener(this);
			jPanel2.setBackground(Color.ORANGE);
			this.add("South", jPanel2);
		}

		public class Panel extends JPanel {

			public Panel(String imgFile) {
				start = new ImageIcon(imgFile);
				imgbox = new JLabel(start);
				imgbox.setSize(start.getIconWidth(), start.getIconHeight());
				add(imgbox);
				this.setBackground(Color.ORANGE);

			}

		}

	}
