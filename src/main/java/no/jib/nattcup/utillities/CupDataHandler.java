package no.jib.nattcup.utillities;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.*;

public class CupDataHandler {

    public void save(String fname, JSONObject jsonObject) throws IOException {
        FileWriter file = new FileWriter(fname);
        file.write(jsonObject.toString());
        file.close();
    }

    public JSONObject load(String fname) throws IOException {
        File f = new File(fname);
        if (f.exists()){
            InputStream is = new FileInputStream(fname);
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            JSONObject json = new JSONObject(jsonTxt);
            return json;
        }
        return null;
    }
}
