package liquibase.util;

public class LogUtil {

    /**
     * SLF4j MDC for the current {@link liquibase.changelog.ChangeSet}
     */
    public static final String MDC_CHANGESET = "changeSet";

    /**
     * SLF4j MDC for the current {@link liquibase.parser.preprocessor.ParsedNodePreprocessor}
     */
    public static final String MDC_PREPROCESSOR = "preprocessor";
}
