package rigidbodyphy;

import rigidbody.force.RigidBodyForceGenerator;
import _lib.LinkedList;
import _math.Real;
import _math.Vector3D;

public class MagneticForceGenerator extends RigidBodyForceGenerator {

	private LinkedList < ChargedBoxBody > m_bodies = new LinkedList < ChargedBoxBody > ();
	
	private Vector3D m_fieldDirection = Vector3D.WORLD_Z_AXIS;
	private Real m_fieldStrength = Real.ZERO;
	
	private Vector3D m_field = this.m_fieldDirection.multiply( this.m_fieldStrength );
	
	private boolean m_treatAsParticle = false;
	
	private boolean m_fixObject = false;
	
	public MagneticForceGenerator() {
		
	}
	
	public void addBody( ChargedBoxBody body ) {
		this.m_bodies.add( body );
	}
	
	public void setMagneticField( Vector3D direction , Real strength ) {
		this.m_field = direction.multiply( strength );
	}
	
	public void setTreatAsParticle( boolean b ) {
		this.m_treatAsParticle = b;
	}
	
	public void setFixObject( boolean b ) {
		this.m_fixObject = b;
	}
	
	@Override
	public void generateForce() {
		for ( ChargedBoxBody body : this.m_bodies ) {
			Real q = body.getCharge();
			
			//calculate the velocity of the charged particle on the body
			
			//linear velocity is the body's velocity
			Vector3D bodyVelocity;
			if ( this.m_fixObject == true ) {
				bodyVelocity = body.getFixedVelocity();
			} else {
				bodyVelocity = body.getVelocity();
			}
			
			//calculate the linear velocity from rotation v = r * omega
			Vector3D r = body.getChargeLocation();
			Vector3D omega = body.getAngularVelocity();
			
			//add linear velocities together
			Vector3D v;
			if ( this.m_fixObject == true ) {
				v = bodyVelocity;
			} else {
				v = bodyVelocity.add( r.cross( omega ) );
			}
			
			Vector3D B = this.m_field;
			
			//apply F_m = q v cross B
			Vector3D magneticForce = q.multiply( v.cross( B ) );
			if ( this.m_treatAsParticle == true ) {
				if ( this.m_fixObject == false ) {
					body.addForceVector( magneticForce );
				}
			} else {
				if ( this.m_fixObject == true ) {
					Vector3D torque = body.getChargeLocation().cross( magneticForce ).invert();
					body.addTorqueVector( torque );
				} else {
					body.addForceAtPoint( magneticForce , body.getChargeLocation() );
				}
			}
		}
	}
}
