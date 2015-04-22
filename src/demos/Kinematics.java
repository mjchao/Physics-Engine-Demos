package demos;

import java.awt.event.KeyEvent;
import java.util.Scanner;

import javax.media.j3d.Transform3D;
import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import particle.Particle;
import particle.force.ParticleForceGenerator;
import particlephy.Gravity;
import particlephy.GroundContactGenerator;
import _math.Real;
import _math.Vector3D;
import force.Force;
import graphics.Ground;
import graphics.particle.ParticleSphere;
import gui.InputField;

public class Kinematics extends ParticleDemoPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private KinematicsControls pnlControls = new KinematicsControls();
	
	private Particle testParticle;
	private Vector3D initialPosition = new Vector3D( Real.ZERO , new Real( 97 ) , Real.ZERO );
	private Vector3D initialVelocity = new Vector3D( Real.ZERO , new Real( 3 ) , Real.ZERO );
	private Vector3D initialAcceleration = Vector3D.ZERO;
	private Vector3D gravity = new Vector3D( Real.ZERO , new Real( -9.8 ) , Real.ZERO );
	private ParticleForceGenerator gravityGenerator = new Gravity( this.gravity );
	private GroundContactGenerator ground = new GroundContactGenerator( new Real( 95 ) );
	
	public Kinematics() {
		super();
		
		this.testParticle = new Particle( Real.FIVE , new Vector3D( Real.ZERO , new Real( 97 ) , Real.ZERO ) , new Vector3D( new Real( 0 ) , new Real( 3 ) , new Real( 0 ) ) );
		ParticleSphere particleGraphic = new ParticleSphere( this.testParticle );
		this.addObject( this.testParticle , particleGraphic );
		
		this.addObject( new Ground( 5 , 0.1f , 500 , 0 , 94.6f , 500 ) );
		
		this.gravityGenerator.addObject( this.testParticle );
		this.m_world.addForceGenerator( this.gravityGenerator );
		
		this.ground.addParticle( this.testParticle );
		this.m_world.addContactGenerator( this.ground );
			
		reset();

	}
	
	@Override
	public void reset() {
		this.testParticle.setPosition( this.initialPosition );
		this.m_eyeX = this.initialPosition.getX().value();
		this.m_eyeY = this.initialPosition.getY().value();
		this.m_eyeZ = this.initialPosition.getZ().value() - 25;
		Transform3D move = lookFromPointToPoint( new Point3d( this.m_eyeX , this.m_eyeY , this.m_eyeZ ) , new Point3d( this.m_eyeX , this.m_eyeY , this.m_eyeZ + 5 ) );
		updateEye( move );
		super.updateScene();
		super.reset();
	}
	
	@Override
	public void startSimulation() {
		super.startSimulation();
		try {
			Scanner positionScanner = new Scanner( this.pnlControls.getInitialPosition() );
			double posX = positionScanner.nextDouble();
			double posY = positionScanner.nextDouble();
			double posZ = positionScanner.nextDouble();
			this.initialPosition = new Vector3D( new Real( posX ) , new Real( posY ) , new Real( posZ ) );
			
			Scanner velocityScanner = new Scanner( this.pnlControls.getInitialVelocity() );
			double velX = velocityScanner.nextDouble();
			double velY = velocityScanner.nextDouble();
			double velZ = velocityScanner.nextDouble();
			this.initialVelocity = new Vector3D( new Real( velX ) , new Real( velY ) , new Real( velZ ) );
			
			Scanner gravityScanner = new Scanner( this.pnlControls.getInitialGravity() );
			double gX = gravityScanner.nextDouble();
			double gY = gravityScanner.nextDouble();
			double gZ = gravityScanner.nextDouble();
			this.gravity = new Vector3D( new Real( gX ) , new Real( gY ) , new Real( gZ ) );
		} catch ( Exception e ) {
			JOptionPane.showMessageDialog( null , "Invalid starting conditions" );
			reset();
			return;
		}
		this.testParticle.setPosition( this.initialPosition );
		System.out.println( this.testParticle.getPosition() );
		this.testParticle.setVelocity( this.initialVelocity );
		this.testParticle.setAcceleration( this.initialAcceleration );
		this.gravityGenerator.setForce( new Force( this.gravity ) );
	}

	@Override
	public ControlPanel getControlsPanel() {
		return this.pnlControls;
	}
	
	private Real upwardForce = new Real( 10000 );
	@Override
	public void handleKeyPressed( int keyCode ) {
		super.handleKeyPressed( keyCode );
		if ( keyCode == KeyEvent.VK_U ) {
			System.out.println("ok");
			this.testParticle.addForceVector( new Vector3D( Real.ZERO , this.upwardForce , Real.ZERO ) );
		}
	}

	protected class KinematicsControls extends ControlPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private InputField inpInitialPosition;
		private InputField inpInitialVelocity;
		private InputField inpGravityForce;
		
		public KinematicsControls() {
			super();
		}
		
		@Override
		protected void setupInput() {
			super.setupInput();
			this.inpInitialPosition = new InputField( "Position" , "0 97 0" );
			super.pnlInput.add( this.inpInitialPosition );
			
			this.inpInitialVelocity = new InputField( "Velocity" , "0 5 0" );
			super.pnlInput.add( this.inpInitialVelocity );
			
			this.inpGravityForce = new InputField( "Gravity" , "0 -9.8 0" );
			super.pnlInput.add( this.inpGravityForce );
		}
		
		public String getInitialPosition() {
			return this.inpInitialPosition.getInput();
		}
		
		public String getInitialVelocity() {
			return this.inpInitialVelocity.getInput();
		}
		
		public String getInitialGravity() {
			return this.inpGravityForce.getInput();
		}
	}
}
