package demos;

import force.MassedObject;
import rigidbody.RigidBody;
import rigidbody.run.RigidBodyWorld;
import rigidbody.run.objects.Testable;
import _math.Real;

abstract public class RigidBodyDemoPanel extends DemoPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * physics world for rigidbodies
	 */
	protected RigidBodyWorld m_world = new RigidBodyWorld();

	private Thread physicsThread = new Thread() {
		@Override
		public void run() {
			while ( Thread.currentThread().isInterrupted() == false ) {
				if ( RigidBodyDemoPanel.this.started ) {
					try {
						performAdditionalOperations();
						RigidBodyDemoPanel.this.m_world.runPhysics( new Real( 0.001 ) );
						RigidBodyDemoPanel.this.updateScene();
						RigidBodyDemoPanel.this.repaint();
						Thread.sleep( 1 );
					} catch ( InterruptedException e ) {
						break;
					}
				} else {
					try {
						Thread.sleep( 1000 );
					} catch ( InterruptedException e ) {
						//ignore
					}
				}
			}
		}
	};
	
	protected void performAdditionalOperations() {
		//do nothing
	}
	
	@Override
	public void run() {
		this.physicsThread.start();
	}
	
	/**
	 * adds the given rigidbody and graphical representation as a <code>Testable</code>
	 * object to the world
	 * 
	 * @param body			the rigidbody to which physics is applied
	 * @param object		the testable, graphical object representing the rigidbody
	 */
	@Override
	public void addObject( MassedObject body , Testable object ) {
		this.m_world.addRigidBody( ( RigidBody ) body );
		addObject( object );
	}
}
