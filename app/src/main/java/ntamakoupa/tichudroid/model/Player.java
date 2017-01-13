package ntamakoupa.tichudroid.model;

import java.io.Serializable;

/**
 * Created by Lefteris on 14/10/2014.
 */
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    public long id;
    public String name;
    public String photo;
    public int match_w;
    public int match_l;
    public int grand_w;
    public int grand_l;
    public int tichu_w;
    public int tichu_l;

    @Override
    public String toString() {
        return name;
    }

    public Player() {}

    // setters
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setMatch_w(int match_w) {
        this.match_w = match_w;
    }

    public void setMatch_l(int match_l) {
        this.match_l = match_l;
    }

    public void setGrand_w(int grand_w) {
        this.grand_w = grand_w;
    }

    public void setGrand_l(int grand_l) {
        this.grand_l = grand_l;
    }

    public void setTichu_w(int tichu_w) {
        this.tichu_w = tichu_w;
    }

    public void setTichu_l(int tichu_l) {
        this.tichu_l = tichu_l;
    }

    // getters
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoto() {
        return this.photo;
    }

    public int getMatch_w() {
        return this.match_w;
    }

    public int getMatch_l(){
        return this.match_l;
    }

    public int getGrand_w() {
        return this.grand_w;
    }

    public int getGrand_l() {
        return this.grand_l;
    }

    public int getTichu_w() {
        return this.tichu_w;
    }

    public int getTichu_l() {
        return this.tichu_l;
    }
}
