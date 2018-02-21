import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextMain {

    public static void main(String... args) throws IOException {
        final Path path = Paths.get("/home/vp-byte/test/best/assssss");
        final Path newFile = Paths.get("/home/vp-byte/test/hallo/assssss");
        Files.move(path, newFile);

    }
}
