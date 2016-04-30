package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import particle.ParticleAttr;


public class ParticleEditor implements ChangeListener, ActionListener
{
	private JRadioButton rbCircle;
	private JRadioButton rbSquare;
	private JSlider [] confSliders;
	private JSlider [] colorSliders;
	private ParticleViewer particleViewer;
	public enum S {
	    MAX_PARTICLES(0), 
	    LIFESPAN(1), LIFESPAN_VAR(2),
	    START_SIZE(3), START_SIZE_VAR(4),
	    FINISH_SIZE(5), FINISH_SIZE_VAR(6),
	    EMITTER_ANGLE(7), EMITTER_ANGLE_VAR(8),
	    SPEED(9), SPEED_VAR(10)
	    ;
	    int i; private S(int i){this.i=i;}
	}
	
	private final String [] labels = {"Max Particles", 
			"Lifespan", "Lifespan Variance",
			"Start Size", "Start Size Variance",
			"Finish Size", "Finish Size Variance",
			"Emitter Angle", "Angle Variance",
			"Speed", "Speed Variance"
			};
	
	private boolean bReady = false;
	
	public static void main(String[] args) 
	{
		new ParticleEditor();
	}
	
	public ParticleEditor ()
	{
		
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		
		JFrame frame = new JFrame();
		frame.setTitle("ParticleEditor");
		
		Container cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());

		particleViewer = new ParticleViewer();
		
		JPanel pParticle = new JPanel();
		Box b = new Box(BoxLayout.Y_AXIS);
		

		// ------------ Particle Pattern ------------
		JPanel pPattern = new JPanel();
		pPattern.setBorder(BorderFactory.createTitledBorder("Particle Pattern"));
		rbCircle = new JRadioButton("Circle");
		rbCircle.addActionListener(this);
		rbSquare = new JRadioButton("Square");
		rbSquare.addActionListener(this);
		rbCircle.setSelected(true);
		
		ButtonGroup group = new ButtonGroup();
	    group.add(rbCircle);
	    group.add(rbSquare);

		pPattern.add(rbCircle);
		pPattern.add(rbSquare);
		
		b.add(pPattern);
		
		// ------------ Particle Config ------------
		JPanel pConf = new JPanel();
		pConf.setBorder(BorderFactory.createTitledBorder("Particle Configuration"));

		confSliders = new JSlider [labels.length];
		pConf.setLayout(new GridLayout(labels.length, 2));
		for(int i = 0; i < confSliders.length; i++){
			JLabel label = new JLabel(labels[i]);
			pConf.add(label);
			confSliders[i] = new JSlider();
			confSliders[i].addChangeListener(this);
			pConf.add(confSliders[i]);
		}

		confSliders[S.MAX_PARTICLES.i].setMaximum(300);
		confSliders[S.MAX_PARTICLES.i].setMinimum(20);
		confSliders[S.MAX_PARTICLES.i].setValue(250);
		confSliders[S.LIFESPAN.i].setMaximum(300);
		confSliders[S.LIFESPAN.i].setMinimum(20);
		confSliders[S.LIFESPAN.i].setValue(100);
		confSliders[S.LIFESPAN_VAR.i].setMaximum(200);
		confSliders[S.LIFESPAN_VAR.i].setMinimum(0);
		confSliders[S.LIFESPAN_VAR.i].setValue(100);
		confSliders[S.START_SIZE.i].setMaximum(96);
		confSliders[S.START_SIZE.i].setMinimum(1);
		confSliders[S.START_SIZE.i].setValue(32);
		confSliders[S.START_SIZE_VAR.i].setMaximum(96);
		confSliders[S.START_SIZE_VAR.i].setMinimum(1);
		confSliders[S.START_SIZE_VAR.i].setValue(8);
		confSliders[S.FINISH_SIZE.i].setMaximum(96);
		confSliders[S.FINISH_SIZE.i].setMinimum(1);
		confSliders[S.FINISH_SIZE.i].setValue(8);
		confSliders[S.FINISH_SIZE_VAR.i].setMaximum(96);
		confSliders[S.FINISH_SIZE_VAR.i].setMinimum(1);
		confSliders[S.FINISH_SIZE_VAR.i].setValue(4);
		confSliders[S.EMITTER_ANGLE.i].setMaximum(360);
		confSliders[S.EMITTER_ANGLE.i].setMinimum(0);
		confSliders[S.EMITTER_ANGLE.i].setValue(180);
		confSliders[S.EMITTER_ANGLE_VAR.i].setMaximum(360);
		confSliders[S.EMITTER_ANGLE_VAR.i].setMinimum(0);
		confSliders[S.EMITTER_ANGLE_VAR.i].setValue(90);
		confSliders[S.SPEED.i].setMaximum(600);
		confSliders[S.SPEED.i].setMinimum(0);
		confSliders[S.SPEED.i].setValue(250);
		confSliders[S.SPEED_VAR.i].setMaximum(600);
		confSliders[S.SPEED_VAR.i].setMinimum(0);
		confSliders[S.SPEED_VAR.i].setValue(100);
		
		b.add(pConf);
		
		// ------------ Particle Color ------------
		JPanel pColor = new JPanel();
		pColor.setBorder(BorderFactory.createTitledBorder("Particle Color"));
		
		String [] colorLabels = {"Start Red", "Start Green", "Start Blue", 
				"Finish Red", "Finish Green", "Finish Blue"};
		colorSliders = new JSlider [colorLabels.length];
		pColor.setLayout(new GridLayout(colorLabels.length, 2));
		for(int i = 0; i < colorSliders.length; i++){
			JLabel label = new JLabel(colorLabels[i]);
			pColor.add(label);
			colorSliders[i] = new JSlider();
			colorSliders[i].setMaximum(255);
			colorSliders[i].setMinimum(0);
			colorSliders[i].setValue(255);
			colorSliders[i].addChangeListener(this);
			pColor.add(colorSliders[i]);
		}
		b.add(pColor);
		// ---------------------------------------
		
		pParticle.add(b);
		
		cp.add(particleViewer, BorderLayout.CENTER);
		cp.add(pParticle, BorderLayout.EAST);

		bReady = true;
		stateChanged(null);
		frame.setSize(1024, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}
	
	public void update(){
		if(!bReady)
			return;
		particleViewer.setMaxParticles(confSliders[S.MAX_PARTICLES.i].getValue());
		ParticleAttr ptcAttr = new ParticleAttr();
		ptcAttr.lifespan = (confSliders[S.LIFESPAN.i].getValue());
		ptcAttr.startSize = (confSliders[S.START_SIZE.i].getValue());
		ptcAttr.finishSize = (confSliders[S.FINISH_SIZE.i].getValue());
		ptcAttr.emitterAngle = (confSliders[S.EMITTER_ANGLE.i].getValue());
		ptcAttr.speed = (confSliders[S.SPEED.i].getValue() / 100);
		ptcAttr.startColor = new Color(colorSliders[0].getValue(),
				colorSliders[1].getValue(), colorSliders[2].getValue());
		ptcAttr.endColor = new Color(colorSliders[3].getValue(),
				colorSliders[4].getValue(), colorSliders[5].getValue());
		particleViewer.setParticleAttr(ptcAttr);
		ParticleAttr ptcAttrVar = new ParticleAttr();
		ptcAttrVar.lifespan = (confSliders[S.LIFESPAN_VAR.i].getValue());
		ptcAttrVar.startSize = (confSliders[S.START_SIZE_VAR.i].getValue());
		ptcAttrVar.finishSize = (confSliders[S.FINISH_SIZE_VAR.i].getValue());
		ptcAttrVar.speed = (confSliders[S.SPEED_VAR.i].getValue() / 100);
		ptcAttrVar.emitterAngle = (confSliders[S.EMITTER_ANGLE_VAR.i].getValue());
		particleViewer.setParticleAttrVar(ptcAttrVar);
		particleViewer.setPattern(rbCircle.isSelected()? 0: 1);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		update();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		update();
	}


}



