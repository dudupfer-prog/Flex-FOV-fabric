package net.id107.flexfov;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import net.id107.flexfov.projection.Projection;

public class ShaderManager {

	private int shaderProgram;
	private int vertexShader;
	private int fragmentShader;
	
	public void createShaderProgram(Projection projection) {
		if (shaderProgram != 0) return;
		
		String vSource = projection.getVertexShader();
		String fSource = projection.getFragmentShader();
		
		if (vSource == null || vSource.isEmpty()) {
			System.err.println("[FlexFOV] Vertex shader source is empty!");
		}
		if (fSource == null || fSource.isEmpty()) {
			System.err.println("[FlexFOV] Fragment shader source is empty!");
		}
		
		shaderProgram = GL20.glCreateProgram();
		vertexShader = createShader(vSource, GL20.GL_VERTEX_SHADER);
		fragmentShader = createShader(fSource, GL20.GL_FRAGMENT_SHADER);
		
		GL20.glAttachShader(shaderProgram, vertexShader);
		GL20.glAttachShader(shaderProgram, fragmentShader);
		GL20.glBindAttribLocation(shaderProgram, 0, "vertex");
		GL30.glBindFragDataLocation(shaderProgram, 0, "color");
		GL20.glLinkProgram(shaderProgram);
		
		if (GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			System.err.println("[FlexFOV] Shader Link Error: " + getLogInfo(shaderProgram));
			return;
		}
		GL20.glValidateProgram(shaderProgram);
		if (GL20.glGetProgrami(shaderProgram, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
			System.err.println("[FlexFOV] Shader Validate Warning: " + getLogInfo(shaderProgram));
		}
	}
	
	private int createShader(String source, int type) {
		int shader = GL20.glCreateShader(type);
		if (shader == 0) {
			System.err.println("[FlexFOV] Could not create shader object");
			return 0;
		}
		GL20.glShaderSource(shader, source != null ? source : "");
		GL20.glCompileShader(shader);
		
		if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("[FlexFOV] Shader Compile Error (" + type + "): " + getLogInfo(shader));
		}
		
		return shader;
	}
	
	private String getLogInfo(int shader) {
		return GL20.glGetShaderInfoLog(shader, GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH));
	}
	
	public void deleteShaderProgram() {
		if (shaderProgram == 0) return;
		GL20.glDetachShader(shaderProgram, vertexShader);
		GL20.glDetachShader(shaderProgram, fragmentShader);
		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);
		GL20.glDeleteProgram(shaderProgram);
		vertexShader = 0;
		fragmentShader = 0;
		shaderProgram = 0;
	}
	
	public int getShaderProgram() {
		return shaderProgram;
	}
}
