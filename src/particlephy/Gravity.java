package particlephy;

import force.Force;
import particle.force.ParticleForceGenerator;
import _math.Vector3D;

public class Gravity extends ParticleForceGenerator {

	public Gravity( Vector3D force ) {
		super( new Force( force ) );
	}
}
