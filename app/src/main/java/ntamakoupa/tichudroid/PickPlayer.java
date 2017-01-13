package ntamakoupa.tichudroid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ntamakoupa.tichudroid.helper.PlayerItemAdapter;
import ntamakoupa.tichudroid.model.Player;


public class PickPlayer extends Activity {

    private ListView lv;
    Player player;
    PlayerItemAdapter adapter;

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent();
        setResult(RESULT_CANCELED, mIntent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        populateList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_player);

        Typeface typeFacebold =Typeface.createFromAsset(getAssets(),"fonts/zonaprobold.ttf");

        ////// CUSTOM ACTION BAR //////
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.windowTitle);
        mCustomView.setBackgroundColor(getResources().getColor(R.color.red));
        mTitleTextView.setTypeface(typeFacebold);
        mTitleTextView.setText("PICK PLAYER");
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

    public void populateList(){
        lv = (ListView) findViewById(R.id.playersList);
        lv.setScrollingCacheEnabled(false);
        lv.setAnimationCacheEnabled(false);
        final ArrayList<Player> playersList = (ArrayList<Player>)getIntent().getSerializableExtra("playersList");
        adapter = new PlayerItemAdapter(this,android.R.layout.simple_list_item_1,playersList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                player = playersList.get(position);
                playersList.remove(position);
                Intent intentMessage = new Intent();
                intentMessage.putExtra("player", player);
                intentMessage.putExtra("playersList", playersList);
                setResult(RESULT_OK, intentMessage);
                finish();
            }
        });
    }

}
