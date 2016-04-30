package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import particle.Particle;
import particle.ParticleAttr;
import particle.ParticleCircle;
import particle.ParticleSquare;

@SuppressWarnings("serial")
public class ParticleViewer extends JPanel implements MouseMotionListener, MouseListener
{
	private Particle [] particles = new Particle [300];
	private int maxParticles = 0;
	private int pattern = -1;
	private ParticleAttr ptcAttr = new ParticleAttr();
	private ParticleAttr ptcAttrVar = new ParticleAttr();
	private Point centerPos = new Point(320, 320);
	
	ParticleViewer(){
		for(int i = 0; i < particles.length; i++){
			particles[i] = generateNewParticle();
		}
		
		ActionListener move = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < maxParticles; i++){
					particles[i].makeMove();
					if(!particles[i].isAlive()){
						particles[i] = generateNewParticle();
					}
				}
				repaint();
			}
		};
		(new Timer(10,move)).start();
        addMouseMotionListener(this);
        addMouseListener(this);
	}

	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		this.setBackground(Color.BLACK);

		for(int i = 0; i < maxParticles; i++){
			Particle p = particles[i];
			p.paint(g);
		}		
	}
	
	Particle generateNewParticle(){
		Particle p;
		switch(pattern){
		case 0:
			p = new ParticleCircle(centerPos.x, centerPos.y, ptcAttr, ptcAttrVar);
			break;
		case 1:
			p = new ParticleSquare(centerPos.x, centerPos.y, ptcAttr, ptcAttrVar);
			break;
		default: 
			p = new Particle(centerPos.x, centerPos.y, ptcAttr, ptcAttrVar);
		}
		return p;
	}

	public void setParticleAttr(ParticleAttr ptcAttr) {
		this.ptcAttr = ptcAttr;
	}

	public void setParticleAttrVar(ParticleAttr ptcAttrVar) {
		this.ptcAttrVar = ptcAttrVar;
	}

	public void setMaxParticles(int maxPtcs) {
		this.maxParticles = maxPtcs;
	}
	
	public void setPattern(int i) {
		pattern = i;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		centerPos = e.getPoint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {
		centerPos.x = this.getWidth() / 2;
		centerPos.y = this.getHeight() / 2;	
	}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	
}

