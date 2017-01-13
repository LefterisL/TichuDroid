package ntamakoupa.tichudroid.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ntamakoupa.tichudroid.R;
import ntamakoupa.tichudroid.model.Match;

/**
 * Created by Lefteris on 2/3/2015.
 */
public class MatchItemAdapter extends ArrayAdapter<Match> {
    // View lookup cache
    private static class ViewHolder {
        TextView score_1;
        TextView score_2;
        TextView player_1_name;
        TextView player_2_name;
        TextView player_3_name;
        TextView player_4_name;
        ImageView player_1_avatar;
        ImageView player_2_avatar;
        ImageView player_3_avatar;
        ImageView player_4_avatar;
    }

    public MatchItemAdapter(Context context, int textViewResourceId, ArrayList<Match> matches){
        super(context, textViewResourceId,matches);
    }

    Context context = getContext();

    Typeface typeFacethin = Typeface.createFromAsset(context.getAssets(),"fonts/zonaprothin.ttf");
    Typeface typeFacebold = Typeface.createFromAsset(context.getAssets(),"fonts/zonaprobold.ttf");

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Match match = getItem(position);

        ViewHolder viewHolder;

        DatabaseHelper db = new DatabaseHelper(context.getApplicationContext());

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.match_list_item, parent, false);
            viewHolder.player_1_name = (TextView) convertView.findViewById(R.id.player_1_name);
            viewHolder.player_2_name = (TextView) convertView.findViewById(R.id.player_2_name);
            viewHolder.player_3_name = (TextView) convertView.findViewById(R.id.player_3_name);
            viewHolder.player_4_name = (TextView) convertView.findViewById(R.id.player_4_name);
            viewHolder.player_1_avatar = (ImageView) convertView.findViewById(R.id.player_1_avatar);
            viewHolder.player_2_avatar = (ImageView) convertView.findViewById(R.id.player_2_avatar);
            viewHolder.player_3_avatar = (ImageView) convertView.findViewById(R.id.player_3_avatar);
            viewHolder.player_4_avatar = (ImageView) convertView.findViewById(R.id.player_4_avatar);
            viewHolder.score_1  = (TextView) convertView.findViewById(R.id.score_1);
            viewHolder.score_2  = (TextView) convertView.findViewById(R.id.score_2);
            viewHolder.player_1_name.setTypeface(typeFacethin);
            viewHolder.player_2_name.setTypeface(typeFacethin);
            viewHolder.player_3_name.setTypeface(typeFacethin);
            viewHolder.player_4_name.setTypeface(typeFacethin);
            viewHolder.score_1.setTypeface(typeFacebold);
            viewHolder.score_2.setTypeface(typeFacebold);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.player_1_name.setText(db.getPlayerName(match.getPlayer_1()));
        viewHolder.player_1_avatar.setImageResource(context.getResources().getIdentifier("drawable/" + db.getPlayerPhoto(match.getPlayer_1()), null, context.getPackageName()));

        viewHolder.player_2_name.setText(db.getPlayerName(match.getPlayer_2()));
        viewHolder.player_2_avatar.setImageResource(context.getResources().getIdentifier("drawable/" + db.getPlayerPhoto(match.getPlayer_2()), null, context.getPackageName()));

        viewHolder.player_3_name.setText(db.getPlayerName(match.getPlayer_3()));
        viewHolder.player_3_avatar.setImageResource(context.getResources().getIdentifier("drawable/" + db.getPlayerPhoto(match.getPlayer_3()), null, context.getPackageName()));

        viewHolder.player_4_name.setText(db.getPlayerName(match.getPlayer_4()));
        viewHolder.player_4_avatar.setImageResource(context.getResources().getIdentifier("drawable/" + db.getPlayerPhoto(match.getPlayer_4()), null, context.getPackageName()));

        viewHolder.score_1.setText(String.valueOf(match.getScore_1()));
        viewHolder.score_2.setText(String.valueOf(match.getScore_2()));

        db.close();

        return convertView;
    }
}
