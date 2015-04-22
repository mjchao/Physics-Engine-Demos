package rigidbodyphy;

import rigidbody.Matrix3;
import rigidbody.RigidBody;
import _math.Real;
import _math.Vector3D;

public class SphereBody extends RigidBody {

	public static Matrix3 calculateInverseInertiaTensor( Real mass , Real radius ) {
		Real c = Real.TWO.divide( Real.FIVE ).multiply( mass ).multiply( radius.squared() );
		Real[][] tensorData = { { c , Real.ZERO , Real.ZERO } ,
						  	    { Real.ZERO , c , Real.ZERO } ,
						  	    { Real.ZERO , Real.ZERO , c } };
		return new Matrix3( tensorData );
	}
	
	public SphereBody( Real mass , Real radius , Vector3D initialPos ) {
		super( mass , calculateInverseInertiaTensor( mass , radius ) , initialPos );
	}
}
