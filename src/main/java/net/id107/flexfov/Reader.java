package net.id107.flexfov;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class Reader {

	public static String read(String resourceIn) {
		InputStream is = null;
		
		try {
			MinecraftClient mc = MinecraftClient.getInstance();
			if (mc != null) {
				ResourceManager resourceManager = mc.getResourceManager();
				if (resourceManager != null) {
					Optional<Resource> optionalResource = resourceManager.getResource(new Identifier(resourceIn));
					if (optionalResource.isPresent()) {
						is = optionalResource.get().getInputStream();
					}
				}
			}
		} catch (Exception e) {
			// Fallback caso a API do Minecraft falhe
		}
		
		if (is == null) {
			// Leitura direta do JAR via Classpath
			String path = resourceIn.contains(":") ? resourceIn.substring(resourceIn.indexOf(":") + 1) : resourceIn;
			is = Reader.class.getResourceAsStream("/assets/flexfov/" + path);
			if (is == null) {
				is = Reader.class.getResourceAsStream("/" + path);
			}
		}
		
		if (is == null) {
			System.err.println("[FlexFOV] Shader resource not found: " + resourceIn);
			return "";
		}
		
		try (InputStream stream = is) {
			byte[] bytes = stream.readAllBytes();
			return new String(bytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
