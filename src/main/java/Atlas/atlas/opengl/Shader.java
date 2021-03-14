package Atlas.atlas.opengl;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import Atlas.atlas.core.Log;
import Atlas.atlas.math.Mat4f;
import Atlas.atlas.math.Vec2f;
import Atlas.atlas.math.Vec3f;
import Atlas.atlas.math.Vec4f;

public class Shader {
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	private static final String LOCATION = "res/shader/";
	
	public static String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		
		try {
			shaderReader = new BufferedReader(new FileReader(LOCATION + fileName));
			String line;
			while((line = shaderReader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			shaderReader.close();
			Log.coreLog("Shader loaded: " + fileName);
		} catch (FileNotFoundException e) {
			System.err.println("File: " + fileName + " not found at " + LOCATION);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Failed to read shaderfile: " + fileName);
			e.printStackTrace();
		}
		
		return shaderSource.toString();
	}
	
	private int rendererId;
	
	private enum ShaderType {
		vertex, fragment
	}
	
	private ShaderType type;
	
	public Shader(String path) {
		StringBuilder vertexSrc = null;
		StringBuilder fragmentSrc = null;
		BufferedReader shaderReader = null;
		
		try {
			shaderReader = new BufferedReader(new FileReader(path));
			String line;
			while((line = shaderReader.readLine()) != null ) {
				String[] tokens = line.split(" ");
				
				if(tokens[0].equals("#type")) {
					Log.coreLog(line);
					if ( tokens[1].equals("vertex")) {
						type = ShaderType.vertex;
						vertexSrc = new StringBuilder();
					}
					if ( tokens[1].equals("fragment") ) {
						type = ShaderType.fragment;
						fragmentSrc = new StringBuilder();
					}
					continue;
				}
				switch (type) {
				case fragment: fragmentSrc.append(line).append("\n"); break;
				case vertex: vertexSrc.append(line).append("\n"); break;
				default: System.err.println("Unkown Shadertype"); break;				
				}
			}
			shaderReader.close();
			Log.coreLog("Shader loaded: " + path);
			
			int vertexShader = glCreateShader(GL_VERTEX_SHADER);
			glShaderSource(vertexShader, vertexSrc.toString());
			glCompileShader(vertexShader);
			
			if ( glGetShaderi(vertexShader, GL_COMPILE_STATUS) == 0 ) {
				System.err.println(this.getClass().getName() + " " + glGetShaderInfoLog(vertexShader, 1024));
				glDeleteShader(vertexShader);
				System.exit(1);
			}
			
			int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
			glShaderSource(fragmentShader, fragmentSrc.toString());
			glCompileShader(fragmentShader);
			
			if ( glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == 0 ) {
				System.err.println(this.getClass().getName() + " " + glGetShaderInfoLog(fragmentShader, 1024));
				glDeleteShader(fragmentShader);
				System.exit(1);
			}
			
			rendererId = glCreateProgram();
			
			glAttachShader(rendererId, vertexShader);
			glAttachShader(rendererId, fragmentShader);
			
			glLinkProgram(rendererId);
			
			if ( glGetProgrami(rendererId, GL_LINK_STATUS) == 0 ) {
				System.err.println(this.getClass().getName() + " " + glGetProgramInfoLog(rendererId, 1024));
				glDeleteProgram(rendererId);
				System.exit(1);
			}
			
			glDetachShader(rendererId, vertexShader);
			glDetachShader(rendererId, fragmentShader);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public Shader(String vertexSrc, String fragmentSrc) {
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, vertexSrc);
		glCompileShader(vertexShader);
		
		if ( glGetShaderi(vertexShader, GL_COMPILE_STATUS) == 0 ) {
			System.err.println(this.getClass().getName() + " " + glGetShaderInfoLog(vertexShader, 1024));
			glDeleteShader(vertexShader);
			System.exit(1);
		}
		
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, fragmentSrc);
		glCompileShader(fragmentShader);
		
		if ( glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == 0 ) {
			System.err.println(this.getClass().getName() + " " + glGetShaderInfoLog(fragmentShader, 1024));
			glDeleteShader(fragmentShader);
			System.exit(1);
		}
		
		rendererId = glCreateProgram();
		
		glAttachShader(rendererId, vertexShader);
		glAttachShader(rendererId, fragmentShader);
		
		glLinkProgram(rendererId);
		
		if ( glGetProgrami(rendererId, GL_LINK_STATUS) == 0 ) {
			System.err.println(this.getClass().getName() + " " + glGetProgramInfoLog(rendererId, 1024));
			glDeleteProgram(rendererId);
			System.exit(1);
		}
		
		glDetachShader(rendererId, vertexShader);
		glDetachShader(rendererId, fragmentShader);
	}
	
	public void bind() {
		glUseProgram(rendererId);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	public void UploadUniformInt(String name, int value) {
		int location = glGetUniformLocation(rendererId, name);
		glUniform1i(location, value);
	}
	
	public void UploadUniformFloat(String name, float value) {
		int location = glGetUniformLocation(rendererId, name);
		glUniform1f(location, value);
	}
	
	public void UploadUniformFloat2(String name, Vec2f values) {
		int location = glGetUniformLocation(rendererId, name);
		glUniform2f(location, values.getX(), values.getY());
	}
	
	public void UploadUniformFloat3(String name, Vec3f values) {
		int location = glGetUniformLocation(rendererId, name);
		glUniform3f(location, values.getX(), values.getY(), values.getZ());
	}
	
	public void UploadUniformFloat4(String name, Vec4f values) {
		int location = glGetUniformLocation(rendererId, name);
		glUniform4f(location, values.getX(), values.getY(), values.getZ(), values.getW());
	}
	
	public void UploadUniformMat4(String name, Mat4f matrix) {
		int location = glGetUniformLocation(rendererId, name);
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		glUniformMatrix4fv(location, false, matrixBuffer);
	}
	
	public int getRendererId() {
		return rendererId;
	}
}
