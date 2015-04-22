package graphics.particle;

import particle.Particle;
import particle.force.spring.ParticleUnanchoredSpring;
import rigidbody.run.objects.TestableSpring;

public class UnanchoredSpringGraphic extends TestableSpring {

	/**
	 * the spring that this graphic represents
	 */
	private ParticleUnanchoredSpring m_spring;
	
	/**
	 * the object connected to this spring
	 */
	private Particle m_object;
	
	public UnanchoredSpringGraphic( ParticleUnanchoredSpring spring , Particle object ) {
		super( spring.getReferencePosition() , object.getPosition() );
		this.m_spring = spring;
		this.m_object = object;
		super.setup();
	}

	@Override
	protected void updateEndpoints() {
		this.setEndpoint1( this.m_spring.getReferencePosition() );
		this.setEndpoint2( this.m_object.getPosition() );
		super.updateEndpoints();
	}

}
