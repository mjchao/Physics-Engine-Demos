package rigidbodyphy;

import rigidbody.RigidBody;
import rigidbody.collision.broad.PotentialContactGenerator;
import rigidbody.collision.generate.BoxAndPlaneCollisionGenerator;
import rigidbody.collision.generate.Contact;
import rigidbody.collision.generate.ContactGenerator;
import rigidbody.collision.generate.Primitive;
import rigidbody.collision.generate.PrimitiveBox;
import rigidbody.collision.generate.PrimitivePlane;
import rigidbody.collision.generate.PrimitiveSphere;
import rigidbody.collision.generate.SphereAndPlaneCollisionGenerator;
import _lib.LinkedList;
import _math.Real;
import _math.Vector3D;

public class GroundContactGenerator extends PotentialContactGenerator {

	final public static Real FRICTION = Real.ZERO;
	final public static Real ELASTICITY = Real.ONE;
	final public static Real PENETRATION_OFFSET = ContactGenerator.DEFAULT_PENETRATION_OFFSET;
	private BoxAndPlaneCollisionGenerator m_boxCollisionGenerator = new BoxAndPlaneCollisionGenerator( FRICTION , ELASTICITY , PENETRATION_OFFSET );
	private SphereAndPlaneCollisionGenerator m_sphereCollisionGenerator = new SphereAndPlaneCollisionGenerator( FRICTION , ELASTICITY , PENETRATION_OFFSET );
	
	private LinkedList < Primitive > m_bodiesInSystem = new LinkedList < Primitive > ();
	
	private PrimitivePlane m_plane;
	
	public GroundContactGenerator( Real height ) {
		this.m_plane = new PrimitivePlane( Vector3D.WORLD_Y_AXIS , height );
	}
	
	public void addBoxBody( RigidBody body , Vector3D halfSize ) {
		PrimitiveBox boxToAdd = new PrimitiveBox( body , body.getOrientation().toOrientationAndPositionMatrix( body.getPosition() ) , halfSize );
		this.m_bodiesInSystem.add( boxToAdd );
	}
	
	public void addSphereBody( RigidBody body , Real radius ) {
		PrimitiveSphere sphereToAdd = new PrimitiveSphere( body , body.getOrientation().toOrientationAndPositionMatrix( body.getPosition() ) , radius );
		this.m_bodiesInSystem.add( sphereToAdd );
	}
	
	@Override
	public void generatePotentialContacts() {
		for ( Primitive p : this.m_bodiesInSystem ) {
			LinkedList < Contact > contactsGenerated = null;
			if ( p instanceof PrimitiveBox ) {
				contactsGenerated = this.m_boxCollisionGenerator.generateContacts( p , this.m_plane );
			} else if ( p instanceof PrimitiveSphere ) {
				contactsGenerated = this.m_sphereCollisionGenerator.generateContacts( p , this.m_plane );
			}
			if ( contactsGenerated != null ) {
				addContactsToResolver( contactsGenerated );
			}
		}
	}
	
	protected void addContactsToResolver( LinkedList < Contact > contacts ) {
		for ( Contact contact : contacts ) {
			this.addContact( contact );
		}
	}

	@Override
	public void resolve( Real duration ) {
		super.resolve( duration );
	}
}
