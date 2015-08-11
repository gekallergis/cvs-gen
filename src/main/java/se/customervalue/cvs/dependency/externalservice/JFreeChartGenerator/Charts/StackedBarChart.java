package se.customervalue.cvs.dependency.externalservice.JFreeChartGenerator.Charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import se.customervalue.cvs.common.CVSConfig;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

public class StackedBarChart {
	private SecureRandom random = new SecureRandom();
	private String fileName;
	private String chartTitle;
	private String xAxisTitle;
	private String yAxisTitle;
	private String[] rowKeys;
	private List<Color> colorList;
	private double[][] chartData;
	private String[] columnKeys;

	public StackedBarChart(String chartTitle, String xAxisTitle, String yAxisTitle, String[] rowKeys, String[] columnKeys, double[][] chartData, List<Color> colorList) throws IOException {

		this.chartTitle = chartTitle;
		this.xAxisTitle = xAxisTitle;
		this.yAxisTitle = yAxisTitle;
		this.rowKeys = rowKeys;
		this.colorList = colorList;
		this.chartData = chartData;
		this.columnKeys = columnKeys;

		// Create the dataset:
		CategoryDataset dataset = createDataset();
		// Create the chart using the dataset:
		JFreeChart chart = createChart(dataset);
		//Save chart as PNG:
		ChartUtilities.saveChartAsPNG(new File(CVSConfig.TEMP_FS_STORE + "/" + generateRandomFileName() + ".png"), chart, CVSConfig.CHART_WIDTH, CVSConfig.CHART_HEIGHT);
	}

	public String getFileName() {
		return fileName;
	}

	private CategoryDataset createDataset() {
		return DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, chartData);
	}

	private String generateRandomFileName() {
		fileName = new BigInteger(130, random).toString(32);
		return fileName;
	}

	private JFreeChart createChart(CategoryDataset dataset) {

		final JFreeChart chart = ChartFactory.createStackedBarChart(
				chartTitle, // The chart title.
				xAxisTitle, // The X Axis title.
				yAxisTitle, // The Y Axis title.
				dataset, // The dataset to be used for the data.
				PlotOrientation.VERTICAL, // The orientation of the plot.
				true,
				true,
				false);

		CategoryPlot localCategoryPlot = (CategoryPlot) chart.getPlot();
		CategoryAxis localCategoryAxis = localCategoryPlot.getDomainAxis();
		StackedBarRenderer localStackedBarRenderer = (StackedBarRenderer) localCategoryPlot.getRenderer();

		// Coloring the background of the chart:
		localCategoryPlot.setBackgroundPaint(Color.WHITE);
		localCategoryPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		// Setting the colors of the bars:
		for (int i = 0; i < colorList.size(); i++) {
			localStackedBarRenderer.setSeriesPaint(i, colorList.get(i));
		}
		// Make the bars 2D:
		localStackedBarRenderer.setBarPainter(new StandardBarPainter());
		// Tilt the bar labels on Y axis by setting the angle:
		localCategoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.85D));
		// Setting the background of the chart:
		chart.setBackgroundPaint(Color.WHITE);
		// Remove the border of legend:
		chart.getLegend().setFrame(BlockBorder.NONE);

		return chart;
	}
}
