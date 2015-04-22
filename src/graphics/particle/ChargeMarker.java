package graphics.particle;

import graphics.Colors;

import javax.media.j3d.Appearance;

import rigidbodyphy.ChargedBoxBody;
import _math.Vector3D;

public class ChargeMarker extends LocationMarker {
	
	
	private ChargedBoxBody m_body;
	
	public ChargeMarker( ChargedBoxBody body ) {
		super( 0.2f );
		this.m_body = body;
		Appearance ap = new Appearance();
		ap.setColoringAttributes( Colors.WHITE );
		this.setAppearance( ap );
		setup();
	}

	@Override
	public void updatePosition() {
		Vector3D worldCoordinates = this.m_body.convertLocalToWorld( this.m_body.getChargeLocation() );
		this.setX( worldCoordinates.getX() );
		this.setY( worldCoordinates.getY() );
		this.setZ( worldCoordinates.getZ() );
		super.updatePosition();
	}
}
