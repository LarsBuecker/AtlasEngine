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
import org.lwjgl.opengl.GL40;

import Atlas.atlas.core.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import de.matthiasmann.twl.utils.PNGDecoder;

public class Texture2D {

	private String path;
	private int width, height;
	private int rendererId;
	
	public Texture2D(String path) {
		 //load png file
	    PNGDecoder decoder = null;
		try {
			decoder = new PNGDecoder(new FileInputStream(path));
			//create a byte buffer big enough to store RGBA values
		    ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());

		    //decode
		    decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);

		    //flip the buffer so its ready to read
		    buffer.flip();

		    //create a texture
		    int id = glGenTextures();
		    this.rendererId = id;
		    Log.coreLog(path +  " " + id);

		    //bind the texture
		    glBindTexture(GL_TEXTURE_2D, id);

		    //tell opengl how to unpack bytes
		    glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

		    //set the texture parameters, can be GL_LINEAR or GL_NEAREST
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
