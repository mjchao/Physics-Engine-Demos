package particlephy;

import particle.Particle;
import particle.force.ParticleForceGenerator;
import _math.Real;
import _math.Vector3D;

public class GForceGenerator extends ParticleForceGenerator {

	public static Real G = new Real( 6.67E-11f );
	
	private Particle m_reference;
	
	private Real m_G;
	
	public GForceGenerator( Particle reference , Real g ) {
		this.m_reference = reference;
		this.m_G = g;
	}
	
	public void setG( Real g ) {
		this.m_G = g;
	}
	
	@Override
	public void generateForce() {
		for ( Particle particle : this.m_objects ) {
			Vector3D forceToApply = calculateGravitationalForce( particle );
			particle.addForceVector( forceToApply );
		}
	}
	
	protected Vector3D calculateGravitationalForce( Particle particle ) {
		Vector3D distance = this.m_reference.getPosition().subtract( particle.getPosition() );
		Real distanceSquared = distance.magnitudeSquared();
		Vector3D direction = distance.normalize();
		Real forceMagnitude = this.m_G.multiply( this.m_reference.getMass() ).multiply( particle.getMass() ).divide( distanceSquared );
		Vector3D force = forceMagnitude.multiply( direction );
		return force;
	}
}
