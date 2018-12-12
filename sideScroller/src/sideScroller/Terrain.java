package sideScroller;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

public class Terrain {
	Function fn = new Function();
	static String[][] map = new String[500][500];
	int waterHeight = 200;
	Image stoneBrick = fn.getImage("stone_brick.png");
	Image grassBlock = fn.getImage("grass_block.png");
	Image waterSource = fn.getImage("water_source.png");
	Image diamondBlock = fn.getImage("diamond_block.png");
	Image stoneBlock = fn.getImage("stone_block.png");
	Image ironBlock = fn.getImage("iron_block.png");
	int seed;
	ArrayList<Hitbox> hitboxes = new ArrayList<Hitbox>();
	ArrayList<WaterHitbox> waterSources = new ArrayList<WaterHitbox>();
	public Terrain()
	{
		seed = 5;//716310180;//(int) (Math.random()*10000);
		System.out.println(seed);
	}
	public void draw(Graphics g, ScreenManager sm)
	{
		Graphics2D g2 = (Graphics2D)g;
		double[] offset = sm.getOffset();
		double[] scale = sm.getScale();
		hitboxes.removeAll(hitboxes);
		waterSources.removeAll(waterSources);
		for(int x = (int) (offset[0]); x < 90 + (int) (offset[0]); x++)
		{
			for(int y = (int) (offset[1]); y < 45 + (int) (offset[1]); y++)
			{
				double[] point = sm.toScreen(x, y);
				point[0] -= (offset[0]*scale[0]) + offset[0];
				point[1] -= (offset[1]*scale[1]) + offset[1];
				
				if(map[x][y].equals("block"))
				{
					
					g2.drawImage(stoneBlock, (int) (point[0]),
									  		 (int) (point[1]),
									  		 (int) (scale[0]),
									  		 (int) (scale[1]), null);
					hitboxes.add(new Hitbox((int) (point[0]),
									  		(int) (point[1]),
									  		(int) (scale[0]),
									  		(int) (scale[1])));
				}
				else if(map[x][y].equals("grass"))
				{
					g2.drawImage(grassBlock, (int) (point[0]),
									  		 (int) (point[1]),
									  		 (int) (scale[0]),
									  		 (int) (scale[1]), null);
					hitboxes.add(new Hitbox((int) (point[0]),
									  		(int) (point[1]),
									  		(int) (scale[0]),
									  		(int) (scale[1])));
				}
				else if(map[x][y].equals("water"))
				{
					g2.drawImage(waterSource, (int) (point[0]),
											  (int) (point[1]),
											  (int) (scale[0]),
											  (int) (scale[1]), null);
					waterSources.add(new WaterHitbox((int) (point[0]),
											(int) (point[1]),
											(int) (scale[0]),
											(int) (scale[1])));
				}
				else if(map[x][y].equals("diamond"))
				{
					g2.drawImage(diamondBlock, (int) (point[0]),
											   (int) (point[1]),
											   (int) (scale[0]),
											   (int) (scale[1]), null);
					hitboxes.add(new Hitbox((int) (point[0]),
											(int) (point[1]),
											(int) (scale[0]),
											(int) (scale[1])));
				}
				else if(map[x][y].equals("iron"))
				{
					g2.drawImage(ironBlock, (int) (point[0]),
											(int) (point[1]),
											(int) (scale[0]),
											(int) (scale[1]), null);
					hitboxes.add(new Hitbox((int) (point[0]),
											(int) (point[1]),
											(int) (scale[0]),
											(int) (scale[1])));
				}
				
			}
		}
		for(Hitbox i : hitboxes)
		{
			//i.draw(g2);
		}
	}
	public void step(ScreenManager sm)
	{
		double[] offset = sm.getOffset();
		double[] scale = sm.getScale();
		for(int x = (int) (offset[0]); x < 90 + (int) (offset[0]); x++)
		{
			for(int y = (int) (offset[1]); y < 45 + (int) (offset[1]); y++)
			{
				double[] point = sm.toScreen(x, y);
				point[0] -= (offset[0]*scale[0]) + offset[0];
				point[1] -= (offset[1]*scale[1]) + offset[1];
			}
		}
	}
	public void addObject(String block, int x, int y)
	{
		map[x][y] = block;
	}
	public boolean contact(double x, double y)
	{
		for(Hitbox i : hitboxes)
		{
			if(i.touching(x, y))
				return true;
		}
		return false;
	}
	public boolean inWater(double x, double y)
	{
		for(WaterHitbox i : waterSources)
		{
			if(i.touching(x, y))
				return true;
		}
		return false;
	}

}
class Hitbox
{
	double x;
	double y;
	double w;
	double h;
	Hitbox(double x, double y, double w, double h)
	{
		this.x = x;
		this.y = y;
		this.w = w + 1;
		this.h = h + 1;
	}
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.drawRect((int) x, (int) y, (int) w, (int) h);
	}
	public boolean touching(double x, double y)
	{
		if(x > this.x && x < this.x + this.w && y > this.y && y < this.y + this.h)
			return true;
		else
			return false;
	}
}
class WaterHitbox
{
	double x;
	double y;
	double w;
	double h;
	WaterHitbox(double x, double y, double w, double h)
	{
		this.x = x;
		this.y = y;
		this.w = w + 1;
		this.h = h + 1;
	}
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.drawRect((int) x, (int) y, (int) w, (int) h);
	}
	public boolean touching(double x, double y)
	{
		if(x > this.x && x < this.x + this.w && y > this.y && y < this.y + this.h)
			return true;
		else
			return false;
	}
}
