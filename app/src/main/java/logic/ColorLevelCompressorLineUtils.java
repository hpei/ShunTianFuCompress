package logic;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.support.SupportSeriesRender;


/**
 * Created by haopei on 2016/3/25.
 */
public class ColorLevelCompressorLineUtils extends BaseSupportUtils {

    //显示电压的曲线

    public ColorLevelCompressorLineUtils(Context context) {
        super(context);
        mContext = context;
    }


    public View initLineGraphView(SupportSeriesRender seriesRender, XYMultipleSeriesDataset dataset) {
        mXYMultipleSeriesDataSet = new XYMultipleSeriesDataset();
        if (null != seriesRender) {
            mXYRenderer.addSupportRenderer(seriesRender);
        }
        View view = ChartFactory.getLineChartView(mContext, dataset, mXYRenderer);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }

    @Override
    protected void setRespectiveRender(XYMultipleSeriesRenderer render) {

        render.setPointSize(8f);//点的大小
        render.setXAxisMax(60);
        render.setXAxisMin(0);
        render.setYAxisMax(50);
        render.setYAxisMin(0);

//        render.setTargetLineVisible(true);
//        render.setTargetValue(15f);

    }

    @Override
    protected XYSeriesRenderer getSimpleSeriesRender(int color) {
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(color);
        renderer.setFillPoints(true);   //是否是实心的点
        renderer.setDisplayChartValues(true);  // 设置是否在点上显示数据
        renderer.setLineWidth(4f);    //设置曲线的宽度
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setInnerCircleColor(Color.parseColor("#CC9B61"));
        renderer.setChartValuesTextSize(18f);
        return renderer;
    }
}
