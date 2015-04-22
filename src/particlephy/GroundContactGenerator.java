package particlephy;

import particle.Particle;
import particle.collision.ParticleContact;
import particle.collision.ParticleContactGenerator;
import _lib.LinkedList;
import _math.Real;
import _math.Vector3D;

public class GroundContactGenerator extends ParticleContactGenerator {

	private Real m_height;
	
	private LinkedList < Particle > m_particlesInSystem = new LinkedList < Particle > ();
	
	public GroundContactGenerator( Real height) {
		this.m_height = height;
	}
	
	public void addParticle( Particle toAdd ) {
		this.m_particlesInSystem.add( toAdd );
	}

	@Override
	public LinkedList < ParticleContact > generateContact() {
		LinkedList < ParticleContact > rtn = new LinkedList < ParticleContact > ();
		for ( Particle particle : this.m_particlesInSystem ) {
			if ( particle.getPosition().getY().compareTo( this.m_height ) < 0 ) {
				ParticleContact contact = new ParticleContact( particle , null , Real.ONE_HALF , Vector3D.WORLD_Y_AXIS , this.m_height.subtract( particle.getPosition().getY() ) );
				rtn.add( contact );
			}
		}
		return rtn;
	}
	

}
