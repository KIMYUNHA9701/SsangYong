package first;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Game extends JFrame{
	
	int size;
	int score;
	int hiscore;
	JPanel menu,pan;
	JLabel[] label = new JLabel[3];
	String[] labelText = {"2048","점수 : ","최고점수 : "};
	JButton[] menuButton = new JButton[3];
	ImageIcon[] imgIcon = new ImageIcon[3];
	String[] imgPath = {"\\PICTURE\\home.jpg","\\PICTURE\\back.jpg","\\PICTURE\\restart.jpg"};
	RoundedButton[][] button;
	int initCnt = 2;
	int endCnt = 0;
	boolean endFlag = false;
	int[] randNum = new int[2];
	
	int[][] table;
	int[][] imsi;
	int imsiScore;
	
	public void copyTable() {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				imsi[i][j] = table[i][j];
			}
		}
	}
	
	public void backDrawTableButton() {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				table[i][j] = imsi[i][j];
			}
		}
		score = imsiScore;
		drawButton();
		writeScore();
	}
	
	public void writehiScore() {
		if(endFlag && score > hiscore) hiscore = score;
		label[2].setText(labelText[2] + hiscore);
		score = 0;
		writeScore();
	}
	
	public void writeScore() {
		label[1].setText(labelText[1] + score);
	}
	
	public void drawButton() {
		//cleanButton
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				button[i][j].setText("");
			}
		}
		
		//drawButton
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if(table[i][j] != 0)
				button[i][j].setText(table[i][j]+"");
			}
		}
	}
	
	public void moveDown() {
		for (int i = button.length-1; i > 0; i--) {
			for (int j = 0; j < button[0].length; j++) {
				if(table[i][j] == 0) continue;
				if(i > 0) {
					if(table[i][j] == table[i-1][j]) {
						table[i][j] += table[i-1][j]; 
						score += table[i][j];
						table[i-1][j] = 0;
					}
					if(table[i][j] == 0) {
						table[i][j] = table[i-1][j];
						table[i-1][j]= 0;
					}
				}
			}
		}
		for(int k = 0; k < size; k++) {
			for (int i = 0; i < button.length; i++) {
				for (int j = 0; j < button[0].length; j++) {
					if(table[i][j] == 0) continue;
					if(i < button.length-1) {
						if(table[i+1][j] == 0) {
							table[i+1][j] = table[i][j];
							table[i][j]= 0;
						}
						if(table[i+1][j] == table[i][j]) {
							table[i+1][j] += table[i][j];
							score += table[i+1][j];
							table[i][j]=0;
						}
					}
				}
			}
		}
	}
	
	public void moveUp() {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if(table[i][j] == 0) continue;
				if(i < button.length-2) {
					if(table[i][j] == table[i+1][j]) {
					table[i][j] += table[i+1][j]; 
					score += table[i][j];
					table[i+1][j] = 0;
					}
					if(table[i][j] == 0) {
						table[i][j] = table[i+1][j];
						table[i+1][j] = 0;
					}
				}
			}
		}
		for(int k = 0 ; k < size; k++) {
			for (int i = button.length-1; i > 0; i--) {
				for (int j = 0; j < button[0].length; j++) {
					if(table[i][j] == 0) continue;
					if(i > 0) {
						if(table[i-1][j] == 0) {
							table[i-1][j] = table[i][j];
							table[i][j] = 0;
						}
						if(table[i-1][j] == table[i][j]) {
							table[i-1][j] += table[i][j];
							score += table[i-1][j];
							table[i][j] = 0;
						}
					}
				}
			}
		}
	}
	
	public void moveLeft() {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if(table[i][j] == 0) continue;
				if(j < button[0].length-1) {
					if(table[i][j] == table[i][j+1]) {
						table[i][j] += table[i][j+1]; 
						score += table[i][j];
						table[i][j+1] = 0;
					}
					if(table[i][j] == 0) {
						table[i][j] = table[i][j+1];
						table[i][j+1]= 0;
					}
				}
			}
		}
		for(int k = 0; k < size; k++) {
			for (int i = 0; i < button.length; i++) {
				for (int j = button[i].length-1; j > 0; j--) {
					if(table[i][j] == 0) continue;
					if(j > 0) {
						if(table[i][j-1] == 0) {
							table[i][j-1] = table[i][j];
							table[i][j]= 0;
						}
						if(table[i][j-1] == table[i][j]) {
							table[i][j-1] += table[i][j];
							score += table[i][j-1];
							table[i][j] = 0;
						}
					}
				}
			}
		}
	}
	
	public void moveRight() {
		for (int i = 0; i < button.length; i++) {
			for (int j = button[0].length-1; j > 0; j--) {
				if(table[i][j] == 0) continue;
				if(j > 0) {
					if(table[i][j] == table[i][j-1]) {
						table[i][j] += table[i][j-1]; 
						score += table[i][j];
						table[i][j-1] = 0;
					}
					if(table[i][j] == 0) {
						table[i][j] = table[i][j-1];
						table[i][j-1]= 0;
					}
				}
			}
		}
		for(int k = 0; k < size; k++) {
			for (int i = 0; i < button.length; i++) {
				for (int j = 0; j < button[0].length; j++) {
					if(table[i][j] == 0) continue;
					if(j < button[0].length-1) {
						if(table[i][j+1] == 0) {
							table[i][j+1] = table[i][j];
							table[i][j]= 0;
						}
						if(table[i][j+1] == table[i][j]) {
							table[i][j+1] += table[i][j];
							score += table[i][j+1];
							table[i][j] = 0;
						}
					}
				}
			}
		}
	}
	
	
	
	
	public void cleanButton() {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				button[i][j].setText("");
			}
		}
	}
	
	public void cleanTable() {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				table[i][j]=0;
			}
		}
	}
	
	public void viewTable() {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				System.out.print(table[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	public void addRandNum() {
		boolean flag = true;
		int randNum = 0;
		endCnt = 0;
		while(flag) {
			randNum = (int)(Math.random() * (table.length * table[0].length));
			int i = randNum / table.length;
			int j = randNum % table[0].length;
//			System.out.println("randNum = " + randNum);
//			System.out.println("i=" + i + ", j=" + j);
			if(table[i][j] == 0) {
				table[i][j] = 2;
				flag = false;
			}
			System.out.println("루프중");
			endCnt++;
			if(endCnt > 50) {
				JOptionPane.showMessageDialog(this, "-게임오버!-");
				endFlag = true;
				break;
			}
		}
	}
	

	public void initTableButton() {
		int num = 0;
		for (int i = 0; i < randNum.length; i++) {
			num = (int)(Math.random() * (button.length * button[0].length));
			randNum[i] = num;
			while(i!=0 && randNum[i] == randNum[i-1]) {
				randNum[i] = (int)(Math.random() * (button.length * button[0].length));
			}
			System.out.println(randNum[i]);
		}
		
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if(randNum[0] == i*(button[0].length) + j || randNum[1] == i*(button[0].length) + j) {
					button[i][j].setText("2");
					table[i][j] = 2;
				}
			}
		}
	}
	
	public void initMenuButton() {
		for(int i=0; i<menuButton.length; i++) {
			menuButton[i] = new JButton();
			imgIcon[i] = new ImageIcon(imgPath[i]);
			Image img = imgIcon[i].getImage();
			img = img.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
			imgIcon[i].setImage(img);
			menuButton[i].setIcon(imgIcon[i]);
			menuButton[i].setContentAreaFilled(false);
			menuButton[i].setFocusPainted(false);
			menuButton[i].setBorderPainted(false);
		}
	}
	
	public void addListenerAtMenuButton() {
		menuButton[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Puzzle();
			}
		});
		menuButton[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Back");
				backDrawTableButton();
				Game.this.requestFocusInWindow();
			}
		});
		menuButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Restart");
				writehiScore();
				cleanTable();
				cleanButton();
				initTableButton();
				Game.this.setFocusable(true);
	            Game.this.requestFocusInWindow();
			}
		});
	}
	
	public void initPanel(int size){
		button = new RoundedButton[size][size];
		table = new int[size][size];
		imsi = new int[size][size];
		menu = new JPanel();
	    menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
	    label[0] = new JLabel(labelText[0]);
	    label[1] = new JLabel(labelText[1] + score);
	    label[2] = new JLabel(labelText[2] + hiscore);
	    initMenuButton();
	    addListenerAtMenuButton();
	    
	    label[0].setFont(new Font(labelText[0], Font.BOLD, 30));
	    
	   
	    Box box1 = Box.createHorizontalBox();
	    box1.add(label[0]);
	    box1.add(Box.createRigidArea(new Dimension(350,0)));
	    box1.add(label[1]);
	    box1.add(Box.createRigidArea(new Dimension(30,0)));
	    box1.add(label[2]);
	    Box box2 = Box.createHorizontalBox();
	    box2.add(menuButton[0]);
	    box2.add(Box.createRigidArea(new Dimension(350,0)));
	    box2.add(menuButton[1]);
	    box2.add(Box.createRigidArea(new Dimension(10,0)));
	    box2.add(menuButton[2]);
	    box2.add(Box.createRigidArea(new Dimension(0,50)));
	    menu.add(box1);
	    menu.add(box2);
	    
	    pan = new JPanel(new GridLayout(size,size));
	    for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				button[i][j] = new RoundedButton("",new Color(255,247,242),new Color(70,103,106));
				button[i][j].setFont(new Font("",Font.BOLD,20));
				pan.add(button[i][j]);
			}
		}
	    
	    initTableButton();
	    
	    menu.setBackground(new Color(255,204,102));
	    this.add("North",menu);
	    this.add(pan);
	    
	}

	public Game(int size) {
		super("Game");
		initPanel(this.size=size);
		this.setFocusable(true);
		
		this.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					System.out.println("Left");
					imsiScore = score;
					copyTable();
					moveLeft();
					writeScore();
					drawButton();
					addRandNum();
					drawButton();
				}
				
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					System.out.println("RIGHT");
					imsiScore = score;
					copyTable();
					moveRight();
					writeScore();
					drawButton();
					addRandNum();
					drawButton();
				}
				
				if(e.getKeyCode() == KeyEvent.VK_UP) {
					System.out.println("UP");
					imsiScore = score;
					copyTable();
					moveUp();
					writeScore();
					drawButton();
					addRandNum();
					drawButton();
				}
				
				if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					System.out.println("DOWN");
					imsiScore = score;
					copyTable();
					moveDown();
					writeScore();
					drawButton();
					addRandNum();
					drawButton();
				}
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					viewTable();
				}
			}
		});
		this.setBounds(100, 100, 600, 600);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
