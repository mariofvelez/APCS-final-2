package sideScroller;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Function {
	public int[] xPoints(double[][] a) // returns an array of the x positions of a button
	{
		int[] b = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			b[i] = (int) a[i][0];
		}
		return b;
	}
	public int[] xPoints3D(double[][] a) // returns an array of the x positions of a button
	{
		int[] b = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			b[i] = (int) a[i][0];
		}
		return b;
	}

	public int[] yPoints(double[][] a) // returns an array of the y positions of a button
	{
		int[] b = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			b[i] = (int) a[i][1];
		}
		return b;
	}
	public int[] yPoints3D(double[][] a) // returns an array of the y positions of a button
	{
		int[] b = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			b[i] = (int) (a[i][1] - a[i][2]);
		}
		return b;
	}

	public int min(int[] a) {
		int b = 1920;
		for (int i = 0; i < a.length; i++) {
			if (a[i] < b)
				b = a[i];
		}
		return b;
	}

	public int max(int[] a) {
		int b = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] > b)
				b = a[i];
		}
		return b;
	}

	public Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Field.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	public double[] selectionSort(double[] n)
	{
		double[] arr = n;
		int selected = 0;
		int comparisons = 0;
		int swaps = 0;
		for(int i = 0; i < arr.length; i++)
		{
			for(int j = selected; j < arr.length; j++)
			{
				if(arr[j] < arr[i])
				{
					double temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
					swaps++;
				}
				comparisons++;
			}
			selected++;
			//System.out.print(arr[i] + " ");
			
		}
		//System.out.println();
		//System.out.println("comparisons: " + comparisons);
		//System.out.println("swaps: " + swaps);
		return arr;
	}
	public int[] countingSort(int[] n)
	{
		int arr[] = n;
		int[] counter = new int[max(arr) + 1];
		for(int i = 0; i < arr.length; i++)
		{
			counter[arr[i]] ++;
		}
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i = 0; i < counter.length; i++)
		{
			for(int j = 0; j < counter[i]; j++)
			{
				temp.add(i);
			}
		}
		for(int i = 0; i < n.length; i++)
		{
			arr[i] = temp.get(i);
			//System.out.print(arr[i] + " ");
		}
		//System.out.println();
		return arr;
	}
}
