package dk.itu.mhso.wmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Resources {
	public static String getLevelsPath() {
		if(Main.PRODUCTION) return WMDConstants.class.getResource("/resources/levels").toString();
		else return "resources/levels";
	}
	
	public static String getSpritesPath() {
		if(Main.PRODUCTION) return WMDConstants.class.getResource("/resources/sprites").toString();
		else return "resources/sprites";
	}
	
	public static String getUnitinfoPath() {
		if(Main.PRODUCTION) return WMDConstants.class.getResource("/resources/unitinfo").toString();
		else return "resources/unitinfo";
	}
	
	public static String[] getDefaultEnemies() {
		Charset charset = Charset.forName("UNICODE");
		try(BufferedReader reader = Files.newBufferedReader(Paths.get("resources/default_enemies.txt"), charset)) {
			List<String> lines = new ArrayList<>();
			String line = reader.readLine();
			while(line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			return lines.toArray(new String[lines.size()]);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
