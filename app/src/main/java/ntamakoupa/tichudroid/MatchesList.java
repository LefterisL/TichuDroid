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
import ntamakoupa.tichudroid.helper.MatchItemAdapter;
import ntamakoupa.tichudroid.model.Match;

public class MatchesList extends Activity {

    DatabaseHelper db;
    private ListView lv;
    ArrayList<Match> matches;
    MatchItemAdapter adapter;
    private int REL_SWIPE_MIN_DISTANCE;
    private int REL_SWIPE_MAX_OFF_PATH;
    private int REL_SWIPE_THRESHOLD_VELOCITY;

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        populateList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        //better to use density-aware measurements.
        DisplayMetrics dm = getResources().getDisplayMetrics();
        REL_SWIPE_MIN_DISTANCE = (int)(200.0f * dm.densityDpi / 280.0f + 0.5);
        REL_SWIPE_MAX_OFF_PATH = (int)(250.0f * dm.densityDpi / 160.0f + 0.5);
        REL_SWIPE_THRESHOLD_VELOCITY = (int)(200.0f * dm.densityDpi / 160.0f + 0.5);

        Typeface typeFacebold =Typeface.createFromAsset(getAssets(),"fonts/zonaprobold.ttf");

        ////// CUSTOM ACTION BAR //////
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.windowTitle);
        mCustomView.setBackgroundColor(getResources().getColor(R.color.grey));
        mTitleTextView.setTypeface(typeFacebold);
        mTitleTextView.setText("MATCHES");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        ImageView backUp = (ImageView) mCustomView.findViewById(R.id.backUp);
        View.OnClickListener listnm = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        backUp.setOnClickListener(listnm);
        ////// CUSTOM ACTION BAR //////

    }

    private void populateList(){
        db = new DatabaseHelper(getApplicationContext());
        matches = db.getAllMatches();
        lv = (ListView) findViewById(R.id.matches_list);
        lv.setScrollingCacheEnabled(false);
        lv.setAnimationCacheEnabled(false);
        adapter = new MatchItemAdapter(this,android.R.layout.simple_list_item_1,matches);
        lv.setAdapter(adapter);
        final GestureDetector gestureDetector = new GestureDetector(new MyGestureDetector());
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }};
        lv.setOnTouchListener(gestureListener);
        db.closeDB();
    }

    private void myOnItemClick(int position) {
        if( position >= 0 ) {
            Intent i = new Intent(getApplicationContext(), MatchProfile.class);
            i.putExtra("match_id", matches.get(position).getId());
            startActivity(i);
        }
    }

    private void onRTLFling(final int position) {
        if( position >= 0 ) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete match")
                    .setMessage("Are you sure you want to delete this match?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            db.deleteMatch(matches.get(position).getId());
                            matches.remove(position);
                            lv.invalidateViews();
                            adapter.notifyDataSetChanged();
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

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        // Detect a single-click and call my own handler.
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int pos = lv.pointToPosition((int)e.getX(), (int)e.getY());
            myOnItemClick(pos);
            return false;
        }

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
