package graphics;

import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import rigidbody.run.objects.Testable;

import com.sun.j3d.utils.geometry.Box;

public class Ground extends Box implements Testable {

	/**
	 * the transformation on this <code>TestableColorCube</code> from the original
	 * [ 0 , 0 , 0 ]
	 */
	private Transform3D m_transform = new Transform3D();
	
	/**
	 * a completely set-up group that provides what's needed to draw this
	 * <code>TestableColorCube</code> in Java 3D
	 */
	private TransformGroup m_transformGroup = new TransformGroup();
	
	private float m_x;
	/**
	 * y value of the ground
	 */
	private float m_y;
	
	private float m_z;
	
	public Ground( float len , float wid , float height , float x , float y , float z ) {
		super( len , wid , height , new Appearance() );
		
		//Color3f color = new Color3f( 0f , 1f , 0f );
		//ColoringAttributes ca = new ColoringAttributes( color , ColoringAttributes.SHADE_FLAT );
		//ap.setColoringAttributes( ca );
		
		for ( int side = 0 ; side < 6 ; side ++ ) {
			Appearance sideAp = new Appearance();
			sideAp.setColoringAttributes( Colors.generateRandomColoringAttribute() );
			this.getShape( side ).setAppearance( sideAp );
		}
		
		this.m_x = x;
		this.m_y = y;
		this.m_z = z;
		setup();
	}
	
	@Override
	public void setup() {
		
		//allow us to write to the transform group
		this.m_transformGroup.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
		
		Vector3f graphicPosition = new Vector3f( this.m_x , this.m_y , this.m_z );
		this.m_transform.setTranslation( graphicPosition );
		
		this.m_transformGroup.setTransform( this.m_transform );
		this.m_transformGroup.addChild( this );
	}

	@Override
	public void draw() {
		Vector3f graphicPosition = new Vector3f( this.m_x , this.m_y , this.m_z );
		this.m_transform.setTranslation( graphicPosition );
	}

	@Override
	public Group getGroup() {
		return this.m_transformGroup;
	}
}
