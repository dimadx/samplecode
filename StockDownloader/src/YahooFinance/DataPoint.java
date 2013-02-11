package YahooFinance;

import org.joda.time.DateTime;

public class DataPoint {
	public final DateTime dateTime;
	public final double openPrice;
	public final double highPrice;
	public final double lowPrice;
	public final double closePrice;
	public final double volume;
	public final double closePriceAdjusted;

	public DataPoint(DateTime dateTime, double openPrice, double highPrice,
			double lowPrice, double closePrice, double volume,
			double closePriceAdjusted) {
		this.dateTime = dateTime;
		this.openPrice = openPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closePrice = closePrice;
		this.volume = volume;
		this.closePriceAdjusted = closePriceAdjusted;
	}

	@Override
	public String toString() {
		return String
				.format("date=%s, open=%.2f, high=%.2f, low=%.2f, close=%.2f, volume=%.2f, closeAdjusted=%.4f",
						dateTime.toString("yyyy-MM-dd"), openPrice, highPrice,
						lowPrice, closePrice, volume, closePriceAdjusted);
	}
	
	// TODO: Add hashCode(), equals()
}
