package dk.itu.mhso.wmd;

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
}
