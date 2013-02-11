package YahooFinance;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import au.com.bytecode.opencsv.CSVReader;

public class YahooFinanceService {
	/**
	 * Returns URL to request historical data from Yahoo! Finance.
	 * 
	 * @param ticker
	 *            Ticker
	 * @param startDate
	 *            Starting date
	 * @param endDate
	 *            Ending date
	 * @return
	 */
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

	/**
	 * Parses data historical stock quotes from Yahoo! Finance web service.
	 * 
	 * @param yahooDownloadedText
	 * @return List of data points
	 * @throws Exception
	 */
	public static DataPoint[] parseYahooDataPoints(String yahooDownloadedText)
			throws Exception {
		CSVReader csvReader = new CSVReader(new StringReader(
				yahooDownloadedText));

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
				throw new Exception("Unexpected number of columns: "
						+ row.length);

			String[] dataParts = row[0].split("-");
			int year = Integer.parseInt(dataParts[0]);
			int month = Integer.parseInt(dataParts[1]);
			int day = Integer.parseInt(dataParts[2]);
			DateTime dateTime = new DateTime(year, month, day, 0, 0);
			Double openPrice = Double.parseDouble(row[1]);
			Double highPrice = Double.parseDouble(row[2]);
			Double lowPrice = Double.parseDouble(row[3]);
			Double closePrice = Double.parseDouble(row[4]);
			Double volume = Double.parseDouble(row[5]);
			Double closePriceAdjusted = Double.parseDouble(row[6]);

			DataPoint dataPoint = new DataPoint(dateTime, openPrice, highPrice,
					lowPrice, closePrice, volume, closePriceAdjusted);
			dataPoints.add(dataPoint);
		}

		// Reverse the order.
		java.util.Collections.reverse(dataPoints);
				
		return dataPoints.toArray(new DataPoint[0]);
	}

}
