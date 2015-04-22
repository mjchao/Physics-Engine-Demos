package graphics.particle;

import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import rigidbody.run.objects.Testable;
import _math.Real;

import com.sun.j3d.utils.geometry.Sphere;

abstract public class LocationMarker extends Sphere implements Testable {
	/**
	 * the transformation on this <code>TestableColorCube</code> from the original
	 * [ 0 , 0 , 0 ]
	 */
	protected Transform3D m_transform = new Transform3D();
	
	/**
	 * a completely set-up group that provides what's needed to draw this
	 * <code>TestableColorCube</code> in Java 3D
	 */
	protected TransformGroup m_transformGroup = new TransformGroup();

	private Real m_x = Real.ZERO;
	
	private Real m_y = Real.ZERO;
	
	private Real m_z = Real.ZERO;
	
	public LocationMarker( float radius ) {
		super( radius );
	}
	
	public LocationMarker() {
		super( 0.3f );
	}
	
	@Override
	public void setup() {
		
		//allow us to write to the transform group
		this.m_transformGroup.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
		
		//update the position and orientation of the sphere
		updatePosition();
		
		this.m_transformGroup.setTransform( this.m_transform );
		this.m_transformGroup.addChild( this );
	}
	
	protected void updatePosition() {
		Vector3f graphicPosition = new Vector3f( this.m_x.value() , this.m_y.value() , this.m_z.value() );
		this.m_transform.setTranslation( graphicPosition );
		this.m_transformGroup.setTransform( this.m_transform );
	}
	
	public void setX( Real x ) {
		this.m_x = x;
	}
	
	public Real getX() {
		return this.m_x;
	}
	
	public void setY( Real y ) {
		this.m_y = y;
	}
	
	public Real getY() {
		return this.m_y;
	}
	
	public void setZ( Real z ) {
		this.m_z = z;
	}
	
	public Real getZ() {
		return this.m_z;
	}

	@Override
	public void draw() {
		updatePosition();
	}

	@Override
	public Group getGroup() {
		return this.m_transformGroup;
	}

}
