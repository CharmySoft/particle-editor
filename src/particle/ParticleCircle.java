package particle;

import java.awt.Graphics;

public class ParticleCircle extends Particle{
	
	public ParticleCircle(double x, double y, ParticleAttr attr, ParticleAttr attrVar) {
		super(x, y, attr, attrVar);
	}

	public void makeMove(){
		super.makeMove();
	}
	
	public void paint(Graphics g) 
	{
		super.paint(g);
		g.fillOval((int)x, (int)y, size.width, size.height);
	}
	
}
