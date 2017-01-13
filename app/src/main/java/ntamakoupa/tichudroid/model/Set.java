package ntamakoupa.tichudroid.model;

/**
 * Created by Lefteris on 22/2/2015.
 */
public class Set {

    public long id;
    public int match_id;
    public int score_1;
    public int score_2;
    public String player_1;
    public String player_2;
    public String player_3;
    public String player_4;
    public int player_1_result;
    public int player_2_result;
    public int player_3_result;
    public int player_4_result;

    public Set() {
    }

    public void setId(long id){
        this.id = id;
    }

    public void setMatch_id(int match_id) { this.match_id = match_id; }

    public void setScore_1(int score_1){
        this.score_1 = score_1;
    }

    public void setScore_2(int score_2){
        this.score_2 = score_2;
    }

    public void setPlayer_1(String player_1) { this.player_1 = player_1; }

    public void setPlayer_2(String player_2) { this.player_2 = player_2; }

    public void setPlayer_3(String player_3) { this.player_3 = player_3; }

    public void setPlayer_4(String player_4) { this.player_4 = player_4; }

    public void setPlayer_1_result(int player_1_result) { this.player_1_result = player_1_result; }

    public void setPlayer_2_result(int player_2_result) { this.player_2_result = player_2_result; }

    public void setPlayer_3_result(int player_3_result) { this.player_3_result = player_3_result; }

    public void setPlayer_4_result(int player_4_result) { this.player_4_result = player_4_result; }

    public long getId(){ return this.id; }

    public int getMatch_id() { return this.match_id; }

    public int getScore_1(){ return this.score_1; }

    public int getScore_2(){ return this.score_2; }

    public String getPlayer_1() { return this.player_1; }

    public String getPlayer_2() { return this.player_2; }

    public String getPlayer_3() { return this.player_3; }

    public String getPlayer_4() { return this.player_4; }

    public int getPlayer_1_result() { return this.player_1_result; }

    public int getPlayer_2_result() { return this.player_2_result; }

    public int getPlayer_3_result() { return this.player_3_result; }

    public int getPlayer_4_result() { return this.player_4_result; }

}
