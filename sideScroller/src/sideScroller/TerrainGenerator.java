package sideScroller;

//only generates the terrain
public class TerrainGenerator {
	
	Terrain terrain = new Terrain();
	
	public TerrainGenerator()
	{
		
	}
	public void generate(int seed)
	{
		//use some kind of noise to generate terrain with floating islands
		terrain.addObject("block", 0, 0);
		System.out.println("generating structure");
		
		int r = random(seed);
		int iters = 30;
		for(int i = 0; i < iters; i++)
		{
			for(int x = 0; x < Terrain.map.length; x++) //random stone terrain
			{
				for(int y = 0; y < Terrain.map[x].length; y++)
				{
					int r1 = random(r);
					int r2 = random(r1);
					r = random(r2);
//					if(x > 1 && Terrain.map[x-1][y].equals("block") && toDecimal(r)<0.4)
//						terrain.addObject("block", x, y);
//					else if(y > 1 && Terrain.map[x][y - 1].equals("block") && toDecimal(r1)<0.9)
//						terrain.addObject("block", x, y);
//					else if(toDecimal(r2)<0.03)
//						terrain.addObject("block", x, y);
//					else if(y > 15)
//						terrain.addObject("block", x, y);
//					else
//						terrain.addObject("empty", x, y);
					if(i == 0 && toDecimal(r)<0.015)
					{
						terrain.addObject("block", x, y);
					}
					else
					{
						if(i == 0)
							terrain.addObject("empty", x, y);
						else
						{
							r = random(r);
							if(x > 1 && Terrain.map[x-1][y].equals("block") && toDecimal(r)<0.6) {
								terrain.addObject("block1", x, y);
							}
							r = random(r);
							if(y > 1 && Terrain.map[x][y-1].equals("block") && toDecimal(r)<0.3) {
								terrain.addObject("block1", x, y);
							}
							r = random(r);
							if(y < Terrain.map[x].length - 1 && Terrain.map[x][y+1].equals("block") && toDecimal(r)<0.3) {
								terrain.addObject("block1", x, y);
							}
							r = random(r);
							if(x < Terrain.map.length - 1 && Terrain.map[x+1][y].equals("block") && toDecimal(r)<0.6) {
								terrain.addObject("block1", x, y);
							}
						}
					}
					
	//				System.out.println(toDecimal(r));
	//				System.out.println(toDecimal(r1));
	//				System.out.println(toDecimal(r2));
					
				}
				
			}
			for(int x = 0; x < Terrain.map.length; x++) //random stone terrain
			{
				for(int y = 0; y < Terrain.map[x].length; y++)
				{
					if(Terrain.map[x][y].equals("block1"))
						terrain.addObject("block", x, y);
				}
			}
			System.out.println((i + 1)*(100/iters) + "% complete");
		}
		System.out.println("filling gaps");
		for(int i = 0; i < 5; i++)
		{
			for(int x = 0; x < Terrain.map.length; x++) //random stone terrain
			{
				for(int y = 0; y < Terrain.map[x].length; y++)
				{
					int adj = 0;
					if(x > 1 && x < Terrain.map.length - 1)
					{
						if(Terrain.map[x-1][y].equals("block"))
							adj++;
						if(Terrain.map[x+1][y].equals("block"))
							adj++;
					}
					if(y > 1 && y < Terrain.map[x].length - 1)
					{
						if(Terrain.map[x][y - 1].equals("block"))
							adj++;
						if(Terrain.map[x][y + 1].equals("block"))
							adj++;
					}
					if(adj >= 3)
						terrain.addObject("block", x, y);
						
				}
			}
			System.out.println((i + 1)*20 + "% complete");
		}
		System.out.println("adding grass");
		for(int x = 0; x < Terrain.map.length; x++) //random stone terrain
		{
			for(int y = 0; y < Terrain.map[x].length; y++)
			{
				if(y > 0 && y < Terrain.map[x].length - 1 && Terrain.map[x][y - 1].equals("empty") && Terrain.map[x][y + 1].equals("block"))
				{
					terrain.addObject("grass", x, y);
				}
			}
		}
		System.out.println("adding water");
		for(int x = 0; x < Terrain.map.length; x++) //random stone terrain
		{
			for(int y = 0; y < Terrain.map[x].length; y++)
			{
				if(y > Terrain.map[x].length - terrain.waterHeight && Terrain.map[x][y].equals("empty"))
				{
					terrain.addObject("water", x, y);
				}
			}
		}
		System.out.println("adding ores");
		for(int i = 0; i < 3; i++)
		{
			for(int x = 0; x < Terrain.map.length; x++) //random stone terrain
			{
				for(int y = 20; y < Terrain.map[x].length; y++)
				{
					if(i == 0)
					{
						int depth = 0;
						for(; depth < 20 && Terrain.map[x][y - depth].equals("block"); depth++){}
						r = random(r);
						if(depth > 10 && toDecimal(r) < 0.018)
						{
							terrain.addObject("diamond", x, y);
						}
						r = random(r);
						if(depth > 5 && toDecimal(r) < 0.05)
						{
							terrain.addObject("iron", x, y);
						}
					}
					else
					{
						if(x > 5 && y > 5 && x < Terrain.map.length - 5 && y < Terrain.map[x].length - 5)
						{
							r = random(r);
							if((Terrain.map[x][y - 1].equals("diamond") ||
								Terrain.map[x][y + 1].equals("diamond") ||
								Terrain.map[x - 1][y].equals("diamond") ||
								Terrain.map[x + 1][y].equals("diamond")) && toDecimal(r) < 0.4)
							{
								terrain.addObject("diamond", x, y);
							}
							else if((Terrain.map[x][y - 1].equals("iron") ||
									 Terrain.map[x][y + 1].equals("iron") ||
									 Terrain.map[x - 1][y].equals("iron") ||
									 Terrain.map[x + 1][y].equals("iron")) && toDecimal(r) < 0.4)
							{
								terrain.addObject("iron", x, y);
							}
						}
					}
					
					
				}
			}
		}
		System.out.println("finished");
		//change the type of noise based on the biome
		//add water
		//add caves
		//add trees
		//add generated structures
		//add animals
	}
	public int index(String s, int i)
	{
		return Integer.parseInt(s.substring(i, i + 1));
	}
	public int random(int seed)
	{
		int s0 = seed;
		String s = Integer.toString(seed);
		int s1 = seed*seed;//(int) (Math.pow(10, s.length()-1) - seed);
		s1 ^= s1<<23;
		s1 ^= s1>>17;
		s1 ^= s0;
		s1 ^= s0>>26;
		return s1 + s0;
	}
	public double toDecimal(int a)
	{
		double n = Math.abs(a);
		String s = Integer.toString(a);
		double n1 = (int) (Math.pow(10, s.length()));
		return n/n1;
//		while(n>=1)
//		{
//			n/=10;
//		}
//		n *= (10/9);
//		return n;
	}
	

}
