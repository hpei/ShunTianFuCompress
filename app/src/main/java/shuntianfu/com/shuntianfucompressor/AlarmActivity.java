package shuntianfu.com.shuntianfucompressor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.AppContext;
import model.AlarmData;

public class AlarmActivity extends AppCompatActivity {


    private ListView listView;
    private LinearLayout back;

    private List<AlarmData> alarmDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        listView = (ListView) findViewById(R.id.lv_alarm_data);
        back = (LinearLayout) findViewById(R.id.common_title_left);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });

        initData();

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return alarmDatas.size();
            }

            @Override
            public Object getItem(int position) {
                return alarmDatas.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;

                if (null == convertView) {
                    holder = new ViewHolder();
                    convertView = View.inflate(AppContext.getInstance(), R.layout.item_alarm_data, null);
                    holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                    holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }


                AlarmData data = alarmDatas.get(position);

                if (null != data.getContent()) {
                    holder.tv_content.setText(data.getContent());
                }

                if (null != data.getCreateTime()) {

                    holder.tv_time.setText(changeTime(Long.valueOf(data.getCreateTime())));
                }

                return convertView;
            }
        });
    }

    private void initData() {
        if (null != AppContext.getInstance().getAlarmDatas()) {
            alarmDatas = AppContext.getInstance().getAlarmDatas();
        }

    }


    class ViewHolder {

        TextView tv_time;
        TextView tv_content;

    }

    private String changeTime(long mill) {
        Date date = new Date(mill);
        String strs = "";
        try {
            //yyyy表示年MM表示月dd表示日
            //yyyy-MM-dd是日期的格式，比如2015-12-12如果你要得到2015年12月12日就换成yyyy年MM月dd日
            SimpleDateFormat sdf = new SimpleDateFormat("yy-mm-dd HH:mm:ss");
            //进行格式化
            strs = sdf.format(date);
            System.out.println(strs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

}
