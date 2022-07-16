import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	
	static int hight = 600;
	static int width = 600;
	static int unit = 25;
	static int gameUnit = (hight*width)/unit;
	static int delay = 150;
	final int[] x = new int[gameUnit];
	final int[] y = new int[gameUnit];
	int bodysize = 3;
	int eatCount=0;
	int eatx;
	int eaty;
	int direction = 'R';
	boolean run = false;
	Timer timer;
	Random random;
	
	GamePanel(){
		random  = new Random();
		this.setPreferredSize(new Dimension(hight,width));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newEat();
		run = true;
		timer = new Timer(delay,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g ) {
		if(run) {
		/*for(int i=0;i<hight/unit;i++) {
			g.drawLine(i*unit, 0, i*unit, hight);
			g.drawLine(0, i*unit, width, i*unit);
		}*/
		g.setColor(Color.red);
		g.fillOval(eatx, eaty, unit, unit);
		
		for(int i = 0; i<bodysize;i++) {
			if(i==0) {
				g.setColor(Color.blue);
				g.fillRect(x[i], y[i], unit, unit);
			}else {
				g.setColor(Color.white);
				g.fillRect(x[i], y[i], unit, unit);	
			}
		}
		
		g.setColor(Color.green);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics mat = g.getFontMetrics(g.getFont());
		g.drawString("Score : "+eatCount, (width-mat.stringWidth("Score : "+eatCount))/2, g.getFont().getSize());
		
		}else {
			gameOver(g);
		}
		
	}
	public void move() {
		for(int i= bodysize;i>0;i--) {
			x[i]= x[i-1];
			y[i]= y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0]= y[0]-unit;
			break;
		case 'D':
			y[0] = y[0]+unit;
			break;
		case 'L':
			x[0] = x[0]-unit;
			break;
		case 'R':
			x[0] = x[0]+unit;
			break;
		}
		
	}
	public void eat() {
		if(x[0]==eatx&&y[0]==eaty) {
			bodysize++;
			eatCount++;
			newEat();
		}
	}
	public void newEat() {
		eatx = random.nextInt((int)(width/unit))*unit;
		eaty = random.nextInt((int)(hight/unit))*unit;
	}
	public void checkCollution() {
		for(int i=bodysize;i>0;i--) {
			if(x[0]==x[i]&&y[0]==y[i]) {
				run = false;
			}
		}
		if(x[0]<0) {
			run = false;
		}
		if(x[0]>width) {
			run = false;
		}
		if(y[0]<0) {
			run = false;
		}
		if(y[0]>hight) {
			run = false;
		}
		
		if(!run) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		
		g.setColor(Color.green);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics mat = g.getFontMetrics(g.getFont());
		g.drawString("Score : "+eatCount, (width-mat.stringWidth("Score : "+eatCount))/2, g.getFont().getSize());
		
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics mat1 = g.getFontMetrics(g.getFont());
		g.drawString("Game Over", (width-mat1.stringWidth("Game Over"))/2, hight/2);
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(run) {
			move();
			eat();
			checkCollution();
		}
		repaint();
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}

}
