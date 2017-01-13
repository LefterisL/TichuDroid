package ntamakoupa.tichudroid;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ntamakoupa.tichudroid.helper.DatabaseHelper;
import ntamakoupa.tichudroid.model.Player;


public class PlayerProfile extends Activity {

    DatabaseHelper db;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        Typeface typeFacebold = Typeface.createFromAsset(getAssets(),"fonts/zonaprobold.ttf");
        Typeface typeFacethin = Typeface.createFromAsset(getAssets(),"fonts/zonaprothin.ttf");

        ////// CUSTOM ACTION BAR //////
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.windowTitle);
        mCustomView.setBackgroundColor(getResources().getColor(R.color.red));
        mTitleTextView.setTypeface(typeFacebold);
        mTitleTextView.setText("PROFILE");
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
        ////// CUSTOM ACTION BAR END//////

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            db = new DatabaseHelper(getApplicationContext());
            player = db.getPlayer(extras.getLong("player_id"));
            db.close();
        }
        TextView playerName = (TextView)findViewById(R.id.playerName);
        TextView avatarText = (TextView)findViewById(R.id.avatarText);
        TextView stats      = (TextView)findViewById(R.id.stats);
        TextView wins       = (TextView)findViewById(R.id.wins);
        TextView grand      = (TextView)findViewById(R.id.grand);
        TextView tichu      = (TextView)findViewById(R.id.tichu);
        TextView wins_txt   = (TextView)findViewById(R.id.wins_txt);
        TextView grands_txt = (TextView)findViewById(R.id.grands_txt);
        TextView tichus_txt = (TextView)findViewById(R.id.tichus_txt);
        ImageView avatarView = (ImageView)findViewById(R.id.avatarView);
        playerName.setText(player.getName());
        playerName.setTypeface(typeFacethin);
        avatarText.setTypeface(typeFacethin);
        stats.setTypeface(typeFacethin);
        wins.setTypeface(typeFacethin);
        grand.setTypeface(typeFacethin);
        tichu.setTypeface(typeFacethin);
        wins_txt.setText(player.getMatch_w() + "/" + player.getMatch_l());
        wins_txt.setTypeface(typeFacethin);
        grands_txt.setText(player.getGrand_w() + "/" + player.getGrand_l());
        grands_txt.setTypeface(typeFacethin);
        tichus_txt.setText(player.getTichu_w() + "/" + player.getTichu_l());
        tichus_txt.setTypeface(typeFacethin);
        avatarView.setImageResource(getResources().getIdentifier("drawable/" + player.getPhoto(), null, getPackageName()));
    }

}
