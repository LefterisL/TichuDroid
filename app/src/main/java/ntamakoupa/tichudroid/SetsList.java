package ntamakoupa.tichudroid;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ntamakoupa.tichudroid.helper.DatabaseHelper;
import ntamakoupa.tichudroid.helper.SetItemAdapter;
import ntamakoupa.tichudroid.model.Match;
import ntamakoupa.tichudroid.model.Player;
import ntamakoupa.tichudroid.model.Set;

public class SetsList extends Activity {

    DatabaseHelper db;
    private ListView lv;
    Match match;
    ArrayList<Set> sets;
    private int REL_SWIPE_MIN_DISTANCE;
    private int REL_SWIPE_MAX_OFF_PATH;
    private int REL_SWIPE_THRESHOLD_VELOCITY;
    TextView score_1_top;
    TextView score_2_top;
    SetItemAdapter adapter;
    Player player_1;
    Player player_2;
    Player player_3;
    Player player_4;

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent();
        setResult(RESULT_OK, mIntent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets_list);

        //better to use density-aware measurements.
        DisplayMetrics dm = getResources().getDisplayMetrics();
        REL_SWIPE_MIN_DISTANCE = (int)(170.0f * dm.densityDpi / 210.0f + 0.5);
        REL_SWIPE_MAX_OFF_PATH = (int)(250.0f * dm.densityDpi / 160.0f + 0.5);
        REL_SWIPE_THRESHOLD_VELOCITY = (int)(200.0f * dm.densityDpi / 160.0f + 0.5);

        db = new DatabaseHelper(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            match = db.getMatch(extras.getLong("match_id"));
            player_1 = db.getPlayer(match.getPlayer_1());
            player_2 = db.getPlayer(match.getPlayer_2());
            player_3 = db.getPlayer(match.getPlayer_3());
            player_4 = db.getPlayer(match.getPlayer_4());
        }
        sets = db.getAllSets(match.getId());

        Typeface typeFacebold = Typeface.createFromAsset(getAssets(),"fonts/zonaprobold.ttf");

        score_1_top = (TextView)findViewById(R.id.score_1_top);
        score_2_top = (TextView)findViewById(R.id.score_2_top);
        TextView score_txt = (TextView)findViewById(R.id.score_txt);
        TextView undo = (TextView)findViewById(R.id.undo);
        ImageView p1_image = (ImageView)findViewById(R.id.p1_image);
        ImageView p2_image = (ImageView)findViewById(R.id.p2_image);
        ImageView p3_image = (ImageView)findViewById(R.id.p3_image);
        ImageView p4_image = (ImageView)findViewById(R.id.p4_image);


        score_txt.setTypeface(typeFacebold);
        score_1_top.setTypeface(typeFacebold);
        score_2_top.setTypeface(typeFacebold);
        undo.setTypeface(typeFacebold);

        score_1_top.setText(String.valueOf(match.getScore_1()));
        score_2_top.setText(String.valueOf(match.getScore_2()));
        p1_image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("drawable/" + player_1.getPhoto(), null, getPackageName())));
        p2_image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("drawable/" + player_2.getPhoto(), null, getPackageName())));
        p3_image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("drawable/" + player_3.getPhoto(), null, getPackageName())));
        p4_image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("drawable/" + player_4.getPhoto(), null, getPackageName())));


        ////// CUSTOM ACTION BAR //////
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.windowTitle);
        mCustomView.setBackgroundColor(getResources().getColor(R.color.red));
        mTitleTextView.setTypeface(typeFacebold);
        mTitleTextView.setText("SCORES");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        ImageView backUp = (ImageView) mCustomView.findViewById(R.id.backUp);
        View.OnClickListener listnm = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        };
        backUp.setOnClickListener(listnm);
        ////// CUSTOM ACTION BAR //////

        lv = (ListView) findViewById(R.id.sets_list);
        lv.setScrollingCacheEnabled(false);
        lv.setAnimationCacheEnabled(false);
        adapter = new SetItemAdapter(this,android.R.layout.simple_list_item_1,sets);
        lv.setAdapter(adapter);
        final GestureDetector gestureDetector = new GestureDetector(new MyGestureDetector());
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }};
        lv.setOnTouchListener(gestureListener);

        db.closeDB();
    }

    public void undoAction(View v) {
        if( sets.size() >= 1) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete set")
                    .setMessage("Are you sure you want to delete the last set?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            match = db.deleteSetReturnMatch(1, true, match);
                            sets.remove(sets.get(sets.size() - 1));
                            lv.invalidateViews();
                            score_1_top.setText(String.valueOf(match.getScore_1()));
                            score_2_top.setText(String.valueOf(match.getScore_2()));
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void onRTLFling(int position) {
        match = db.deleteSetReturnMatch(sets.get(position).getId(),false,null);
        sets.remove(position);
        lv.invalidateViews();
        adapter.notifyDataSetChanged();
        score_1_top.setText(String.valueOf(match.getScore_1()));
        score_2_top.setText(String.valueOf(match.getScore_2()));
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e1.getY() - e2.getY()) > REL_SWIPE_MAX_OFF_PATH)
                return false;
            if(e1.getX() - e2.getX() > REL_SWIPE_MIN_DISTANCE &&
                    Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) {
                int pos = lv.pointToPosition((int)e1.getX(), (int)e2.getY());
                onRTLFling(pos);
            }
            return false;
        }

    }
}