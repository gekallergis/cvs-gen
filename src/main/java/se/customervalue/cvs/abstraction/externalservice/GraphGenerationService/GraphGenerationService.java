package se.customervalue.cvs.abstraction.externalservice.GraphGenerationService;

import java.io.IOException;
import java.util.List;
import java.awt.Color;

public interface GraphGenerationService {
	String createLineChart(String chartTitle, String xAxisTitle, String yAxisTitle, String[] rowKeys, String[] columnKeys, double[][] chartData, List<Color> colorList) throws IOException;
	String createBarChart(String chartTitle, String xAxisTitle, String yAxisTitle, String rowKey, String[] columnKeys, double[] chartData, Color color) throws IOException;
	String createStackedBarChart(String chartTitle, String xAxisTitle, String yAxisTitle, String[] rowKeys, String[] columnKeys, double[][] chartData, List<Color> colorList) throws IOException;
	String createDualLineBarChart(String chartTitle, String xAxisTitle, String yAxisTitle, String y2AxisTitle, String[] rowKeys, String[] columnKeys, double[][] chartData, List<Color> colorList) throws IOException;
}
