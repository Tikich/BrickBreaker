package com.bessergames.breakoutgame.desktop;

import java.awt.Color;
import java.awt.Graphics2D;

public class BrickPiece
{
	public double x, y, dx, dy, gravity;
	private Color color;
	private BrickGenerator brickMap;
	public int size;
	
	public BrickPiece(double brickx, double bricky, BrickGenerator theBrickMap) 
	{
		brickMap = theBrickMap;
		x = brickx + brickMap.brickwidth/2;
		y = bricky + brickMap.brickheight/2;
		dx = (Math.random() * 30 - 15);
		dy = (Math.random() * 30 - 15);
		size = (int)(Math.random() * 15 + 2);
		color = new Color(brickMap.r, brickMap.d, brickMap.b);
		gravity = 0.8;
		
	}
	
	public void update() 
	{
		x += dx;
		y += dy;
		dy += gravity;
	}
	
	public void draw(Graphics2D g) 
	{
		g.setColor(color);
		g.fillRect((int)x, (int)y, size, size);

	}
}
