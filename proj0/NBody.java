import java.io.DataInputStream;
import java.io.FileInputStream;

public class NBody
{
	public static void main(String[] args)
	{
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double univRadius = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		int num = planets.length;

		StdAudio.play("audio/2001.mid");

		StdDraw.setXscale(-univRadius, univRadius);
		StdDraw.setYscale(-univRadius, univRadius);
		StdDraw.clear();
		//to initialize the canvas 
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for(int i = 0; i < num; i++)
		{
			planets[i].draw();
		}

		//animations
		for(double t = 0; t < T; t+= dt)
		{
			double xForces[] = new double[num];
			double yForces[] = new double[num];
			//calculate x and y 
			for(int i = 0; i < num; i++)
			{
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			for(int i = 0; i < num; i++)
			{
				planets[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.clear();
			//to initialize the canvas 
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for(int i = 0; i < num; i++)
			{
				planets[i].draw();
			}
			StdDraw.show(10);
		}
		
	}

	public static double readRadius(String txt)
	{
		double radius = 0.0;
		try
		{
			DataInputStream di = new DataInputStream(new FileInputStream(txt));
			di.readLine();
			String radiusRaw = di.readLine();
			radius = convertToDouble(radiusRaw);
			di.close();
		}
		catch(Exception e)
		{

		}
		return radius;
	}

	public static Planet[] readPlanets(String txt)
	{
		int totalNum = 0;
		double uniR = 0.0;
		try
		{
			DataInputStream di = new DataInputStream(new FileInputStream(txt));
			int num = Integer.parseInt(di.readLine());
			di.readLine();
			Planet planets[] = new Planet[num];
			for(int i = 0; i < num; i++)
			{
				String planet_raw = di.readLine();
				planet_raw = planet_raw.substring(1);
				String elements[] = planet_raw.split("  ");
				double xxPos = convertToDouble(elements[0]);
				double yyPos = convertToDouble(elements[1]);
				double xxVel = convertToDouble(elements[2]);
				double yyVel = convertToDouble(elements[3]);
				double mass = convertToDouble(elements[4]);
				String file_elements[] = elements[elements.length - 1].split(" ");
				String imgFileName = file_elements[file_elements.length - 1];
				planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
			}
			di.close();
			return planets;
		}
		catch(Exception e)
		{

		}
		return null;
	}

	private static double convertToDouble(String s)
	{
		int splitIndex = s.indexOf("e");
		String sign = s.substring(splitIndex + 1, splitIndex + 2);
		double base = Double.parseDouble(s.substring(0, splitIndex));
		double exp = Double.parseDouble(s.substring(splitIndex + 2));
		if(sign.equals("+"))
			return base * Math.pow(10, exp);
		return base * Math.pow(10, -exp);
	}
}