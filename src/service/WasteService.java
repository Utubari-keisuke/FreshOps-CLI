package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.WasteLog;

public class WasteService {
	private final List<WasteLog> wasteLogs = new ArrayList<>();
	private int nextLogId = 1;

	public void addWasteLog(String itemName, int quantity, String unit, int unitPrice, String reason) {
		int lossAmount = quantity * unitPrice;
		WasteLog log = new WasteLog(nextLogId++, itemName, quantity, unit, lossAmount, LocalDate.now(), reason);
		wasteLogs.add(log);
	}

	public void showWasteReport() {
		System.out.println("\n==================== 廃棄ロス集計レポート ====================");
		if (wasteLogs.isEmpty()) {
			System.out.println("※現在、廃棄履歴はありません。");
			System.out.println("============================================================");
			return;
		}

		int totalLoss = 0;
		System.out.printf("%-4s | %-16s | %-8s | %-10s | %-12s | %s%n",
				"ID", "品名", "数量", "損失額", "廃棄日", "理由");
		System.out.println("------------------------------------------------------------");

		for (WasteLog log : wasteLogs) {
			totalLoss += log.getLossAmount();
			System.out.printf("%-4d | %-16s | %-8s | %-10s | %-12s | %s%n",
					log.getLogId(),
					log.getItemName(),
					log.getWastedQuantity() + log.getUnit(),
					"¥" + String.format("%,d", log.getLossAmount()),
					log.getWastedDate(),
					log.getReason());
		}

		System.out.println("------------------------------------------------------------");
		System.out.printf("◆ 累計廃棄件数 : %d 件%n", wasteLogs.size());
		System.out.printf("◆ 累計損失総額 : ¥%,d%n", totalLoss);
		System.out.println("============================================================");
	}
}
