package ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import logic.ColorLevelMultiLineUtils;
import shuntianfu.com.shuntianfucompressor.R;


public class ColorLevelMultiLineActivity extends Activity {
    private ColorLevelMultiLineUtils mLineUtils;
    private LinearLayout mLinearLayoutLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_level_multi_line);
        mLinearLayoutLine = (LinearLayout) findViewById(R.id.linearLayout_line);
        mLineUtils = new ColorLevelMultiLineUtils(this.getApplicationContext());
//        mLinearLayoutLine.addView(mLineUtils.initLineGraphView());

    }


}
