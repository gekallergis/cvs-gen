package se.customervalue.cvs.dependency.externalservice.JFreeChartGenerator.Charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import se.customervalue.cvs.common.CVSConfig;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

public class BarChart {
	private SecureRandom random = new SecureRandom();
	private String fileName;
	private String chartTitle;
	private String xAxisTitle;
	private String yAxisTitle;
	private String rowKey;
	private double[] chartData;
	private String[] columnKeys;
	private Color color;

	public BarChart(String chartTitle, String xAxisTitle, String yAxisTitle, String rowKey, String[] columnKeys, double[] chartData, Color color) throws IOException {

		this.chartTitle = chartTitle;
		this.xAxisTitle = xAxisTitle;
		this.yAxisTitle = yAxisTitle;
		this.rowKey = rowKey;
		this.chartData = chartData;
		this.color = color;
		this.columnKeys = columnKeys;

		// Create the dataset:
		CategoryDataset dataset = createDataset();
		// Create the chart:
		JFreeChart chart = createChart(dataset);
		// Save chart as PNG:
		ChartUtilities.saveChartAsPNG(new File(CVSConfig.TEMP_FS_STORE + "/" + generateRandomFileName() + ".png"), chart, CVSConfig.CHART_WIDTH, CVSConfig.CHART_HEIGHT);
	}

	public String getFileName() {
		return fileName;
	}

	private CategoryDataset createDataset() {

		// Create the dataset:
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < chartData.length; i++) {
			dataset.addValue(chartData[i], rowKey, columnKeys[i]);
		}
		return dataset;
	}

	private String generateRandomFileName() {
		fileName = new BigInteger(130, random).toString(32);
		return fileName;
	}

	private JFreeChart createChart(final CategoryDataset dataset) {

		// Create the chart:
		final JFreeChart chart = ChartFactory.createBarChart(
				chartTitle, // chart title
				xAxisTitle, // domain axis label
				yAxisTitle, // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
		);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		// set the background color for the chart...
		chart.setBackgroundPaint(Color.white);

		// Get a reference to the plot for further customisation...
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

		// Set the range axis to display integers only:
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBarPainter(new StandardBarPainter());
		renderer.setSeriesPaint(0, color);

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.85D));
		// Remove the border of legend:
		chart.getLegend().setFrame(BlockBorder.NONE);

		// OPTIONAL CUSTOMISATION COMPLETED.
		return chart;

	}
}
