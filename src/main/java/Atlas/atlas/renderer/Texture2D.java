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
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL45;

import Atlas.atlas.core.Log;
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
		    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		    //upload texture
		    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		    // Generate Mip Map
		    GL40.glGenerateMipmap(GL_TEXTURE_2D);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		// TODO Load texture with GL version below 4.5
		GL45.glTextureSubImage2D(rendererId, 0, 0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
	}
	
	public void bind(int slot) {
		// TODO GL Version check for version under 4.5
//		glBindTexture(slot, this.rendererId);
		GL45.glBindTextureUnit(slot, this.rendererId);
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
