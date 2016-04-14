package shuntianfu.com.shuntianfucompressor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import common.AppContext;
import common.Constants;
import utils.SharedPreferencesUtils;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SettingActivity";
    private LinearLayout back;
    private RelativeLayout settingFazhi;
    private RelativeLayout addUser;
    private RelativeLayout modifyPassword;
    private RelativeLayout alarm;
    private RelativeLayout exit;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context = this;
        initView();
    }

    private void initView() {
        back = (LinearLayout) findViewById(R.id.common_title_left);
        back.setVisibility(View.VISIBLE);
        settingFazhi = (RelativeLayout) findViewById(R.id.rl_setting_fazhi);
        addUser = (RelativeLayout) findViewById(R.id.rl_add_user);
        modifyPassword = (RelativeLayout) findViewById(R.id.rl_modify_password);
        alarm = (RelativeLayout) findViewById(R.id.rl_look_for_alarm);
        exit = (RelativeLayout) findViewById(R.id.rl_exit);

        Boolean isAdmin = AppContext.getInstance().isAdmin();
        Log.e(TAG, "isAdmin==" + isAdmin);
        if (isAdmin) {
            addUser.setVisibility(View.VISIBLE);
            settingFazhi.setVisibility(View.VISIBLE);
        } else {
            addUser.setVisibility(View.GONE);
            settingFazhi.setVisibility(View.GONE);
        }

        settingFazhi.setOnClickListener(this);
        addUser.setOnClickListener(this);
        modifyPassword.setOnClickListener(this);
        alarm.setOnClickListener(this);
        back.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_title_left:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                break;
            case R.id.rl_setting_fazhi:
                settingFazhi();
                break;
            case R.id.rl_add_user:
                addUser();
                break;
            case R.id.rl_modify_password:
                modifyPassword();
                break;
            case R.id.rl_look_for_alarm:
                startActivity(new Intent(this, AlarmActivity.class));
                this.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                break;
            case R.id.rl_exit:
                new AlertDialog.Builder(context).setMessage("确定退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();

                break;
            default:
                break;
        }
    }

    private void logout() {
        //退出清空密码
//		SharedPreferencesUtils.saveString(context, Constants.USER_NAME, "");
        SharedPreferencesUtils.saveString(context, Constants.USER_PASSWORD, "");

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        finish();
    }


    private void modifyPassword() {

        startActivity(new Intent(this, ModifyPasswordActivity.class));
        this.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);

    }

    private void addUser() {
        startActivity(new Intent(this, UserAddActivity.class));
        this.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    private void settingFazhi() {
        startActivity(new Intent(this, SettingFazhiActivity.class));
        this.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }
}
