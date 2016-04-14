package shuntianfu.com.shuntianfucompressor;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import business.CompressorDataManager;
import common.AppContext;
import common.Constants;
import model.Threshold;
import ui.ClearEditText;
import utils.CustomToast;
import utils.DataHandler;
import utils.JSONUtils;
import utils.SharedPreferencesUtils;
import utils.ViewUtil;

public class SettingFazhiActivity extends AppCompatActivity implements View.OnClickListener, ClearEditText.ChecTextkNull {
    private static final String TAG = "SettingFazhiActivity";

    private LinearLayout back;

    private ClearEditText dianyaFazhi;
    private ClearEditText dianyaFazhiLow;
    private ClearEditText dianliuFazhi;
    private ClearEditText dianliuFazhiLow;
    private ClearEditText wenduFazhi;
    private ClearEditText wenduFazhiLow;
    private ClearEditText gaoyaFazhi;
    private ClearEditText gaoyaFazhiLow;
    private ClearEditText diyaFazhi;
    private ClearEditText diyaFazhiLow;

    private Button submit;
    private DataHandler updateHandler = new DataHandler() {
        @Override
        public void onData(int code, String msg, Object obj) {
            if (code == Constants.SERVER_SUCCESS) {

                Threshold threshold = JSONUtils.json2Bean(obj.toString(), Threshold.class);


                if (!TextUtils.isEmpty(threshold.getDianyaUpper())) {
                    AppContext.getInstance().setDianyaFazhi(threshold.getDianyaUpper());
                }
                if (!TextUtils.isEmpty(threshold.getDianyaLower())) {
                    AppContext.getInstance().setDiyaFazhiLower(threshold.getDianyaLower());
                }
                if (!TextUtils.isEmpty(threshold.getDianliuUpper())) {
                    AppContext.getInstance().setDianliuFazhi(threshold.getDianliuUpper());
                }
                if (!TextUtils.isEmpty(threshold.getDianliuLower())) {
                    AppContext.getInstance().setDianliuFazhiLower(threshold.getDianliuLower());
                }
                if (!TextUtils.isEmpty(threshold.getWenduUpper())) {
                    AppContext.getInstance().setWenduFazhi(threshold.getWenduUpper());
                }
                if (!TextUtils.isEmpty(threshold.getWenduLower())) {
                    AppContext.getInstance().setWenduFazhiLower(threshold.getWenduLower());
                }
                if (!TextUtils.isEmpty(threshold.getGaoyaUpper())) {
                    AppContext.getInstance().setGaoyaFazhi(threshold.getGaoyaUpper());
                }
                if (!TextUtils.isEmpty(threshold.getGaoyaLower())) {
                    AppContext.getInstance().setGaoyaFazhiLower(threshold.getGaoyaLower());
                }
                if (!TextUtils.isEmpty(threshold.getDiyaUpper())) {
                    AppContext.getInstance().setDiyaFazhi(threshold.getDiyaUpper());
                }
                if (!TextUtils.isEmpty(threshold.getDiyaLower())) {
                    AppContext.getInstance().setDiyaFazhiLower(threshold.getDiyaLower());
                }

                Log.e("SettingFazhiActivity", "返回 threshold=" + threshold.toString());
                CustomToast.showToast(SettingFazhiActivity.this, "设置成功", 1500);
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);

            } else {
                CustomToast.showToast(SettingFazhiActivity.this, "设置失败", 2000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_fazhi);


        initView();

        initData();
    }

    private void initData() {
        //*****************
        if (null != AppContext.getInstance().getDianyaFazhi()) {
            dianyaFazhi.setText(AppContext.getInstance().getDianyaFazhi());
            dianyaFazhi.setSelection(dianyaFazhi.getText().length());
        }
        if (null != AppContext.getInstance().getDianyaFazhiLower()) {
            dianyaFazhiLow.setText(AppContext.getInstance().getDianyaFazhiLower());
        }
        //*****************
        if (null != AppContext.getInstance().getDianliuFazhi()) {
            dianliuFazhi.setText(AppContext.getInstance().getDianliuFazhi());
        }
        if (null != AppContext.getInstance().getDianliuFazhiLower()) {
            dianliuFazhiLow.setText(AppContext.getInstance().getDianliuFazhiLower());
        }
        //*****************
        if (null != AppContext.getInstance().getWenduFazhi()) {
            wenduFazhi.setText(AppContext.getInstance().getWenduFazhi());
        }
        if (null != AppContext.getInstance().getWenduFazhiLower()) {
            wenduFazhiLow.setText(AppContext.getInstance().getWenduFazhiLower());
        }
        //*****************
        if (null != AppContext.getInstance().getGaoyaFazhi()) {
            gaoyaFazhi.setText(AppContext.getInstance().getGaoyaFazhi());
        }
        if (null != AppContext.getInstance().getGaoyaFazhiLower()) {
            gaoyaFazhiLow.setText(AppContext.getInstance().getGaoyaFazhiLower());
        }
        //*****************
        if (null != AppContext.getInstance().getDiyaFazhi()) {
            diyaFazhi.setText(AppContext.getInstance().getDiyaFazhi());
        }
        if (null != AppContext.getInstance().getDiyaFazhiLower()) {
            diyaFazhiLow.setText(AppContext.getInstance().getDiyaFazhiLower());
        }

    }

    private void initView() {
        back = (LinearLayout) findViewById(R.id.common_title_left);
        back.setVisibility(View.VISIBLE);
        dianyaFazhi = (ClearEditText) findViewById(R.id.setting_dianya_fazhi);
        dianyaFazhiLow = (ClearEditText) findViewById(R.id.setting_dianya_fazhi_low);
        dianliuFazhi = (ClearEditText) findViewById(R.id.setting_dianliu_fazhi);
        dianliuFazhiLow = (ClearEditText) findViewById(R.id.setting_dianliu_fazhi_low);
        wenduFazhi = (ClearEditText) findViewById(R.id.setting_wendu_fazhi);
        wenduFazhiLow = (ClearEditText) findViewById(R.id.setting_wendu_fazhi_low);
        gaoyaFazhi = (ClearEditText) findViewById(R.id.setting_gaoya_fazhi);
        gaoyaFazhiLow = (ClearEditText) findViewById(R.id.setting_gaoya_fazhi_low);
        diyaFazhi = (ClearEditText) findViewById(R.id.setting_diya_fazhi);
        diyaFazhiLow = (ClearEditText) findViewById(R.id.setting_diya_fazhi_low);

        submit = (Button) findViewById(R.id.btn_submit_fazhi);

        submit.setFocusable(true);
        submit.requestFocus();

        submit.setEnabled(false);

        dianyaFazhi.setChecTextkNull(this);
        dianyaFazhiLow.setChecTextkNull(this);
        dianliuFazhi.setChecTextkNull(this);
        dianliuFazhiLow.setChecTextkNull(this);
        wenduFazhi.setChecTextkNull(this);
        wenduFazhiLow.setChecTextkNull(this);
        gaoyaFazhi.setChecTextkNull(this);
        gaoyaFazhiLow.setChecTextkNull(this);
        diyaFazhi.setChecTextkNull(this);
        diyaFazhiLow.setChecTextkNull(this);

        back.setOnClickListener(this);
        submit.setOnClickListener(this);

        //可能由于界面为加载完全而无法弹出软键盘。此时应该适当的延迟弹出软键盘
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //可能由于界面为加载完全而无法弹出软键盘。此时应该适当的延迟弹出软键盘
                ViewUtil.showSoftInput(SettingFazhiActivity.this, dianyaFazhi);
            }
        }, 200);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_title_left:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                break;
            case R.id.btn_submit_fazhi:
                submitFazhi();
//				new Asy().execute("");


                break;
            default:
                break;
        }
    }

    String token = SharedPreferencesUtils.getString(this, Constants.USER_TOKEN, "");


    class Asy extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            // 使用GET方法发送请求,需要把参数加在URL后面，用？连接，参数之间用&分隔
            String url = Constants.SERVER_HOST + Constants.UPDATE_THRESHOLD
                    + "?dianyaThreshold=" + 15
                    + "&dianliuThreshold=" + 14
                    + "&wenduThreshold=" + 13
                    + "&gaoyaThreshold=" + 12
                    + "&diyaThreshold=" + 11
                    + "&token=" + token;

            // 生成请求对象
            HttpGet httpGet = new HttpGet(url);
            HttpClient httpClient = new DefaultHttpClient();

            // 发送请求
            try {

                HttpResponse response = httpClient.execute(httpGet);

                // 显示响应
                showResponseResult(response);// 一个私有方法，将响应结果显示出来

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }
    }

    /**
     * 显示响应结果到命令行和TextView
     *
     * @param response
     */
    private void showResponseResult(HttpResponse response) {
        if (null == response) {
            return;
        }

        HttpEntity httpEntity = response.getEntity();
        try {
            InputStream inputStream = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            String result = "";
            String line = "";
            while (null != (line = reader.readLine())) {
                result += line;

            }

            Log.i(TAG, "result==" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void submitFazhi() {
        //提交阀值 需要存储？
        Threshold threshold = new Threshold();
        if (!TextUtils.isEmpty(dianyaFazhi.getText().toString().trim())) {
            threshold.setDianyaUpper(dianyaFazhi.getText().toString().trim());
            AppContext.getInstance().setDianyaFazhi(dianyaFazhi.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(dianliuFazhi.getText().toString().trim())) {
            threshold.setDianliuUpper(dianliuFazhi.getText().toString().trim());
            AppContext.getInstance().setDianyaFazhi(dianliuFazhi.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(wenduFazhi.getText().toString().trim())) {
            threshold.setWenduUpper(wenduFazhi.getText().toString().trim());
            AppContext.getInstance().setDianyaFazhi(wenduFazhi.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(gaoyaFazhi.getText().toString().trim())) {
            threshold.setGaoyaUpper(gaoyaFazhi.getText().toString().trim());
            AppContext.getInstance().setDianyaFazhi(gaoyaFazhi.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(diyaFazhi.getText().toString().trim())) {
            threshold.setDiyaUpper(diyaFazhi.getText().toString().trim());
            AppContext.getInstance().setDianyaFazhi(diyaFazhi.getText().toString().trim());
        }
        //***********************************
        if (!TextUtils.isEmpty(dianyaFazhiLow.getText().toString().trim())) {
            threshold.setDianyaLower(dianyaFazhiLow.getText().toString().trim());
            AppContext.getInstance().setDianyaFazhiLower(dianyaFazhiLow.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(dianliuFazhiLow.getText().toString().trim())) {
            threshold.setDianliuLower(dianliuFazhiLow.getText().toString().trim());
            AppContext.getInstance().setDianliuFazhiLower(dianliuFazhiLow.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(wenduFazhiLow.getText().toString().trim())) {
            threshold.setWenduLower(wenduFazhiLow.getText().toString().trim());
            AppContext.getInstance().setWenduFazhiLower(wenduFazhiLow.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(gaoyaFazhiLow.getText().toString().trim())) {
            threshold.setGaoyaLower(gaoyaFazhiLow.getText().toString().trim());
            AppContext.getInstance().setGaoyaFazhiLower(gaoyaFazhiLow.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(diyaFazhiLow.getText().toString().trim())) {
            threshold.setDiyaLower(diyaFazhiLow.getText().toString().trim());
            AppContext.getInstance().setDiyaFazhiLower(diyaFazhiLow.getText().toString().trim());
        }


        CompressorDataManager.getInstance().updateFazhi(this, threshold, updateHandler);

    }

    @Override
    public void checkNull() {
        if (!dianyaFazhi.isNull()
                && !dianyaFazhiLow.isNull()
                && !dianliuFazhi.isNull()
                && !dianliuFazhiLow.isNull()
                && !wenduFazhi.isNull()
                && !wenduFazhiLow.isNull()
                && !gaoyaFazhi.isNull()
                && !gaoyaFazhiLow.isNull()
                && !diyaFazhi.isNull()
                && !diyaFazhiLow.isNull()) {
            submit.setEnabled(true);
        } else {
            submit.setEnabled(false);
        }
    }
}
