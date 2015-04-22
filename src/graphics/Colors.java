package graphics;

import javax.media.j3d.ColoringAttributes;
import javax.vecmath.Color3f;

public class Colors {


	public static ColoringAttributes WHITE = new ColoringAttributes( new Color3f( 1 , 1 , 1 ) , ColoringAttributes.NICEST );
	
	public static ColoringAttributes generateRandomColoringAttribute() {
		float colorSum = 0;
		float r = 0;
		float g = 0;
		float b = 0;
		while ( colorSum < 1 ) {
			r = ( float ) Math.random();
			g = ( float ) Math.random();
			b = ( float ) Math.random();
			colorSum = r + g + b;
		}
		Color3f color = new Color3f( r , g , b );
		ColoringAttributes ca = new ColoringAttributes( color , ColoringAttributes.NICEST );
		return ca;
	}
}
