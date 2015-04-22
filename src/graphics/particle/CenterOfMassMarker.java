package graphics.particle;

import javax.media.j3d.Appearance;

import _lib.LinkedList;
import _math.Real;
import _math.Vector3D;
import force.MassedObject;
import graphics.Colors;

/**
 * marks the center of mass of a system of <code>Particle</code>s
 * 
 */
public class CenterOfMassMarker extends LocationMarker {
	
	/**
	 * the objects for which we want to mark the center of mass
	 */
	private LinkedList < MassedObject > m_objects = new LinkedList < MassedObject > ();
	
	public CenterOfMassMarker() {
		super();
		Appearance ap = new Appearance();
		ap.setColoringAttributes( Colors.WHITE );
		this.setAppearance( ap );
		setup();
	}
	
	public void addMassedObject( MassedObject object ) {
		this.m_objects.add( object );
	}
	
	@Override
	protected void updatePosition() {
		Real totalMass = Real.ZERO;
		for ( MassedObject object : this.m_objects ) {
			totalMass = totalMass.add( object.getMass() );
		}
		
		Real centerOfMassX = Real.ZERO;
		Real centerOfMassY = Real.ZERO;
		Real centerOfMassZ = Real.ZERO;
		for ( MassedObject object : this.m_objects ) {
			Real weightedProportion = object.getMass().divide( totalMass );
			Vector3D bodyPosition = object.getPosition();
			centerOfMassX = centerOfMassX.add( bodyPosition.getX().multiply( weightedProportion ) );
			centerOfMassY = centerOfMassY.add( bodyPosition.getY().multiply( weightedProportion ) );
			centerOfMassZ = centerOfMassZ.add( bodyPosition.getZ().multiply( weightedProportion ) );
		}
		setX( centerOfMassX );
		setY( centerOfMassY );
		setZ( centerOfMassZ );
		super.updatePosition();
	}
}
