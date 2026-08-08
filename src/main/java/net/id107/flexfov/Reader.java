package net.id107.flexfov;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class Reader {

	public static String read(String resourceIn) {
		ResourceManager resourceManager = MinecraftClient.getInstance().getResourceManager();
		Optional<Resource> optionalResource = resourceManager.getResource(new Identifier(resourceIn));
		if (optionalResource.isEmpty()) {
			System.out.println("Shader not found: " + resourceIn);
			return "";
		}
		
		Resource resource = optionalResource.get();
		try (InputStream is = resource.getInputStream()) {
			if (is == null) {
				System.out.println("Shader input stream is null");
				return "";
			}
			
			StringBuilder sb = new StringBuilder();
			int i = is.read();
			while (i != -1) {
				sb.append((char) i);
				i = is.read();
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}
