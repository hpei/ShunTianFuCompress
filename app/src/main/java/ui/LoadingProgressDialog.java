package ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import shuntianfu.com.shuntianfucompressor.R;


/**
 * Created by haopei on 2015/11/5.
 */
public class LoadingProgressDialog extends ProgressDialog {

    private  Context context = null;
    public static LoadingProgressDialog loadingProgressDialog = null;


    public LoadingProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public LoadingProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static LoadingProgressDialog createDialog(Context context) {
        loadingProgressDialog = new LoadingProgressDialog(context, R.style.LoadingProgressDialog);
        loadingProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        loadingProgressDialog.show();//写在setContentView()前！
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingProgressDialog.setContentView(View.inflate(context,R.layout.loading_progressdialog_item,null),layoutParams );

        return loadingProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (loadingProgressDialog == null) {
            return;
        }
    }

    /**
     * [Summary]
     * setTitile 标题
     *
     * @param strTitle
     * @return
     */
    public LoadingProgressDialog setTitile(String strTitle) {
        return loadingProgressDialog;
    }

    /**
     * [Summary]
     * setMessage 提示内容
     *
     * @param strMessage
     * @return
     */
    public LoadingProgressDialog setMessage(String strMessage) {
        TextView tvMsg = (TextView) loadingProgressDialog.findViewById(R.id.tv_loading);
        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }
        return loadingProgressDialog;
    }

}
