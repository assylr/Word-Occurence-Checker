
package word_occurrences;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


final class FileWalker {

    private final Occurrences occ;
    private final File rootDir;

    FileWalker(String rootDirPath, Occurrences occ) throws FileNotFoundException {
        this.occ = occ;
        this.rootDir = new File(rootDirPath);

        if (!this.rootDir.isDirectory()) {
            throw new FileNotFoundException(rootDirPath + " does not exist, " +
                    "or is not a directory.");
        }
    }

    void populateOccurrenceMap() {
        try {
            populateOccurrenceMap(rootDir);
        } catch (IOException ex) {
            // We should never really get to this point, but just in case...
            System.out.println(ex);
        }
    }

    private void populateOccurrenceMap(File fileOrDir) throws IOException {

        // TODO: Implement me!!!

        if (fileOrDir.isDirectory()) {

            for (File file : fileOrDir.listFiles()) {
                populateOccurrenceMap(file);
            }

        } else if (fileOrDir.isFile()) {

            BufferedReader readerFile = new BufferedReader(new FileReader(fileOrDir));

            int chrct;
            String token = "";

            int line = 1, column = 1;

            do {

                chrct = readerFile.read();

                if(Syntax.isInWord((char) chrct)) {

                    token = token + (char) chrct;
                    column++;

                } else {

                    if (token.length() > 0) {

                        FilePosition pos = new FilePosition(line, column - token.length());
                        occ.put(token, fileOrDir.getPath(), pos);
                        token = "";

                    }

                    column++;

                    if (Syntax.isNewLine((char) chrct)) {

                        line++;
                        column = 1; // newLine -> column starts at 1 again

                    }

                }

            } while (chrct != -1);

            readerFile.close();

        }
    }
}


