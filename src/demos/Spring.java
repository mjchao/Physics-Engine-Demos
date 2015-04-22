package demos;

import graphics.particle.CenterOfMassMarker;
import graphics.particle.ParticleSphere;
import graphics.particle.UnanchoredSpringGraphic;
import gui.InputField;

import java.util.Scanner;

import javax.media.j3d.Transform3D;
import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import particle.Particle;
import particle.force.spring.ParticleUnanchoredSpring;
import _math.Real;
import _math.Vector3D;

public class Spring extends ParticleDemoPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SpringControlsPanel pnlControls = new SpringControlsPanel();
	
	private Particle m_sphere1;
	private ParticleSphere m_sphere1Graphic;
	private float m_sphere1Radius = 0.5f;
	private Real m_sphere1Mass = new Real( 5 );
	private Vector3D m_sphere1Pos = new Vector3D( new Real( -1.5f ) , new Real( 100 ) , new Real( 0 ) );
	private Vector3D m_sphere1Vel =  new Vector3D( new Real ( -3 ) , Real.ZERO , Real.ZERO );
	private Vector3D m_sphere1Accel = Vector3D.ZERO;
	
	private Particle m_sphere2;
	private ParticleSphere m_sphere2Graphic;
	private float m_sphere2Radius = 0.5f;
	private Real m_sphere2Mass = new Real( 5 );
	private Vector3D m_sphere2Pos = new Vector3D( new Real( 1.5f ) , new Real( 100 ) , new Real( 0 ) );
	private Vector3D m_sphere2Vel = Vector3D.ZERO;
	private Vector3D m_sphere2Accel = Vector3D.ZERO;
	
	private ParticleUnanchoredSpring m_spring;
	private UnanchoredSpringGraphic m_springGraphic;
	private Real m_springConstant = new Real( 100 );
	private Real m_restLength = new Real( 3 );
	
	private CenterOfMassMarker m_centerOfMass;
	
	public Spring() {
		this.m_sphere1 = new Particle( this.m_sphere1Mass , this.m_sphere1Pos , this.m_sphere1Vel , this.m_sphere1Accel );
		this.m_sphere1Graphic = new ParticleSphere( this.m_sphere1 , this.m_sphere1Radius );
		this.addObject( this.m_sphere1 , this.m_sphere1Graphic );
		
		this.m_sphere2 = new Particle( this.m_sphere2Mass , this.m_sphere2Pos , this.m_sphere2Vel , this.m_sphere2Accel );
		this.m_sphere2Graphic = new ParticleSphere( this.m_sphere2 , this.m_sphere2Radius );
		this.addObject( this.m_sphere2 , this.m_sphere2Graphic );
		
		this.m_spring = new ParticleUnanchoredSpring( this.m_sphere1 , this.m_springConstant , this.m_restLength );
		this.m_spring.addObject( this.m_sphere2 );
		this.m_springGraphic = new UnanchoredSpringGraphic( this.m_spring , this.m_sphere2 );
		this.addObject( this.m_springGraphic );
		this.m_world.addForceGenerator( this.m_spring );
		
		this.m_centerOfMass = new CenterOfMassMarker();
		this.m_centerOfMass.addMassedObject( this.m_sphere1 );
		this.m_centerOfMass.addMassedObject( this.m_sphere2 );
		this.addObject( this.m_centerOfMass );
		
		reset();
	}
	
	@Override
	public ControlPanel getControlsPanel() {
		return this.pnlControls;
	}
	
	@Override
	public void startSimulation() {
		super.startSimulation();
		try {
			float k = Float.valueOf( this.pnlControls.getK() ).floatValue();
			this.m_springConstant = new Real( k );
			
			float restLength = Float.valueOf( this.pnlControls.getRestLength() ).floatValue();
			this.m_restLength = new Real( restLength );
			
			float sphere1Mass = Float.valueOf( this.pnlControls.getSphere1Mass() ).floatValue();
			this.m_sphere1Mass = new Real( sphere1Mass );
			
			Scanner scan1Pos = new Scanner( this.pnlControls.getSphere1Pos() );
			this.m_sphere1Pos = new Vector3D( new Real( scan1Pos.nextFloat() ) , new Real( scan1Pos.nextFloat() ) , new Real( scan1Pos.nextFloat() ) );
			
			Scanner scan1Vel = new Scanner( this.pnlControls.getSphere1Vel() );
			this.m_sphere1Vel = new Vector3D( new Real( scan1Vel.nextFloat() ) , new Real( scan1Vel.nextFloat() ) , new Real( scan1Vel.nextFloat() ) );
			
			float sphere2Mass = Float.valueOf( this.pnlControls.getSphere2Mass() ).floatValue();
			this.m_sphere2Mass = new Real( sphere2Mass );
			
			Scanner scan2Pos = new Scanner( this.pnlControls.getSphere2Pos() );
			this.m_sphere2Pos = new Vector3D( new Real( scan2Pos.nextFloat() ) , new Real( scan2Pos.nextFloat() ) , new Real( scan2Pos.nextFloat() ) );
			
			Scanner scan2Vel = new Scanner( this.pnlControls.getSphere2Vel() );
			this.m_sphere2Vel = new Vector3D( new Real( scan2Vel.nextFloat() ) , new Real( scan2Vel.nextFloat() ) , new Real( scan2Vel.nextFloat() ) );
		} catch ( Exception e ) {
			JOptionPane.showMessageDialog( null , "Invalid starting conditions" );
			e.printStackTrace();
			reset();
			return;
		}
		
		this.m_sphere1.setMass( this.m_sphere1Mass );
		this.m_sphere1.setPosition( this.m_sphere1Pos );
		this.m_sphere1.setVelocity( this.m_sphere1Vel );
		this.m_sphere1.setAcceleration( this.m_sphere1Accel );
		
		this.m_sphere2.setMass( this.m_sphere2Mass );
		this.m_sphere2.setPosition( this.m_sphere2Pos );
		this.m_sphere2.setVelocity( this.m_sphere2Vel );
		this.m_sphere2.setAcceleration( this.m_sphere2Accel );
		
		this.m_spring.setSpringConstant( this.m_springConstant );
		this.m_spring.setRestLength( this.m_restLength );
	}
	
	@Override
	public void reset() {
		this.m_sphere1.setPosition( this.m_sphere1Pos );
		this.m_sphere2.setPosition( this.m_sphere2Pos );
		this.m_centerOfMass.draw();
		this.m_eyeX = this.m_centerOfMass.getX().value();
		this.m_eyeY = this.m_centerOfMass.getY().value();
		this.m_eyeZ = this.m_centerOfMass.getZ().value() - 15;
		Transform3D move = lookFromPointToPoint( new Point3d( this.m_eyeX , this.m_eyeY , this.m_eyeZ ) , new Point3d( this.m_eyeX , this.m_eyeY , this.m_eyeZ + 5 ) );
		updateEye( move );
		super.updateScene();
		super.reset();
	}
	
	

	protected class SpringControlsPanel extends ControlPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private InputField inpK;
		private InputField inpRestLength;
		private InputField inpSphere1Mass;
		private InputField inpSphere1Pos;
		private InputField inpSphere1Vel;
		private InputField inpSphere2Mass;
		private InputField inpSphere2Pos;
		private InputField inpSphere2Vel;
		
		public SpringControlsPanel() {
			super();
		}
		
		@Override
		public void setupControls() {
			super.setupControls();
			
			this.inpK = new InputField( "Spring Constant" , "100" , 2 );
			super.pnlInput.add( this.inpK );
			
			this.inpRestLength = new InputField( "Rest Length" , "3" , 2 );
			super.pnlInput.add( this.inpRestLength );
			
			this.inpSphere1Mass = new InputField( "Sphere 1 Mass" , "5" , 2 );
			super.pnlInput.add( this.inpSphere1Mass );
			
			this.inpSphere1Pos = new InputField( "Sphere 1 Pos" , "1.5 100 0" );
			super.pnlInput.add( this.inpSphere1Pos );
			
			this.inpSphere1Vel = new InputField( "Sphere 1 Vel" , "-3 0 0 " );
			super.pnlInput.add( this.inpSphere1Vel );
			
			this.inpSphere2Mass = new InputField( "Sphere 2 Mass" , "5" , 2 );
			super.pnlInput.add( this.inpSphere2Mass );
			
			this.inpSphere2Pos = new InputField( "Sphere 2 Pos" , "-1.5 100 0" );
			super.pnlInput.add( this.inpSphere2Pos );
			
			this.inpSphere2Vel = new InputField( "Sphere 2 Vel" , "0 0 0" );
			super.pnlInput.add( this.inpSphere2Vel );
			
		}
		
		public String getK() {
			return this.inpK.getInput();
		}
		
		public String getRestLength() {
			return this.inpRestLength.getInput();
		}
		
		public String getSphere1Mass() {
			return this.inpSphere1Mass.getInput();
		}
		
		public String getSphere1Pos() {
			return this.inpSphere1Pos.getInput();
		}
		
		public String getSphere1Vel() {
			return this.inpSphere1Vel.getInput();
		}
		
		public String getSphere2Mass() {
			return this.inpSphere2Mass.getInput();
		}
		
		public String getSphere2Pos() {
			return this.inpSphere2Pos.getInput();
		}
		
		public String getSphere2Vel() {
			return this.inpSphere2Vel.getInput();
		}
	}
	
}
