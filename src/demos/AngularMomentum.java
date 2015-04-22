package demos;

import force.Force;
import graphics.Ground;
import graphics.rigidbody.RigidBodyBox;
import gui.InputField;

import java.awt.event.KeyEvent;
import java.util.Scanner;

import javax.media.j3d.Transform3D;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import rigidbody.RigidBody;
import rigidbody.run.objects.TestableSphere;
import rigidbodyphy.BoxBody;
import rigidbodyphy.BoxBodyCollider;
import rigidbodyphy.Gravity;
import rigidbodyphy.GroundContactGenerator;
import rigidbodyphy.SphereBody;
import _math.Quaternion;
import _math.Real;
import _math.Vector3D;

public class AngularMomentum extends RigidBodyDemoPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AngularMomentumControls pnlControls = new AngularMomentumControls();
	
	private BoxBody m_box;
	private RigidBodyBox m_boxGraphic;
	private Real m_boxMass = new Real( 1 );
	private Vector3D m_boxSize = new Vector3D( new Real( 1 ) , new Real( 1 ) , new Real( 1 ) );
	private Vector3D m_boxPos = new Vector3D( new Real( 0 ) , new Real( 102 ) , new Real( 0 ) );
	
	
	private SphereBody m_sphere;
	private TestableSphere m_sphereGraphic;
	private Real m_sphereMass = new Real( 1 );
	private Real m_sphereRadius = new Real( 1 );
	private Vector3D m_spherePos = new Vector3D( new Real( 1.2 ) , new Real( 100 ) , new Real( 0 ) );
	
	private BoxBodyCollider m_boxCollider = new BoxBodyCollider();
	
	private Vector3D m_g = new Vector3D( Real.ZERO , new Real( -0.98 ) , Real.ZERO );
	private Gravity m_gravity = new Gravity( new Force( this.m_g ) );

	private GroundContactGenerator m_ground = new GroundContactGenerator( new Real( 98 ) );
	
	public AngularMomentum() {
		this.m_box = new BoxBody( this.m_boxMass , this.m_boxSize.getX() , this.m_boxSize.getY() , this.m_boxSize.getZ() , this.m_boxPos );
		this.currentBody = this.m_box;
		this.m_boxGraphic = new RigidBodyBox( this.m_box , this.m_boxSize.getX().value() , this.m_boxSize.getY().value() , this.m_boxSize.getZ().value() );
		this.addObject( this.m_box , this.m_boxGraphic );

		this.m_sphere = new SphereBody( this.m_sphereMass , this.m_sphereRadius , this.m_spherePos );
		this.m_sphereGraphic = new TestableSphere( this.m_sphere , this.m_sphereRadius.value() );
		this.addObject( this.m_sphere , this.m_sphereGraphic );
		
		this.m_boxCollider.addBoxBody( this.m_box , this.m_boxSize );
		this.m_boxCollider.addSphereBody( this.m_sphere , this.m_sphereRadius );
		this.m_world.addRigidBodyCollisionGenerator( this.m_boxCollider );
		
		Ground groundGraphic = new Ground( 100 , 1 , 500 , 0 , 97 , 250 );
		this.m_ground.addBoxBody( this.m_box , this.m_boxSize );
		this.m_ground.addSphereBody( this.m_sphere , this.m_sphereRadius );

		this.addObject( groundGraphic );
		this.m_world.addRigidBodyCollisionGenerator( this.m_ground );
		
		this.m_gravity.addObject( this.m_box );
		this.m_gravity.addObject( this.m_sphere );
		this.m_world.addRigidBodyForceGenerator( this.m_gravity );
		
		reset();
	}
	
	@Override
	public void startSimulation() {
		super.startSimulation();
		this.m_box.setVelocity( Vector3D.ZERO );
		this.m_box.setAcceleration( Vector3D.ZERO );
		this.m_box.setAngularVelocity( Vector3D.ZERO );
		this.m_box.setPosition( this.m_boxPos );
		this.m_box.resetNetForce();
		this.m_box.resetNetTorque();
		
		this.m_sphere.setVelocity( Vector3D.ZERO );
		this.m_sphere.setAcceleration( Vector3D.ZERO );
		this.m_sphere.setAngularVelocity( Vector3D.ZERO );
		this.m_sphere.setPosition( this.m_spherePos );
		this.m_sphere.resetNetForce();
		this.m_sphere.resetNetTorque();
		try {
			Scanner scanGravity = new Scanner( this.pnlControls.getGravity() );
			float gravityX = scanGravity.nextFloat();
			float gravityY = scanGravity.nextFloat();
			float gravityZ = scanGravity.nextFloat();
			this.m_g = new Vector3D( new Real( gravityX ) , new Real( gravityY ) , new Real( gravityZ ) );
			this.m_gravity.setForce( new Force( this.m_g ) );
		} catch ( Exception e ) {
			JOptionPane.showMessageDialog( null , "Invalid starting conditions" );
			reset();
			return;
		}
	}
	
	@Override
	public void reset() {
		this.m_sphere.setPosition( this.m_spherePos );
		this.m_sphere.setOrientation( Quaternion.ZERO );
		this.m_box.setPosition( this.m_boxPos );
		this.m_box.setOrientation( Quaternion.ZERO );
		this.m_eyeX = this.m_spherePos.getX().value();
		this.m_eyeY = this.m_spherePos.getY().value();
		this.m_eyeZ = this.m_spherePos.getZ().value() - 35;
		Transform3D move = lookFromPointToPoint( new Point3d( this.m_eyeX , this.m_eyeY , this.m_eyeZ ) , new Point3d( this.m_eyeX , this.m_eyeY , this.m_eyeZ + 5 ) );
		updateEye( move );
		super.updateScene();
		super.reset();
	}
	
	@Override
	public ControlPanel getControlsPanel() {
		return this.pnlControls;
	}
	
	private Real forceValue = new Real( 1000 );
	private RigidBody currentBody;
	
	@Override
	public void handleKeyPressed( int keyCode ) {
		super.handleKeyPressed( keyCode );
		if ( keyCode == KeyEvent.VK_1 ) {
			this.currentBody.addForceVector( new Vector3D( this.forceValue , Real.ZERO , Real.ZERO ) );
		} else if ( keyCode == KeyEvent.VK_2 ) {
			this.currentBody.addForceVector( new Vector3D( Real.ZERO , this.forceValue , Real.ZERO ) );
		} else if ( keyCode == KeyEvent.VK_3 ) {
			this.currentBody.addForceVector( new Vector3D( Real.ZERO , Real.ZERO , this.forceValue ) );
		} else if ( keyCode == KeyEvent.VK_4 ) {
			System.out.println( "ok" );
			this.currentBody.addTorqueVector( new Vector3D( this.forceValue , Real.ZERO , Real.ZERO ) );
		} else if ( keyCode == KeyEvent.VK_5 ) {
			this.currentBody.addTorqueVector( new Vector3D( Real.ZERO , this.forceValue , Real.ZERO ) );
		} else if ( keyCode == KeyEvent.VK_6 ) {
			this.currentBody.addTorqueVector( new Vector3D( Real.ZERO , Real.ZERO , this.forceValue ) );
		} else if ( keyCode == KeyEvent.VK_SHIFT ) {
			this.forceValue = this.forceValue.multiply( Real.NEGATIVE_ONE );
			this.pnlControls.changeForceSign();
		} else if ( keyCode == KeyEvent.VK_CONTROL ) {
			if ( this.currentBody == this.m_box ) {
				this.currentBody = this.m_sphere;
			} else {
				this.currentBody = this.m_box;
			}
			this.pnlControls.changeCurrentBody();
		}
	}
	
	protected class AngularMomentumControls extends ControlPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private InputField inpGravity;
		private JLabel lblForceSign;
		private JLabel lblCurrentBody;
		
		public AngularMomentumControls() {
			super();
		}
		
		@Override
		public void setupInput() {
			super.setupInput();
			
			this.inpGravity = new InputField( "Gravity" , "0 -0.98 0" );
			super.pnlInput.add( this.inpGravity );
			
			this.lblForceSign = new JLabel( "Current force sign: + , " );
			super.pnlInput.add( this.lblForceSign );
			
			this.lblCurrentBody = new JLabel( "Current body: Box" );
			super.pnlInput.add( this.lblCurrentBody );
		}
		
		public String getGravity() {
			return this.inpGravity.getInput();
		}
		
		boolean positive = true;
		public void changeForceSign() {
			this.positive = !this.positive;
			if ( this.positive ) {
				this.lblForceSign.setText( "Curent force sign: + , " );
			} else {
				this.lblForceSign.setText( "Current force sign: - , " );
			}
		}
		
		boolean isBox = true;
		public void changeCurrentBody() {
			this.isBox = !this.isBox;
			if ( this.isBox ) {
				this.lblCurrentBody.setText( "Current body: Box" );
			} else {
				this.lblCurrentBody.setText( "Current body: Sphere" );
			}
		}
		
	}

}
