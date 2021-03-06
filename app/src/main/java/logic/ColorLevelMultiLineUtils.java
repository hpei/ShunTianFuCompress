package logic;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.support.SupportColorLevel;
import org.achartengine.renderer.support.SupportSeriesRender;

import java.util.ArrayList;

/**
 * Created by wangjia on 28/06/14.
 */
public class ColorLevelMultiLineUtils extends BaseSupportUtils {
    /**
     * 本类就以血压图表作为例子，血压需要2条曲线，DIA，SYS ，而且DIA，SYS都有各自的颜色分级
     */

    private static final String TAG = ColorLevelMultiLineUtils.class.getSimpleName();
    private final static int COLOR_ORANGE = Color.parseColor("#FFFF9432");
    private final static int COLOR_RED = Color.parseColor("#DDFA3D35");
    private final static int COLOR_GREEN = Color.parseColor("#DF1A9B32");

    public ColorLevelMultiLineUtils(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void setRespectiveRender(XYMultipleSeriesRenderer render) {
        render.setPointSize(12f);
        render.setXAxisMax(50);
        render.setXAxisMin(0);
        render.setYAxisMax(170);
        render.setYAxisMin(-50);
        render.setPanEnabled(true, false);
        render.setTargetLineVisible(true);
        render.setTargetValue(150f);
//        render.setZoomButtonsVisible(true);
        render.setShowGrid(true); // 是否显示网格
        render.setApplyBackgroundColor(true); // 是否可以设置背景颜色，默认为false
        render.setBackgroundColor(Color.WHITE); // 设置网格背景颜色，需要和上面属性一起用
        render.setAxesColor(Color.BLACK); // x，y轴的颜色
        render.setMargins(new int[]{50, 50, 55, 10}); // 数据分别为曲线图离屏幕的上左下右的间距
    }
    public View initLineGraphView(SupportSeriesRender seriesRender,XYSeries sysSeries,double[] allDataSets,String[] hours) {
        mXYMultipleSeriesDataSet = new XYMultipleSeriesDataset();

        if (null != seriesRender) {
            mXYRenderer.addSupportRenderer(seriesRender);
        }

        for (int i = 0; i < 400; i++) {

            sysSeries.add(i, allDataSets[i%10]);
            mXYRenderer.addXTextLabel(i, hours[i%10]);
        }
        mXYMultipleSeriesDataSet.addSeries(sysSeries);

        View view = ChartFactory.getLineChartView(mContext, mXYMultipleSeriesDataSet, mXYRenderer);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }
    public View initLineGraphView1() {
        mXYMultipleSeriesDataSet = new XYMultipleSeriesDataset();

        final SupportSeriesRender lineSeriesRenderSys = new SupportSeriesRender();
        lineSeriesRenderSys.setClickPointColor(Color.parseColor("#8F77AA"));
        lineSeriesRenderSys.setColorLevelValid(true);
        ArrayList<SupportColorLevel> list = new ArrayList<SupportColorLevel>();

        // 若有多个颜色等级可以使用这个用法
        SupportColorLevel supportColorLevel_a = new SupportColorLevel(100, 120, COLOR_ORANGE);
        SupportColorLevel supportColorLevel_b = new SupportColorLevel(120, 130, COLOR_GREEN);
        SupportColorLevel supportColorLevel_c = new SupportColorLevel(130, 140, COLOR_ORANGE);
        SupportColorLevel supportColorLevel_d = new SupportColorLevel(140, 150, COLOR_RED);


        list.add(supportColorLevel_a);
        list.add(supportColorLevel_b);
        list.add(supportColorLevel_c);
        list.add(supportColorLevel_d);
        lineSeriesRenderSys.setColorLevelList(list);

        String[] hours = new String[10000];
        double[] allDataSets = new double[]{
                125, 130, 140, 131,123,
                125, 149, 137, 134,165
        };
        XYSeries sysSeries = new XYSeries("电压（V）");
        for (int i = 0; i < 8000; i++) {
            sysSeries.add(i, allDataSets[i%10]);
            mXYRenderer.addXTextLabel(i, hours[i]);
        }
        mXYRenderer.addSupportRenderer(lineSeriesRenderSys);
        mXYMultipleSeriesDataSet.addSeries(sysSeries);
//        //----------------------------------------------------------
//
//        final SupportSeriesRender lineSeriesRenderDia = new SupportSeriesRender();
//        lineSeriesRenderDia.setColorLevelValid(true);
//        ArrayList<SupportColorLevel> listDia = new ArrayList<SupportColorLevel>();
//
//        listDia.add(new SupportColorLevel(40, 50, COLOR_ORANGE));
//        listDia.add(new SupportColorLevel(50, 60, COLOR_GREEN));
//        listDia.add(new SupportColorLevel(60, 70, COLOR_ORANGE));
//        listDia.add(new SupportColorLevel(70, 80, COLOR_RED));
//        lineSeriesRenderDia.setColorLevelList(listDia);
//
//        mXYRenderer.addSupportRenderer(lineSeriesRenderDia);
//
//        double[] allDataSetsDia = new double[]{
//                55, 48, 60, 70, 63, 75, 60, 77, 54, 48
//        };
//        int length = allDataSetsDia.length;
//        XYSeries sysSeriesDia = new XYSeries("DIA");
//        for (int i = 0; i < length; i++) {
//            sysSeriesDia.add(i, allDataSetsDia[i]);
//        }
//
//        XYSeriesRenderer xySeriesRenderer = getSimpleSeriesRender(Color.GREEN);
//        xySeriesRenderer.setPointStyle(PointStyle.SQUARE);
//        mXYRenderer.addSeriesRenderer(xySeriesRenderer);
//        mXYMultipleSeriesDataSet.addSeries(sysSeriesDia);
        //---------------------------------------------------------------------

        //如果不许要颜色分级功能，则直接用原始的lineChart既可
        View view = ChartFactory.getLineChartView(mContext, mXYMultipleSeriesDataSet, mXYRenderer);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }


    @Override
    protected XYSeriesRenderer getSimpleSeriesRender(int color) {
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(color);
        renderer.setFillPoints(true);   //是否是实心的点
        renderer.setDisplayChartValues(false);  // 设置是否在点上显示数据
        renderer.setLineWidth(5f);    //设置曲线的宽度
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setInnerCircleColor(Color.parseColor("#CC9B61"));
        return renderer;
    }


}
