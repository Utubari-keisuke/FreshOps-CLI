package model;

import java.time.LocalDate;

public class WasteLog {
	private int logId;
	private String itemName;
	private int wastedQuantity;
	private String unit;
	private int lossAmount;
	private LocalDate wastedDate;
	private String reason;

	public WasteLog(int logId, String itemName, int wastedQuantity, String unit, int lossAmount, LocalDate wastedDate,
			String reason) {
		this.logId = logId;
		this.itemName = itemName;
		this.wastedQuantity = wastedQuantity;
		this.unit = unit;
		this.lossAmount = lossAmount;
		this.wastedDate = wastedDate;
		this.reason = reason;
	}

	public int getLogId() {
		return logId;
	}

	public String getItemName() {
		return itemName;
	}

	public int getWastedQuantity() {
		return wastedQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public int getLossAmount() {
		return lossAmount;
	}

	public LocalDate getWastedDate() {
		return wastedDate;
	}

	public String getReason() {
		return reason;
	}
}