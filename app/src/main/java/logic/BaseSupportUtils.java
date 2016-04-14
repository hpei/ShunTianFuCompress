package logic;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.support.SupportTargetLineStyle;

/**
 * Created by wangjia on 28/06/14.
 */
public abstract class BaseSupportUtils {
    private static final String TAG = BaseSupportUtils.class.getSimpleName();
    protected Context mContext;
    public XYMultipleSeriesRenderer mXYRenderer;
    public XYMultipleSeriesDataset mXYMultipleSeriesDataSet;

    protected BaseSupportUtils(Context context) {
        this.mContext = context;
        mXYRenderer = new XYMultipleSeriesRenderer();
        initRender();
    }

    private void initRender() {
        LogUtils.LOGE(TAG,"initRender");
        // 1, 构造显示用渲染图
        float textSize1 = 45f;
        float textSize2 = 40f;
        float textSize3 = 25f;

        mXYRenderer.setChartTitle("Chart Title");
        mXYRenderer.setXTitle("时间");
        mXYRenderer.setYTitle("Y Title");

        mXYRenderer.setLabelsColor(Color.parseColor("#00FF00"));

        //设置字体的大小
        mXYRenderer.setAxisTitleTextSize(textSize2);
        mXYRenderer.setChartTitleTextSize(textSize1);
        mXYRenderer.setLegendTextSize(textSize3);
        mXYRenderer.setLabelsTextSize(textSize3);

        // 设置背景颜色
        mXYRenderer.setBackgroundColor(Color.BLACK);
        // 设置页边空白的颜色
        mXYRenderer.setMarginsColor(Color.BLACK);

        // 设置背景颜色生效
        mXYRenderer.setApplyBackgroundColor(true);

        // 设置坐标轴,轴的颜色
        mXYRenderer.setXLabelsColor(Color.parseColor("#FFFFFF"));
        mXYRenderer.setYLabelsColor(0, Color.parseColor("#FFFFFF"));

//        mXYRenderer.setAxesColor(Color.BLACK); // x，y轴的颜色\
        mXYRenderer.setXLabelsAngle(-70); // 设置X轴标签倾斜角度(clockwise degree)

        //设置网格的颜色
        mXYRenderer.setShowGrid(true);
        mXYRenderer.setGridColor(Color.parseColor("#55797C7E"));//Color.parseColor("#45b97c")
        mXYRenderer.addSeriesRenderer(getSimpleSeriesRender(Color.parseColor("#00FF00")));

        // 设置合适的刻度,在轴上显示的数量是 MAX / labels
        mXYRenderer.setYLabels(20);
        mXYRenderer.setXLabels(0); // 设置 X 轴不显示数字（改用我们手动添加的文字标签）

        mXYRenderer.setMargins(new int[]{50, 66, 55, 20});
        mXYRenderer.setXAxisMax(20);
        mXYRenderer.setXAxisMin(0);
        mXYRenderer.setYAxisMin(0);
        mXYRenderer.setYAxisMax(20);

        // 设置x,y轴显示的排列,默认是 Align.CENTER
        mXYRenderer.setXLabelsAlign(Paint.Align.CENTER);
        mXYRenderer.setYLabelsAlign(Paint.Align.RIGHT);

        mXYRenderer.setAntialiasing(true);
        mXYRenderer.setFitLegend(true);
        mXYRenderer.setShowAxes(true);  // 设置坐标轴是否可见
        mXYRenderer.setShowLegend(true);
        // 设置x,y轴上的刻度的颜色
        // 设置是否显示,坐标轴的轴,默认为 true
        // 是否支持图表移动
        mXYRenderer.setPanEnabled(true, true);// 表盘移动
        mXYRenderer.setZoomButtonsVisible(false);
        mXYRenderer.setZoomEnabled(true, false);
        mXYRenderer.setZoomInLimitX(5);
        mXYRenderer.setZoomInLimitY(20);
//        mXYRenderer.setZoomLimits(new double[]{100, 50, 20, 5 }); // 点击放大缩小时候使用
        mXYRenderer.setClickEnabled(true);// 是否可点击
        mXYRenderer.setSelectableBuffer(40);// 点击区域大小

        //设置目标线
//        mXYRenderer.setTargetLineVisible(true);
        mXYRenderer.setTargetLineColor(Color.MAGENTA);
        mXYRenderer.setTargetValue(12.5f);
        mXYRenderer.setTargetLineStyle(SupportTargetLineStyle.Line_Dotted);
        mXYRenderer.setTargetDescription("阀值");


        Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/Futura.ttc");
        mXYRenderer.setTextTypeface(typeFace);

        setRespectiveRender(mXYRenderer);
    }


    /**
     * 这个方法主要是让各自不同的图表设置自己需要的部分样式
     */
    protected  abstract void setRespectiveRender(XYMultipleSeriesRenderer render);

    /**
     * 这个方法主要是用来描述曲线中“点”的属性，如点的样式,颜色，大小等，
     * 如果不想显示出点，则在上面的方法中设置PointSize(0)即可
     * @param color
     * @return
     */
    protected abstract XYSeriesRenderer getSimpleSeriesRender(int color);
}
