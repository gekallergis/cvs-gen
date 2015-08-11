package se.customervalue.cvs.dependency.externalservice.JFreeChartGenerator.Charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.EmptyBlock;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import se.customervalue.cvs.common.CVSConfig;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.List;

public class DualLineBarChart {
	private SecureRandom random = new SecureRandom();
	private String fileName;
	private String chartTitle;
	private String xAxisTitle;
	private String yAxisTitle;
	private String y2AxisTitle;
	private String[] rowKeys;
	private String[] columnKeys;
	private double[][] chartData2D;
	private List<Color> colorList;

	public DualLineBarChart(String chartTitle, String xAxisTitle, String yAxisTitle, String y2AxisTitle, String[] rowKeys, String[] columnKeys, double[][] chartData2D, List<Color> colorList) throws IOException {

		this.chartTitle = chartTitle;
		this.xAxisTitle = xAxisTitle;
		this.yAxisTitle = yAxisTitle;
		this.y2AxisTitle = y2AxisTitle;
		this.rowKeys = rowKeys;
		this.columnKeys = columnKeys;
		this.chartData2D = chartData2D;
		this.colorList = colorList;

		// Create the chart:
		JFreeChart chart = createChart();
		//Save chart as PNG:
		ChartUtilities.saveChartAsPNG(new File(CVSConfig.TEMP_FS_STORE + "/" + generateRandomFileName() + ".png"), chart, CVSConfig.CHART_WIDTH, CVSConfig.CHART_HEIGHT);
	}

	public String getFileName() {
		return fileName;
	}

	private CategoryDataset createDataset1() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int i = 0; i < chartData2D.length - 1; i++) {
			for (int y = 0; y < chartData2D[i].length; y++) {
				dataset.addValue(chartData2D[i][y], rowKeys[i], columnKeys[y]);
			}
		}
		return dataset;
	}

	private CategoryDataset createDataset2() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int y = 0; y < chartData2D[chartData2D.length - 1].length; y++) {
			dataset.addValue(chartData2D[chartData2D.length - 1][y], rowKeys[chartData2D.length - 1], columnKeys[y]);
		}
		return dataset;
	}

	private String generateRandomFileName() {
		fileName = new BigInteger(130, random).toString(32);
		return fileName;
	}

	private JFreeChart createChart() {
		JFreeChart chart = ChartFactory.createBarChart(
				chartTitle,
				xAxisTitle,
				yAxisTitle,
				createDataset1(),
				PlotOrientation.VERTICAL,
				true,
				true,
				false
		);

		// Set the background color of the chart:
		chart.setBackgroundPaint(Color.WHITE);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		// Create and set the dataset for the line chart:
		CategoryDataset categorydataset = createDataset2();
		plot.setDataset(1, categorydataset);
		plot.mapDatasetToRangeAxis(1, 1);

		// Tilt the xAxis labels:
		CategoryAxis categoryaxis = plot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.85D));

		// Set percent symbol in yAxis:
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		DecimalFormat pctFormat = new DecimalFormat("#.0%");
		rangeAxis.setNumberFormatOverride(pctFormat);

		// Set the title of the second yAxis:
		NumberAxis numberaxis = new NumberAxis(y2AxisTitle);
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		plot.setRangeAxis(1, numberaxis);

		LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
		// Set the width of the line:
		BasicStroke wideLine = new BasicStroke(3.5f);
		lineandshaperenderer.setSeriesStroke(0, wideLine);
		// Remove shapes from line:
		lineandshaperenderer.setSeriesShapesVisible(0, false);
		// Set the color of the line:
		lineandshaperenderer.setSeriesPaint(0, colorList.get(chartData2D.length - 1));
		plot.setRenderer(1, lineandshaperenderer);

		// Make the bars 2D:
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBarPainter(new StandardBarPainter());
		for (int i = 0; i < chartData2D.length - 1; i++) {
			renderer.setSeriesPaint(i, colorList.get(i));
		}
		BlockContainer blockcontainer = new BlockContainer(new BorderArrangement());
		blockcontainer.add(new EmptyBlock(2000D, 0.0D));

		// Set the background color of the plot:
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

		// Remove the border of legend:
		chart.getLegend().setFrame(BlockBorder.NONE);
		return chart;
	}
}
