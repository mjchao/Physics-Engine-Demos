package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import demos.AngularMomentum;
import demos.CircularMotion;
import demos.DemoPanel;
import demos.Kinematics;
import demos.Magnetism;
import demos.Spring;

public class MainFrame extends JFrame {

	@SuppressWarnings("unused")
	final public static void main( String[] args ) {
		new MainFrame();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTabbedPane tbDemos = new JTabbedPane();
		private Kinematics pnlKinematics = new Kinematics();
		private CircularMotion pnlCircMotion = new CircularMotion();
		private AngularMomentum pnlAngularMomentum = new AngularMomentum();
		private Magnetism pnlMagnetism = new Magnetism();
		private Spring pnlSpring = new Spring();
	
	private DemoPanel pnlCurrent;
	private JPanel pnlControls;
	
	public MainFrame() {
		setLayout( new BorderLayout() );
		
		this.tbDemos.addTab( "Kinematics" , this.pnlKinematics );
		this.pnlCurrent = this.pnlKinematics;
		
		this.tbDemos.addTab( "Circular Motion" , this.pnlCircMotion );
		
		this.tbDemos.addTab( "Angular Momentum" , this.pnlAngularMomentum );
		
		this.tbDemos.addTab( "Magnetism" , this.pnlMagnetism );
		
		this.tbDemos.addTab( "Simple Harmonic Motion" , this.pnlSpring );
		
		add( this.tbDemos , BorderLayout.CENTER );
		
		this.pnlControls = new JPanel();
		this.pnlControls.add( this.pnlCurrent.getControlsPanel() );
		add( this.pnlControls , BorderLayout.SOUTH );
		
		addDemoTabListener();
		
		setVisible( true );
		setSize( 500 , 500 );
		setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
		
		this.pnlKinematics.run();
		this.pnlCircMotion.run();
		this.pnlAngularMomentum.run();
		this.pnlMagnetism.run();
		this.pnlSpring.run();
	}
	
	public void addDemoTabListener() {
		this.tbDemos.addChangeListener( new ChangeListener() {

			@Override
			public void stateChanged( ChangeEvent e ) {
				loadCorrectControlsPanel();
			}
			
		});
	}
	
	public void loadCorrectControlsPanel() {
		if ( this.tbDemos.getSelectedComponent() == this.pnlKinematics ) {
			this.pnlCurrent = this.pnlKinematics;
		} else if ( this.tbDemos.getSelectedComponent() == this.pnlCircMotion ) {
			this.pnlCurrent = this.pnlCircMotion;
		} else if ( this.tbDemos.getSelectedComponent() == this.pnlAngularMomentum ) {
			this.pnlCurrent = this.pnlAngularMomentum;
		} else if ( this.tbDemos.getSelectedComponent() == this.pnlMagnetism ) {
			this.pnlCurrent = this.pnlMagnetism;
		} else if ( this.tbDemos.getSelectedComponent() == this.pnlSpring ) {
			this.pnlCurrent = this.pnlSpring;
		}
		this.pnlControls.removeAll();
		this.pnlControls.add( this.pnlCurrent.getControlsPanel() );
		this.pnlControls.revalidate();
		this.pnlControls.repaint();
	}
}
