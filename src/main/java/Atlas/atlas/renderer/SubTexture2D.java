package Atlas.atlas.renderer;

import Atlas.atlas.math.Vec2f;

public class SubTexture2D {
	
	private Texture2D texture;
	private Vec2f[] texCoords = new Vec2f[4];

	public SubTexture2D(Texture2D texture, Vec2f min, Vec2f max) {
		this.texture = texture;
		texCoords[0] = new Vec2f( min.getX(), min.getY() );
		texCoords[1] = new Vec2f( max.getX(), min.getY() );
		texCoords[2] = new Vec2f( max.getX(), max.getY() );
		texCoords[3] = new Vec2f( min.getX(), max.getY() );
	}
	
	public static SubTexture2D createFromCoords(Texture2D texture, Vec2f coords, Vec2f spriteSize) {
		
		Vec2f min = new Vec2f((coords.getX() * spriteSize.getX()) / texture.getWidth(), (coords.getY() * spriteSize.getY()) / texture.getHeight());
		Vec2f max = new Vec2f(((coords.getX() + 1) * spriteSize.getX()) / texture.getWidth(), ((coords.getY() + 1) * spriteSize.getY()) / texture.getHeight());
		
		return new SubTexture2D(texture, min, max);
	}

	public Texture2D getTexture() {
		return texture;
	}

	public Vec2f[] getTexCoords() {
		return texCoords;
	}
}
