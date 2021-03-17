package Atlas.atlas.renderer;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL45;

public class Framebuffer {
	
	private FramebufferSpecification spec;
	private int rendererId = 0;
	private int colorAttachment = 0;
	private int depthAttachment = 0;
	
	public Framebuffer(FramebufferSpecification spec) {
		this.spec = spec;
		Invalidate();
	}
	
	public FramebufferSpecification getSpecification() {
		return spec;
		
	}
	
	public void bind() {
		GL45.glBindFramebuffer(GL_FRAMEBUFFER, rendererId);
		GL11.glViewport(0, 0, spec.width, spec.height);
	}
	
	public void unbind() {
		GL45.glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	public void resize(int width, int height) {
		spec.width = width;
		spec.height = height;
		Invalidate();
	}
	
	public void Invalidate() {
		
		if (rendererId != 0) {
			GL45.glDeleteFramebuffers(rendererId);
			GL45.glDeleteTextures(colorAttachment);
			GL45.glDeleteTextures(depthAttachment);
		}
		
		rendererId = GL45.glGenFramebuffers();
		GL45.glBindFramebuffer(GL_FRAMEBUFFER, rendererId);
		
		colorAttachment = GL45.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, colorAttachment);
		GL45.glTexImage2D(GL_TEXTURE_2D, 0, GL45.GL_RGBA8, spec.width, spec.height, 0, GL45.GL_RGBA, GL45.GL_UNSIGNED_BYTE, (java.nio.ByteBuffer) null);
		GL15.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		GL15.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		GL45.glFramebufferTexture2D(GL_FRAMEBUFFER, GL45.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, colorAttachment, 0);
		
		depthAttachment = GL45.glGenTextures();
		GL15.glBindTexture(GL_TEXTURE_2D, depthAttachment);
		GL45.glTexImage2D(GL_TEXTURE_2D, 0, GL45.GL_DEPTH24_STENCIL8, spec.width, spec.height, 0, GL45.GL_DEPTH_STENCIL, GL45.GL_UNSIGNED_INT_24_8, (java.nio.ByteBuffer) null);
		GL45.glFramebufferTexture2D(GL_FRAMEBUFFER, GL45.GL_DEPTH_STENCIL_ATTACHMENT, GL11.GL_TEXTURE_2D, depthAttachment, 0);
		
		if ( GL45.glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL45.GL_FRAMEBUFFER_COMPLETE ) {
			System.err.println("Framebuffer uncomplete!");
		}
		
		GL45.glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	public int getColorAttachmentRendererId() {
		return colorAttachment;
	}
	
}
