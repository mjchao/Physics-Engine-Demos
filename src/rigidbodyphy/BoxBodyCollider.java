package rigidbodyphy;

import rigidbody.RigidBody;
import rigidbody.collision.broad.PotentialContactGenerator;
import rigidbody.collision.generate.BoxAndSphereCollisionGenerator;
import rigidbody.collision.generate.Contact;
import rigidbody.collision.generate.ContactGenerator;
import rigidbody.collision.generate.PrimitiveBox;
import rigidbody.collision.generate.PrimitiveSphere;
import _lib.LinkedList;
import _math.Real;
import _math.Vector3D;

public class BoxBodyCollider extends PotentialContactGenerator {

	final public static Real FRICTION = Real.ZERO;
	final public static Real ELASTICITY = Real.ONE;
	final public static Real PENETRATION_OFFSET = ContactGenerator.DEFAULT_PENETRATION_OFFSET;
	
	//private BoxAndBoxCollisionGenerator m_boxBoxCollider = new BoxAndBoxCollisionGenerator( FRICTION , ELASTICITY , PENETRATION_OFFSET );
	//private SphereAndSphereCollisionGenerator m_sphereSpherecollider = new SphereAndSphereCollisionGenerator( FRICTION , ELASTICITY , PENETRATION_OFFSET ); 
	private BoxAndSphereCollisionGenerator m_boxSphereCollider = new BoxAndSphereCollisionGenerator( FRICTION , ELASTICITY , PENETRATION_OFFSET );
	
	private LinkedList < PrimitiveBox > m_boxBodies = new LinkedList < PrimitiveBox > ();
	private LinkedList < PrimitiveSphere > m_sphereBodies = new LinkedList < PrimitiveSphere > ();
	
	public BoxBodyCollider() {
		super();
	}
	
	public void addBoxBody( RigidBody body , Vector3D halfSize ) {
		PrimitiveBox boxToAdd = new PrimitiveBox( body , body.getOrientation().toOrientationAndPositionMatrix( body.getPosition() ) , halfSize );
		this.m_boxBodies.add( boxToAdd );
	}
	
	public void addSphereBody( RigidBody body , Real radius ) {
		PrimitiveSphere sphereToAdd = new PrimitiveSphere( body , body.getOrientation().toOrientationAndPositionMatrix( body.getPosition() ) , radius );
		this.m_sphereBodies.add( sphereToAdd );
	}
	
	@Override
	public void generatePotentialContacts() {
		for ( PrimitiveBox box : this.m_boxBodies ) {
			for ( PrimitiveSphere sphere : this.m_sphereBodies ) {
				addContact( this.m_boxSphereCollider.generateContacts( box , sphere ) );
			}
		}
	}
	
	protected void addContact( LinkedList < Contact > contacts ) {
		for ( Contact contact : contacts ) {
			this.addContact( contact );
		}
	}
}
