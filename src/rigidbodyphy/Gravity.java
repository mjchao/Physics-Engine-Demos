package rigidbodyphy;

import rigidbody.force.RigidBodyForceGenerator;
import force.Force;

public class Gravity extends RigidBodyForceGenerator {

	public Gravity( Force aForce ) {
		super( aForce );
	}
}
