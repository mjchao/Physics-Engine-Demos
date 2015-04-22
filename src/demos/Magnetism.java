package demos;

import graphics.rigidbody.RigidBodyBox;
import gui.CheckBoxField;
import gui.ComboBoxField;
import gui.InputField;
import gui.SliderField;

import java.awt.event.KeyEvent;
import java.util.Scanner;

import javax.media.j3d.Transform3D;
import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

import rigidbodyphy.ChargedBoxBody;
import rigidbodyphy.MagneticForceGenerator;
import _math.Quaternion;
import _math.Real;
import _math.Vector3D;

public class Magnetism extends RigidBodyDemoPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MagnetismControlsPanel pnlControls = new MagnetismControlsPanel();
	
	private ChargedBoxBody m_box;
	private RigidBodyBox m_boxGraphic;
	private Real m_boxMass = new Real( 5 );
	private Real m_boxCharge = new Real( 1 );
	private Vector3D m_chargeLocation = new Vector3D( new Real( 0 ) , new Real( 0 ) , new Real( 0.25f ) );
	private Vector3D m_boxSize = new Vector3D( new Real( 1 ) , new Real( 0.25 ) , new Real( 0.5 ) );
	private Vector3D m_boxPos = new Vector3D( Real.ZERO , new Real( 100 ) , Real.ZERO );
	private Vector3D m_boxVel = new Vector3D( new Real( 5 ) , new Real( 0 ) , new Real( 0 ) );
	private Vector3D m_boxAccel = Vector3D.ZERO;
	
	private MagneticForceGenerator m_magneticForce = new MagneticForceGenerator();
	
	//private ChargeMarker m_chargeMarker;
	
	public Magnetism() {
		super();
		this.m_box = new ChargedBoxBody( this.m_boxMass , this.m_boxSize.getX() , this.m_boxSize.getY() , this.m_boxSize.getZ() , this.m_boxPos , this.m_boxCharge , this.m_chargeLocation );
		this.m_box.setVelocity( this.m_boxVel );
		this.m_boxGraphic = new RigidBodyBox( this.m_box , this.m_boxSize.getX().value() , this.m_boxSize.getY().value() , this.m_boxSize.getZ().value() );
		this.addObject( this.m_box , this.m_boxGraphic );
		
		//this.m_chargeMarker = new ChargeMarker( this.m_box );
		//this.addObject( this.m_chargeMarker );
		
		this.m_magneticForce.addBody( this.m_box );
		this.m_world.addRigidBodyForceGenerator( this.m_magneticForce );
		
		reset();
	}
	
	@Override
	public void startSimulation() {
		super.startSimulation();
		try {
			float mass = Float.valueOf( this.pnlControls.getMass() ).floatValue();
			this.m_boxMass = new Real( mass );
			
			float charge = Float.valueOf( this.pnlControls.getCharge() ).floatValue();
			this.m_boxCharge = new Real( charge );
			
			Scanner scanPos = new Scanner( this.pnlControls.getPosition() );
			this.m_boxPos = new Vector3D( new Real( scanPos.nextFloat() ) , new Real( scanPos.nextFloat() ) , new Real( scanPos.nextFloat() ) );
			
			Scanner scanVel = new Scanner( this.pnlControls.getVelocity() );
			this.m_boxVel = new Vector3D( new Real( scanVel.nextFloat() ) , new Real( scanVel.nextFloat() ) , new Real( scanVel.nextFloat() ) );
			
			this.m_magneticForce.setTreatAsParticle( this.pnlControls.getTreatAsParticle() );
			this.m_magneticForce.setFixObject( this.pnlControls.getFixObject() );
			
			int fieldDirection = this.pnlControls.getFieldDirection();
			switch ( fieldDirection ) {
				case 1:
					this.magneticFieldDirection = Vector3D.WORLD_X_AXIS;
					break;
				case 2:
					this.magneticFieldDirection = Vector3D.WORLD_Y_AXIS;
					break;
				case 3:
					this.magneticFieldDirection = Vector3D.WORLD_Z_AXIS;
					break;
				default:
					break;
			}
		} catch ( Exception e ) {
			JOptionPane.showMessageDialog( null , "Invalid starting conditions" );
			reset();
			return;
		}
		
		this.m_box.setMass( this.m_boxMass );
		this.m_box.setCharge( this.m_boxCharge );
		this.m_box.setPosition( this.m_boxPos );
		this.m_box.setVelocity( this.m_boxVel );
		
		this.m_magneticForce.setMagneticField( this.magneticFieldDirection , new Real( this.pnlControls.getFieldStrength() ) );
		if ( this.pnlControls.getFixObject() == true ) {
			this.m_box.fixInSpace();
		}
		this.m_magneticForce.setFixObject( this.pnlControls.getFixObject() );
		
		this.m_box.setAcceleration( this.m_boxAccel );
		this.m_box.setAngularVelocity( Vector3D.ZERO );
		this.m_box.setOrientation( Quaternion.ZERO );
	}
	
	
	@Override
	public void reset() {
		this.m_box.setPosition( this.m_boxPos );
		this.m_box.setOrientation( Quaternion.ZERO );
		this.m_eyeX = this.m_boxPos.getX().value();
		this.m_eyeY = this.m_boxPos.getY().value();
		this.m_eyeZ = this.m_boxPos.getZ().value() - 25;
		Transform3D move = lookFromPointToPoint( new Point3d( this.m_eyeX , this.m_eyeY , this.m_eyeZ ) , new Point3d( this.m_eyeX , this.m_eyeY , this.m_eyeZ + 5 ) );
		updateEye( move );
		super.updateScene();
		super.reset();
	}
	
	private Vector3D magneticFieldDirection = Vector3D.WORLD_Z_AXIS;
	
	@Override
	protected void performAdditionalOperations() {
		this.m_magneticForce.setMagneticField( this.magneticFieldDirection , new Real( this.pnlControls.getFieldStrength() ) );
	}
	
	@Override
	public void handleKeyPressed( int keyCode ) {
		super.handleKeyPressed( keyCode );
		if ( keyCode == KeyEvent.VK_1 ) {
			Vector3D torqueToApply = Vector3D.ZERO;
			if ( this.magneticFieldDirection == Vector3D.WORLD_X_AXIS ) {
				torqueToApply = Vector3D.WORLD_Z_AXIS;
			} else if ( this.magneticFieldDirection == Vector3D.WORLD_Y_AXIS ) {
				torqueToApply = Vector3D.WORLD_X_AXIS;
			} else if ( this.magneticFieldDirection == Vector3D.WORLD_Z_AXIS ) {
				torqueToApply = Vector3D.WORLD_X_AXIS;
			}
			torqueToApply = torqueToApply.multiply( new Real( 1000 ) );
			this.m_box.addTorqueVector( torqueToApply );
		}
	}
	
	@Override
	public ControlPanel getControlsPanel() {
		return this.pnlControls;
	}

	public static int[] FIELD_DIRECTION_IDS = { 1 , 2 , 3 };
	
	protected class MagnetismControlsPanel extends ControlPanel {
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private InputField inpMass;
		private InputField inpCharge;
		private InputField inpPos;
		private InputField inpVel;
		private CheckBoxField chkTreatAsParticle;
		private CheckBoxField chkFixObject;
		private SliderField sldrFieldStrength;
		private ComboBoxField cboFieldDirection;
		
		private String[] fieldDirections;
		
		public MagnetismControlsPanel() {
			super();
		}
		
		@Override
		public void setupControls() {
			super.setupControls();
			
			this.inpMass = new InputField( "Mass" , "5" , 2 );
			super.pnlInput.add( this.inpMass );
			
			this.inpCharge = new InputField( "Charge" , "1" );
			super.pnlInput.add( this.inpCharge );
			
			this.inpPos = new InputField( "Position" , "0 100 0" );
			super.pnlInput.add( this.inpPos );
			
			this.inpVel = new InputField( "Velocity" , "0 0 5" );
			super.pnlInput.add( this.inpVel );
			
			this.chkTreatAsParticle = new CheckBoxField( "Treat as particle" );
			super.pnlInput.add( this.chkTreatAsParticle );
			
			this.chkFixObject = new CheckBoxField( "Fix object\n(use current instead)" );
			super.pnlInput.add( this.chkFixObject );
			
			String[] directions = { "Left/Right" , "Up/Down" , "Out/In" };
			this.fieldDirections = directions;
			this.cboFieldDirection = new ComboBoxField( "Field Direction" , this.fieldDirections );
			super.pnlInput.add( this.cboFieldDirection );
			
			this.sldrFieldStrength = new SliderField( "Field Strength" , -10 , 10 , 10 , 1 , 10 );
			super.pnlInput.add( this.sldrFieldStrength );
		}
		
		public String getMass() {
			return this.inpMass.getInput();
		}
		
		public String getCharge() {
			return this.inpCharge.getInput();
		}
		
		public String getPosition() {
			return this.inpPos.getInput();
		}
		
		public String getVelocity() {
			return this.inpVel.getInput();
		}
		
		public boolean getTreatAsParticle() {
			return this.chkTreatAsParticle.isChecked();
		}
		
		public boolean getFixObject() {
			return this.chkFixObject.isChecked();
		}
		
		public int getFieldStrength() {
			return this.sldrFieldStrength.getValue();
		}
		
		public int getFieldDirection() {
			String currentDirection = this.cboFieldDirection.getSelectedItem();
			for ( int i = 0 ; i < this.fieldDirections.length ; i ++ ) {
				if ( currentDirection.equals( this.fieldDirections [ i ] ) ) {
					return FIELD_DIRECTION_IDS[ i ];
				}
			}
			return -1;
		}
	}

}
