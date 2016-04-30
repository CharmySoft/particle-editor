package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import particle.ParticleAttr;


public class ParticleEditor implements ChangeListener, ActionListener
{
	private JRadioButton rbCircle;
	private JRadioButton rbSquare;
	private JSlider [] confSliders;
	private JSlider [] colorSliders;
	private ParticleViewer particleViewer;
	private boolean bReady = false;
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

	private final String [] colorLabels = {"Start Red", "Start Green", "Start Blue", 
			"Finish Red", "Finish Green", "Finish Blue"};
	
	public static void main(String[] args) {
		ParticleEditor pe = new ParticleEditor();
		pe.readFile(new File("spring.ini"));
	}
	
	public void readFile(File file){
		Scanner in;
		try {
			in = new Scanner(file);
			for(JSlider s: confSliders){
				s.setValue(in.nextInt());
			}
			for(JSlider s: colorSliders){
				s.setValue(in.nextInt());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void saveFile(File file){
		try{
			PrintWriter writer = new PrintWriter(file);
			for(JSlider s: confSliders){
				writer.println(s.getValue());
			}
			for(JSlider s: colorSliders){
				writer.println(s.getValue());
			}
			writer.close();
		} 
		catch (IOException exception){
			exception.printStackTrace(); 
		}
	}
	
	public ParticleEditor (){
		JFrame frame = new JFrame();
		frame.setTitle("ParticleEditor");
		Container cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());

		particleViewer = new ParticleViewer();
		
		JPanel pParticle = new JPanel();
		Box b = new Box(BoxLayout.Y_AXIS);
		
		// ------------ Tool Bar --------------------
		JButton btnOpen = new JButton("Open");
		btnOpen.setToolTipText("Open file");	
		btnOpen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser(); 
				chooser.setFileFilter(new FileNameExtensionFilter("Particle Configuration Files" ,"ini"));
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
					readFile(chooser.getSelectedFile());

			}			
		});		
		JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Save file");
		btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser(); 
				chooser.setFileFilter(new FileNameExtensionFilter("Particle Configuration Files" ,"ini"));
				if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) 
					saveFile(chooser.getSelectedFile());

			}			
		});
		JToolBar jtb = new JToolBar();
		jtb.add(btnOpen);		
		jtb.add(btnSave);		

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
		int [] max = {300, 300, 200, 96, 96, 96, 96, 360, 360, 600, 600}; 
		int [] min = {20, 20, 0, 1, 1, 1, 1, 0, 0, 0, 0}; 
		for(int i = 0; i < confSliders.length; i++){
			JLabel label = new JLabel(labels[i]);
			pConf.add(label);
			confSliders[i] = new JSlider();
			confSliders[i].addChangeListener(this);
			confSliders[i].setMaximum(max[i]);
			confSliders[i].setMinimum(min[i]);
			pConf.add(confSliders[i]);
		}
		b.add(pConf);
		
		// ------------ Particle Color ------------
		JPanel pColor = new JPanel();
		pColor.setBorder(BorderFactory.createTitledBorder("Particle Color"));
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

		cp.add(jtb, BorderLayout.NORTH);
		cp.add(particleViewer, BorderLayout.CENTER);
		cp.add(pParticle, BorderLayout.EAST);

		bReady = true;
		update();
		frame.setSize(1024, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
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



