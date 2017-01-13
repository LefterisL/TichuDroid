package ntamakoupa.tichudroid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;

import ntamakoupa.tichudroid.helper.DatabaseHelper;
import ntamakoupa.tichudroid.model.Match;
import ntamakoupa.tichudroid.model.Player;


public class NewSet extends Activity {

    Match match;
    DatabaseHelper db;
    Player player_1;
    Player player_2;
    Player player_3;
    Player player_4;
    String player_1_t = "";
    String player_2_t = "";
    String player_3_t = "";
    String player_4_t = "";
    int player_1_result;
    int player_2_result;
    int player_3_result;
    int player_4_result;

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent();
        setResult(RESULT_CANCELED, mIntent);
        finish();
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_set);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            db = new DatabaseHelper(getApplicationContext());
            match = db.getMatch(extras.getLong("match_id"));
            player_1 = db.getPlayer(match.getPlayer_1());
            player_2 = db.getPlayer(match.getPlayer_2());
            player_3 = db.getPlayer(match.getPlayer_3());
            player_4 = db.getPlayer(match.getPlayer_4());
            db.close();
            if (extras.getString("player_1_t") != null) {
                player_1_t = extras.getString("player_1_t");
            }
            if (extras.getString("player_2_t") != null) {
                player_2_t = extras.getString("player_2_t");
            }
            if (extras.getString("player_3_t") != null) {
                player_3_t = extras.getString("player_3_t");
            }
            if (extras.getString("player_4_t") != null) {
                player_4_t = extras.getString("player_4_t");
            }
        }

        final Typeface typeFacethin = Typeface.createFromAsset(getAssets(),"fonts/zonaprothin.ttf");
        final Typeface typeFacebold = Typeface.createFromAsset(getAssets(),"fonts/zonaprobold.ttf");
        final TextView first_out_txt = (TextView)findViewById(R.id.first_out_txt);

        TextView player_1_name = (TextView)findViewById(R.id.player_1_name);
        TextView player_2_name = (TextView)findViewById(R.id.player_2_name);
        TextView player_3_name = (TextView)findViewById(R.id.player_3_name);
        TextView player_4_name = (TextView)findViewById(R.id.player_4_name);
        ImageView player_1_avatar = (ImageView)findViewById(R.id.player_1_avatar);
        ImageView player_2_avatar = (ImageView)findViewById(R.id.player_2_avatar);
        ImageView player_3_avatar = (ImageView)findViewById(R.id.player_3_avatar);
        ImageView player_4_avatar = (ImageView)findViewById(R.id.player_4_avatar);
        TextView score_1  = (TextView)findViewById(R.id.score_1);
        TextView score_2  = (TextView)findViewById(R.id.score_2);
        final NumberPicker np_1 = (NumberPicker)findViewById(R.id.team_1_score);
        final NumberPicker np_2 = (NumberPicker)findViewById(R.id.team_2_score);
        final NumberPicker first_out = (NumberPicker)findViewById(R.id.first_out);
        TextView save =(TextView)findViewById(R.id.save);


        final String[] nums = new String[32];
        for(int i=0; i<nums.length-1; i++){
            nums[i] = Integer.toString(i*5-25);
        }
        nums[31] = Integer.toString(200);

        final String[] names = new String[] {
                player_1.getName(),
                player_2.getName(),
                player_3.getName(),
                player_4.getName()
        };

        final long[] ids = new long[] {
                player_1.getId(),
                player_2.getId(),
                player_3.getId(),
                player_4.getId()
        };


        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                int score_1 = Integer.parseInt(nums[np_1.getValue()]);
                int score_2 = Integer.parseInt(nums[np_2.getValue()]);
                int player_o = first_out.getValue() + 1;

                if(player_o == 1){
                    player_1_result = 1;
                    player_2_result = 0;
                    player_3_result = 0;
                    player_4_result = 0;
                    if(player_1_t != null && player_1_t.equals("T"))   { score_1 += 100; player_1.setTichu_w(player_1.getTichu_w() + 1); }
                    if(player_1_t != null && player_1_t.equals("GT"))  { score_1 += 200; player_1.setGrand_w(player_1.getGrand_w() + 1); }
                    if(player_2_t != null && player_2_t.equals("T"))   { score_1 -= 100; player_2.setTichu_l(player_2.getTichu_l()-1); }
                    if(player_2_t != null && player_2_t.equals("GT"))  { score_1 -= 200; player_2.setGrand_l(player_2.getGrand_l()-1); }
                    if(player_3_t != null && player_3_t.equals("T"))   { score_2 -= 100; player_3.setTichu_l(player_3.getTichu_l()-1); }
                    if(player_3_t != null && player_3_t.equals("GT"))  { score_2 -= 200; player_3.setGrand_l(player_3.getGrand_l()-1); }
                    if(player_4_t != null && player_4_t.equals("T"))   { score_2 -= 100; player_4.setTichu_l(player_4.getTichu_l() - 1); }
                    if(player_4_t != null && player_4_t.equals("GT"))  { score_2 -= 200; player_4.setGrand_l(player_4.getGrand_l() - 1); }
                }else if(player_o == 2){
                    player_1_result = 0;
                    player_2_result = 1;
                    player_3_result = 0;
                    player_4_result = 0;
                    if(player_1_t != null && player_1_t.equals("T"))   { score_1 -= 100; player_1.setTichu_l(player_1.getTichu_l()+1); }
                    if(player_1_t != null && player_1_t.equals("GT"))  { score_1 -= 200; player_1.setGrand_l(player_1.getGrand_l()+1); }
                    if(player_2_t != null && player_2_t.equals("T"))   { score_1 += 100; player_2.setTichu_w(player_2.getTichu_w()+1); }
                    if(player_2_t != null && player_2_t.equals("GT"))  { score_1 += 200; player_2.setGrand_w(player_2.getGrand_w()+1); }
                    if(player_3_t != null && player_3_t.equals("T"))   { score_2 -= 100; player_3.setTichu_l(player_3.getTichu_l()-1); }
                    if(player_3_t != null && player_3_t.equals("GT"))  { score_2 -= 200; player_3.setGrand_l(player_3.getGrand_l()-1); }
                    if(player_4_t != null && player_4_t.equals("T"))   { score_2 -= 100; player_4.setTichu_l(player_4.getTichu_l()-1); }
                    if(player_4_t != null && player_4_t.equals("GT"))  { score_2 -= 200; player_4.setGrand_l(player_4.getGrand_l()-1); }
                }else if(player_o == 3){
                    player_1_result = 0;
                    player_2_result = 0;
                    player_3_result = 3;
                    player_4_result = 0;
                    if(player_1_t != null && player_1_t.equals("T"))   { score_1 -= 100; player_1.setTichu_l(player_1.getTichu_l()+1); }
                    if(player_1_t != null && player_1_t.equals("GT"))  { score_1 -= 200; player_1.setGrand_l(player_1.getGrand_l()+1); }
                    if(player_2_t != null && player_2_t.equals("T"))   { score_1 -= 100; player_2.setTichu_l(player_2.getTichu_l()-1); }
                    if(player_2_t != null && player_2_t.equals("GT"))  { score_1 -= 200; player_2.setGrand_l(player_2.getGrand_l()-1); }
                    if(player_3_t != null && player_3_t.equals("T"))   { score_2 += 100; player_3.setTichu_w(player_3.getTichu_w()+1); }
                    if(player_3_t != null && player_3_t.equals("GT"))  { score_2 += 200; player_3.setGrand_w(player_3.getGrand_w()+1); }
                    if(player_4_t != null && player_4_t.equals("T"))   { score_2 -= 100; player_4.setTichu_l(player_4.getTichu_l()-1); }
                    if(player_4_t != null && player_4_t.equals("GT"))  { score_2 -= 200; player_4.setGrand_l(player_4.getGrand_l()-1); }
                }else if(player_o == 4){
                    player_1_result = 0;
                    player_2_result = 0;
                    player_3_result = 0;
                    player_4_result = 1;
                    if(player_1_t != null && player_1_t.equals("T"))   { score_1 -= 100; player_1.setTichu_l(player_1.getTichu_l()+1); }
                    if(player_1_t != null && player_1_t.equals("GT"))  { score_1 -= 200; player_1.setGrand_l(player_1.getGrand_l()+1); }
                    if(player_2_t != null && player_2_t.equals("T"))   { score_1 -= 100; player_2.setTichu_l(player_2.getTichu_l()-1); }
                    if(player_2_t != null && player_2_t.equals("GT"))  { score_1 -= 200; player_2.setGrand_l(player_2.getGrand_l()-1); }
                    if(player_3_t != null && player_3_t.equals("T"))   { score_2 -= 100; player_3.setTichu_l(player_3.getTichu_l()-1); }
                    if(player_3_t != null && player_3_t.equals("GT"))  { score_2 -= 200; player_3.setGrand_l(player_3.getGrand_l()-1); }
                    if(player_4_t != null && player_4_t.equals("T"))   { score_2 += 100; player_4.setTichu_w(player_4.getTichu_w()+1); }
                    if(player_4_t != null && player_4_t.equals("GT"))  { score_2 += 200; player_4.setGrand_w(player_4.getGrand_w()+1); }
                }


                match.setScore_1(match.getScore_1() + score_1);
                match.setScore_2(match.getScore_2() + score_2);
                if(match.getScore_1() > match.getScore_2() && match.getScore_1() >= 1000){
                    match.setIs_over(1);
                    player_1.setMatch_w(player_1.getMatch_w()+1);
                    player_2.setMatch_w(player_2.getMatch_w()+1);
                    player_3.setMatch_l(player_3.getMatch_l()+1);
                    player_4.setMatch_l(player_4.getMatch_l()+1);
                }
                if(match.getScore_2() > match.getScore_1() && match.getScore_2() >= 1000){
                    match.setIs_over(1);
                    player_1.setMatch_l(player_1.getMatch_l()+1);
                    player_2.setMatch_l(player_2.getMatch_l()+1);
                    player_3.setMatch_w(player_3.getMatch_w()+1);
                    player_4.setMatch_w(player_4.getMatch_w()+1);
                }

                db = new DatabaseHelper(getApplicationContext());
                db.createSet(match.getId(),score_1,score_2,player_1_t,player_1_result,player_2_t,player_2_result,player_3_t,player_3_result,player_4_t,player_4_result);
                db.updatePlayer(player_1);
                db.updatePlayer(player_2);
                db.updatePlayer(player_3);
                db.updatePlayer(player_4);
                db.updateMatch(match);
                db.close();

                setResult(RESULT_OK, i);
                finish();
            }
        });

        first_out_txt.setTypeface(typeFacebold);
        save.setTypeface(typeFacethin);

        player_1_name.setTypeface(typeFacethin);
        player_2_name.setTypeface(typeFacethin);
        player_3_name.setTypeface(typeFacethin);
        player_4_name.setTypeface(typeFacethin);
        score_1.setTypeface(typeFacebold);
        score_2.setTypeface(typeFacebold);

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


        ////// CUSTOM ACTION BAR //////
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.windowTitle);
        mCustomView.setBackgroundColor(getResources().getColor(R.color.red));
        mTitleTextView.setTypeface(typeFacebold);
        mTitleTextView.setText("NEW SET");
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

        first_out.setMinValue(0);
        first_out.setMaxValue(3);
        first_out.setDisplayedValues(names);
        first_out.setWrapSelectorWheel(true);
        first_out.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(first_out,typeFacebold,typeFacethin);
        setDividerColor(first_out);

        np_1.setMaxValue(nums.length-1);
        np_1.setMinValue(0);
        np_1.setWrapSelectorWheel(true);
        np_1.setDisplayedValues(nums);
        np_1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np_1.setValue(15);
        setNumberPickerTextColor(np_1,typeFacebold,typeFacethin);
        setDividerColor(np_1);

        np_2.setMaxValue(nums.length-1);
        np_2.setMinValue(0);
        np_2.setWrapSelectorWheel(true);
        np_2.setDisplayedValues(nums);
        np_2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np_2.setValue(15);
        setNumberPickerTextColor(np_2,typeFacebold,typeFacethin);
        setDividerColor(np_2);

        //Team 1 score listener
        np_1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                // TODO Auto-generated method stub
                int newIndexVal = -1;
                if(Integer.parseInt(nums[newVal]) != 200){
                    for (int i=0;i<nums.length;i++) {
                        if (nums[i].equals(String.valueOf(100 - Integer.parseInt(nums[newVal])))) {
                            newIndexVal = i;
                            break;
                        }
                    }
                    np_2.setValue(newIndexVal);
                }else{
                    np_2.setValue(5);
                }
            }
        });

        //Team 2 score listener
        np_2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                // TODO Auto-generated method stub
                int newIndexVal = -1;
                if(Integer.parseInt(nums[newVal]) != 200){
                    for (int i=0;i<nums.length;i++) {
                        if (nums[i].equals(String.valueOf(100 - Integer.parseInt(nums[newVal])))) {
                            newIndexVal = i;
                            break;
                        }
                    }
                    np_1.setValue(newIndexVal);
                }else{
                    np_1.setValue(5);
                }
            }
        });

    }

    public void setNumberPickerTextColor(NumberPicker numberPicker, Typeface typefaceBold, Typeface typefaceThin)
    {
        final int count = numberPicker.getChildCount();
        float spTextSize = 20;
        float textSize = spTextSize * getResources().getDisplayMetrics().scaledDensity;
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(getResources().getColor(R.color.white));
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setTypeface(typefaceThin);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setTextSize(textSize);
                    ((EditText) child).setTextColor(getResources().getColor(R.color.white));
                    ((EditText) child).setTypeface(typefaceThin);
                    //((EditText) child).setTextSize(textSize);
                    numberPicker.invalidate();
                } catch (NoSuchFieldException e) {
                    Log.w("setNumberPickerTextColor", e);
                } catch (IllegalAccessException e) {
                    Log.w("setNumberPickerTextColor", e);
                } catch (IllegalArgumentException e) {
                    Log.w("setNumberPickerTextColor", e);
                }
            }
        }
    }

    private void setDividerColor (NumberPicker picker) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    pf.set(picker, getResources().getDrawable(R.drawable.white_line));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
