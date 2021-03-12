package Atlas.atlas.renderer;

import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.opengl.GL30.glTexParameterIi;

import java.io.FileInputStream;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import Atlas.atlas.core.Log;
import Atlas.atlas.opengl.OpenGLContext;

public class Texture2D {

	private String path;
	private int width, height;
	private int rendererId;
	
	public Texture2D(String path) {
		this.path = path;
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/textures/" + path));
			glGenerateMipmap(GL11.GL_TEXTURE_2D);
			glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexParameterf(GL11.GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, 0);
			if (OpenGLContext.getGLCapabilities().GL_EXT_texture_filter_anisotropic) {
				float amount = Math.min(4f, glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
				glTexParameterf(GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
			} else {
				Log.coreLog("Anisotropic Filtering is not supported in Driver!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Tried to load texture " + path + " , didn't work");
			System.exit(-1);
		}
		
		width = texture.getTextureWidth();
		height = texture.getTextureHeight();
		rendererId = texture.getTextureID();
	}
	
	public void bind(int slot) {
		glBindTexture(slot, rendererId);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getPath() {
		return path;
	}	
}
