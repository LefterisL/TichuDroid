package ntamakoupa.tichudroid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ntamakoupa.tichudroid.helper.DatabaseHelper;
import ntamakoupa.tichudroid.model.Match;
import ntamakoupa.tichudroid.model.Player;


public class MatchProfile extends Activity {

    DatabaseHelper db;
    Match match;
    Player player_1;
    Player player_2;
    Player player_3;
    Player player_4;
    Switch p1_tichu;
    Switch p2_tichu;
    Switch p3_tichu;
    Switch p4_tichu;
    Switch p1_grand;
    Switch p2_grand;
    Switch p3_grand;
    Switch p4_grand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_profile);

        Typeface typeFacethin = Typeface.createFromAsset(getAssets(),"fonts/zonaprothin.ttf");
        Typeface typeFacebold = Typeface.createFromAsset(getAssets(),"fonts/zonaprobold.ttf");

        TextView add_score = (TextView)findViewById(R.id.add_score);
        TextView scoreboard = (TextView)findViewById(R.id.scoreboard);
        TextView score_1 = (TextView)findViewById(R.id.score_1);
        TextView score_2 = (TextView)findViewById(R.id.score_2);
        TextView player_1_name = (TextView)findViewById(R.id.player_1_name);
        TextView player_2_name = (TextView)findViewById(R.id.player_2_name);
        TextView player_3_name = (TextView)findViewById(R.id.player_3_name);
        TextView player_4_name = (TextView)findViewById(R.id.player_4_name);
        TextView p1_tichu_text = (TextView)findViewById(R.id.p1_tichu_text);
        TextView p2_tichu_text = (TextView)findViewById(R.id.p2_tichu_text);
        TextView p3_tichu_text = (TextView)findViewById(R.id.p3_tichu_text);
        TextView p4_tichu_text = (TextView)findViewById(R.id.p4_tichu_text);
        TextView p1_grand_text = (TextView)findViewById(R.id.p1_grand_text);
        TextView p2_grand_text = (TextView)findViewById(R.id.p2_grand_text);
        TextView p3_grand_text = (TextView)findViewById(R.id.p3_grand_text);
        TextView p4_grand_text = (TextView)findViewById(R.id.p4_grand_text);
        ImageView player_1_avatar = (ImageView)findViewById(R.id.player_1_avatar);
        ImageView player_2_avatar = (ImageView)findViewById(R.id.player_2_avatar);
        ImageView player_3_avatar = (ImageView)findViewById(R.id.player_3_avatar);
        ImageView player_4_avatar = (ImageView)findViewById(R.id.player_4_avatar);
        p1_tichu = (Switch) findViewById(R.id.player_1_tichu);
        p2_tichu = (Switch) findViewById(R.id.player_2_tichu);
        p3_tichu = (Switch) findViewById(R.id.player_3_tichu);
        p4_tichu = (Switch) findViewById(R.id.player_4_tichu);
        p1_grand = (Switch) findViewById(R.id.player_1_grand);
        p2_grand = (Switch) findViewById(R.id.player_2_grand);
        p3_grand = (Switch) findViewById(R.id.player_3_grand);
        p4_grand = (Switch) findViewById(R.id.player_4_grand);

        add_score.setTypeface(typeFacethin);
        scoreboard.setTypeface(typeFacethin);
        score_1.setTypeface(typeFacebold);
        score_2.setTypeface(typeFacebold);
        p1_tichu_text.setTypeface(typeFacebold);
        p2_tichu_text.setTypeface(typeFacebold);
        p3_tichu_text.setTypeface(typeFacebold);
        p4_tichu_text.setTypeface(typeFacebold);
        p1_grand_text.setTypeface(typeFacebold);
        p2_grand_text.setTypeface(typeFacebold);
        p3_grand_text.setTypeface(typeFacebold);
        p4_grand_text.setTypeface(typeFacebold);
        player_1_name.setTypeface(typeFacethin);
        player_2_name.setTypeface(typeFacethin);
        player_3_name.setTypeface(typeFacethin);
        player_4_name.setTypeface(typeFacethin);


        ////// CUSTOM ACTION BAR //////
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.windowTitle);
        mCustomView.setBackgroundColor(getResources().getColor(R.color.cyan));
        mTitleTextView.setTypeface(typeFacebold);
        mTitleTextView.setText("MATCH");
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


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            db = new DatabaseHelper(getApplicationContext());
            match = db.getMatch(extras.getLong("match_id"));
            player_1 = db.getPlayer(match.getPlayer_1());
            player_2 = db.getPlayer(match.getPlayer_2());
            player_3 = db.getPlayer(match.getPlayer_3());
            player_4 = db.getPlayer(match.getPlayer_4());
            db.close();
        }

        if(match.getIs_over() == 1 ){
            add_score.setVisibility(View.GONE) ;
        }

        //Add score listener
        add_score.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //check for tichu and grand enabled
                if( (p1_tichu.isChecked() && p1_grand.isChecked()) || (p2_tichu.isChecked() && p2_grand.isChecked()) || (p3_tichu.isChecked() && p3_grand.isChecked()) || (p4_tichu.isChecked() && p4_grand.isChecked()) ){
                    Toast.makeText(getApplicationContext(), "Player cant say Tichu and Grand at the same time ", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(getApplicationContext(), NewSet.class);
                    i.putExtra("match_id", match.getId());
                    if(p1_tichu.isChecked()){ i.putExtra("player_1_t","T");}
                    if(p2_tichu.isChecked()){ i.putExtra("player_2_t","T");}
                    if(p3_tichu.isChecked()){ i.putExtra("player_3_t","T");}
                    if(p4_tichu.isChecked()){ i.putExtra("player_4_t","T");}
                    if(p1_grand.isChecked()){ i.putExtra("player_1_t","GT");}
                    if(p2_grand.isChecked()){ i.putExtra("player_2_t","GT");}
                    if(p3_grand.isChecked()){ i.putExtra("player_3_t","GT");}
                    if(p4_grand.isChecked()){ i.putExtra("player_4_t","GT");}
                    startActivityForResult(i, 1);
                }
            }
        });

        //Add score listener
        scoreboard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), SetsList.class);
                i.putExtra("match_id", match.getId());
                startActivityForResult(i, 1);
            }
        });

        //Player 1 setup
        player_1_name.setText(player_1.getName());
        player_1_avatar.setImageResource(getResources().getIdentifier("drawable/" + player_1.getPhoto(), null, getPackageName()));

        //Player 2 setup
        player_2_name.setText(player_2.getName());
        player_2_avatar.setImageResource(getResources().getIdentifier("drawable/" + player_2.getPhoto(), null, getPackageName()));

        //Player 3 setup
        player_3_name.setText(player_3.getName());
        player_3_avatar.setImageResource(getResources().getIdentifier("drawable/" + player_3.getPhoto(), null, getPackageName()));

        //Player 4 setup
        player_4_name.setText(player_4.getName());
        player_4_avatar.setImageResource(getResources().getIdentifier("drawable/" + player_4.getPhoto(), null, getPackageName()));

        //Score 1
        score_1.setText(String.valueOf(match.getScore_1()));
        //Score 2
        score_2.setText(String.valueOf(match.getScore_2()));
    }

    // Call Back method  to get the Message form other Activity    override the method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK ) {
            if (requestCode == 1) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "Update failed, please try again " , Toast.LENGTH_SHORT).show();
            }
        }
    }

}
