package no.jib.nattcup.utillities;

import java.io.*;

public class TempHandler {

    public void save(String data) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter("data/temp.txt")) {
            out.println(data);
            out.flush();
        }
    }

    public String fetch() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader ("data/temp.txt"));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } finally {
            reader.close();
            File file = new File("data/temp.txt");
            file.delete();
        }
    }
}
