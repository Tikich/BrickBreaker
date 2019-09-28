package com.bessergames.breakoutgame.desktop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Gameplay extends JPanel implements KeyListener, ActionListener
{
	private boolean play = false;
	private boolean hit = false;
	private int score = 0;
	
	private int totalBricks = 54;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballPosX = 350;
	private int ballPosY = 525;
	private int ballXdir = -1;
	private int ballYdir = -3;
	
	private BrickGenerator map;
	
	private ArrayList<BrickExplosion> brickExplosion;
	
	private MyMouseMotionListener mouseListener;
	
	
	public Gameplay()
	{
		map = new BrickGenerator(6, 9);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer = new Timer(delay, this);
		timer.start();
		brickExplosion = new ArrayList<BrickExplosion>();
		
		mouseListener = new MyMouseMotionListener();
		addMouseMotionListener(mouseListener);
		addMouseListener(mouseListener);
		
	}
	
	public void paint(Graphics g) 
	{
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//draw map
		map.draw((Graphics2D)g);
		
		//score
		g.setColor(Color.PINK);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Score  " + score, 10, 30);
		
		//paddle
		g.setColor(Color.orange);
		g.fillRect(playerX, 550, 100, 8);
		
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		for(BrickExplosion bx : brickExplosion) 
		{
			bx.update();
			bx.draw((Graphics2D)g);
		}
		
		if(ballPosY > 535)
		{
			play = false;
			hit = false;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 45));
			g.drawString("Game Over!", 220, 300);
			g.drawString("Score: " + score, 255, 350);
			
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Click To Restart", 245, 400);
		}
		
		if(totalBricks == 0)
		{
			play = false;
			hit = false;
			ballPosY = 525;
			g.setColor(Color.pink);
			g.setFont(new Font("serif", Font.BOLD, 45));
			g.drawString("You Win!", 240, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Click To Restart", 245, 400);
		}
		
		if(hit) 
		{
			g.setColor(new Color(173, 216, 230));
			g.fillOval(ballPosX, ballPosY, 20, 20);
			
		}
		else 
		{
			g.setColor(Color.yellow);
			g.fillOval(ballPosX, ballPosY, 20, 20);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		timer.start();	
		if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)))
		{
			ballYdir = -ballYdir;
			hit = false;
		}
		
		
	A:	for(int i = 0; i < map.map.length; i++)
		{
			for(int j = 0; j < map.map[0].length; j++) 
			{
				if(map.map[i][j] > 0)
				{
					int brickX = j * map.brickwidth + 80;					
					int brickY = i * map.brickheight + 50;
					int brickWidth = map.brickwidth;
					int brickHeight = map.brickheight;
					
					Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
					Rectangle ballrect = new Rectangle(ballPosX, ballPosY, 20 ,20);
					Rectangle brickRect = rect;
					
					if(ballrect.intersects(brickRect)) 
					{																	
						map.setBrickValue(0, i, j);
						totalBricks--;
						score += 5;
						hit = true;
						
						if(map.map[i][j] == 0)
						{
							brickExplosion.add(new BrickExplosion(brickX, brickY, map));
						}
						
						if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width)
						{
							ballXdir = -ballXdir;
						}
						else
						{
							ballYdir = -ballYdir;
						}
						break A;
					}
					
				}
			}
		}
		
		if(play)
		{
			ballPosX += ballXdir;
			ballPosY += ballYdir;
			
			if(ballPosX < 0) 
			{
				ballXdir = -ballXdir;
			}
			if(ballPosY < 0) 
			{
				ballYdir = -ballYdir;
			}
			if(ballPosX > 670) 
			{
				ballXdir = -ballXdir;				
			}
		}
		if(!play)
		{
			ballPosX = playerX + 40;
		}
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
		{
			if(playerX >= 600) 
			{
				playerX = 600;
			}
			else 
			{
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) 
		{
			if(playerX < 10) 
			{
				playerX = 10;
			}
			else 
			{
				moveLeft();
			}
		}	
	}

	public void moveRight() 
	{
		play = true;
		playerX += 20;
	}
	public void moveLeft() 
	{
		play = true;
		playerX -= 20;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private class MyMouseMotionListener implements MouseMotionListener,MouseListener
	{
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) 
		{
			playerX = e.getX();
			
		}

		@Override
		public void mouseClicked(MouseEvent e) 
		{
			play = true;
			if(ballPosY > 530)
			{
				play = true;
				ballPosX = playerX;
				ballPosY = 525;
				ballXdir = -1;
				ballYdir = -3;
				score = 0;
				totalBricks = 54;
				map = new BrickGenerator(6,9);
				
				repaint();
			}
			if(totalBricks == 0) 
			{
				play = true;
				ballPosX = playerX;
				ballPosY = 525;
				ballXdir = -1;
				ballYdir = -3;
				playerX = 310;
				score = 0;
				totalBricks = 54;
				map = new BrickGenerator(6,9);
				
				repaint();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


	}
	
	public void update()
	{
		for(int i = 0; i < brickExplosion.size(); i++)
		{
			brickExplosion.get(i).update();
			if(!brickExplosion.get(i).getIsActive())
			{
				brickExplosion.remove(i);
			}
		}
	}
}
