package se.customervalue.cvs.dependency.externalservice.JFreeChartGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.customervalue.cvs.abstraction.externalservice.GraphGenerationService.GraphGenerationService;
import se.customervalue.cvs.dependency.externalservice.JFreeChartGenerator.Charts.BarChart;
import se.customervalue.cvs.dependency.externalservice.JFreeChartGenerator.Charts.DualLineBarChart;
import se.customervalue.cvs.dependency.externalservice.JFreeChartGenerator.Charts.LineChart;
import se.customervalue.cvs.dependency.externalservice.JFreeChartGenerator.Charts.StackedBarChart;

import java.io.IOException;
import java.util.List;
import java.awt.Color;

@Service
public class GraphGenerationServiceJFreeChart implements GraphGenerationService{
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public String createLineChart(String chartTitle, String xAxisTitle, String yAxisTitle, String[] rowKeys, String[] columnKeys, double[][] chartData, List<Color> colorList) throws IOException {
		LineChart chart = new LineChart(chartTitle, xAxisTitle, yAxisTitle, rowKeys, columnKeys, chartData, colorList);
		return chart.getFileName();
	}

	@Override
	public String createBarChart(String chartTitle, String xAxisTitle, String yAxisTitle, String rowKey, String[] columnKeys, double[] chartData, Color color) throws IOException {
		BarChart chart = new BarChart(chartTitle, xAxisTitle, yAxisTitle, rowKey, columnKeys, chartData, color);
		return chart.getFileName();
	}

	@Override
	public String createStackedBarChart(String chartTitle, String xAxisTitle, String yAxisTitle, String[] rowKeys, String[] columnKeys, double[][] chartData, List<Color> colorList) throws IOException {
		StackedBarChart chart = new StackedBarChart(chartTitle, xAxisTitle, yAxisTitle, rowKeys, columnKeys, chartData, colorList);
		return chart.getFileName();
	}

	@Override
	public String createDualLineBarChart(String chartTitle, String xAxisTitle, String yAxisTitle, String y2AxisTitle, String[] rowKeys, String[] columnKeys, double[][] chartData, List<Color> colorList) throws IOException {
		DualLineBarChart chart = new DualLineBarChart(chartTitle, xAxisTitle, yAxisTitle, y2AxisTitle, rowKeys, columnKeys, chartData, colorList);
		return chart.getFileName();
	}
}
