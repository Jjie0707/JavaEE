package e7_e8;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

public class WordCountMain {

    private static class KeywordComparator implements Comparator<String> {
        @Override
        public int compare(String word1, String word2) {
            boolean word1StartsWithLetter = startsWithLetter(word1);
            boolean word2StartsWithLetter = startsWithLetter(word2);

            if (word1StartsWithLetter && !word2StartsWithLetter) {
                return -1;
            }
            if (!word1StartsWithLetter && word2StartsWithLetter) {
                return 1;
            }
            int result = word1.compareToIgnoreCase(word2);
            if (result != 0) {
                return result;
            }
            return word1.compareTo(word2);
        }

        private boolean startsWithLetter(String word) {
            return !word.isEmpty() && Character.isLetter(word.charAt(0));
        }
    }

    private static List<Path> getDataFiles(Path dataDirectory) throws IOException {
        List<Path> dataFiles = new ArrayList<>();

        for (Path path : Files.list(dataDirectory).toList()) {
            if (Files.isRegularFile(path)
                    && path.getFileName().toString().endsWith(".txt")) {
                dataFiles.add(path);
            }
        }
        dataFiles.sort(null);
        return dataFiles;
    }

    private static void writeResult(Path outputFile, Map<String, Integer> wordCountMap) throws IOException {
        TreeMap<String, Integer> sortedMap = new TreeMap<>(new KeywordComparator());
        sortedMap.putAll(wordCountMap);
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
                writer.write(entry.getKey() + "\t" + entry.getValue());
                writer.newLine();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Path dataDirectory = Paths.get("TestData");
        Path outputFile = Paths.get("WordCount.txt");
        ConcurrentMap<String, Integer> wordCountMap = new ConcurrentHashMap<>();

        List<Path> dataFiles = getDataFiles(dataDirectory);

        List<KeywordCountThread> threads = new ArrayList<>();
        for (Path dataFile : dataFiles) {
            KeywordCountThread thread = new KeywordCountThread(dataFile, wordCountMap);
            threads.add(thread);
            thread.start();
        }
        for (KeywordCountThread thread : threads) {
            thread.join();
        }
        writeResult(outputFile, wordCountMap);
        System.out.println("词频统计完成，结果已输出到 " + outputFile.toAbsolutePath());
    }


}
