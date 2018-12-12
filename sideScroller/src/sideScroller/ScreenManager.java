package sideScroller;

public class ScreenManager {
	private double dx; //offset in x and y
	private double dy;
	private double t; //rotation
	private double sx; //scale of x and y
	private double sy;
	
	public ScreenManager()
	{
		dx = 0;
		dy = 0;
		t = 0;
		sx = 1;
		sy = 1;
	}
	public ScreenManager(double dx, double dy)
	{
		this.dx = dx;
		this.dy = dy;
	}
	public void setOffset(double dx, double dy)
	{
		this.dx = dx;
		this.dy = dy;
	}
	public void changeOffset(double dx, double dy)
	{
		this.dx += dx;
		this.dy += dy;
	}
	public double[] getOffset()
	{
		double[] offset = {dx, dy};
		return offset;
	}
	public void setRotation(double t)
	{
		this.t = t;
	}
	public void changeRotation(double t)
	{
		this.t += t;
	}
	public double getRotation()
	{
		return t;
	}
	public void setScale(double sx, double sy)
	{
		this.sx = sx;
		this.sy = sy;
	}
	public void changeScale(double sx, double sy)
	{
		this.sx += sx;
		this.sy += sy;
	}
	public double[] getScale()
	{
		double[] scale = {sx, sy};
		return scale;
	}
	public double[] toScreen(double x, double y)
	{
		double[] xy = {x, y};
		if(t != 0)
		{
			double distance = Math.hypot(xy[0], xy[1]);
			double theta = Math.atan2(xy[1], xy[0]) + t;
			xy[0] = Math.cos(theta)*distance;
			xy[1] = Math.sin(theta)*distance;
		}
		xy[0] *= sx;
		xy[1] *= sy;
		
		xy[0] += dx;
		xy[1] += dy;
		return xy;
	}
	public double[] toScreen3D(double x, double y, double z)
	{
		double[] xyz = {x, y, z};
		if(t != 0)
		{
			double distance = Math.hypot(xyz[0], xyz[1]);
			double theta = Math.atan2(xyz[1], xyz[0]) + t;
			xyz[0] = Math.cos(theta)*distance;
			xyz[1] = Math.sin(theta)*distance;
		}
		xyz[0] *= sx;
		xyz[1] *= sy;
		
		xyz[0] += dx;
		xyz[1] += dy;
		return xyz;
	}

}
