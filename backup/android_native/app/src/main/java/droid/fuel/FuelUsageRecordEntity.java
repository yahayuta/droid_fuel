package droid.fuel;

public class FuelUsageRecordEntity {

	private long rawdate = 0;
	private String date = null;
	private String odo = null;
	private String amount = null;
	private String price = null;
	private String perprice = null;
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the odo
	 */
	public String getOdo() {
		return odo;
	}
	/**
	 * @param odo the odo to set
	 */
	public void setOdo(String odo) {
		this.odo = odo;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the perprice
	 */
	public String getPerprice() {
		return perprice;
	}
	/**
	 * @param perprice the perprice to set
	 */
	public void setPerprice(String perprice) {
		this.perprice = perprice;
	}
	/**
	 * @return the rawdate
	 */
	public long getRawdate() {
		return rawdate;
	}
	/**
	 * @param rawdate the rawdate to set
	 */
	public void setRawdate(long rawdate) {
		this.rawdate = rawdate;
	}
}
