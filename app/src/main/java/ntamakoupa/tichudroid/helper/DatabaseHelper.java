package ntamakoupa.tichudroid.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import ntamakoupa.tichudroid.model.Match;
import ntamakoupa.tichudroid.model.Player;
import ntamakoupa.tichudroid.model.Set;

/**
 * Created by Lefteris on 14/10/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "tichuDroid";

    // Table Names
    public static final String TABLE_PLAYERS = "players";
    public static final String TABLE_MATCHES = "matches";
    public static final String TABLE_SETS    = "sets";

    // Common column names
    public static final String KEY_ID = "id";

    // PLAYERS Table - column names
    public static final String KEY_NAME = "name";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_MATCH_W = "match_w";
    public static final String KEY_MATCH_L = "match_l";
    public static final String KEY_GRAND_W = "grand_w";
    public static final String KEY_GRAND_L = "grand_l";
    public static final String KEY_TICHU_W = "tichu_w";
    public static final String KEY_TICHU_L = "tichu_l";

    // MATCHES Table - column names
    public static final String KEY_PLAYER_1 = "player_1";
    public static final String KEY_PLAYER_2 = "player_2";
    public static final String KEY_PLAYER_3 = "player_3";
    public static final String KEY_PLAYER_4 = "player_4";
    public static final String KEY_SCORE_1  = "score_1";
    public static final String KEY_SCORE_2  = "score_2";
    public static final String KEY_IS_OVER  = "is_over";

    //SETS Table - column names
    public static final String KEY_MATCH_ID     = "match_id";
    public static final String KEY_PLAYER_1_R   = "player_1_r";
    public static final String KEY_PLAYER_2_R   = "player_2_r";
    public static final String KEY_PLAYER_3_R   = "player_3_r";
    public static final String KEY_PLAYER_4_R   = "player_4_r";


    // Table Create Statements
    // PLAYERS table create statement
    private static final String CREATE_TABLE_PLAYERS = "CREATE TABLE "
            + TABLE_PLAYERS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT, "
            + KEY_PHOTO + " TEXT, "
            + KEY_MATCH_W + " INTEGER default 0, "
            + KEY_MATCH_L + " INTEGER default 0, "
            + KEY_GRAND_W + " INTEGER default 0, "
            + KEY_GRAND_L + " INTEGER default 0, "
            + KEY_TICHU_W + " INTEGER default 0, "
            + KEY_TICHU_L + " INTEGER default 0 " + ")";

    //MATCHES table create statement
    private static final String CREATE_TABLE_MATCHES = "CREATE TABLE "
            + TABLE_MATCHES + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_IS_OVER + " INTEGER DEFAULT 0, "
            + KEY_PLAYER_1 + " INTEGER, "
            + KEY_PLAYER_2 + " INTEGER, "
            + KEY_PLAYER_3 + " INTEGER, "
            + KEY_PLAYER_4 + " INTEGER, "
            + KEY_SCORE_1  + " INTEGER DEFAULT 0, "
            + KEY_SCORE_2  + " INTEGER DEFAULT 0 " + ")";

    //SETS table create statement
    private static final String CREATE_TABLE_SETS = "CREATE TABLE "
            + TABLE_SETS + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_MATCH_ID + " INTEGER NOT NULL, "
            + KEY_SCORE_1 + " TEXT, "
            + KEY_SCORE_2 + " INTEGER NOT NULL, "
            + KEY_PLAYER_1 + " TEXT, "
            + KEY_PLAYER_1_R + " INTEGER DEFAULT 0, "
            + KEY_PLAYER_2 + " TEXT, "
            + KEY_PLAYER_2_R + " INTEGER DEFAULT 0, "
            + KEY_PLAYER_3 + " TEXT, "
            + KEY_PLAYER_3_R + " INTEGER DEFAULT 0, "
            + KEY_PLAYER_4 + " TEXT, "
            + KEY_PLAYER_4_R + " INTEGER DEFAULT 0, "
            + " FOREIGN KEY(match_id) REFERENCES matches(id) ON DELETE CASCADE " + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating Table Players
        db.execSQL(CREATE_TABLE_PLAYERS);

        //Creating Table Matches
        db.execSQL(CREATE_TABLE_MATCHES);

        //Creating Table Sets
        db.execSQL(CREATE_TABLE_SETS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETS);
        // create new tables
        onCreate(db);
    }


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    //////////////////////////////////////////////
    ////////// PLAYERS FUNCTIONS BELOW //////////
    //Create Player
    public long createPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName());
        values.put(KEY_PHOTO, player.getPhoto());

        // insert row
        long player_id = db.insert(TABLE_PLAYERS, null, values);
        db.close();
        return player_id;
    }

    //Update Player
    public void updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, player.getName());
        values.put(KEY_PHOTO, player.getPhoto());
        values.put(KEY_MATCH_W, player.getMatch_w());
        values.put(KEY_MATCH_L, player.getMatch_l());
        values.put(KEY_GRAND_W, player.getGrand_w());
        values.put(KEY_GRAND_L, player.getGrand_l());
        values.put(KEY_TICHU_W, player.getTichu_w());
        values.put(KEY_TICHU_L, player.getTichu_l());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TABLE_PLAYERS, values, KEY_ID + "= ?", new String[] { String.valueOf(player.getId()) });
        db.close(); // Closing database connection
    }

    //Delete player
    public void deletePlayer(long player_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" PRAGMA foreign_keys = ON ");
        db.delete(TABLE_PLAYERS, KEY_ID + " = ?",
                new String[] { String.valueOf(player_id) });
        db.delete(TABLE_MATCHES, KEY_PLAYER_1 + " = ?",
                new String[] { String.valueOf(player_id) });
        db.delete(TABLE_MATCHES, KEY_PLAYER_2 + " = ?",
                new String[] { String.valueOf(player_id) });
        db.delete(TABLE_MATCHES, KEY_PLAYER_3 + " = ?",
                new String[] { String.valueOf(player_id) });
        db.delete(TABLE_MATCHES, KEY_PLAYER_4 + " = ?",
                new String[] { String.valueOf(player_id) });
        db.close();
    }

    //Get single player
    public Player getPlayer(long player_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT "
                + KEY_ID + ","
                + KEY_NAME + ","
                + KEY_PHOTO + ","
                + KEY_MATCH_W + ","
                + KEY_MATCH_L + ","
                + KEY_GRAND_W  + ","
                + KEY_GRAND_L + ","
                + KEY_TICHU_W + ","
                + KEY_TICHU_L
                + " FROM " + TABLE_PLAYERS
                + " WHERE "
                + KEY_ID + "=?";

        Player player = new Player();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(player_id) });

        if (cursor.moveToFirst()) {
            do {
                player.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                player.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                player.setPhoto(cursor.getString(cursor.getColumnIndex(KEY_PHOTO)));
                player.setMatch_w(cursor.getInt(cursor.getColumnIndex(KEY_MATCH_W)));
                player.setMatch_l(cursor.getInt(cursor.getColumnIndex(KEY_MATCH_L)));
                player.setGrand_w(cursor.getInt(cursor.getColumnIndex(KEY_GRAND_W)));
                player.setGrand_l(cursor.getInt(cursor.getColumnIndex(KEY_GRAND_L)));
                player.setTichu_w(cursor.getInt(cursor.getColumnIndex(KEY_TICHU_W)));
                player.setTichu_l(cursor.getInt(cursor.getColumnIndex(KEY_TICHU_L)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return player;
    }

    //Get single player
    public String getPlayerName(long player_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String player_name = "";

        String selectQuery = "SELECT "
                + KEY_NAME
                + " FROM " + TABLE_PLAYERS
                + " WHERE "
                + KEY_ID + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(player_id) });

        if (cursor.moveToFirst()) {
            do {
                player_name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return player_name;
    }

    //Get single player
    public String getPlayerPhoto(long player_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String player_photo = "";

        String selectQuery = "SELECT "
                + KEY_PHOTO
                + " FROM " + TABLE_PLAYERS
                + " WHERE "
                + KEY_ID + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(player_id) });

        if (cursor.moveToFirst()) {
            do {
                player_photo = cursor.getString(cursor.getColumnIndex(KEY_PHOTO));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return player_photo;
    }

    //Get all players (array)
    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT *" +
                " FROM "
                + TABLE_PLAYERS
                + " ORDER BY "
                + KEY_NAME;


        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Player player = new Player();
                player.setId(cursor.getInt((cursor.getColumnIndex(KEY_ID))));
                player.setName((cursor.getString(cursor.getColumnIndex(KEY_NAME))));
                player.setPhoto((cursor.getString(cursor.getColumnIndex(KEY_PHOTO))));
                players.add(player);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return players;
    }

    //Get players count
    public int getPlayersCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT  * " +
                "FROM " + TABLE_PLAYERS;

        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }
    ////////// PLAYERS FUNCTIONS BELOW END//////////
    ///////////////////////////////////////////////


    /////////////////////////////////////////////
    ////////// MATCHES FUNCTIONS BELOW //////////
    //Create Match
    public long createMatch(long player_1,long player_2, long player_3, long player_4){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLAYER_1, player_1);
        values.put(KEY_PLAYER_2, player_2);
        values.put(KEY_PLAYER_3, player_3);
        values.put(KEY_PLAYER_4, player_4);

        // insert row
        long match_id = db.insert(TABLE_MATCHES, null, values);
        db.close();
        return match_id;
    }

    //Update Match
    public void updateMatch(Match match){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_PLAYER_1,match.getPlayer_1());
        values.put(KEY_PLAYER_2,match.getPlayer_2());
        values.put(KEY_PLAYER_3,match.getPlayer_3());
        values.put(KEY_PLAYER_4,match.getPlayer_4());
        values.put(KEY_SCORE_1,match.getScore_1());
        values.put(KEY_SCORE_2,match.getScore_2());
        values.put(KEY_IS_OVER,match.getIs_over());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TABLE_MATCHES, values, KEY_ID + "= ?", new String[] { String.valueOf(match.getId()) });
        db.close(); // Closing database connection
    }

    //Delete Match
    public void deleteMatch(long match_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" PRAGMA foreign_keys = ON ");
        db.delete(TABLE_MATCHES, KEY_ID + " = ?",
                new String[] { String.valueOf(match_id) });
        /**
        db.delete(TABLE_SETS, KEY_MATCH_ID + " = ?",
                new String[] { String.valueOf(match_id) });
         **/
        db.close();
    }

    //Get single match
    public Match getMatch(long match_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT "
                + KEY_ID + ","
                + KEY_PLAYER_1 + ","
                + KEY_PLAYER_2 + ","
                + KEY_PLAYER_3 + ","
                + KEY_PLAYER_4 + ","
                + KEY_SCORE_1  + ","
                + KEY_SCORE_2  + ","
                + KEY_IS_OVER
                + " FROM " + TABLE_MATCHES
                + " WHERE "
                + KEY_ID + "=?";

        Match match = new Match();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(match_id) } );

        if (cursor.moveToFirst()) {
            do{
                match.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                match.setPlayer_1(cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_1)));
                match.setPlayer_2(cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_2)));
                match.setPlayer_3(cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_3)));
                match.setPlayer_4(cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_4)));
                match.setScore_1(cursor.getInt(cursor.getColumnIndex(KEY_SCORE_1)));
                match.setScore_2(cursor.getInt(cursor.getColumnIndex(KEY_SCORE_2)));
                match.setIs_over(cursor.getInt(cursor.getColumnIndex(KEY_IS_OVER)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return match;
    }

    //Get all matches
    public ArrayList<Match> getAllMatches(){
        ArrayList<Match> matches = new ArrayList<Match>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * "
                + " FROM "
                + TABLE_MATCHES
                + " ORDER BY "
                + KEY_ID + " DESC ";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if ( cursor.moveToFirst()) {
            do {
                Match match = new Match();
                match.setId(cursor.getInt((cursor.getColumnIndex(KEY_ID))));
                match.setPlayer_1((cursor.getInt((cursor.getColumnIndex(KEY_PLAYER_1)))));
                match.setPlayer_2((cursor.getInt((cursor.getColumnIndex(KEY_PLAYER_2)))));
                match.setPlayer_3((cursor.getInt((cursor.getColumnIndex(KEY_PLAYER_3)))));
                match.setPlayer_4((cursor.getInt((cursor.getColumnIndex(KEY_PLAYER_4)))));
                match.setScore_1((cursor.getInt((cursor.getColumnIndex(KEY_SCORE_1)))));
                match.setScore_2((cursor.getInt((cursor.getColumnIndex(KEY_SCORE_2)))));
                match.setIs_over(cursor.getInt(cursor.getColumnIndex(KEY_IS_OVER)));
                matches.add(match);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return matches;
    }

    //Count matches
    public int getMatchesCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * "
                + " FROM " + TABLE_MATCHES;

        Cursor cursor = db.rawQuery(selectQuery,null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    ////////// MATCHES FUNCTIONS BELOW END //////////
    /////////////////////////////////////////////////

    /////////////////////////////////////////////////
    ////////// SETS FUNCTIONS BELOW /////////////////
    //Create Set
    public long createSet(long match_id,int score_1,int score_2, String p1,int p1_r, String p2,int p2_r, String p3,int p3_r, String p4,int p4_r){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MATCH_ID, match_id);
        values.put(KEY_SCORE_1, score_1);
        values.put(KEY_SCORE_2, score_2);
        values.put(KEY_PLAYER_1, p1);
        values.put(KEY_PLAYER_1_R, p1_r);
        values.put(KEY_PLAYER_2, p2);
        values.put(KEY_PLAYER_2_R, p2_r);
        values.put(KEY_PLAYER_3, p3);
        values.put(KEY_PLAYER_3_R, p3_r);
        values.put(KEY_PLAYER_4, p4);
        values.put(KEY_PLAYER_4_R, p4_r);

        // insert row
        long set_id = db.insert(TABLE_SETS, null, values);
        db.close();
        return set_id;
    }

    //Get single set
    public Set getSet(long set_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT "
                + KEY_ID + ","
                + KEY_MATCH_ID + ","
                + KEY_PLAYER_1 + ","
                + KEY_PLAYER_2 + ","
                + KEY_PLAYER_3 + ","
                + KEY_PLAYER_4 + ","
                + KEY_PLAYER_1_R + ","
                + KEY_PLAYER_2_R + ","
                + KEY_PLAYER_3_R + ","
                + KEY_PLAYER_4_R + ","
                + KEY_SCORE_1  + ","
                + KEY_SCORE_2
                + " FROM " + TABLE_SETS
                + " WHERE "
                + KEY_ID + "=?";

        Set set = new Set();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(set_id) } );

        if (cursor.moveToFirst()) {
            do{
                set.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                set.setMatch_id(cursor.getInt(cursor.getColumnIndex((KEY_MATCH_ID))));
                set.setPlayer_1(cursor.getString(cursor.getColumnIndex(KEY_PLAYER_1)));
                set.setPlayer_2(cursor.getString(cursor.getColumnIndex(KEY_PLAYER_2)));
                set.setPlayer_3(cursor.getString(cursor.getColumnIndex(KEY_PLAYER_3)));
                set.setPlayer_4(cursor.getString(cursor.getColumnIndex(KEY_PLAYER_4)));
                set.setPlayer_1_result(cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_1_R)));
                set.setPlayer_2_result(cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_2_R)));
                set.setPlayer_3_result(cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_3_R)));
                set.setPlayer_4_result(cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_4_R)));
                set.setScore_1(cursor.getInt(cursor.getColumnIndex(KEY_SCORE_1)));
                set.setScore_2(cursor.getInt(cursor.getColumnIndex(KEY_SCORE_2)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return set;
    }

    //Get last set
    public Set getLastSet(long match_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT "
                + KEY_ID + ","
                + KEY_MATCH_ID + ","
                + KEY_PLAYER_1 + ","
                + KEY_PLAYER_2 + ","
                + KEY_PLAYER_3 + ","
                + KEY_PLAYER_4 + ","
                + KEY_PLAYER_1_R + ","
                + KEY_PLAYER_2_R + ","
                + KEY_PLAYER_3_R + ","
                + KEY_PLAYER_4_R + ","
                + KEY_SCORE_1  + ","
                + KEY_SCORE_2
                + " FROM " + TABLE_SETS
                + " WHERE "
                + KEY_MATCH_ID + "=?";

        Set set = new Set();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(match_id) } );

        if (cursor.moveToFirst()) {
            do{
                set.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                set.setMatch_id(cursor.getInt(cursor.getColumnIndex((KEY_MATCH_ID))));
                set.setPlayer_1(cursor.getString(cursor.getColumnIndex(KEY_PLAYER_1)));
                set.setPlayer_2(cursor.getString(cursor.getColumnIndex(KEY_PLAYER_2)));
                set.setPlayer_3(cursor.getString(cursor.getColumnIndex(KEY_PLAYER_3)));
                set.setPlayer_4(cursor.getString(cursor.getColumnIndex(KEY_PLAYER_4)));
                set.setPlayer_1_result(cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_1_R)));
                set.setPlayer_2_result(cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_2_R)));
                set.setPlayer_3_result(cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_3_R)));
                set.setPlayer_4_result(cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_4_R)));
                set.setScore_1(cursor.getInt(cursor.getColumnIndex(KEY_SCORE_1)));
                set.setScore_2(cursor.getInt(cursor.getColumnIndex(KEY_SCORE_2)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return set;
    }

    //Delete Set
    public Match deleteSetReturnMatch(long set_id, boolean undo, Match match){
        Set set;
        if(undo){
            set = this.getLastSet(match.getId());
        }else {
            set = this.getSet(set_id);
            match = this.getMatch(set.getMatch_id());
        }
        Player player_1 = this.getPlayer(match.getPlayer_1());
        Player player_2 = this.getPlayer(match.getPlayer_2());
        Player player_3 = this.getPlayer(match.getPlayer_3());
        Player player_4 = this.getPlayer(match.getPlayer_4());


        match.setScore_1(match.getScore_1() - set.getScore_1());
        match.setScore_2(match.getScore_2() - set.getScore_2());
        if(match.getScore_1() < 1000 && match.getScore_2() < 1000 && match.getScore_1() != match.getScore_2()){
            if(match.getScore_1() > match.getScore_2() && match.getIs_over() == 1){
                player_1.setMatch_w(player_1.getMatch_w()-1);
                player_2.setMatch_w(player_2.getMatch_w()-1);
                player_3.setMatch_l(player_3.getMatch_l()-1);
                player_4.setMatch_l(player_4.getMatch_l()-1);
            }

            if(match.getScore_2() > match.getScore_1() && match.getIs_over() == 1){
                player_1.setMatch_l(player_1.getMatch_l()-1);
                player_2.setMatch_l(player_2.getMatch_l()-1);
                player_3.setMatch_w(player_3.getMatch_w()-1);
                player_4.setMatch_w(player_4.getMatch_w()-1);
            }
            match.setIs_over(0);
        }
        this.updateMatch(match);

        if(set.getPlayer_1().equals("T")){
            if(set.getPlayer_1_result() == 1){
                player_1.setTichu_w(player_1.getTichu_w()-1);
            }else{
                player_1.setTichu_l(player_1.getTichu_l()-1);
            }
        }else if(set.getPlayer_1().equals("GT")){
            if(set.getPlayer_1_result() == 1){
                player_1.setGrand_w(player_1.getGrand_w()-1);
            }else{
                player_1.setGrand_l(player_1.getGrand_l()-1);
            }
        }

        if(set.getPlayer_2().equals("T")){
            if(set.getPlayer_2_result() == 1){
                player_2.setTichu_w(player_2.getTichu_w()-1);
            }else{
                player_2.setTichu_l(player_2.getTichu_l()-1);
            }
        }else if(set.getPlayer_2().equals("GT")){
            if(set.getPlayer_2_result() == 1){
                player_2.setGrand_w(player_2.getGrand_w()-1);
            }else{
                player_2.setGrand_l(player_2.getGrand_l()-1);
            }
        }

        if(set.getPlayer_3().equals("T")){
            if(set.getPlayer_3_result() == 1){
                player_3.setTichu_w(player_3.getTichu_w()-1);
            }else{
                player_3.setTichu_l(player_3.getTichu_l()-1);
            }
        }else if(set.getPlayer_3().equals("GT")){
            if(set.getPlayer_3_result() == 1){
                player_3.setGrand_w(player_3.getGrand_w()-1);
            }else{
                player_3.setGrand_l(player_3.getGrand_l()-1);
            }
        }

        if(set.getPlayer_4().equals("T")){
            if(set.getPlayer_4_result() == 1){
                player_4.setTichu_w(player_4.getTichu_w()-1);
            }else{
                player_4.setTichu_l(player_4.getTichu_l()-1);
            }
        }else if(set.getPlayer_4().equals("GT")){
            if(set.getPlayer_2_result() == 1){
                player_4.setGrand_w(player_4.getGrand_w()-1);
            }else{
                player_4.setGrand_l(player_4.getGrand_l()-1);
            }
        }

        this.updatePlayer(player_1);
        this.updatePlayer(player_2);
        this.updatePlayer(player_3);
        this.updatePlayer(player_4);

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SETS, KEY_ID + " = ?",
                new String[] { String.valueOf(set.getId()) });
        db.close();

        return match;
    }

    //Get all sets by id
    public ArrayList<Set> getAllSets(long match_id){
        ArrayList<Set> sets = new ArrayList<Set>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * " +
                " FROM " + TABLE_SETS
                + " WHERE "
                + KEY_MATCH_ID + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(match_id) });
        if ( cursor.moveToFirst()) {
            do {
                Set set = new Set();
                set.setId(cursor.getInt((cursor.getColumnIndex(KEY_ID))));
                set.setScore_1(cursor.getInt((cursor.getColumnIndex(KEY_SCORE_1))));
                set.setScore_2(cursor.getInt((cursor.getColumnIndex(KEY_SCORE_2))));
                set.setPlayer_1(cursor.getString((cursor.getColumnIndex(KEY_PLAYER_1))));
                set.setPlayer_1_result(cursor.getInt((cursor.getColumnIndex(KEY_PLAYER_1_R))));
                set.setPlayer_2(cursor.getString((cursor.getColumnIndex(KEY_PLAYER_2))));
                set.setPlayer_2_result(cursor.getInt((cursor.getColumnIndex(KEY_PLAYER_2_R))));
                set.setPlayer_3(cursor.getString((cursor.getColumnIndex(KEY_PLAYER_3))));
                set.setPlayer_3_result(cursor.getInt((cursor.getColumnIndex(KEY_PLAYER_3_R))));
                set.setPlayer_4(cursor.getString((cursor.getColumnIndex(KEY_PLAYER_4))));
                set.setPlayer_4_result(cursor.getInt((cursor.getColumnIndex(KEY_PLAYER_4_R))));
                sets.add(set);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return sets;
    }
    ////////// SETS FUNCTIONS BELOW END /////////////
    /////////////////////////////////////////////////
}