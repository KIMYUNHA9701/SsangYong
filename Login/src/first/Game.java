package first;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.MemberDao;


public class Game extends JFrame{
	
	static int size;
	int score;
	int hiscore;
	JPanel menu,pan;
	JLabel[] label = new JLabel[3];
	String[] labelText = {"2048","점수 : ","최고점수 : "};
	JButton[] menuButton = new JButton[3];
	ImageIcon[] imgIcon = new ImageIcon[3];
	String[] imgPath = {"Game1Image\\home.jpg","Game1Image\\back.jpg","Game1Image\\restart.jpg"};
	int initCnt = 2;
	int endCnt = 0;
	boolean endFlag = false;
	int[] randNum = new int[2];
	
	static Tile[][] table;
	Tile[][] imsi;
	int imsiScore;
	String sessionId = null;
	
	int item = 0;
	
	public void copyTable() {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				imsi[i][j].value = table[i][j].value;
			}
		}
	}
	
	public void backDrawTableButton() {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				table[i][j].value = imsi[i][j].value;
			}
		}
		score = imsiScore;
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
	
	
	public void moveDown() {
		for (int i = table.length-1; i > 0; i--) {
			for (int j = 0; j < table[0].length; j++) {
				if(table[i][j].value == 0) continue;
				if(i > 0) {
					if(table[i][j] == table[i-1][j]) {
						table[i][j].value += table[i-1][j].value; 
						score += table[i][j].value;
						table[i-1][j].value = 0;
					}
					if(table[i][j].value == 0) {
						table[i][j].value = table[i-1][j].value;
						table[i-1][j].value= 0;
					}
				}
			}
		}
		for(int k = 0; k < size; k++) {
			for (int i = 0; i < table.length; i++) {
				for (int j = 0; j < table[0].length; j++) {
					if(table[i][j].value == 0) continue;
					if(i < table.length-1) {
						if(table[i+1][j].value == 0) {
							table[i+1][j].value = table[i][j].value;
							table[i][j].value= 0;
						}
						if(table[i+1][j].value == table[i][j].value) {
							table[i+1][j].value += table[i][j].value;
							score += table[i+1][j].value;
							table[i][j].value=0;
						}
					}
				}
			}
		}
	}
	
	public void moveUp() {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				if(table[i][j].value == 0) continue;
				if(i < table.length-2) {
					if(table[i][j] == table[i+1][j]) {
					table[i][j].value += table[i+1][j].value; 
					score += table[i][j].value;
					table[i+1][j].value = 0;
					}
					if(table[i][j].value == 0) {
						table[i][j].value = table[i+1][j].value;
						table[i+1][j].value = 0;
					}
				}
			}
		}
		for(int k = 0 ; k < size; k++) {
			for (int i = table.length-1; i > 0; i--) {
				for (int j = 0; j < table[0].length; j++) {
					if(table[i][j].value == 0) continue;
					if(i > 0) {
						if(table[i-1][j].value == 0) {
							table[i-1][j].value = table[i][j].value;
							table[i][j].value = 0;
						}
						if(table[i-1][j].value == table[i][j].value) {
							table[i-1][j].value += table[i][j].value;
							score += table[i-1][j].value;
							table[i][j].value = 0;
						}
					}
				}
			}
		}
	}
	
	public void moveLeft() {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				if(table[i][j].value == 0) continue;
				if(j < table[0].length-1) {
					if(table[i][j].value == table[i][j+1].value) {
						table[i][j].value += table[i][j+1].value; 
						score += table[i][j].value;
						table[i][j+1].value = 0;
					}
					if(table[i][j].value == 0) {
						table[i][j].value = table[i][j+1].value;
						table[i][j+1].value= 0;
					}
				}
			}
		}
		for(int k = 0; k < size; k++) {
			for (int i = 0; i < table.length; i++) {
				for (int j = table[i].length-1; j > 0; j--) {
					if(table[i][j].value == 0) continue;
					if(j > 0) {
						if(table[i][j-1].value == 0) {
							table[i][j-1].value = table[i][j].value;
							table[i][j].value= 0;
						}
						if(table[i][j-1].value == table[i][j].value) {
							table[i][j-1].value += table[i][j].value;
							score += table[i][j-1].value ;
							table[i][j].value = 0;
						}
					}
				}
			}
		}
	}
	
	public void moveRight() {
		for (int i = 0; i < table.length; i++) {
			for (int j = table[0].length-1; j > 0; j--) {
				if(table[i][j].value == 0) continue;
				if(j > 0) {
					if(table[i][j].value == table[i][j-1].value) {
						table[i][j].value += table[i][j-1].value; 
						score += table[i][j].value;
						table[i][j-1].value = 0;
					}
					if(table[i][j].value == 0) {
						table[i][j].value = table[i][j-1].value;
						table[i][j-1].value= 0;
					}
				}
			}
		}
		for(int k = 0; k < size; k++) {
			for (int i = 0; i < table.length; i++) {
				for (int j = 0; j < table[0].length; j++) {
					if(table[i][j].value == 0) continue;
					if(j < table[0].length-1) {
						if(table[i][j+1].value == 0) {
							table[i][j+1].value = table[i][j].value;
							table[i][j].value= 0;
						}
						if(table[i][j+1].value == table[i][j].value) {
							table[i][j+1].value += table[i][j].value;
							score += table[i][j+1].value;
							table[i][j].value = 0;
						}
					}
				}
			}
		}
	}
	
	
	public void cleanTable() {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				table[i][j].value=0;
			}
		}
	}
	
	public void viewTable() {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				System.out.print(table[i][j].value + "\t");
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
			if(table[i][j].value == 0) {
				table[i][j].value = 2;
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
			num = (int)(Math.random() * (table.length * table[0].length));
			randNum[i] = num;
			while(i!=0 && randNum[i] == randNum[i-1]) {
				randNum[i] = (int)(Math.random() * (table.length * table[0].length));
			}
			System.out.println(randNum[i]);
		}
		
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				if(randNum[0] == i*(table[0].length) + j || randNum[1] == i*(table[0].length) + j) {
					table[i][j].value = 2;
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
				MemberDao.updatePoint(sessionId,MemberDao.selectPoint(sessionId) + (int)(score / 10));
				writehiScore();
				System.out.println("(after)hiscore : " + hiscore);
				MemberDao.updateGameScore(sessionId, hiscore,3);
				new Puzzle(sessionId);
				dispose();
			}
		});
		menuButton[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Back");
				if(item < 1) return;
				item--;
				backDrawTableButton();
				pan.repaint();
				Game.this.requestFocusInWindow();
			}
		});
		menuButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Restart");
				writehiScore();
				cleanTable();
				initTableButton();
				pan.repaint();
				Game.this.setFocusable(true);
	            Game.this.requestFocusInWindow();
			}
		});
	}
	
	public void initTable() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				table[i][j] = new Tile();
				imsi[i][j] = new Tile();
			}
		}
	}
	
	public void initPanel(int size){
		table = new Tile[size][size];
		imsi = new Tile[size][size];
		initTable();
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
	    
	    pan = new GridPan();
	    
	    initTableButton();
	    
	    menu.setBackground(new Color(255,204,102));
	    this.add("North",menu);
	    this.add(pan);
	    System.out.println(pan.toString());
	}

	public Game(int size,String id) {
		super("Game");
		sessionId = id;
		item = 1;
//		item = (int)((ArrayList)MemberDao.selectGameItem(id, 3)).get(0);
		hiscore = MemberDao.selectGameScore(sessionId,3);
		System.out.println("hiscore : " + hiscore);
		initPanel(Game.size=size);
		this.setFocusable(true);
		
		this.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					System.out.println("Left");
					imsiScore = score;
					copyTable();
					moveLeft();
					writeScore();
					addRandNum();
					pan.repaint();
				}
				
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					System.out.println("RIGHT");
					imsiScore = score;
					copyTable();
					moveRight();
					writeScore();
					addRandNum();
					pan.repaint();
				}
				
				if(e.getKeyCode() == KeyEvent.VK_UP) {
					System.out.println("UP");
					imsiScore = score;
					copyTable();
					moveUp();
					writeScore();
					addRandNum();
					pan.repaint();
				}
				
				if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					System.out.println("DOWN");
					imsiScore = score;
					copyTable();
					moveDown();
					writeScore();
					addRandNum();
					pan.repaint();
				}
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					viewTable();
				}
			}
		});
		this.setBounds(100, 100, 600, 600);
		this.setResizable(false);
//		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				MemberDao.updatePoint(sessionId,MemberDao.selectPoint(sessionId) + (int)(score / 10));
				writehiScore();
				MemberDao.updateGameScore(sessionId, hiscore,3);
				new Puzzle(sessionId);
				Game.this.dispose();
			}
		});
	}

	public static class GridPan extends JPanel{
		static final Color BG_COLOR = new Color(0xbbada0);
		static int TILE_SIZE;
		static final int TILES_MARGIN = 12;
		static int TABLE_OFFSET;
		static int TABLE_MARGIN;
		
		
		public GridPan() {
			setPreferredSize(new Dimension(340, 400));
			setFocusable(true);
			tileSetting();
			repaint();
		}
		
		public void tileSetting() { 
			switch(Game.size) {
			case 3:
				TILE_SIZE = 128;
				TABLE_OFFSET = 50;
				TABLE_MARGIN = 30;
				break;
			case 4:
				TILE_SIZE = 95;
				TABLE_OFFSET = 45;
				TABLE_MARGIN = 30;
				break;
			case 5:
				TILE_SIZE = 75;
				TABLE_OFFSET = 60;
				TABLE_MARGIN = 15;
				break;
			}
		}
		
		
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(BG_COLOR);
			g.fillRect(0, 0, this.getSize().width, this.getSize().height);
			for(int y = 0 ; y < size; y++) {
				for(int x = 0; x < size; x++) {
					drawTile(g,table[y][x],x,y);
				}
			}
		}
		
		public void drawTile(Graphics g2, Tile tile,int x,int y) {
			Graphics2D g = ((Graphics2D)g2);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		    int value = tile.value;
		    int xOffset = offsetCoors(x);
		    xOffset += TABLE_OFFSET;
		    int yOffset = offsetCoors(y);
		    g.setColor(tile.getBackground());
		    g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14);
		    g.setColor(tile.getForeground());
		    final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
		    final Font font = new Font("Arial",Font.BOLD,size);
		    g.setFont(font);
		    
		    String s = String.valueOf(value);
		    final FontMetrics fm = getFontMetrics(font);
		    
		    final int w = fm.stringWidth(s);
		    final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];
		    
		    if(value != 0)
		    	g.drawString(s,xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 -2);
		    
		}
		
		private static int offsetCoors(int arg) {
			return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN + TABLE_MARGIN;
		}
		
	}
	
	static class Tile {
	    public int value;

	    public Tile() {
	      this(0);
	    }

	    public Tile(int num) {
	      value = num;
	    }

	    public boolean isEmpty() {
	      return value == 0;
	    }

	    public Color getForeground() {
	      return value < 16 ? new Color(0x776e65) :  new Color(0xf9f6f2);
	    }

	    public Color getBackground() {
	      switch (value) {
	        case 2:    return new Color(0xeee4da);
	        case 4:    return new Color(0xede0c8);
	        case 8:    return new Color(0xf2b179);
	        case 16:   return new Color(0xf59563);
	        case 32:   return new Color(0xf67c5f);
	        case 64:   return new Color(0xf65e3b);
	        case 128:  return new Color(0xedcf72);
	        case 256:  return new Color(0xedcc61);
	        case 512:  return new Color(0xedc850);
	        case 1024: return new Color(0xedc53f);
	        case 2048: return new Color(0xedc22e);
	      }
	      return new Color(0xcdc1b4);
	    }
	  }
}
