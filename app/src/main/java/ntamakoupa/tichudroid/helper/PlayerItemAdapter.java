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
import ntamakoupa.tichudroid.model.Player;

/**
 * Created by Lefteris on 2/3/2015.
 */
public class PlayerItemAdapter extends ArrayAdapter<Player> {
    // View lookup cache
    private static class ViewHolder {
        TextView name;
        ImageView avatar;
    }

    public PlayerItemAdapter(Context context, int textViewResourceId, ArrayList<Player> players){
        super(context, textViewResourceId,players);
    }
    Context context = getContext();

    Typeface typeFacebold = Typeface.createFromAsset(getContext().getAssets(),"fonts/zonaprobold.ttf");

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Player player = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.player_list_item,parent,false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            viewHolder.name.setTypeface(typeFacebold);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(player.getName());
        viewHolder.avatar.setImageResource(context.getResources().getIdentifier("drawable/" + player.getPhoto(), null, context.getPackageName()));

        return convertView;
    }
}
