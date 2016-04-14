package shuntianfu.com.shuntianfucompressor;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhefei.fragment.LazyFragment;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.support.SupportColorLevel;
import org.achartengine.renderer.support.SupportSeriesRender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import business.MainManager;
import common.AppContext;
import common.Constants;
import logic.ColorLevelCompressorLineUtils;
import model.Compressor;
import model.CompressorData;
import ui.LoadingProgressDialog;
import ui.MyWheelView;
import ui.TimeSelectFragment;
import utils.CustomToast;
import utils.DataHandler;

/**
 * Created by haopei on 2016/3/25.
 */
public class CompressorLineFragment extends LazyFragment implements View.OnClickListener, MyWheelView.OnWheelSelectListener {

    private static final String TAG = "CompressorLineFragment";

    private int currentSign = 0;//几率当前显示的是电压？电流？等的标识

    private final static int COLOR_ORANGE = Color.parseColor("#FFFF9432");
    private final static int COLOR_RED = Color.parseColor("#DDFA3D35");
    //    private final static int COLOR_GREEN = Color.parseColor("#DF1A9B32");
    private final static int COLOR_GREEN = Color.parseColor("#7CFC00");
    private final static int COLOR_BLUE = 0xFF0000FF;

    private int tabIndex;
    public static final String INTENT_INT_INDEX = "intent_int_index";
    private TextView refresh;
    //        private ColorLevelMultiLineUtils mLineUtils;
    private ColorLevelCompressorLineUtils mLineUtils;
    private LinearLayout mLinearLayoutLine;


    private View view;
    private TextView dianya;
    private TextView dianliu;
    private TextView wendu;
    private TextView gaoya;
    private TextView diya;

    private ImageView compressStatus;
    private ImageView compressStatus1;

    private ProgressBar progressBar;


    private RelativeLayout rlChooseTime;
    private TextView currentTimeTv;
    private long currentTime = -1;//当前时间 (s)
    private long startTime = -1;//起始时间 (s)
    private byte gapTime = 6;//间隔时间，default 6h
    private Date currentDate;
    private Date startDate;
    private List<String> timeList;
    private int currentPageIndex;

    private Compressor nowCompressor;

    private List<CompressorData> compressorDatas = new ArrayList<>();//五组数据对象集合


    private DataHandler dataHandler = new DataHandler() {
        @Override
        public void onData(int code, String msg, Object obj) {
            utils.Log.d(TAG, "code=" + code);
            if (code == Constants.SERVER_SUCCESS) {
                if (compressorDatas.size() != 0) {
                    compressorDatas.clear();
                }
                List<CompressorData> tempDatas = (List<CompressorData>) obj;
                //倒序放置
                for (int i = tempDatas.size() - 1; i >= 0; i--) {
                    compressorDatas.add(tempDatas.get(i));
                }

                CompressorData compressorData = compressorDatas.get(0);
                if (TextUtils.equals("1", compressorData.getDefrosterStatus())) {
                    compressStatus1.setBackgroundResource(R.drawable.gray_point);
                } else {
                    compressStatus1.setBackgroundResource(R.drawable.yellow_point);

                }

                if (TextUtils.equals("1", compressorData.getStatus())) {
                    compressStatus.setBackgroundResource(R.drawable.gray_point);
                } else {
                    compressStatus.setBackgroundResource(R.drawable.red_point);

                }
//				compressorDatas = (List<CompressorData>) obj;
                //根据选中的电压电流等决定显示哪个属性
                showData();

            } else {
                hideCustomProgressDialog();
                CustomToast.showToast(AppContext.getInstance(), "压缩机数据请求失败", 2000);
            }
        }
    };

    /**
     * 根据选中的电压等信息显示数据
     */
    private void showData() {
        //默认显示电压数据
        if (currentSign == Constants.DIAYA) {
            showDianya();
        } else if (currentSign == Constants.DIANLIU) {
            showDianliu();
        } else if (currentSign == Constants.WENDU) {
            showWendu();
        } else if (currentSign == Constants.GAOYA) {
            showGaoya();
        } else if (currentSign == Constants.DIYA) {
            showDiya();
        } else {

        }

        hideCustomProgressDialog();
    }


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_tabmain_item);

        try {

            if (null != getArguments()) {
                currentPageIndex = getArguments().getInt(CompressorLineFragment.INTENT_INT_INDEX) + 1;
                nowCompressor = getArguments().getParcelable(Constants.NOWCOMPRESSOR);
                Log.d("TAG", "currentPageIndex==" + currentPageIndex);
            }

            initView();

            initTime();
            //请求数据
            requestData();
            //请求压缩机状态
            requestStatus();


            showCustomProgrssDialog();
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }

    }

    private void requestStatus() {

        //请求当前压缩机状态


    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        if (currentSign == Constants.DIAYA) {
            showDianya();
        } else if (currentSign == Constants.DIANLIU) {
            showDianliu();
        } else if (currentSign == Constants.WENDU) {
            showWendu();
        } else if (currentSign == Constants.GAOYA) {
            showGaoya();
        } else if (currentSign == Constants.DIYA) {
            showDiya();
        } else {

        }
    }

    /**
     * 请求数据
     */
    private void requestData() {
        Log.d(TAG, "startTime==" + startTime + "end==" + new Date().getTime());
        MainManager.getInstance().realTimeList(AppContext.getInstance(), startTime, new Date().getTime(), nowCompressor.getCompressorName(), dataHandler);


    }


    private void initView() {

        tabIndex = getArguments().getInt(INTENT_INT_INDEX);
//        progressBar = (ProgressBar) findViewById(R.id.fragment_mainTab_item_progressBar);
        dianya = (TextView) findViewById(R.id.tv_dianya);
        dianliu = (TextView) findViewById(R.id.tv_dianliu);
        wendu = (TextView) findViewById(R.id.tv_wendu);
        gaoya = (TextView) findViewById(R.id.tv_gaoya);
        diya = (TextView) findViewById(R.id.tv_diya);
        rlChooseTime = (RelativeLayout) findViewById(R.id.rl_choose_time);
        currentTimeTv = (TextView) findViewById(R.id.current_time);
        mLinearLayoutLine = (LinearLayout) findViewById(R.id.linearLayout_line);
        compressStatus = (ImageView) findViewById(R.id.iv_compress_status);
        compressStatus1 = (ImageView) findViewById(R.id.iv_compress_status1);
//        refresh = (TextView) findViewById(R.id.tv_refresh);
//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                refresh();
//
////                showInfoBy1();
//            }
//        });
//        progressBar.setVisibility(View.VISIBLE);

        dianliu.setOnClickListener(this);
        diya.setOnClickListener(this);
        wendu.setOnClickListener(this);
        gaoya.setOnClickListener(this);
        dianya.setOnClickListener(this);
        rlChooseTime.setOnClickListener(this);


        //默认选中电压
        dianya.setSelected(true);
        dianliu.setSelected(false);
        wendu.setSelected(false);
        gaoya.setSelected(false);
        diya.setSelected(false);

        mLineUtils = new ColorLevelCompressorLineUtils(getApplicationContext());


    }

    private void initTime() {
        //获取当前时间
        currentDate = new Date();
        //date.getTime()拿到的是毫秒
        currentTime = currentDate.getTime();
//        Log.i("compressorLineFragment", "currentDate=" + currentDate.toString());
//        Log.i("compressorLineFragment", "currentDate getTime=" + currentDate.getTime());

        //弹出选择时间段的dialog
        timeList = new ArrayList<>();

        int gapCount = 1;
        for (int i = 0; i < 28; i++) {
            long time = currentTime - gapTime * 3600000 * gapCount;
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sDateTime = sdf.format(date);
            timeList.add(sDateTime);
            gapCount++;

            if (i == 0) {
                //减去6小时
                currentTimeTv.setText("起始时间：" + sDateTime);
                startTime = time;
            }

        }
    }

    private void refresh() {
        mLinearLayoutLine.removeAllViews();
        XYMultipleSeriesDataset mXYMultipleSeriesDataSet = new XYMultipleSeriesDataset();

        double[] allDataSets = new double[]{
                30, 20, 23, 45, 56, 57, 67, 32, 34
        };

        XYSeries sysSeries = new XYSeries("电压(V)");
        mLineUtils.mXYRenderer.clearXTextLabels();
        for (int i = 0; i < 300; i++) {
            sysSeries.add(i, allDataSets[i % 9]);
//            mLineUtils.mXYRenderer.addXTextLabel(i, hours[i]);
        }
        mXYMultipleSeriesDataSet.addSeries(sysSeries);


        mLinearLayoutLine.addView(mLineUtils.initLineGraphView(null, mXYMultipleSeriesDataSet));

//        view.invalidate();

    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        handler.removeMessages(1);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_dianya:
                //电压
                showCustomProgrssDialog();
                showDianya();
                hideCustomProgressDialog();
                break;
            case R.id.tv_dianliu:
                //电流
                showCustomProgrssDialog();
                showDianliu();
                hideCustomProgressDialog();
                break;
            case R.id.tv_wendu:
                //温度
                showCustomProgrssDialog();
                showWendu();
                hideCustomProgressDialog();
                break;
            case R.id.tv_gaoya:
                //高压
                showCustomProgrssDialog();
                showGaoya();
                hideCustomProgressDialog();
                break;
            case R.id.tv_diya:
                //低压
                showCustomProgrssDialog();
                showDiya();
                hideCustomProgressDialog();
                break;
            case R.id.rl_choose_time: {

                showSelectDialog(timeList, currentTimeTv.getText().toString());

                break;
            }

            default:
                break;
        }
    }

    /**
     * 显示电压曲线
     */
    private void showDianya() {
        currentSign = Constants.DIAYA;
        //默认选中电压
        dianya.setSelected(true);
        dianliu.setSelected(false);
        wendu.setSelected(false);
        gaoya.setSelected(false);
        diya.setSelected(false);

        List<String> data = new ArrayList<>();
        List<String> times = new ArrayList<>();

        for (CompressorData compressorData : compressorDatas) {
            data.add(compressorData.getDianya());
            times.add(changeTime(Long.valueOf(compressorData.getCreateTime())));
        }

        final SupportSeriesRender seriesRender = new SupportSeriesRender();
        seriesRender.setClickPointColor(Color.parseColor("#8F77AA"));
        seriesRender.setColorLevelValid(true);

        mLineUtils.mXYRenderer.clearXTextLabels();
        mLineUtils.mXYRenderer.removeAllSupportRenderers();
        mLineUtils.mXYRenderer.setChartTitle("第" + currentPageIndex + "台压缩机电压曲线图");
        mLineUtils.mXYRenderer.setYTitle("电压");
        mLineUtils.mXYRenderer.setYAxisMax(385);
        mLineUtils.mXYRenderer.setYAxisMin(375);
        mLineUtils.mXYRenderer.setYLabels(20);

        Float fazhi;
        Float fazhi1;
        ArrayList<SupportColorLevel> list = new ArrayList<>();

        if (null != AppContext.getInstance().getDianyaFazhi() && null != AppContext.getInstance().getDianyaFazhiLower()) {
//            mLineUtils.mXYRenderer.setTargetLineVisible(true);
            fazhi = Float.valueOf(AppContext.getInstance().getDianyaFazhi());
            fazhi1 = Float.valueOf(AppContext.getInstance().getDianyaFazhiLower());
//            mLineUtils.mXYRenderer.setTargetValue(fazhi);
//            mLineUtils.mXYRenderer.setTargetDescription("电压阀值" + AppContext.getInstance().getDianyaFazhi());
            // 若有多个颜色等级可以使用这个用法

//            SupportColorLevel supportColorLevel_a = new SupportColorLevel(-1000, fazhi1, COLOR_BLUE);
            SupportColorLevel supportColorLevel_a = new SupportColorLevel(-1000, fazhi1, COLOR_RED);
            SupportColorLevel supportColorLevel_b = new SupportColorLevel(fazhi1, fazhi, COLOR_BLUE);
            SupportColorLevel supportColorLevel_c = new SupportColorLevel(fazhi, Integer.MAX_VALUE, COLOR_RED);
            list.add(supportColorLevel_a);
            list.add(supportColorLevel_b);
            list.add(supportColorLevel_c);
            seriesRender.setColorLevelList(list);

        } else {
//            mLineUtils.mXYRenderer.setTargetLineVisible(false);
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        XYSeries sysSeries = new XYSeries("电压(V)");
        for (int i = 0; i < compressorDatas.size(); i++) {
            sysSeries.add(i, Double.valueOf(data.get(i)));
            if (i % 6 == 0) {
                mLineUtils.mXYRenderer.addXTextLabel(i, times.get(i));
            }
        }

        dataset.addSeries(sysSeries);

        view = mLineUtils.initLineGraphView(seriesRender, dataset);//seriesRender,sysSeries,allDataSets,hours

        mLinearLayoutLine.removeAllViews();
        mLinearLayoutLine.addView(view);
    }

    /**
     * 显示电流曲线
     */
    private void showDianliu() {
        currentSign = Constants.DIANLIU;
        //默认选中电流
        dianya.setSelected(false);
        dianliu.setSelected(true);
        wendu.setSelected(false);
        gaoya.setSelected(false);
        diya.setSelected(false);

        XYMultipleSeriesDataset mXYMultipleSeriesDataSet = new XYMultipleSeriesDataset();

        List<String> data = new ArrayList<>();
        List<String> times = new ArrayList<>();

        for (CompressorData compressorData : compressorDatas) {
            data.add(compressorData.getDianliu());
            times.add(changeTime(Long.valueOf(compressorData.getCreateTime())));
        }


        XYSeries sysSeries = new XYSeries("电流(I)");
        mLineUtils.mXYRenderer.clearXTextLabels();
        for (int i = 0; i < compressorDatas.size(); i++) {
            sysSeries.add(i, Double.valueOf(data.get(i)));
            if (i % 6 == 0) {
                mLineUtils.mXYRenderer.addXTextLabel(i, times.get(i));
            }
        }
        mXYMultipleSeriesDataSet.addSeries(sysSeries);

        mLineUtils.mXYRenderer.removeAllSupportRenderers();
        mLineUtils.mXYRenderer.setChartTitle("第" + currentPageIndex + "台压缩机电流曲线图");
        mLineUtils.mXYRenderer.setYAxisMax(15);
        mLineUtils.mXYRenderer.setYAxisMin(8);
        mLineUtils.mXYRenderer.setYLabels(20);
        mLineUtils.mXYRenderer.setYTitle("电流");

        final SupportSeriesRender seriesRender = new SupportSeriesRender();
        seriesRender.setClickPointColor(Color.parseColor("#8F77AA"));
        seriesRender.setColorLevelValid(true);

        ArrayList<SupportColorLevel> list = new ArrayList<>();
        Float fazhi;
        Float fazhi1;

        if (null != AppContext.getInstance().getDianliuFazhi() && null != AppContext.getInstance().getDianliuFazhiLower()) {
//            mLineUtils.mXYRenderer.setTargetLineVisible(true);
            fazhi = Float.valueOf(AppContext.getInstance().getDianliuFazhi());
            fazhi1 = Float.valueOf(AppContext.getInstance().getDianliuFazhiLower());
//            mLineUtils.mXYRenderer.setTargetValue(fazhi);
//            mLineUtils.mXYRenderer.setTargetDescription("电流阀值" + AppContext.getInstance().getDianliuFazhi());
            // 若有多个颜色等级可以使用这个用法
            SupportColorLevel supportColorLevel_a = new SupportColorLevel(-1000, fazhi1, COLOR_RED);
            SupportColorLevel supportColorLevel_b = new SupportColorLevel(fazhi1, fazhi, COLOR_BLUE);
            SupportColorLevel supportColorLevel_c = new SupportColorLevel(fazhi, Integer.MAX_VALUE, COLOR_RED);
            list.add(supportColorLevel_a);
            list.add(supportColorLevel_b);
            list.add(supportColorLevel_c);
            seriesRender.setColorLevelList(list);

        } else {
//            mLineUtils.mXYRenderer.setTargetLineVisible(false);
        }

        mLinearLayoutLine.removeAllViews();
        mLinearLayoutLine.addView(mLineUtils.initLineGraphView(seriesRender, mXYMultipleSeriesDataSet));
    }

    /**
     * 显示温度曲线
     */
    private void showWendu() {
        currentSign = Constants.WENDU;
        //默认选中温度
        dianya.setSelected(false);
        dianliu.setSelected(false);
        wendu.setSelected(true);
        gaoya.setSelected(false);
        diya.setSelected(false);

        XYMultipleSeriesDataset mXYMultipleSeriesDataSet = new XYMultipleSeriesDataset();

        List<String> data = new ArrayList<>();
        List<String> times = new ArrayList<>();

        for (CompressorData compressorData : compressorDatas) {
            data.add(compressorData.getWendu());
            times.add(changeTime(Long.valueOf(compressorData.getCreateTime())));
        }

        XYSeries sysSeries = new XYSeries("温度(℃)");
        mLineUtils.mXYRenderer.clearXTextLabels();
        for (int i = 0; i < compressorDatas.size(); i++) {
            sysSeries.add(i, Double.valueOf(data.get(i)));
            if (i % 6 == 0) {
                mLineUtils.mXYRenderer.addXTextLabel(i, times.get(i));
            }
        }
        mXYMultipleSeriesDataSet.addSeries(sysSeries);

        mLineUtils.mXYRenderer.removeAllSupportRenderers();
        mLineUtils.mXYRenderer.setChartTitle("第" + currentPageIndex + "台压缩机温度曲线图");
        mLineUtils.mXYRenderer.setYAxisMax(7);
        mLineUtils.mXYRenderer.setYAxisMin(-25);
        mLineUtils.mXYRenderer.setYLabels(30);
        mLineUtils.mXYRenderer.setYTitle("温度");

        final SupportSeriesRender seriesRender = new SupportSeriesRender();
        seriesRender.setClickPointColor(Color.parseColor("#8F77AA"));
        seriesRender.setColorLevelValid(true);

        ArrayList<SupportColorLevel> list = new ArrayList<>();
        Float fazhi;
        Float fazhi1;

        if (null != AppContext.getInstance().getWenduFazhi() && null != AppContext.getInstance().getWenduFazhiLower()) {
//            mLineUtils.mXYRenderer.setTargetLineVisible(true);
            fazhi = Float.valueOf(AppContext.getInstance().getWenduFazhi());
            fazhi1 = Float.valueOf(AppContext.getInstance().getWenduFazhiLower());
//            mLineUtils.mXYRenderer.setTargetValue(fazhi);
//            mLineUtils.mXYRenderer.setTargetDescription("温度阀值" + AppContext.getInstance().getWenduFazhi());
            // 若有多个颜色等级可以使用这个用法
            SupportColorLevel supportColorLevel_a = new SupportColorLevel(-1000, fazhi1, COLOR_RED);
            SupportColorLevel supportColorLevel_b = new SupportColorLevel(fazhi1, fazhi, COLOR_BLUE);
            SupportColorLevel supportColorLevel_c = new SupportColorLevel(fazhi, Integer.MAX_VALUE, COLOR_RED);
            list.add(supportColorLevel_a);
            list.add(supportColorLevel_b);
            list.add(supportColorLevel_c);
            seriesRender.setColorLevelList(list);

        } else {
//            mLineUtils.mXYRenderer.setTargetLineVisible(false);
        }

        mLinearLayoutLine.removeAllViews();
        mLinearLayoutLine.addView(mLineUtils.initLineGraphView(seriesRender, mXYMultipleSeriesDataSet));
    }

    /**
     * 显示高压曲线
     */
    private void showGaoya() {
        currentSign = Constants.GAOYA;
        //默认选中高压
        dianya.setSelected(false);
        dianliu.setSelected(false);
        wendu.setSelected(false);
        gaoya.setSelected(true);
        diya.setSelected(false);

        XYMultipleSeriesDataset mXYMultipleSeriesDataSet = new XYMultipleSeriesDataset();

        List<String> data = new ArrayList<>();
        List<String> times = new ArrayList<>();

        for (CompressorData compressorData : compressorDatas) {
            data.add(compressorData.getGaoya());
            times.add(changeTime(Long.valueOf(compressorData.getCreateTime())));
        }

        XYSeries sysSeries = new XYSeries("高压(kg)");
        mLineUtils.mXYRenderer.clearXTextLabels();
        for (int i = 0; i < compressorDatas.size(); i++) {
            sysSeries.add(i, Double.valueOf(data.get(i)));
            if (i % 6 == 0) {
                mLineUtils.mXYRenderer.addXTextLabel(i, times.get(i));
            }
        }
        mXYMultipleSeriesDataSet.addSeries(sysSeries);

        mLineUtils.mXYRenderer.removeAllSupportRenderers();
        mLineUtils.mXYRenderer.setChartTitle("第" + currentPageIndex + "台压缩机高压曲线图");
        mLineUtils.mXYRenderer.setYAxisMax(28);
        mLineUtils.mXYRenderer.setYAxisMin(8);
        mLineUtils.mXYRenderer.setYLabels(20);
        mLineUtils.mXYRenderer.setYTitle("高压(kg)");

        final SupportSeriesRender seriesRender = new SupportSeriesRender();
        seriesRender.setClickPointColor(Color.parseColor("#8F77AA"));
        seriesRender.setColorLevelValid(true);

        ArrayList<SupportColorLevel> list = new ArrayList<>();
        Float fazhi;
        Float fazhi1;//低

        if (null != AppContext.getInstance().getGaoyaFazhi() && null != AppContext.getInstance().getGaoyaFazhiLower()) {
//            mLineUtils.mXYRenderer.setTargetLineVisible(true);
            fazhi = Float.valueOf(AppContext.getInstance().getGaoyaFazhi());
            fazhi1 = Float.valueOf(AppContext.getInstance().getGaoyaFazhiLower());
//            mLineUtils.mXYRenderer.setTargetValue(fazhi);
//            mLineUtils.mXYRenderer.setTargetDescription("高压阀值" + AppContext.getInstance().getGaoyaFazhi());
            // 若有多个颜色等级可以使用这个用法
            SupportColorLevel supportColorLevel_a = new SupportColorLevel(-1000, fazhi1, COLOR_RED);
            SupportColorLevel supportColorLevel_b = new SupportColorLevel(fazhi1, fazhi, COLOR_BLUE);
            SupportColorLevel supportColorLevel_c = new SupportColorLevel(fazhi, Integer.MAX_VALUE, COLOR_RED);
            list.add(supportColorLevel_a);
            list.add(supportColorLevel_b);
            list.add(supportColorLevel_c);
            seriesRender.setColorLevelList(list);

        } else {
//            mLineUtils.mXYRenderer.setTargetLineVisible(false);
        }

        mLinearLayoutLine.removeAllViews();
        mLinearLayoutLine.addView(mLineUtils.initLineGraphView(seriesRender, mXYMultipleSeriesDataSet));
    }

    /**
     * 显示低压曲线
     */
    private void showDiya() {
        currentSign = Constants.DIYA;
        //默认选中低压
        dianya.setSelected(false);
        dianliu.setSelected(false);
        wendu.setSelected(false);
        gaoya.setSelected(false);
        diya.setSelected(true);

        XYMultipleSeriesDataset mXYMultipleSeriesDataSet = new XYMultipleSeriesDataset();

        List<String> data = new ArrayList<>();
        List<String> times = new ArrayList<>();

        for (CompressorData compressorData : compressorDatas) {
            data.add(compressorData.getDiya());
            times.add(changeTime(Long.valueOf(compressorData.getCreateTime())));
        }

        XYSeries sysSeries = new XYSeries("低压(kg)");
        mLineUtils.mXYRenderer.clearXTextLabels();
        for (int i = 0; i < compressorDatas.size(); i++) {
            sysSeries.add(i, Double.valueOf(data.get(i)));
            if (i % 6 == 0) {
                mLineUtils.mXYRenderer.addXTextLabel(i, times.get(i));
            }
        }
        mXYMultipleSeriesDataSet.addSeries(sysSeries);

        mLineUtils.mXYRenderer.removeAllSupportRenderers();
        mLineUtils.mXYRenderer.setChartTitle("第" + currentPageIndex + "台压缩机低压曲线图");
        mLineUtils.mXYRenderer.setYAxisMax(8);
        mLineUtils.mXYRenderer.setYAxisMin(0);
        mLineUtils.mXYRenderer.setYLabels(20);
        mLineUtils.mXYRenderer.setYTitle("低压");

        final SupportSeriesRender seriesRender = new SupportSeriesRender();
        seriesRender.setClickPointColor(Color.parseColor("#8F77AA"));
        seriesRender.setColorLevelValid(true);

        ArrayList<SupportColorLevel> list = new ArrayList<>();
        Float fazhi;
        Float fazhi1;

        if (null != AppContext.getInstance().getDiyaFazhi() && null != AppContext.getInstance().getDiyaFazhiLower()) {
//            mLineUtils.mXYRenderer.setTargetLineVisible(true);
            fazhi = Float.valueOf(AppContext.getInstance().getDiyaFazhi());
            fazhi1 = Float.valueOf(AppContext.getInstance().getDiyaFazhiLower());
//            mLineUtils.mXYRenderer.setTargetValue(fazhi);
//            mLineUtils.mXYRenderer.setTargetDescription("高压阀值" + AppContext.getInstance().getDiyaFazhi());
            // 若有多个颜色等级可以使用这个用法
            SupportColorLevel supportColorLevel_a = new SupportColorLevel(-1000, fazhi1, COLOR_RED);
            SupportColorLevel supportColorLevel_b = new SupportColorLevel(fazhi1, fazhi, COLOR_BLUE);
            SupportColorLevel supportColorLevel_c = new SupportColorLevel(fazhi, Integer.MAX_VALUE, COLOR_RED);
            list.add(supportColorLevel_a);
            list.add(supportColorLevel_b);
            list.add(supportColorLevel_c);
            seriesRender.setColorLevelList(list);

        } else {
//            mLineUtils.mXYRenderer.setTargetLineVisible(false);
        }

        mLinearLayoutLine.removeAllViews();
        mLinearLayoutLine.addView(mLineUtils.initLineGraphView(seriesRender, mXYMultipleSeriesDataSet));
    }

    private void showSelectDialog(List<String> list, String data) {
        TimeSelectFragment pick = new TimeSelectFragment();
        try {
            pick.setList(list);
            pick.setCurrentTime(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        pick.setTargetFragment(CompressorLineFragment.this, 0);
        pick.show(getActivity().getSupportFragmentManager().beginTransaction(), null);
    }

    @Override
    public void onSelected(int selectedIndex, String selectTime) {
        try {
            showCustomProgrssDialog();
            currentTimeTv.setText("起始时间：" + selectTime);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = sdf.parse(selectTime);
            //网络请求的开始时间
            startTime = startDate.getTime();
            Log.i("compressorLineFragment", "startDate=" + startDate.toString());

            //网络请求，刷新数据
            MainManager.getInstance().realTimeList(AppContext.getInstance(), startTime,
                    new Date().getTime(), nowCompressor.getCompressorName(), dataHandler);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String changeTime(long mill) {
        Date date = new Date(mill);
        String strs = "";
        try {
            //yyyy表示年MM表示月dd表示日
            //yyyy-MM-dd是日期的格式，比如2015-12-12如果你要得到2015年12月12日就换成yyyy年MM月dd日
            SimpleDateFormat sdf = new SimpleDateFormat("dd HH:mm:ss");
            //进行格式化
            strs = sdf.format(date);
            System.out.println(strs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }


    protected LoadingProgressDialog loadingProgressDialog;

    public void showCustomProgrssDialog() {

        if (null == loadingProgressDialog)

            loadingProgressDialog = LoadingProgressDialog.createDialog(getActivity());

        loadingProgressDialog.setCanceledOnTouchOutside(false);//点击无效，回退有效

//        loadingProgressDialog.setCancelable(false);//点击无效，回退无效

    }

    public void hideCustomProgressDialog() {

        if (null != loadingProgressDialog) {

            loadingProgressDialog.dismiss();

            loadingProgressDialog = null;

        }

    }
}
