package particle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

public class Particle extends ParticleAttr{
	protected double x;
	protected double y;
	private Color color;
	private int life;
	private boolean alive;
	protected Dimension size;
	
	public Particle(double x, double y, ParticleAttr attr, ParticleAttr attrVar) {
		super();
		this.x = x;
		this.y = y;
		life = 0;
		alive = true;
		size = new Dimension();
		color = Color.WHITE;
		Random rand = new Random();
		lifespan = (int) (rand.nextFloat() * attrVar.lifespan - attrVar.lifespan/2 
				+ attr.lifespan);
		startSize = (int) (rand.nextFloat() * attrVar.startSize - attrVar.startSize/2 
				+ attr.startSize);
		finishSize = (int) (rand.nextFloat() * attrVar.finishSize - attrVar.finishSize/2 
				+ attr.finishSize);
		emitterAngle = (int) (rand.nextFloat() * attrVar.emitterAngle - attrVar.emitterAngle/2 
				+ attr.emitterAngle);
		speed = (rand.nextFloat() * attrVar.speed - attrVar.speed/2 
				+ attr.speed);
		startColor = attr.startColor;
		endColor = attr.endColor;
	}
	
	public void makeMove(){
		if(!isAlive()){
			return;
		}
		life++;
		if(life > lifespan){
			alive = false;
			return;
		}		
		x += speed * Math.sin(Math.toRadians(emitterAngle));
		y += speed * Math.cos(Math.toRadians(emitterAngle));
		int newSize = (int) (startSize + (finishSize - startSize) * (1.0 * life / lifespan));
		size.setSize(newSize, newSize);
		int newR = (int) (startColor.getRed() + 
				(endColor.getRed() - startColor.getRed()) * (1.0 * life / lifespan));
		int newG = (int) (startColor.getGreen() + 
				(endColor.getGreen() - startColor.getGreen()) * (1.0 * life / lifespan));
		int newB = (int) (startColor.getBlue() + 
				(endColor.getBlue() - startColor.getBlue()) * (1.0 * life / lifespan));
		color = new Color(newR, newG, newB);
		
	}
	
	public void paint(Graphics g) 
	{
		g.setColor(color);
	}
	
	public boolean isAlive(){
		return alive;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Dimension getSize() {
		return this.size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	
	
	
}
