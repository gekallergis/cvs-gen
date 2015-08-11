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
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import se.customervalue.cvs.common.CVSConfig;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;


public class LineChart {
	private SecureRandom random = new SecureRandom();
	private String fileName;
	private String chartTitle;
	private String xAxisTitle;
	private String yAxisTitle;
	private String[] rowKeys;
	private String[] columnKeys;
	private double[][] chartData;
	private List<Color> colorList;

	public LineChart(String chartTitle, String xAxisTitle, String yAxisTitle, String[] rowKeys, String[] columnKeys, double[][] chartData, List<Color> colorList) throws IOException {
		this.chartTitle = chartTitle;
		this.xAxisTitle = xAxisTitle;
		this.yAxisTitle = yAxisTitle;
		this.rowKeys = rowKeys;
		this.columnKeys = columnKeys;
		this.chartData = chartData;
		this.colorList = colorList;

		// Create the dataset:
		CategoryDataset dataset = createDataset();
		// Create the chart:
		JFreeChart chart = createChart(dataset);
		//Save chart as PNG:
		ChartUtilities.saveChartAsPNG(new File(CVSConfig.TEMP_FS_STORE + "/" + generateRandomFileName() + ".png"), chart, CVSConfig.CHART_WIDTH, CVSConfig.CHART_HEIGHT);

	}

	public String getFileName() {
		return fileName;
	}

	private CategoryDataset createDataset() {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < chartData.length; i++) {
			for (int y = 0; y < chartData[i].length; y++) {
				dataset.addValue(chartData[i][y], rowKeys[i], columnKeys[y]);
			}
		}
		return dataset;
	}

	private String generateRandomFileName() {
		fileName = new BigInteger(130, random).toString(32);
		return fileName;
	}

	private JFreeChart createChart(CategoryDataset dataset) {

		// Create the chart:
		JFreeChart chart = ChartFactory.createLineChart(
				chartTitle, // chart title
				xAxisTitle, // x axis label
				yAxisTitle, // y axis label
				dataset, // data
				PlotOrientation.VERTICAL,
				true, // include legend
				true, // tooltips
				false // urls
		);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		chart.setBackgroundPaint(Color.WHITE);

		// Get a reference to the plot for further customisation:
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		CategoryItemRenderer renderer = plot.getRenderer();
		CategoryAxis localCategoryAxis = plot.getDomainAxis();
		NumberAxis localNumberAxis = (NumberAxis) plot.getRangeAxis();
		LineAndShapeRenderer localLineAndShapeRenderer = (LineAndShapeRenderer) plot.getRenderer();

		// Set the color of the background and the grid:
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		// Set the units in the Y axis:
		localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		localLineAndShapeRenderer.setDrawOutlines(true);
		localLineAndShapeRenderer.setUseFillPaint(true);

		// Make the lines visible:
		renderer.setSeriesVisible(0, true);
		renderer.setSeriesVisible(1, true);

		// Set the colors of the lines:
		for (int i = 0; i < colorList.size(); i++) {
			renderer.setSeriesPaint(i, colorList.get(i));
		}
		// Set the thickness of the lines:
		BasicStroke wideLine = new BasicStroke(3.5f);
		for (int i = 0; i < colorList.size(); i++) {
			renderer.setSeriesStroke(i, wideLine);
		}

		localCategoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.85D));
		// Change the auto tick unit selection to integer units only:
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// Remove the border of legend:
		chart.getLegend().setFrame(BlockBorder.NONE);

		// OPTIONAL CUSTOMISATION COMPLETED.
		return chart;
	}
}
