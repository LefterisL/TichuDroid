package ntamakoupa.tichudroid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ntamakoupa.tichudroid.helper.DatabaseHelper;
import ntamakoupa.tichudroid.model.Player;


public class NewMatch extends Activity {

    static final int PICK_PLAYER_REQUEST = 1;  // The request code
    DatabaseHelper db;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    TextView addp1txt;
    TextView addp2txt;
    TextView addp3txt;
    TextView addp4txt;
    boolean p1C;
    boolean p2C;
    boolean p3C;
    boolean p4C;
    ArrayList<Player> playersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);

        Typeface typeFacethin = Typeface.createFromAsset(getAssets(),"fonts/zonaprothin.ttf");
        Typeface typeFacebold = Typeface.createFromAsset(getAssets(),"fonts/zonaprobold.ttf");

        ////// CUSTOM ACTION BAR //////
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.windowTitle);
        mCustomView.setBackgroundColor(getResources().getColor(R.color.cyan));
        mTitleTextView.setTypeface(typeFacebold);
        mTitleTextView.setText("NEW MATCH");
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

        TextView start =(TextView)findViewById(R.id.start);
        TextView addPlayersText =(TextView)findViewById(R.id.addPlayersText);

        addp1txt =(TextView)findViewById(R.id.addp1txt);
        addp2txt =(TextView)findViewById(R.id.addp2txt);
        addp3txt =(TextView)findViewById(R.id.addp3txt);
        addp4txt =(TextView)findViewById(R.id.addp4txt);

        start.setTypeface(typeFacethin);
        addPlayersText.setTypeface(typeFacethin);
        addp1txt.setTypeface(typeFacebold);
        addp2txt.setTypeface(typeFacebold);
        addp3txt.setTypeface(typeFacebold);
        addp4txt.setTypeface(typeFacebold);

        db = new DatabaseHelper(getApplicationContext());
        playersList = db.getAllPlayers();
        db.closeDB();

        //Player 1 listener
        ImageButton addp1 = (ImageButton)findViewById(R.id.player1);
        addp1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), PickPlayer.class);
                if(player1 != null){
                    playersList.add(player1);
                }
                i.putExtra("playersList", playersList);
                startActivityForResult(i, 1);
            }
        });
        //Player 2 listener
        ImageButton addp2 = (ImageButton)findViewById(R.id.player2);
        addp2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), PickPlayer.class);
                if(player2 != null){
                    playersList.add(player2);
                }
                i.putExtra("playersList", playersList);
                startActivityForResult(i, 2);
            }
        });
        //Player 3 listener
        ImageButton addp3 = (ImageButton)findViewById(R.id.player3);
        addp3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), PickPlayer.class);
                if(player3 != null){
                    playersList.add(player3);
                }
                i.putExtra("playersList", playersList);
                startActivityForResult(i, 3);
            }
        });
        //Player 4 listener
        ImageButton addp4 = (ImageButton)findViewById(R.id.player4);
        addp4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), PickPlayer.class);
                if(player4 != null){
                    playersList.add(player4);
                }
                i.putExtra("playersList", playersList);
                startActivityForResult(i, 4);
            }
        });
    }

    // Call Back method  to get the Message form other Activity    override the method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK ) {
            TextView start =(TextView)findViewById(R.id.start);
            playersList = (ArrayList<Player>) data.getSerializableExtra("playersList");
            // check if the request code is same as what is passed  here it is 2
            if (requestCode == 1) {

                ImageButton p1avatar = (ImageButton) findViewById(R.id.player1);
                android.view.ViewGroup.LayoutParams params = p1avatar.getLayoutParams();
                params.height = p1avatar.getHeight();
                params.width = p1avatar.getWidth();
                p1avatar.setLayoutParams(params);
                player1 = (Player) data.getSerializableExtra("player");
                addp1txt.setText(player1.getName());
                p1avatar.setImageResource(getResources().getIdentifier("drawable/" + player1.getPhoto(), null, getPackageName()));
                p1C = true;

            }else if (requestCode == 2) {

                ImageButton p2avatar = (ImageButton) findViewById(R.id.player2);
                android.view.ViewGroup.LayoutParams params = p2avatar.getLayoutParams();
                params.height = p2avatar.getHeight();
                params.width = p2avatar.getWidth();
                p2avatar.setLayoutParams(params);
                player2 = (Player) data.getSerializableExtra("player");
                addp2txt.setText(player2.getName());
                p2avatar.setImageResource(getResources().getIdentifier("drawable/" + player2.getPhoto(), null, getPackageName()));
                p2C = true;

            }else if (requestCode == 3) {

                ImageButton p3avatar = (ImageButton) findViewById(R.id.player3);
                android.view.ViewGroup.LayoutParams params = p3avatar.getLayoutParams();
                params.height = p3avatar.getHeight();
                params.width = p3avatar.getWidth();
                p3avatar.setLayoutParams(params);
                player3 = (Player) data.getSerializableExtra("player");
                addp3txt.setText(player3.getName());
                p3avatar.setImageResource(getResources().getIdentifier("drawable/" + player3.getPhoto(), null, getPackageName()));
                p3C = true;

            }else if (requestCode == 4) {

                ImageButton p4avatar = (ImageButton) findViewById(R.id.player4);
                android.view.ViewGroup.LayoutParams params = p4avatar.getLayoutParams();
                params.height = p4avatar.getHeight();
                params.width = p4avatar.getWidth();
                p4avatar.setLayoutParams(params);
                player4 = (Player) data.getSerializableExtra("player");
                addp4txt.setText(player4.getName());
                p4avatar.setImageResource(getResources().getIdentifier("drawable/" + player4.getPhoto(), null, getPackageName()));
                p4C = true;
            }
            if(p1C && p2C && p3C && p4C)
            {
                start.setBackgroundColor(getResources().getColor(R.color.red));
                //Start Match
                start.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(getApplicationContext(), MatchProfile.class);
                        db = new DatabaseHelper(getApplicationContext());
                        long match_id = db.createMatch(player1.getId(), player2.getId(), player3.getId(), player4.getId());
                        db.closeDB();
                        i.putExtra("match_id", match_id);
                        startActivity(i);
                        finish();
                    }
                });
            }
        }
    }
}
