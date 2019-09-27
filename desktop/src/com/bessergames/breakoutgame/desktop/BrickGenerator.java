package com.bessergames.breakoutgame.desktop;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;


public class BrickGenerator
{
	public int map[][];
	public int brickwidth;
	public int brickheight;
	
	Random rand = new Random();
	
	public float r = rand.nextFloat();
	public float d = rand.nextFloat();
	public float b = rand.nextFloat();
	
	public BrickGenerator(int row, int col)
	{
		map = new int[row][col];
		for(int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[0].length;j++) 
			{
				map[i][j] = 1;
			}
		}
		brickwidth = 540/col;
		brickheight = 150/row;
		
	}
	
	public void numGenerator(float r, float d , float b) 
	{
		if(r == 0 && d == 0 && b == 0) 
		{
			r = rand.nextFloat();
			d = rand.nextFloat();
			b = rand.nextFloat();
		}
	}
	
	public void draw(Graphics2D g) 
	{
		
		for(int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[0].length;j++) 
			{				
				if(map[i][j] > 0) 
				{
					g.setColor(new Color(r,d,b));
					g.fillRect(j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);
					
					g.setStroke(new BasicStroke(7));
					g.setColor(Color.black);
					g.drawRect(j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);
				}
			}
		}
	}
	public void setBrickValue(int value, int row, int col)
	{
		map[row][col] = value;
	}
}
