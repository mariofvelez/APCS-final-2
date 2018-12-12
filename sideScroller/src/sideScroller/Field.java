package sideScroller;

import java.awt.geom.Ellipse2D;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.net.URL;
import java.util.ArrayList;
import javax.sound.sampled.Line;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
//import sideScroller.Function;

public class Field extends Canvas
		implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener, Runnable {
	// private Object[][] grid = new Object[50][50];
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Graphics bufferGraphics; // graphics for backbuffer
	private BufferStrategy bufferStrategy;

	public float timeStep = 1f / 60;
	public int velIters = 8;
	public int posIters = 3;

	int mousex = 0; // mouse values
	int mousey = 0;
	
	int bx = 0;
	int by = 0;
	double bz = 0;
	double bv = 0;
	int bvx = 0;
	int bvy = 10;
	double gravity = 0.6;

	Font font = new Font("Arial", 0, 15); // font
	Font font1 = new Font("Airstrike Regular", 0, 80);
	Font font2 = new Font("Airstrike Regular", 0, 60);
	Font font3 = new Font("Airstrike Regular", 0, 30);
	Font font4 = new Font("Airstrike Regular", 0, 15);
	Color color = new Color(50, 50, 50);
	Color color1 = new Color(200, 200, 200);
	
	Function fn = new Function();
	static ScreenManager sm = new ScreenManager();
	Player p = new Player();
	static TerrainGenerator tg = new TerrainGenerator();

	private int runTime = 0;

	private ArrayList<Integer> keysDown; // holds all the keys being held down
	private ArrayList<Integer> buttonsDown;

	private Thread thread;

	private boolean running;

	public Field(Dimension size) throws Exception {
		this.setPreferredSize(size);
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		
		sm.setOffset(250, 290);
		sm.setScale(30, 30);

		this.thread = new Thread(this);
		running = true;

		keysDown = new ArrayList<Integer>();

		File newFile = new File("C:/Users/Mario Velez/Desktop/enemy1.txt");
		if (newFile.exists())
			System.out.println("The file already exists");
		else {
			try {
				newFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				FileWriter filew = new FileWriter(newFile);
				BufferedWriter buffw = new BufferedWriter(filew);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		FileReader file = new FileReader("C:/Users/Mario Velez/Desktop/enemy1.txt");
		BufferedReader reader = new BufferedReader(file);

		ArrayList<String> text = new ArrayList<String>();
		String line = reader.readLine();

		while (line != null) {
			text.add(line);
			line = reader.readLine();

		}
		reader.close();
		for (int i = 0; i < text.size(); i++) {
			System.out.println(text.get(i));
		}
		tg.generate(tg.terrain.seed);

	}

	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		if (bufferStrategy == null) {
			this.createBufferStrategy(2);
			bufferStrategy = this.getBufferStrategy();
			bufferGraphics = bufferStrategy.getDrawGraphics();

			this.thread.start();
		}
	}

	@Override
	public void run() {
		// what runs when editor is running
		while (running) {
			DoLogic();
			Draw();

			DrawBackbufferToScreen();

			Thread.currentThread();
			try {
				Thread.sleep(16, 667);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void DrawBackbufferToScreen() {
		bufferStrategy.show();

		Toolkit.getDefaultToolkit().sync();
	}

	/*
	 * =============================================================================
	 * ====================================== TITLE SCREEN LOGIC
	 * =============================================================================
	 * =====================================
	 */
	public void DoLogic() {
		double speed = 0.2;
		double[] offset = sm.getOffset();
		double[] scale = sm.getScale();
		p.step(keysDown.contains(KeyEvent.VK_LEFT),
				keysDown.contains(KeyEvent.VK_RIGHT),
				keysDown.contains(KeyEvent.VK_SPACE),
				keysDown.contains(KeyEvent.VK_SHIFT), gravity, tg.terrain);
		if(keysDown.contains(KeyEvent.VK_UP))
			sm.changeScale(0.1, 0.1);
		if(keysDown.contains(KeyEvent.VK_DOWN))
			sm.changeScale(-0.1, -0.1);
		if(keysDown.contains(KeyEvent.VK_W) && offset[1] > 0)
		{
			sm.changeOffset(0, -speed);
			p.move(0, speed*scale[1]);
		}
		if(keysDown.contains(KeyEvent.VK_A) && offset[0] > 0)
		{
			sm.changeOffset(-speed, 0);
			p.move(speed*scale[0], 0);
		}
		if(keysDown.contains(KeyEvent.VK_S))
		{
			sm.changeOffset(0, speed);
			p.move(0, -speed*scale[1]);
		}
		if(keysDown.contains(KeyEvent.VK_D))
		{
			sm.changeOffset(speed, 0);
			p.move(-speed*scale[0], 0);
		}
		
	}

	public void Draw() // titleScreen
	{
		// clears the backbuffer
		bufferGraphics = bufferStrategy.getDrawGraphics();
		try {
			bufferGraphics.clearRect(0, 0, this.getSize().width, this.getSize().height);
			// where everything will be drawn to the backbuffer
			Graphics2D g2 = (Graphics2D) bufferGraphics;
			//int mapSize = 500;
			tg.terrain.draw(g2, sm);
			p.draw(g2, sm, mousex, mousey);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bufferGraphics.dispose();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!keysDown.contains(e.getKeyCode()) && e.getKeyCode() != 86)
			keysDown.add(new Integer(e.getKeyCode()));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(keysDown.contains(KeyEvent.VK_SPACE))
			bv = 10;
		keysDown.remove(new Integer(e.getKeyCode()));
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	public void Sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		

	}

	@Override
	public void mousePressed(MouseEvent e) {
		

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		

	}

}
