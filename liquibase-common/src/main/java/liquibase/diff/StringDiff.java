package liquibase.diff;

public class StringDiff {
    private String referenceVersion;
    private String currentVersion;


    public StringDiff(String referenceVersion, String currentVersion) {
        this.referenceVersion = referenceVersion;
        this.currentVersion = currentVersion;
    }


    public String getReferenceVersion() {
        return referenceVersion;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public boolean areEqual() {
        if (referenceVersion == null) {
            return currentVersion == null;
        }
        return referenceVersion.equals(currentVersion);
    }
}
