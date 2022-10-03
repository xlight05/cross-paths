import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;

public class TestSample {
    private static final Path RESOURCE_DIRECTORY = Paths.get(
            "src/test/resources/compiler_plugin_tests").toAbsolutePath();
    private static final Path TXT = RESOURCE_DIRECTORY.resolve("hello.txt");


    @Test
    public void testIdlPluginBuildProject() throws IOException, URISyntaxException {
        String mainContent = Files.readString(TXT);
//        String path = RESOURCE_DIRECTORY.normalize().toString();
//        System.out.println(path);
//        String replacement = Matcher.quoteReplacement(path + File.separator);
//        String newMainContent = mainContent.replaceAll("<<PROJECT_DIR>>", replacement);
//        System.out.println("URI Test");
//        String newUrl = "file://" + replacement + "hello.txt";
//        System.out.println("Given");
//        System.out.println(newUrl);
        System.out.println("Expected");
        URL url = RESOURCE_DIRECTORY.resolve("hello.txt").toUri().toURL();
        String urlString = Matcher.quoteReplacement(url.toString());
        System.out.println(urlString);

        String newMainContent = mainContent.replaceAll("<<PROJECT_DIR>>", urlString);
        Files.write(TXT, newMainContent.getBytes(StandardCharsets.UTF_8));
        
        URL newUrl = new URL(urlString);
//        URL url = new URL(newUrl);
        Path test = RESOURCE_DIRECTORY.resolve("test.txt");
        try (BufferedInputStream in = new BufferedInputStream(newUrl.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(test.toFile())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }

        System.out.println(Files.readString(test));
        
//        Files.write(TXT, newMainContent.getBytes(StandardCharsets.UTF_8));

//        String newContent = Files.readString(TXT);
//        System.out.println(newContent);

//        Files.write(TXT, mainContent.getBytes(StandardCharsets.UTF_8));
        Assert.assertTrue(true);
    }
}
