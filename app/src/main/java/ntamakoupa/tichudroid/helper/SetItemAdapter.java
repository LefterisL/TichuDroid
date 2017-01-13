package ntamakoupa.tichudroid.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ntamakoupa.tichudroid.R;
import ntamakoupa.tichudroid.model.Set;

/**
 * Created by Lefteris on 1/3/2015.
 */
public class SetItemAdapter extends ArrayAdapter<Set> {
    // View lookup cache
    private static class ViewHolder {
        TextView p1;
        TextView p2;
        TextView p3;
        TextView p4;
        TextView score_1;
        TextView score_2;
    }

    public SetItemAdapter(Context context, int textViewResourceId, ArrayList<Set> sets){
        super(context, textViewResourceId, sets);
    }

    Typeface typeFacebold = Typeface.createFromAsset(getContext().getAssets(),"fonts/zonaprobold.ttf");
    Typeface typeFacethin = Typeface.createFromAsset(getContext().getAssets(),"fonts/zonaprothin.ttf");
    int green = getContext().getResources().getColor(R.color.green);
    int red = getContext().getResources().getColor(R.color.red);

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        Set set = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.set_list_item, parent, false);
            viewHolder.p1 = (TextView) convertView.findViewById(R.id.p1);
            viewHolder.p2 = (TextView) convertView.findViewById(R.id.p2);
            viewHolder.p3 = (TextView) convertView.findViewById(R.id.p3);
            viewHolder.p4 = (TextView) convertView.findViewById(R.id.p4);
            viewHolder.score_1  = (TextView) convertView.findViewById(R.id.score_1);
            viewHolder.score_2  = (TextView) convertView.findViewById(R.id.score_2);
            viewHolder.p1.setTypeface(typeFacebold);
            viewHolder.p2.setTypeface(typeFacebold);
            viewHolder.p3.setTypeface(typeFacebold);
            viewHolder.p4.setTypeface(typeFacebold);
            viewHolder.score_1.setTypeface(typeFacethin);
            viewHolder.score_2.setTypeface(typeFacethin);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.score_1.setText(String.valueOf(set.getScore_1()));
        viewHolder.score_2.setText(String.valueOf(set.getScore_2()));
        viewHolder.p1.setText(set.getPlayer_1());
        if(set.getPlayer_1_result() == 1){
            viewHolder.p1.setTextColor(green);
        }else{
            viewHolder.p1.setTextColor(red);
        }
        viewHolder.p2.setText(set.getPlayer_2());
        if(set.getPlayer_2_result() == 1){
            viewHolder.p2.setTextColor(green);
        }else{
            viewHolder.p2.setTextColor(red);
        }
        viewHolder.p3.setText(set.getPlayer_3());
        if(set.getPlayer_3_result() == 1){
            viewHolder.p3.setTextColor(green);
        }else{
            viewHolder.p3.setTextColor(red);
        }
        viewHolder.p4.setText(set.getPlayer_4());
        if(set.getPlayer_4_result() == 1){
            viewHolder.p4.setTextColor(green);
        }else{
            viewHolder.p4.setTextColor(red);
        }

        return convertView;
    }
}
