package ntamakoupa.tichudroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton players_list = (ImageButton) findViewById(R.id.players_list);
        players_list.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Main.this, PlayersList.class);
                startActivity(i);
            }
        });

        ImageButton new_match = (ImageButton) findViewById(R.id.new_match);
        new_match.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Main.this, NewMatch.class);
                startActivity(i);
            }
        });

        ImageButton matches_list = (ImageButton) findViewById(R.id.matches_list);
        matches_list.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Main.this, MatchesList.class);
                startActivity(i);
            }
        });

        ImageButton help = (ImageButton) findViewById(R.id.help);
        help.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Main.this, Feedback.class);
                startActivity(i);
            }
        });
    }

}
