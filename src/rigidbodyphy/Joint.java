package rigidbodyphy;

import rigidbody.RigidBody;
import rigidbody.collision.broad.PotentialContactGenerator;
import rigidbody.collision.generate.Contact;
import rigidbody.collision.generate.ContactGenerator;
import _math.Real;
import _math.Vector3D;

public class Joint extends PotentialContactGenerator {

	private RigidBody m_reference;
	
	private Vector3D m_referenceConnectionLocal;
	
	private RigidBody m_other;
	
	private Vector3D m_otherConnectionLocal;
	
	private Real m_maxDisplacement;
	
	private Real m_friction = Real.ZERO;
	
	private Real m_elasticity = Real.ZERO;
	
	public Joint( RigidBody reference , Vector3D referenceConnectionPoint , RigidBody other , Vector3D otherConnectionPoint , Real maxDisplacement ) {
		this.m_reference = reference;
		this.m_referenceConnectionLocal = referenceConnectionPoint;
		this.m_other = other;
		this.m_otherConnectionLocal = otherConnectionPoint;
		this.m_maxDisplacement = maxDisplacement;
	}
	
	@Override
	public void generatePotentialContacts() {
		Vector3D referenceConnectionPoint = this.m_reference.getPosition().add( this.m_referenceConnectionLocal );
		Vector3D otherConnectionPoint = this.m_other.getPosition().add( this.m_otherConnectionLocal );
		
		//check if the connection points have gotten more apart than the
		//maximum allowable displacement
		Real distanceBetweenConnectionPoints = referenceConnectionPoint.subtract( otherConnectionPoint ).magnitude();
		if ( distanceBetweenConnectionPoints.compareTo( this.m_maxDisplacement ) > 0 ) {
			Vector3D contactNormal = otherConnectionPoint.subtract( referenceConnectionPoint ).normalize();
			Vector3D contactPoint = referenceConnectionPoint.add( otherConnectionPoint ).divide( Real.TWO );
			Real penetration = distanceBetweenConnectionPoints.subtract( this.m_maxDisplacement );
			Contact contact = new Contact( this.m_reference , this.m_other , contactPoint , contactNormal , penetration , ContactGenerator.DEFAULT_PENETRATION_OFFSET , this.m_friction , this.m_elasticity );
			this.addContact( contact );
		}
		
	}
	
	@Override
	public void resolve( Real duration ) {
		super.resolve( duration );
		this.m_reference.setAngularVelocity( Vector3D.ZERO );
		this.m_other.setAngularVelocity( Vector3D.ZERO );
	}

}
