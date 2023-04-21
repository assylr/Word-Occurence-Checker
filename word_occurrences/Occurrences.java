
package word_occurrences;

import java.io.FileNotFoundException;
import java.util.TreeMap;
import java.util.TreeSet;

public class Occurrences {

    // Maps words -> filename -> sets of their Positions in the file.
    private final TreeMap<String, TreeMap<String, TreeSet<FilePosition>>> occMap;

    public Occurrences(String rootDirPath) throws FileNotFoundException {
        occMap = new TreeMap<>();
        FileWalker walker = new FileWalker(rootDirPath, this);
        walker.populateOccurrenceMap();
    }

    /*
        Called by FileWalker to add an occurrence to the occMap
     */
    void put(String word, String filePath, FilePosition pos) {

        // TODO: Implement me!!!

        word = word.toLowerCase();

        // 2 cases:
        // 1) word is not present in occMap - > just put new filePath and filePos of words to occMap
        //
        // 2) word is present - > another 2 cases: 1) filePath is already there -> just add pos to filePath
        //                                         2) filePath is not there -> create filePath and add pos to it

        TreeSet<FilePosition> posSet = new TreeSet<>();
        posSet.add(pos);

        if (occMap == null || !occMap.containsKey(word)) { // works if occMap is null or if the word is not present in the map

            TreeMap<String, TreeSet<FilePosition>> map = new TreeMap<>();

            map.put(filePath, posSet);

            occMap.put(word, map);

        } else { // word is already in occMap -> will just update number of occurrences of that word

            if (!occMap.get(word).containsKey(filePath)) // if filePath is not present in map
                occMap.get(word).put(filePath, posSet);
            else
                occMap.get(word).get(filePath).add(pos);
        }

    }


    /**
     * @return the number of distinct words found in the files
     */
    public int distinctWords() {

        // TODO: Implement me!!!
        return occMap.size();

    }

    /**
     * @return the number of total word occurrences across all files
     */
    public int totalOccurrences() {

        // TODO: Implement me!!!

        int totalWords = 0;

        for (String word : occMap.keySet()) {
            totalWords = totalWords + totalOccurrencesOfWord(word);
        }

        return totalWords;
    }

    /**
     * Finds the total number of occurrences of a given word across
     * all files.  If the word is not found among the occurrences,
     * simply return 0.
     *
     * @param word whose occurrences we are counting
     * @return the number of occurrences
     */
    public int totalOccurrencesOfWord(String word) {

        // TODO: Implement me!!!

        int specificWordCount = 0;

        if (occMap.containsKey(word)) {

            for (TreeSet val : occMap.get(word).values()) {
                specificWordCount = specificWordCount + val.size();
                //System.out.println("val size: " + val.size());
            }

            return specificWordCount;

        } else
            return 0;

    }

    /**
     * Finds the total number of occurrences of a given word in the given file.
     * If the file is not found in Occurrences, or if the word does not occur
     * in the file, simply return 0.
     *
     * @param word whose occurrences we are counting
     * @param filepath of the file
     * @return the number of occurrences
     */
    public int totalOccurrencesOfWordInFile(String word, String filepath) {

        // TODO: Implement me!!!

        if (!occMap.get(word).containsKey(filepath) || !occMap.containsKey(word)
        || occMap.get(word) == null || occMap.get(word).get(filepath) == null)
            return 0;

        int countWordInFile = 0;

        return occMap.get(word).get(filepath).size();

    }

    public String toString() {

        // TODO: Implement me!!!
        StringBuilder sb = new StringBuilder();

        String word = "some_word";
        String filepath = "some\\file\\path.txt";
        FilePosition pos = null;

        for (String copyWord : occMap.keySet()) {

            word = copyWord;

            sb.append("\""+ word + "\" has " + totalOccurrencesOfWord(word) +
                    " occurrence(s):\n");

            for (String copyFilePath : occMap.get(word).keySet()) {

                filepath = copyFilePath;

                for (FilePosition copyPos : occMap.get(word).get(filepath)) {

                    pos = copyPos;

                    sb.append("   ( file: \"" + filepath + "\"; " + pos + " )\n");

                }

            }


        }

        return sb.toString();
    }
}


