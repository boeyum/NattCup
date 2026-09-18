package no.jib.nattcup.utillities;

import no.jib.nattcup.domain.CupData;
import no.jib.nattcup.domain.Lag;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonHandler {
    private String errorMessage = new  String();

    public JSONObject generateTurnamentJson(String fileName, CupData cd) {
        ArrayList<String> keyset = cd.getKeySet();
        JSONObject obj = new JSONObject();
        obj.put("file", fileName);
        obj.put("turnamentlenght", cd.getTurnamentLenght());
        obj.put("matchlenght", cd.getMatchLength());
        JSONArray arr = new JSONArray();
        for(int x=0;x < keyset.size(); x++) {
            JSONObject jo = new JSONObject();
            Lag curr = cd.getLag(keyset.get(x));
            jo.put("name", curr.getNavn());
            jo.put("goal", curr.getMaal());
            jo.put("mins", curr.getMinutter());
            arr.put(jo);
        }
        obj.put("teams", arr);
        return obj;
    }

    public CupData getCupDataFromJson(JSONObject json) {
        JSONArray arr = json.getJSONArray("teams");
        CupData cd = new CupData();
        cd.setFileName(json.getString("file"));
        cd.setSpilletid(json.getInt("matchlenght"));
        cd.setLengde(json.getInt("turnamentlenght"));
        for(int i = 0; i < arr.length(); i++) {
            JSONObject jo = arr.getJSONObject(i);
            Lag curr = new Lag(jo.getString("name"),jo.getInt("mins"),jo.getInt("goal"));
            cd.addLag(jo.getString("name"), curr);
        }
        return cd;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
