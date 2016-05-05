package liquibase.util;

/**
 * Utilties for working with the general system
 */
public abstract class SystemUtil {

    /**
     * @return true if current running in Windows.
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows ");
    }

}
