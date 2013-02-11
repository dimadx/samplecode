package StockDownloaderUtil;

import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.joda.time.*;

import YahooFinance.DataPoint;
import YahooFinance.YahooFinanceService;

public class StockDownloaderUtil {
	public static void main(String[] args) throws Exception {
		try {
			// Download and display stock quotes for Amazon, Inc.
			DataPoint[] dataPoints = downloadHistoricalDataFromYahoo("AMZN", new DateTime(2000, 1, 1, 0, 0), DateTime.now());

			System.out.println("Successfully downloaded stock quotes:");
			for (DataPoint dataPoint : dataPoints) {
				System.out.println(dataPoint);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Downloads historical stock quote data from Yahoo! Finance.
	 * @param ticker
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	private static DataPoint[] downloadHistoricalDataFromYahoo(String ticker,
			DateTime startDate, DateTime endDate) throws Exception {
		// Get URL first.
		String yahooUrl = YahooFinanceService.makeYahooUrl(ticker,
				startDate, endDate);
		
		// Download text.
		String text = downloadTextFromUrl(yahooUrl);
		
		// Parse text into data points.
		DataPoint[] dataPoints = YahooFinanceService
				.parseYahooDataPoints(text);
		return dataPoints;
	}

	/**
	 * Downloads file from a given URL, and returns it as a String.
	 * 
	 * @param url
	 * @return Contents of a file
	 * @throws Exception
	 */
	private static String downloadTextFromUrl(String url) throws Exception {
		HttpMethod getMethod = new GetMethod(url);
		int code = new HttpClient().executeMethod(getMethod);
		if (code != 200)
			throw new Exception("Unexpected response code from server: " + code);

		InputStream inputStream = getMethod.getResponseBodyAsStream();
		StringWriter stringWriter = new StringWriter();
		IOUtils.copy(inputStream, stringWriter);
		return stringWriter.toString();
	}
}
