package ntamakoupa.tichudroid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ntamakoupa.tichudroid.helper.AvatarItemAdapter;
import ntamakoupa.tichudroid.helper.DatabaseHelper;
import ntamakoupa.tichudroid.model.Player;


public class NewPlayer extends Activity {

    DatabaseHelper db;
    EditText name;
    String imgName ="";
    static final String[] avatars = {"guy", "guy2", "guy3", "girl", "girl2", "girl3", "boy", "ufo", "frank", "diamond","monster","hero"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);

        Typeface typeFacebold =Typeface.createFromAsset(getAssets(),"fonts/zonaprobold.ttf");
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
        mTitleTextView.setText("NEW PLAYER");
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

        TextView submitView =(TextView)findViewById(R.id.submit);
        TextView pickName =(TextView)findViewById(R.id.pickName);
        TextView pickAvatar =(TextView)findViewById(R.id.pickAvatar);
        submitView.setTypeface(typeFacethin);
        pickName.setTypeface(typeFacethin);
        pickAvatar.setTypeface(typeFacethin);

        GridView gv = (GridView) findViewById(R.id.avatarList);
        gv.setDrawSelectorOnTop(false);
        gv.setAdapter(new AvatarItemAdapter(this, avatars));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
                imgName = avatars[position];
                v.setSelected(true);
            }
        });


        submitView.setOnClickListener ( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (EditText) findViewById(R.id.playerName);
                if(name.getText().toString().trim().length() != 0 && imgName.length() != 0 ) {
                    Player player = new Player();
                    player.setName(name.getText().toString());
                    player.setPhoto(imgName);
                    db = new DatabaseHelper(getApplicationContext());
                    db.createPlayer(player);
                    db.closeDB();
                    Intent i = new Intent(NewPlayer.this, PlayersList.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Please fill in a name and select an avatar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
