package dev.appkr.cpResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws IOException {
        // read file content
        try (InputStream in = App.class.getClassLoader().getResourceAsStream("dir/file.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            final String content = reader
                .lines()
                .collect(Collectors.joining("\n"));
            System.out.println("File content:\n" + content);
        }

        // get file path
        final URL url = App.class.getClassLoader().getResource("dir/file.txt");
        System.out.println("URL: " + url);              // jar:file:/Users/appkr/Desktop/app.jar!/dir/file.txt
        System.out.println("Path: " + url.getPath());   // file:/Users/appkr/Desktop/app.jar!/dir/file.txt

        // NOTE: cannot read content in Jar
        // Exception in thread "main" java.nio.file.NoSuchFileException: file:/Users/appkr/Desktop/app.jar!/dir/file.txt
        final String content = new String(Files.readAllBytes(Paths.get(url.getPath())));
        System.out.println("File content:\n" + content);
    }
}
