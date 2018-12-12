package sideScroller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import sideScroller.Window;
import sideScroller.Field;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Window extends JFrame
{
	public Window(String name)
	{
		super(name);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension windowSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		this.setSize(windowSize);
	}
	public static void main(String[] args) throws Exception
	{
		Window window = new Window("Window");
		
		Container contentPane = window.getContentPane();
		contentPane.setLayout(new GridLayout(1,1));
		
		Field edit2 = new Field(window.getSize());
		contentPane.add(edit2);
		
		window.setVisible(true);
		window.setLocation(-7, 0);
		//window.setResizable(false);
	}
}