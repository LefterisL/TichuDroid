package ntamakoupa.tichudroid.helper;

/**
 * Created by Lefteris on 4/3/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import ntamakoupa.tichudroid.R;

public class AvatarItemAdapter extends BaseAdapter {
    private Context context;
    private final String[] avatars;

    public AvatarItemAdapter(Context context, String[] avatars) {
        this.context = context;
        this.avatars = avatars;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);
            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.avatar_list_item, null);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView.findViewById(R.id.avatar);
            String avatar = avatars[position];
            imageView.setImageResource(context.getResources().getIdentifier("drawable/" + avatar, null, context.getPackageName()));

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return avatars.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
