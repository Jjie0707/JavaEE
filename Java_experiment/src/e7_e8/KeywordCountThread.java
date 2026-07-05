package e7_e8;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class KeywordCountThread extends Thread {
    private final Path dataFile;
    private final ConcurrentMap<String, Integer> wordCountMap;

    public KeywordCountThread(Path dataFile, ConcurrentMap<String, Integer> wordCountMap) {
        this.dataFile = dataFile;
        this.wordCountMap = wordCountMap;
    }

    @Override
    public void run() {
        try {
            Map<String, Integer> localWordCountMap = countKeywordsWithCharset();
            mergeToTotalMap(localWordCountMap);
            System.out.println(dataFile.getFileName() + " 处理完成");
        } catch (IOException e) {
            System.out.println(dataFile.getFileName() + " 处理失败: " + e.getMessage());
        }
    }

    private Map<String, Integer> countKeywordsWithCharset() throws IOException {
        try {
            return countKeywords(StandardCharsets.UTF_8);
        } catch (MalformedInputException e) {
            try {
                return countKeywords(StandardCharsets.UTF_16);
            } catch (MalformedInputException ex) {
                return countKeywords(Charset.forName("GB18030"));
            }
        }
    }

    private Map<String, Integer> countKeywords(Charset charset) throws IOException {
        Map<String, Integer> currentWordCountMap = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(dataFile, charset)) {
            String header = reader.readLine();
            if (header == null)   return currentWordCountMap;

            int deIndex = findDEIndex(header);
            if (deIndex == -1) return currentWordCountMap;

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\t", -1);
                if (deIndex >= fields.length) {
                    continue;
                }
                countOneDeField(fields[deIndex], currentWordCountMap);
            }
        }
        return currentWordCountMap;
    }

    private int findDEIndex(String header) {
        String[] fieldNames = header.split("\t", -1);
        for (int i = 0; i < fieldNames.length; i++) {
            if ("DE".equals(removeBom(fieldNames[i]).trim())) {
                return i;
            }
        }
        return -1;
    }

    private String removeBom(String text) {
        if (text != null && text.startsWith("\uFEFF")) {
            return text.substring(1);
        }
        return text;
    }

    private void countOneDeField(String deField, Map<String, Integer> currentWordCountMap) {
        String[] keywords = deField.split(";");
        for (String keyword : keywords) {
            String word = keyword.trim();
            if (!word.isEmpty()) {
                currentWordCountMap.merge(word, 1, Integer::sum);
            }
        }
    }

    private void mergeToTotalMap(Map<String, Integer> localWordCountMap) {
        for (Map.Entry<String, Integer> entry : localWordCountMap.entrySet()) {
            wordCountMap.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }
}
