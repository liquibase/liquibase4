package liquibase.resource

import liquibase.JUnitScope
import liquibase.util.StreamUtil
import spock.lang.Specification

public class FileSystemResourceAccessorTest extends Specification {

    private FileSystemResourceAccessor createResourceAccessor(String subdir) {
        String packageDirectory = getLiquibaseResourcesDirectoryAbsolutePath()

        if (subdir != null) {
            packageDirectory = packageDirectory + "/" + subdir;
        }
        return new FileSystemResourceAccessor(packageDirectory);
    }

    private FileSystemResourceAccessor createJarResourceAccessor() {
        def logFactoryUrl = getClass().getClassLoader().getResource("test-file.jar")
        return new FileSystemResourceAccessor(logFactoryUrl.toExternalForm().replaceFirst("file:/", ""))
    }

    private FileSystemResourceAccessor createZipResourceAccessor() {
        def logFactoryUrl = getClass().getClassLoader().getResource("test-file.zip")
        return new FileSystemResourceAccessor(logFactoryUrl.toExternalForm().replaceFirst("file:/", ""))
    }

    private String getLiquibaseResourcesDirectoryAbsolutePath() {
        File thisClassFile = new File(new URI(this.getClass().getClassLoader().getResource("liquibase/Scope.class").toExternalForm()));
        return thisClassFile.getParentFile().getParentFile().getAbsolutePath();
    }

    def "constructor only accepts directories"() {
        when:
        ResourceAccessor ignored = new FileSystemResourceAccessor(getLiquibaseResourcesDirectoryAbsolutePath() + "liquibase/Scope.class");

        then:
        thrown(IllegalArgumentException)
    }

    def "readContents finds resource for single file directly in path"() {
        when:
        def contents = createResourceAccessor(null).openStreams("META-INF/services/liquibase.database.Database")

        then:
        contents.size() == 1
        StreamUtil.readStreamAsString(contents.iterator().next(), JUnitScope.instance) == "liquibase.database.core.GenericDatabase"

        cleanup:
        contents.close();
    }

    def "readContents finds resource for single file in sub-path"() {
        when:
        def contents = createResourceAccessor(null).openStreams("liquibase/resource/ResourceAccessor.class")

        then:
        contents.size() == 1

        cleanup:
        contents.close()
    }

    def "readContents returns empty set for invalid file"() {
        expect:
        createResourceAccessor(null).openStreams("liquibase/resource/Fake.file").size() == 0
    }

    def "list finds files correctly (no recursion)"() throws IOException {
        expect:
        createResourceAccessor("liquibase").list(null, null, false).join("\n") == """
AbstractExtensibleObject.class
ExtensibleObject.class
Scope\$Attr.class
Scope.class
SingletonObject.class
ValidationErrors\$1.class
ValidationErrors\$ErrorCheck.class
ValidationErrors\$FieldCheck.class
ValidationErrors.class
""".trim()
    }

    def "list finds files correctly (yes recursion)"() throws IOException {
        expect:
        createResourceAccessor("liquibase/database").list(null, null, true).join("\n") == """
AbstractJdbcDatabase.class
Database\$Feature.class
Database\$IdentifierCaseHandling.class
Database\$QuotingStrategy.class
Database.class
DatabaseConnection.class
DatabaseFactory.class
JdbcConnection.class
OfflineConnection\$OutputLiquibaseSql.class
OfflineConnection.class
core/GenericDatabase.class
""".trim()
    }

    def "list can find a single file if it exists"() throws Exception {
        expect:
        createResourceAccessor(null).list("liquibase/database/Database.class", null, true).join("\n") == "liquibase/database/Database.class"
        createResourceAccessor(null).list("liquibase/database/Database.class", null, false).join("\n") == "liquibase/database/Database.class"
        createResourceAccessor("liquibase").list("database/Database.class", null, true).join("\n") == "database/Database.class"
        createResourceAccessor(null).list("Database.class", "liquibase/database", false).join("\n") == "liquibase/database/Database.class"
        createResourceAccessor(null).list("Database.class", "liquibase/database/AbstractJdbcDatabase.class", false).join("\n") == "liquibase/database/Database.class"
    }

    def "list returns empty for a single file if it does not exists"() throws Exception {
        expect:
        createResourceAccessor(null).list("Fake.class", null, true).size() == 0
        createResourceAccessor(null).list("Fake.class", null, false).size() == 0
        createResourceAccessor("liquibase").list("database/Fake.class", null, true).size() == 0
        createResourceAccessor(null).list("Fake.class", "liquibase/database", false).size() == 0
    }

    def "list from an invalid relativeTo returns empty"() throws Exception {
        createResourceAccessor(null).list("Fake.class", "/fake/path", false).size() == 0
    }

    def "lists files correctly with a relative-to-other-action (recursive no)"() throws Exception {
        expect:
        createResourceAccessor("liquibase").list(null, "database", false).join("\n") == """
database/AbstractJdbcDatabase.class
database/Database\$Feature.class
database/Database\$IdentifierCaseHandling.class
database/Database\$QuotingStrategy.class
database/Database.class
database/DatabaseConnection.class
database/DatabaseFactory.class
database/JdbcConnection.class
database/OfflineConnection\$OutputLiquibaseSql.class
database/OfflineConnection.class
""".trim()
    }

    def "list comes back alphabetically"() throws IOException {
        expect:
        def files = createResourceAccessor(null).list(null, null, false);
        String lastFile = null;
        for (file in files) {
            if (lastFile != null) {
                assert lastFile.compareTo(file) > 0: file + " should have come before " + lastFile;
            }
        }
    }

    def "list will find files when a path only exists in one rootPath"() {
        when:
        def resourceAccessor = new FileSystemResourceAccessor(getLiquibaseResourcesDirectoryAbsolutePath() + "/liquibase/database", getLiquibaseResourcesDirectoryAbsolutePath() + "/liquibase/resource", getLiquibaseResourcesDirectoryAbsolutePath() + "/liquibase/snapshot")

        then:
        resourceAccessor.list("core", null, false).join("\n") == """
core/GenericDatabase.class
""".trim()

    }

    def "list will find files from root of all rootPaths"() {
        when:
        def resourceAccessor = new FileSystemResourceAccessor(getLiquibaseResourcesDirectoryAbsolutePath() + "/liquibase/database", getLiquibaseResourcesDirectoryAbsolutePath() + "/liquibase/resource", getLiquibaseResourcesDirectoryAbsolutePath() + "/liquibase/snapshot")

        then:
        resourceAccessor.list(null, null, false).join("\n") == """
AbstractJdbcDatabase.class
AbstractResourceAccessor.class
ClassLoaderResourceAccessor.class
CompositeResourceAccessor.class
Database\$Feature.class
Database\$IdentifierCaseHandling.class
Database\$QuotingStrategy.class
Database.class
DatabaseConnection.class
DatabaseFactory.class
FileSystemResourceAccessor\$1.class
FileSystemResourceAccessor\$2.class
FileSystemResourceAccessor\$3.class
FileSystemResourceAccessor\$ResourceFileVisitor.class
FileSystemResourceAccessor.class
InputStreamList.class
JdbcConnection.class
OfflineConnection\$OutputLiquibaseSql.class
OfflineConnection.class
OsgiResourceAccessor.class
ResourceAccessor.class
Snapshot\$1.class
Snapshot.class
SnapshotFactory.class
""".trim()
    }

    def "list can find files in the root of a jar (not recursive)"() {
        expect:
        createJarResourceAccessor().list(null, null, false).join("\n") == """
root-file1.txt
root-file2.txt
""".trim()

    }

    def "list can find files in the root of a jar (recursive)"() {
        expect:
        createJarResourceAccessor().list(null, null, true).join("\n") == """
com/example/example-file1.txt
com/example/example-file2.txt
com/example/test/compressed.txt.gz
com/example/test/test-file1.txt
com/example/test/test-file2.txt
root-file1.txt
root-file2.txt
""".trim()
    }

    def "list can find a single file in a jar"() {
        expect:
        createJarResourceAccessor().list("com/example/example-file2.txt", null, true).join("\n") == "com/example/example-file2.txt"
    }

    def "list on a zip returns empty for a single file if it does not exists"() throws Exception {
        expect:
        createJarResourceAccessor().list("Fake.class", null, true).size() == 0
        createJarResourceAccessor().list("database/Fake.class", null, false).size() == 0
        createJarResourceAccessor().list("Fake.class", "liquibase/database", false).size() == 0
    }

    def "readContents can read from jar"() {
        when:
        def contents = createJarResourceAccessor().openStreams("com/example/example-file1.txt")

        then:
        contents.size() == 1
        StreamUtil.readStreamAsString(contents.iterator().next(), JUnitScope.instance) == "This file is in com/example as example-file1.txt"

        cleanup:
        contents.close()
    }

    def "readContents can read from a compressed file in a jar"() {
        when:
        def content = createJarResourceAccessor().openStreams("com/example/test/compressed.txt.gz")

        then:
        content.size() == 1
        StreamUtil.readStreamAsString(content.iterator().next(), JUnitScope.instance) == "This file is compressed as gz in the jar"

        cleanup:
        content.close()
    }

    def "list can find files in the root of a zip (recursive)"() {
        expect:
        createZipResourceAccessor().list(null, null, true).join("\n") == """
com/example/example-file1.txt
com/example/example-file2.txt
com/example/test/compressed.txt.gz
com/example/test/test-file1.txt
com/example/test/test-file2.txt
root-file1.txt
root-file2.txt
""".trim()
    }

    def "readContents can read from zip"() {
        when:
        def contents = createZipResourceAccessor().openStreams("com/example/example-file1.txt")

        then:
        contents.size() == 1
        StreamUtil.readStreamAsString(contents.iterator().next(), JUnitScope.instance) == "This file is in com/example as example-file1.txt in the zip"
    }

}
