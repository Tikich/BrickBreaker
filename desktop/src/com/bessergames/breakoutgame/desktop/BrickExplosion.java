package com.bessergames.breakoutgame.desktop;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class BrickExplosion 
{
	private ArrayList<BrickPiece> pieces;
	private int x,y;
	private BrickGenerator brickMap;
	private boolean isActive;
	private long startTime;
	
	public BrickExplosion(int theX, int theY, BrickGenerator theBrickMap)
	{
		x = theX;
		y = theY;
		brickMap = theBrickMap;
		isActive = true;
		startTime = System.nanoTime();
		pieces = new ArrayList<BrickPiece>();
		initialize();
	}
	
	public void initialize() 
	{
		int randomNum = (int)(Math.random() * 20 + 5);
		
		for(int i = 0; i < randomNum; i++)
		{
			pieces.add(new BrickPiece(x, y, brickMap));
		}
	}
	
	public void update() 
	{
		for(BrickPiece bp : pieces)
		{
			bp.update();
		}
		if((System.nanoTime() - startTime) / 1000000 > 2000 && isActive)
		{
			isActive = false;
		}
	}
	
	public void draw(Graphics2D g) 
	{
		for(BrickPiece bp : pieces)
		{
			bp.draw(g);
		}
	}
		
	public boolean getIsActive() { return isActive;}
	
	
	
}