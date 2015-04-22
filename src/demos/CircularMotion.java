package demos;

import graphics.particle.ParticleSphere;
import gui.InputField;

import java.awt.event.KeyEvent;
import java.util.Scanner;

import javax.media.j3d.Transform3D;
import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import particle.Particle;
import particlephy.GForceGenerator;
import _math.Real;
import _math.Vector3D;


//v = [ 0 , 11.5229 , 0 ] allows for uniform circular motion with radius = 3
public class CircularMotion extends ParticleDemoPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Particle planet;
	private ParticleSphere planetGraphic;
	private float planetRadius = 1f;
	private Real planetMass = new Real( 5.972E12f );
	private Vector3D planetPosition = new Vector3D( Real.ZERO , new Real( 100 ) , Real.ZERO );
	private Vector3D planetVelocity = Vector3D.ZERO;
	private Vector3D planetAcceleration = Vector3D.ZERO;
	
	private Particle satellite;
	private ParticleSphere satelliteGraphic;
	private float satelliteRadius = 0.1f;
	private Real satelliteMass = new Real( 3.117E-9f );
	private Vector3D satellitePosition = new Vector3D( new Real( -3 ) , new Real( 100 ) , Real.ZERO );
	private Vector3D satelliteVelocity = new Vector3D( Real.ZERO , new Real( 12 ) , Real.ZERO );
	private Vector3D satelliteAcceleration = Vector3D.ZERO;
	
	private Real G = GForceGenerator.G;
	private GForceGenerator forceField;
	
	private CircularMotionControls pnlControls = new CircularMotionControls();

	public CircularMotion() {
		super();
		
		this.planet = new Particle( this.planetMass , this.planetPosition , this.planetVelocity , this.planetAcceleration );
		this.planetGraphic = new ParticleSphere( this.planet , this.planetRadius );
		this.addObject( this.planet , this.planetGraphic );
		
		this.satellite = new Particle( this.satelliteMass , this.satellitePosition , this.satelliteVelocity , this.satelliteAcceleration );
		this.satelliteGraphic = new ParticleSphere( this.satellite , this.satelliteRadius );
		this.addObject( this.satellite , this.satelliteGraphic );
		
		this.forceField = new GForceGenerator( this.planet , this.G );
		this.forceField.addObject( this.satellite );
		this.m_world.addForceGenerator( this.forceField );
		
		reset();
	}
	
	@Override
	public void startSimulation() {
		super.startSimulation();
		try {
			float newPlanetMass = Float.valueOf( this.pnlControls.getPlanetMass() ).floatValue();
			//newPlanetMass /= 1E12f;
			this.planetMass = new Real( newPlanetMass );
			
			float newSatelliteMass = Float.valueOf( this.pnlControls.getSatelliteMass() ).floatValue();
			//newSatelliteMass /= 1E12f;
			this.satelliteMass = new Real( newSatelliteMass );
			
			Scanner scanPos = new Scanner( this.pnlControls.getInitialPosition() );
			float posX = scanPos.nextFloat();
			float posY = scanPos.nextFloat();
			float posZ = scanPos.nextFloat();
			this.satellitePosition = new Vector3D( new Real( posX ) , new Real( posY ) , new Real( posZ ) );
			
			Scanner scanVel = new Scanner( this.pnlControls.getInitialVelocity() );
			float velX = scanVel.nextFloat();
			float velY = scanVel.nextFloat();
			float velZ = scanVel.nextFloat();
			this.satelliteVelocity = new Vector3D( new Real( velX ) , new Real( velY ) , new Real( velZ ) );
			
			float newG = Float.valueOf( this.pnlControls.getG() ).floatValue();
			this.G = new Real( newG );
			
		} catch ( Exception e ) {
			JOptionPane.showMessageDialog( null , "Invalid starting conditions" );
			reset();
			return;
		}
		
		this.planet.setMass( this.planetMass );
		
		this.satellite.setMass( this.satelliteMass );
		this.satellite.setPosition( this.satellitePosition );
		this.satellite.setVelocity( this.satelliteVelocity );
		this.satellite.setAcceleration( this.satelliteAcceleration );
		
		this.forceField.setG( this.G );
	}
	
	@Override
	public void reset() {
		this.satellite.setPosition( this.satellitePosition );
		this.m_eyeX = this.planetPosition.getX().value();
		this.m_eyeY = this.planetPosition.getY().value();
		this.m_eyeZ = -30;
		Transform3D move = lookFromPointToPoint( new Point3d( this.m_eyeX , this.m_eyeY , this.m_eyeZ ) , new Point3d( this.m_eyeX , this.m_eyeY , this.m_eyeZ + 5 ) );
		updateEye( move );
		super.updateScene();
		super.reset();
	}

	@Override
	public ControlPanel getControlsPanel() {
		return this.pnlControls;
	}
	
	private static Real ONE_POINT_ZERO_FIVE = new Real( 1.05 );
	private static Real POINT_NINE_FIVE = new Real( 0.95 );
	
	@Override
	public void handleKeyPressed( int keyCode ) {
		super.handleKeyPressed( keyCode );
		if ( keyCode == KeyEvent.VK_1 ) {
			this.satellite.setVelocity( this.satellite.getVelocity().multiply( POINT_NINE_FIVE ) );
		} else if ( keyCode == KeyEvent.VK_2 ) {
			this.satellite.setVelocity( this.satellite.getVelocity().multiply( ONE_POINT_ZERO_FIVE ) );
		}
	}
	
	protected class CircularMotionControls extends ControlPanel {
		
		private InputField inpPlanetMass;
		private InputField inpSatelliteMass;
		private InputField inpPos;
		private InputField inpVel;
		private InputField inpG;
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CircularMotionControls() {
			super();
		}
		
		@Override
		protected void setupInput() {
			super.setupInput();
			
			this.inpPlanetMass = new InputField( "Planet Mass" , "5.972E12" );
			super.pnlInput.add( this.inpPlanetMass );
			
			this.inpSatelliteMass = new InputField( "Satellite Mass" , "3.117E-9" );
			super.pnlInput.add( this.inpSatelliteMass );
			
			this.inpPos = new InputField( "Position" , "-3 100 0" );
			super.pnlInput.add( this.inpPos );
			
			this.inpVel = new InputField( "Velocity" , "0 12 0" );
			super.pnlInput.add( this.inpVel );
			
			this.inpG = new InputField( "G constant" , "6.67E-11" );
			super.pnlInput.add( this.inpG );
		}
		
		public String getPlanetMass() {
			return this.inpPlanetMass.getInput();
		}
		
		public String getSatelliteMass() {
			return this.inpSatelliteMass.getInput();
		}
		
		public String getInitialPosition() {
			return this.inpPos.getInput();
		}
		
		public String getInitialVelocity() {
			return this.inpVel.getInput();
		}
		
		public String getG() {
			return this.inpG.getInput();
		}
	}
}
