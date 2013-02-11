package YahooFinance;

public class DataPoint {
	public final double openPrice;
	public final double closePrice;
	public final double highPrice;
	public final double lowPrice;
	public final double volume;
	public final double closePriceAdjusted;
	
	public DataPoint(double openPrice, double highPrice, double lowPrice, double closePrice, double volume, double closePriceAdjusted) {
		this.openPrice = openPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closePrice = closePrice;
		this.volume = volume;
		this.closePriceAdjusted = closePriceAdjusted;
	}
}
