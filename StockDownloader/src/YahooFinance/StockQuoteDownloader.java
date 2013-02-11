package YahooFinance;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;

import au.com.bytecode.opencsv.CSVReader;

public class StockQuoteDownloader {
	public static String makeYahooUrl(String ticker, DateTime startDate,
			DateTime endDate) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://ichart.finance.yahoo.com/table.csv");

		urlString.append("?s=" + ticker);
		urlString.append("&g=d");

		urlString.append("&a=" + startDate.getMonthOfYear());
		urlString.append("&b=" + startDate.getDayOfMonth());
		urlString.append("&c=" + startDate.getYear());

		urlString.append("&d=" + endDate.getMonthOfYear());
		urlString.append("&e=" + endDate.getDayOfMonth());
		urlString.append("&f=" + endDate.getYear());
		return urlString.toString();
	}

	public static String downloadQuotesFromYahooUrl(String url) throws Exception {
		HttpMethod getMethod = new GetMethod(url);
		int code = new HttpClient().executeMethod(getMethod);
		if (code != 200) {
			throw new Exception("Unexpected response code from server: " + code);
		}

		InputStream inputStream = getMethod.getResponseBodyAsStream();
		StringWriter stringWriter = new StringWriter();
		IOUtils.copy(inputStream, stringWriter);
		return stringWriter.toString();
	}

	public static DataPoint[] parseYahooDataPoints(String yahooDownloadedString)
			throws Exception {
		CSVReader csvReader = new CSVReader(new StringReader(
				yahooDownloadedString));

		List<DataPoint> dataPoints = new ArrayList<DataPoint>();

		// Skip headers.
		csvReader.readNext();

		// Read all rows.
		String[] row;
		while (true) {
			row = csvReader.readNext();
			if (row == null)
				break;
			if (row.length != 7)
				throw new Exception("Unexpeced number of columns: "
						+ row.length);

			String[] dataParts = row[0].split("-");
			int year = Integer.parseInt(dataParts[0]);
			int month = Integer.parseInt(dataParts[1]);
			int day = Integer.parseInt(dataParts[2]);
			DateTime date = new DateTime(year, month, day, 0, 0);
			Double openPrice = Double.parseDouble(row[1]);
			Double highPrice = Double.parseDouble(row[2]);
			Double lowPrice = Double.parseDouble(row[3]);
			Double closePrice = Double.parseDouble(row[4]);
			Double volume = Double.parseDouble(row[5]);
			Double closePriceAdjusted = Double.parseDouble(row[6]);

			DataPoint dataPoint = new DataPoint(openPrice, highPrice, lowPrice,
					closePrice, volume, closePriceAdjusted);
			dataPoints.add(dataPoint);
		}
		return dataPoints.toArray(new DataPoint[0]);
	}
}
