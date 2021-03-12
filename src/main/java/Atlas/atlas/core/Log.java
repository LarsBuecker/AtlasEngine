package Atlas.atlas.core;

public class Log {
	
	public static void coreLog(String msg) {
		System.out.println("CORE: " + msg);
	}
	
	public static void clientLog(String msg) {
		System.out.println("APP: " + msg);
	}
}
