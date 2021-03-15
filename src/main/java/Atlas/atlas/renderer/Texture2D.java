package Atlas.atlas.renderer;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL45;

import Atlas.atlas.core.Log;
import Atlas.atlas.opengl.OpenGLContext;
import de.matthiasmann.twl.utils.PNGDecoder;

public class Texture2D {

	private String path;
	private int width, height;
	private int rendererId;
	
	public Texture2D(String path) {
	    PNGDecoder decoder = null;
		try {
			decoder = new PNGDecoder(new FileInputStream(path));
		    ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());

		    decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
		    buffer.flip();

		    int id = glGenTextures();
		    this.rendererId = id;

		    glBindTexture(GL_TEXTURE_2D, id);

		    glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

		    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		    //upload texture
		    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		    // Generate Mip Map
		    GL40.glGenerateMipmap(GL_TEXTURE_2D);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
		
		width = decoder.getWidth();
		height = decoder.getHeight();
		
		Log.coreLog("Texture " + path + " loaded!");
	}
	
	public Texture2D(int width, int height) {
		int id = glGenTextures();
	    this.rendererId = id;
	    glBindTexture(GL_TEXTURE_2D, id);
		
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);   
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		Log.coreLog("Texture " + path + " loaded!");
	}
	
	public void setData(ByteBuffer data, int size) {
		if(OpenGLContext.getGLCapabilities().OpenGL45)
			GL45.glTextureSubImage2D(rendererId, 0, 0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
		else 
			System.err.println("Updating an existing texture requieres OpenGL version 4.5 or higher!");
	}
	
	public void bind(int slot) {
		if(OpenGLContext.getGLCapabilities().OpenGL45)
			GL45.glBindTextureUnit(slot, this.rendererId);
		else {
			GL15.glActiveTexture(GL15.GL_TEXTURE0 + slot);
			GL15.glBindTexture(GL15.GL_TEXTURE_2D, rendererId);
		}
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

	public int getRendererId() {
		return rendererId;
	}	
}
