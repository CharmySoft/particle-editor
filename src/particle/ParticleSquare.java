package particle;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Random;

public class ParticleSquare extends Particle{

	private int rotation;
	private double rotationSpeed;

	public ParticleSquare(double x, double y, ParticleAttr attr, ParticleAttr attrVar) {
		super(x, y, attr, attrVar);

		Random rand = new Random();
		rotation = (int) (rand.nextFloat() * attrVar.emitterAngle - attrVar.emitterAngle/2 
				+ attr.emitterAngle);
		rotationSpeed = rand.nextFloat() * attrVar.emitterAngle;
	}

	public void makeMove(){
		super.makeMove();
		rotation += rotationSpeed;
	}
	
	public void paint(Graphics g) 
	{
		super.paint(g);
		Polygon rect = new Polygon();
		double s = Math.sin(Math.toRadians(rotation));
		double c = Math.cos(Math.toRadians(rotation));
		Point p;
		p = new Point((int)(x - size.width / 2), (int)(y - size.height / 2));
		p.setLocation((p.getX()-x) * c - (p.getY()-y) * s + x,
		            (p.getX()-x) * s + (p.getY()-y) * c + y);
		rect.addPoint((int)p.getX(), (int)p.getY());
		p = new Point((int)(x - size.width / 2), (int)(y + size.height / 2));
		p.setLocation((p.getX()-x) * c - (p.getY()-y) * s + x,
		            (p.getX()-x) * s + (p.getY()-y) * c + y);
		rect.addPoint((int)p.getX(), (int)p.getY());
		p = new Point((int)(x + size.width / 2), (int)(y + size.height / 2));
		p.setLocation((p.getX()-x) * c - (p.getY()-y) * s + x,
		            (p.getX()-x) * s + (p.getY()-y) * c + y);
		rect.addPoint((int)p.getX(), (int)p.getY());
		p = new Point((int)(x + size.width / 2), (int)(y - size.height / 2));
		p.setLocation((p.getX()-x) * c - (p.getY()-y) * s + x,
		            (p.getX()-x) * s + (p.getY()-y) * c + y);
		rect.addPoint((int)p.getX(), (int)p.getY());
		g.fillPolygon(rect);
	}
	
}
