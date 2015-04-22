package rigidbodyphy;

import rigidbody.Matrix3;
import rigidbody.RigidBody;
import _math.Real;
import _math.Vector3D;

public class BoxBody extends RigidBody {

	public static Matrix3 calculateInverseInertiaTensor( Real mass , Real length , Real width , Real height ) {
		Real[][] tensorData = { { Real.ONE_TWELFTH.multiply( mass ).multiply( width.squared().add( height.squared() ) ) , Real.ZERO , Real.ZERO }  ,
								{ Real.ZERO , Real.ONE_TWELFTH.multiply( mass ).multiply( length.squared().add( height.squared() ) ) , Real.ZERO } ,
								{ Real.ZERO , Real.ZERO , Real.ONE_TWELFTH.multiply( mass ).multiply( length.squared().add( width.squared() ) ) } };
		return new Matrix3( tensorData );
	}
	
	public BoxBody( Real mass , Real length , Real width , Real height , Vector3D initialPos ) {
		super( mass , calculateInverseInertiaTensor( mass , length , width , height ) , initialPos );
	}
}
