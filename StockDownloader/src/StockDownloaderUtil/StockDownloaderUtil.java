package StockDownloaderUtil;

import org.joda.time.*;

import YahooFinance.DataPoint;
import YahooFinance.StockQuoteDownloader;

public class StockDownloaderUtil {
	public static void main(String[] args) throws Exception {
		try {
			String uri = StockQuoteDownloader.makeYahooUrl("AMZN", new DateTime(2000, 1, 1, 0, 0),
					DateTime.now());
			String text = StockQuoteDownloader.downloadQuotesFromYahooUrl(uri);
			DataPoint[] dataPoints = StockQuoteDownloader.parseYahooDataPoints(text);
			System.out.println("Successfully downloaded stock quotes:");
			for (DataPoint dataPoint : dataPoints) {
				System.out
						.println(String.format("open=%.2f, high=%.2f, low=%.2f, close=%.2f, volume=%.2f, closeAdjusted=%f",
										dataPoint.openPrice,
										dataPoint.highPrice,
										dataPoint.lowPrice,
										dataPoint.closePrice, 
										dataPoint.volume,
										dataPoint.closePriceAdjusted));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
