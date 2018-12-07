package draw;
import initialsolution.GreedyAlgorithm;
import dataprocessing.ReadData;
import entity.City;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/6.
 */
public class XYSeriesImpl extends ApplicationFrame{
    public XYSeriesImpl(final String title, List<City> cities) {
        super(title);
        final XYSeries series = new XYSeries("Random Data");
        for(City city:cities){
            series.add(city.getX(), city.getY());
        }
        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "XY Series Demo",
                "X",
                "Y",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }
    public static void main(final String[] args) {

        ReadData rd = new ReadData();
        rd.run();
        GreedyAlgorithm nal = new GreedyAlgorithm(rd.getCityList());
        List<City> resultList = new ArrayList<>();
        nal.nearestInsertion(resultList);
        final XYSeriesImpl demo = new XYSeriesImpl("XY Series Demo",resultList);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        //System.out.println(Tools.calculateLoopDistance(rd.getReadCityList()));
    }
}
