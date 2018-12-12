package sideScroller;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player {
	double x;
	double y;
	double vx;
	double vy;
	double iters = 0;
	Function fn;
	Image player;
	Image torso;
	Image arm;
	Image leg;
	Image torso1;
	Image arm1;
	Image leg1;
	Inventory inv = new Inventory();
	int animateCount = 0;
	int animateType;
	AffineTransform[] tx = new AffineTransform[5];
	/*
	 * 0 - torso
	 * 1 - bottom arm
	 * 2 - top arm
	 * 3 - bottom leg
	 * 4 - top leg
	 */
	
	public Player()
	{
		x = 160;
		y = 180;
		vx = 0;
		vy = 0;
		fn = new Function();
		player = fn.getImage("ping_pong_player.png");
		torso = fn.getImage("torso.png");
		arm = fn.getImage("arm.png");
		leg = fn.getImage("leg.png");
		
		for(int i = 0; i < tx.length; i++)
		{
			tx[i] = AffineTransform.getTranslateInstance(x, y);
		}
	}
	public void draw(Graphics g, ScreenManager sm, int mx, int my)
	{
		Graphics2D g2 = (Graphics2D)g;
		double[] scale = sm.getScale();
		//g2.drawImage(player, (int) x, (int) y, 30, 60, null);
		//g2.drawImage(arm, tx[1], null);
		if(mx > x)
		{
			g2.drawImage(leg, tx[3], null);
			g2.drawImage(torso, tx[0], null);
			g2.drawImage(leg, tx[4], null);
			g2.drawImage(arm, tx[2], null);
		}
		else
		{
			g2.drawImage(leg, tx[3], null);
			g2.drawImage(torso1, tx[0], null);
			g2.drawImage(leg, tx[4], null);
			g2.drawImage(arm, tx[2], null);
		}
		if(inv.open)
			inv.draw(g2);
	}
	public void step(boolean left, boolean right, boolean jump, boolean boost, double gravity, Terrain t)
	{
		boolean leftContact = t.contact(x, y + 30);
		boolean rightContact = t.contact(x + 30, y + 30);
		boolean inWater = t.inWater(x + 15, y + 60);
		
		for(int i = 0; i < tx.length; i++)
		{
			if(i > 0) //not the torso
				tx[i].setToScale(0.2, 0.2);
			else
				tx[i].setToScale(0.3, 0.3);
		}
		
		translate();
		iters += 0.2;
		if(animateCount > 0)
		{
			animateCount--;
		}
		if(left && !right && !leftContact)
		{
			if(boost)
				vx = -8;
			else
				vx = -4;
			walk();
		}
		else if(right && !left && !rightContact)
		{
			if(boost)
				vx = 8;
			else
				vx = 4;
			walk();
		}
		else
		{
			vx = 0;
		}
		
		if(t.contact(x + 15, y + 60))
		{
			vy = 0;
			if(jump)
				vy = -10;
		}
		else
		{
			if(inWater)
			{
				if(vy < 3)
					vy += gravity/5;
				else
					vy = 3;
			}
			else
				vy += gravity;
		}
		if(inWater && jump)
		{
			vy = -3;
		}
		
		y += vy;
		x += vx;
		
		
	}
	public void move(double x, double y)
	{
//		for(int i = 0; i < tx.length; i++)
//		{
//			tx[i] = AffineTransform.getTranslateInstance(x, y);
//		}
		this.x += x;
		this.y += y;
		
		translate();
	}
	private void translate()
	{
		tx[0].translate(x*10/3, (y + 5)*10/3);
		tx[1].translate((x + 5)*10/2, (y + 20)*10/2);
		tx[2].translate((x + 5)*10/2, (y + 20)*10/2);
		tx[3].translate((x + 5)*10/2, (y + 35)*10/2);
		tx[4].translate((x + 5)*10/2, (y + 35)*10/2);
	}
	private void walk()
	{
		tx[1].rotate(Math.sin(iters)/2, 15, 25);
		tx[2].rotate(-Math.sin(iters)/2, 15, 25);
		tx[3].rotate(-Math.sin(iters)/2);
		tx[4].rotate(Math.sin(iters)/2);
		
	}

}

class Inventory
{
	Function fn = new Function();
	boolean open = true;
	Image frame = fn.getImage("frame_large.png");
	ArrayList<Slot> slots = new ArrayList<Slot>();
	Image stoneBrick = fn.getImage("stone_brick.png");
	Image grassBlock = fn.getImage("grass_block.png");
	Image waterSource = fn.getImage("water_source.png");
	Image diamondBlock = fn.getImage("diamond_block.png");
	Image stoneBlock = fn.getImage("stone_block.png");
	Image ironBlock = fn.getImage("iron_block.png");
	public Inventory()
	{
		
	}
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(frame, 75, 75, 1200, 600, null);
		for(int i = 0; i < 10; i++)
		{
			g2.drawImage(frame, 75 + (i*100) + 20, 675 - 100 - 20, 100, 100, null);
		}
		g2.drawImage(grassBlock, 75 + (0*100) + 25, 675 - 100 - 15, 75, 75, null);
		g2.drawImage(stoneBrick, 75 + (1*100) + 25, 675 - 100 - 15, 75, 75, null);
		g2.drawImage(grassBlock, 75 + (2*100) + 25, 675 - 100 - 15, 75, 75, null);
		g2.drawImage(grassBlock, 75 + (3*100) + 25, 675 - 100 - 15, 75, 75, null);
		g2.drawImage(grassBlock, 75 + (4*100) + 25, 675 - 100 - 15, 75, 75, null);
		g2.drawImage(grassBlock, 75 + (5*100) + 25, 675 - 100 - 15, 75, 75, null);
		g2.drawImage(grassBlock, 75 + (6*100) + 25, 675 - 100 - 15, 75, 75, null);
		g2.drawImage(grassBlock, 75 + (7*100) + 25, 675 - 100 - 15, 75, 75, null);
		g2.drawImage(grassBlock, 75 + (8*100) + 25, 675 - 100 - 15, 75, 75, null);
		g2.drawImage(grassBlock, 75 + (9*100) + 25, 675 - 100 - 15, 75, 75, null);
		g2.drawImage(frame, 75 + 1200 - 150 - 20, 75 + 20, 150, 500, null);
		
	}
	public void addSlot(String type)
	{
		if(slots.size() <= 10)
			slots.add(new Slot(type));
	}
}
class Slot
{
	String type;
	int items;
	public Slot(String type)
	{
		this.type = type;
		items = 0;
	}
}
