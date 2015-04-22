package rigidbodyphy;

import _math.Real;
import _math.Vector3D;

public class ChargedBoxBody extends BoxBody {

	private Real m_charge;
	
	/**
	 * location of the charged particle on this body, relative to the center of mass
	 */
	private Vector3D m_chargeLocation;
	
	private Vector3D m_fixedVelocity;
	
	public ChargedBoxBody( Real mass , Real length , Real width , Real height , Vector3D initialPos , Real charge , Vector3D chargeLocation ) {
		super(mass, length, width, height, initialPos);
		this.m_charge = charge;
		this.m_chargeLocation = chargeLocation;
	}
	
	public void setCharge( Real newCharge ) {
		this.m_charge = newCharge;
	}
	
	public Real getCharge() {
		return this.m_charge;
	}
	
	public void fixInSpace() {
		this.m_fixedVelocity = this.getVelocity();
		this.setVelocity( Vector3D.ZERO );
	}
	
	public Vector3D getFixedVelocity() {
		return this.m_fixedVelocity;
	}
	
	public void setChargeLocation( Vector3D newChargeLocation ) {
		this.m_chargeLocation = newChargeLocation;
	}
	
	public Vector3D getChargeLocation() {
		return this.getOrientation().toOrientationAndPositionMatrix( this.getPosition() ).convertLocalToWorld( this.m_chargeLocation ).subtract( this.getPosition() );
	}

}
