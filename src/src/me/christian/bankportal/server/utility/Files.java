package me.christian.bankportal.server.utility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Christian Sweat
 * @since 11:06 AM 7/30/22
 */
public class Files {

    public static String readContent(String fileName) {
        return reader(fileName).map(read -> String.join(" ", read.lines().toList())).orElse("");
    }

    public static List<String> readLines(String fileName) {
        return reader(fileName).map(read -> read.lines().toList()).orElse(new ArrayList<>());
    }

    public static void write(String fileName, String content, boolean append) {
        Optional<BufferedWriter> write = writer(fileName, append);
        write.ifPresent(bufferedWriter -> {
            try {
                bufferedWriter.write(content);
            } catch (IOException exception) {
                System.err.println("Exception thrown -> " + exception.getMessage());
            }
        });
    }

    private static Optional<BufferedReader> reader(String fileName) {
        try {
            return Optional.of(new BufferedReader(new FileReader(fileName)));
        } catch (Exception exception) {
            System.err.println("Exception thrown -> " + exception.getMessage());
            return Optional.empty();
        }
    }

    private static Optional<BufferedWriter> writer(String fileName, boolean append) {
        try {
            return Optional.of(new BufferedWriter(new FileWriter(fileName, append)));
        } catch (Exception exception) {
            System.err.println("Exception thrown -> " + exception.getMessage());
            return Optional.empty();
        }
    }

}
