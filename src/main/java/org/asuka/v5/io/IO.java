package org.asuka.v5.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class IO {
    private static final MyList<String> gameOrigin = new MyList<>(new ArrayList<>());
    private final byte[] originFile;
    private final byte[] v5File;
    private final String path;


    public IO(String path) throws IOException {
        originFile = Objects.requireNonNull(IO.class.getClassLoader()
                                                    .getResourceAsStream("origin.Engine.ini"))
                            .readAllBytes();
        v5File = Objects.requireNonNull(IO.class.getClassLoader()
                                                .getResourceAsStream("v5.Engine.ini"))
                        .readAllBytes();

        this.path = path;

        Files.readAllLines(Path.of(path))
             .stream()
             .forEach(gameOrigin::add);
    }

    public boolean isOrigin() throws IOException {
        return Files.readAllLines(Path.of(path))
                    .stream()
                    .filter("[SystemSettings]"::equals)
                    .toList()
                    .size() == 1;
    }

    public void write() throws IOException {
        Files.deleteIfExists(Path.of(this.path
        ));
        try (var fos = new FileOutputStream(this.path
        )) {
            fos.write(v5File);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() throws IOException {
        Files.deleteIfExists(Path.of(this.path
        ));
        try (var fos = new FileOutputStream(this.path
        )) {
            fos.write(originFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
