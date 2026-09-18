package no.jib.nattcup.utillities;

import java.io.*;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class TextHandler {
    private String fname = new String();
    private String create = new String();
    private String open = new String();
    private String save = new String();
    private String exit = new String();
    private String sname = new String();
    private String team = new String();
    private String time = new String();
    private String rules = new String();
    private String manual = new String();
    private String top = new String();
    private String center = new String();
    private String bottom = new String();
    private String start = new String();
    private String pause = new String();
    private String hgoal = new String();
    private String bgoal = new String();
    private String initier = new String();
    private String backup = new String();
    private String msg1_head = new String();
    private String msg1 = new String();
    private String msg2_head = new String();
    private String msg2 = new String();
    private String teamname = new String();
    private String teamsave = new String();
    private String teamcancel = new String();
    private String timematch = new String();
    private String timeturnament = new String();
    private String timesave = new String();
    private String timecancel = new String();
    private String spiltid = new String();
    private String plussmal = new String();
    private String valg = new String();
    private String nodsave = new String();
    private String nodcancel = new String();
    private String nextpage = new String();
    private String priorpage = new String();


    public TextHandler() throws IOException {
        File f = new File("config/textconfig.json");
        if (f.exists()) {
            InputStream is = new FileInputStream("config/textconfig.json");
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            JSONObject json = new JSONObject(jsonTxt);
            JSONArray arr = json.getJSONArray("setup");
            for (int i = 0; i < arr.length(); i++) {
                if(arr.getJSONObject(i).getString("id").equals("file")) {
                    fname = arr.getJSONObject(i).getString("name");
                    create = arr.getJSONObject(i).getString("create");
                    open = arr.getJSONObject(i).getString("open");
                    save = arr.getJSONObject(i).getString("save");
                    exit = arr.getJSONObject(i).getString("exit");
                } else if(arr.getJSONObject(i).getString("id").equals("setup")) {
                    sname = arr.getJSONObject(i).getString("name");
                    team = arr.getJSONObject(i).getString("team");
                    time = arr.getJSONObject(i).getString("time");
                    rules = arr.getJSONObject(i).getString("rules");
                    manual = arr.getJSONObject(i).getString("manual");
                } else if(arr.getJSONObject(i).getString("id").equals("headings")) {
                    top = arr.getJSONObject(i).getString("top");
                    center= arr.getJSONObject(i).getString("center");
                    bottom = arr.getJSONObject(i).getString("bottom");
                } else if(arr.getJSONObject(i).getString("id").equals("buttons")) {
                    start = arr.getJSONObject(i).getString("start");
                    pause = arr.getJSONObject(i).getString("pause");
                    hgoal = arr.getJSONObject(i).getString("homegoal");
                    bgoal = arr.getJSONObject(i).getString("awaygoal");
                    initier = arr.getJSONObject(i).getString("init");
                    backup = arr.getJSONObject(i).getString("backup");
                } else if(arr.getJSONObject(i).getString("id").equals("messages")) {
                    msg1_head = arr.getJSONObject(i).getString("close");
                    msg1 = arr.getJSONObject(i).getString("closeMsg");
                    msg2_head = arr.getJSONObject(i).getString("lagre");
                    msg2 = arr.getJSONObject(i).getString("lagreMsg");
                } else if(arr.getJSONObject(i).getString("id").equals("teamform")) {
                    teamname = arr.getJSONObject(i).getString("name");
                    teamsave = arr.getJSONObject(i).getString("save");
                    teamcancel = arr.getJSONObject(i).getString("cancel");
                } else if(arr.getJSONObject(i).getString("id").equals("timeform")) {
                    timematch = arr.getJSONObject(i).getString("playtime");
                    timeturnament = arr.getJSONObject(i).getString("time");
                    timesave = arr.getJSONObject(i).getString("save");
                    timecancel = arr.getJSONObject(i).getString("cancel");
                } else if(arr.getJSONObject(i).getString("id").equals("emergency")) {
                    spiltid = arr.getJSONObject(i).getString("time");
                    plussmal = arr.getJSONObject(i).getString("goal");
                    valg = arr.getJSONObject(i).getString("select");
                    nodsave = arr.getJSONObject(i).getString("save");
                    nodcancel = arr.getJSONObject(i).getString("cancel");
                } else if(arr.getJSONObject(i).getString("id").equals("pdfform")) {
                    nextpage = arr.getJSONObject(i).getString("next");
                    priorpage = arr.getJSONObject(i).getString("prior");
                }
            }
        } else {
            throw new FileNotFoundException("Config file not found");
        }
    }

    public String getFname() {
        return fname;
    }

    public String getCreate() {
        return create;
    }

    public String getOpen() {
        return open;
    }

    public String getSave() {
        return save;
    }

    public String getExit() {
        return exit;
    }

    public String getSname() {
        return sname;
    }

    public String getTeam() {
        return team;
    }

    public String getTime() {
        return time;
    }

    public String getRules() {
        return rules;
    }

    public String getManual() {
        return manual;
    }

    public String getTop() {
        return top;
    }

    public String getCenter() {
        return center;
    }

    public String getBottom() {
        return bottom;
    }

    public String getStart() {
        return start;
    }

    public String getPause() {
        return pause;
    }

    public String getHomeGoal() {
        return hgoal;
    }

    public String getAwayGoal() {
        return bgoal;
    }

    public String getInit() {
        return initier;
    }

    public String getBackup() {
        return backup;
    }

    public String getMsg1_head() {
        return msg1_head;
    }

    public String getMsg1() {
        return msg1;
    }

    public String getMsg2_head() {
        return msg2_head;
    }

    public String getMsg2() {
        return msg2;
    }

    public String getTeamName() {
        return teamname;
    }

    public String getTeamSave() {
        return teamsave;
    }

    public String getTeamCancel() {
        return teamcancel;
    }

    public String getTimeMatch() {
        return timematch;
    }

    public String getTimeTurnament() {
        return timeturnament;
    }

    public String getTimeSave() {
        return timesave;
    }

    public String getTimeCancel() {
        return timecancel;
    }

    public String getSpiltTid() {
        return spiltid;
    }

    public String getPlussMaal() {
        return plussmal;
    }

    public String getValg() {
        return valg;
    }

    public String getNodSave() {
        return nodsave;
    }

    public String getNodCancel() {
        return nodcancel;
    }

    public String getNextPage() {
        return nextpage;
    }

    public String getPriorPage() {
        return priorpage;
    }
}
