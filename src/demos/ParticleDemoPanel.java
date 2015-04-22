package demos;

import particle.Particle;
import particle.run.ParticleWorld;
import rigidbody.run.objects.Testable;
import _math.Real;
import force.MassedObject;

abstract public class ParticleDemoPanel extends DemoPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * physics world for particles
	 */
	protected ParticleWorld m_world = new ParticleWorld();
	
	private Thread physicsThread = new Thread() {
		@Override
		public void run() {
			while ( this.isInterrupted() == false ) {
				if ( ParticleDemoPanel.this.started ) {
					try {
						ParticleDemoPanel.this.m_world.runPhysics( new Real( 0.001 ) );
						ParticleDemoPanel.this.updateScene();
						ParticleDemoPanel.this.repaint();
						Thread.sleep( 1 );
					} catch ( InterruptedException e ) {
						break;
					}
				} else {
					try {
						Thread.sleep( 1000 );
					} catch ( InterruptedException e ) {
						break;
					}
				}
			}	
		}
	};
	
	@Override
	public void run() {
		this.physicsThread.start();
	}
	
	/**
	 * adds the given particle and graphical representation as a <code>Testable</code>
	 * object to the world
	 * 
	 * @param body			the particle to which physics is applied
	 * @param object		the testable, graphical object representing the particle
	 */
	@Override
	public void addObject( MassedObject body , Testable object ) {
		this.m_world.addParticle( ( Particle ) body );
		addObject( object );
	}
}
