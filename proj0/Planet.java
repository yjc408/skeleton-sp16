import java.lang.*;

public class Planet
{
	double xxPos;
	double yyPos;
	double xxVel;
	double yyVel;
	double mass;
	String imgFileName;

	public Planet(double xP, double yP, double xV, double yV, double m, String img)
	{
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = new String(img);
	}

	public Planet(Planet p)
	{
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = new String (p.imgFileName);
	}

	public double calcDistance(Planet p)
	{ 
		return Math.sqrt(Math.pow(xxPos - p.xxPos, 2) + Math.pow(yyPos - p.yyPos, 2));
	}

	public double calcForceExertedBy(Planet p)
	{
		double G = 6.67 * Math.pow(10, -11);
		return G * mass * p.mass / Math.pow(calcDistance(p), 2);
	}

	public double calcForceExertedByX(Planet p)
	{
 		return calcForceExertedBy(p) * (p.xxPos - xxPos) / calcDistance(p);
	}

	public double calcForceExertedByY(Planet p)
	{
		return calcForceExertedBy(p) * (p.yyPos - yyPos) / calcDistance(p);
	}

	public double calcNetForceExertedByX(Planet[] ps)
	{
		double netForceX = 0.0;
		for(int i = 0; i < ps.length; i++)
		{
			if(!this.equals(ps[i]))
			{
				netForceX += calcForceExertedByX(ps[i]);
			}
		}
		return netForceX;
	}

	public double calcNetForceExertedByY(Planet[] ps)
	{
		double netForceY = 0.0;
		for(int i = 0; i < ps.length; i++)
		{
			if(!this.equals(ps[i]))
			{
				netForceY += calcForceExertedByY(ps[i]);
			}
		}
		return netForceY;
	}

	public void update(double dt, double Fx, double Fy)
	{
		double ax = Fx / mass;
		double ay = Fy / mass;

		xxVel = xxVel + dt * ax;
		yyVel = yyVel + dt * ay;

		xxPos = xxPos + xxVel * dt;
		yyPos = yyPos + yyVel * dt;
	}

	public void draw()
	{
		StdDraw.picture(xxPos, yyPos, "images/"+imgFileName);
	}
}