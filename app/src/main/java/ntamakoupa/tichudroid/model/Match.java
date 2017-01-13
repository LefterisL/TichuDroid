package ntamakoupa.tichudroid.model;

import java.io.Serializable;

/**
 * Created by Lefteris on 9/2/2015.
 */
public class Match implements Serializable {

    public long id;
    public int player_1;
    public int player_2;
    public int player_3;
    public int player_4;
    public int score_1;
    public int score_2;
    public int is_over;

    public Match() {
    }

    //setters
    public void setId(long id){
        this.id = id;
    }

    public void setIs_over(int is_over){
        this.is_over = is_over;
    }

    public void setPlayer_1(int player_1){
        this.player_1 = player_1;
    }

    public void setPlayer_2(int player_2){
        this.player_2 = player_2;
    }

    public void setPlayer_3(int player_3){
        this.player_3 = player_3;
    }

    public void setPlayer_4(int player_4){
        this.player_4 = player_4;
    }

    public void setScore_1(int score_1){
        this.score_1 = score_1;
    }

    public void setScore_2(int score_2){
        this.score_2 = score_2;
    }

    //GETTERS

    public long getId(){
        return this.id;
    }

    public int getIs_over(){
        return this.is_over;
    }

    public int getPlayer_1(){
        return this.player_1;
    }

    public int getPlayer_2(){
        return this.player_2;
    }

    public int getPlayer_3(){
        return this.player_3;
    }

    public int getPlayer_4(){
        return this.player_4;
    }

    public int getScore_1(){
        return this.score_1;
    }

    public int getScore_2(){
        return this.score_2;
    }
}
