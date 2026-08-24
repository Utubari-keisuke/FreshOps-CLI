package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import model.StockItem;
import util.InputUtil;

public class StockService {
	private final List<StockItem> stockList = new ArrayList<>();
	private final WasteService wasteService;
	private int nextId = 1;

	public StockService(WasteService wasteService) {
		this.wasteService = wasteService;
	}

	// [1] 食材・仕込み品の登録 (Create)
	public void addStock() {
		System.out.println("\n--- [1] 食材・仕込み品の新規登録 ---");
		String name = InputUtil.readString("品名を入力: ");
		String category = InputUtil.readString("カテゴリ (例: 乳製品, シロップ, 茶葉): ");
		int quantity = InputUtil.readPositiveInt("数量を入力: ");
		String unit = InputUtil.readString("単位 (例: ml, g, 個): ");
		int unitPrice = InputUtil.readPositiveInt("単価 (1単位あたりの原価 ¥): ");
		LocalDate expirationDate = InputUtil.readDate("消費期限を入力");

		StockItem item = new StockItem(nextId++, name, category, quantity, unit, unitPrice, expirationDate);
		stockList.add(item);
		System.out.println(">> 「" + name + "」を登録しました。(ID: " + item.getId() + ")");
	}

	// [2] 在庫一覧・期限アラート表示 (Read)
	public void showAllStocks() {
		System.out.println("\n========================== 在庫・賞味期限一覧 ==========================");
		if (stockList.isEmpty()) {
			System.out.println("※現在、登録されている在庫はありません。");
			System.out.println("=======================================================================");
			return;
		}

		LocalDate today = LocalDate.now();
		System.out.printf("%-4s | %-14s | %-8s | %-8s | %-8s | %-10s | %s%n",
				"ID", "品名", "カテゴリ", "残数", "単価", "期限", "状態アラート");
		System.out.println("-----------------------------------------------------------------------");

		for (StockItem item : stockList) {
			long daysUntilExpire = ChronoUnit.DAYS.between(today, item.getExpirationDate());
			String alert;

			if (daysUntilExpire < 0) {
				alert = "【!! 期限切れ (要廃棄) !!】";
			} else if (daysUntilExpire == 0) {
				alert = "【本日中 (優先消費)】";
			} else if (daysUntilExpire <= 2) {
				alert = "【注意: 残り" + daysUntilExpire + "日】";
			} else {
				alert = "良好 (残り" + daysUntilExpire + "日)";
			}

			System.out.printf("%-4d | %-14s | %-8s | %-8s | %-8s | %-10s | %s%n",
					item.getId(),
					item.getName(),
					item.getCategory(),
					item.getQuantity() + item.getUnit(),
					"¥" + item.getUnitPrice(),
					item.getExpirationDate(),
					alert);
		}
		System.out.println("=======================================================================");
	}

	// [3] 在庫数量・期限の更新 (Update)
	public void updateStock() {
		System.out.println("\n--- [3] 在庫数量・期限の更新 ---");
		if (stockList.isEmpty()) {
			System.out.println("※在庫がありません。");
			return;
		}

		int targetId = InputUtil.readInt("更新する食材のIDを入力: ");
		StockItem item = findById(targetId);

		if (item == null) {
			System.out.println("※指定されたIDの食材が見つかりません。");
			return;
		}

		System.out.println("現在のデータ: " + item.getName() + " (残数: " + item.getQuantity() + item.getUnit() + ", 期限: "
				+ item.getExpirationDate() + ")");
		System.out.println("[1] 使用（数量減算）  [2] 数量上書き  [3] 期限変更  [0] キャンセル");
		int subChoice = InputUtil.readInt("操作番号を選択: ");

		switch (subChoice) {
		case 1 -> {
			int useQty = InputUtil.readPositiveInt("使用した数量を入力: ");
			if (useQty > item.getQuantity()) {
				System.out.println("※残数以上の数量は使用できません。");
			} else {
				item.setQuantity(item.getQuantity() - useQty);
				System.out.println(">> 数量を更新しました。(残数: " + item.getQuantity() + item.getUnit() + ")");
			}
		}
		case 2 -> {
			int newQty = InputUtil.readPositiveInt("新しい数量を入力: ");
			item.setQuantity(newQty);
			System.out.println(">> 数量を更新しました。");
		}
		case 3 -> {
			LocalDate newDate = InputUtil.readDate("新しい消費期限を入力");
			item.setExpirationDate(newDate);
			System.out.println(">> 期限を更新しました。");
		}
		case 0 -> System.out.println(">> 更新をキャンセルしました。");
		default -> System.out.println("※無効な選択です。");
		}
	}

	// [4] 廃棄処理 (Delete / 廃棄ログ記録)
	public void wasteStock() {
		System.out.println("\n--- [4] 廃棄処理の実行 ---");
		if (stockList.isEmpty()) {
			System.out.println("※在庫がありません。");
			return;
		}

		int targetId = InputUtil.readInt("廃棄する食材のIDを入力: ");
		StockItem item = findById(targetId);

		if (item == null) {
			System.out.println("※指定されたIDの食材が見つかりません。");
			return;
		}

		System.out.println("対象: " + item.getName() + " (残数: " + item.getQuantity() + item.getUnit() + ", 単価: ¥"
				+ item.getUnitPrice() + ")");
		int wasteQty = InputUtil.readPositiveInt("廃棄する数量を入力: ");

		if (wasteQty > item.getQuantity()) {
			System.out.println("※残数以上の数量は廃棄できません。");
			return;
		}

		String reason = InputUtil.readString("廃棄理由 (例: 期限切れ, 異物混入, 過剰仕込み): ");

		// 廃棄ログに記録
		wasteService.addWasteLog(item.getName(), wasteQty, item.getUnit(), item.getUnitPrice(), reason);

		// 残数の計算、0個になったら在庫リストから削除
		if (wasteQty == item.getQuantity()) {
			stockList.remove(item);
			System.out.println(">> 全量を廃棄し、在庫リストから削除しました。");
		} else {
			item.setQuantity(item.getQuantity() - wasteQty);
			System.out.println(">> 一部を廃棄し、残数を更新しました。(残数: " + item.getQuantity() + item.getUnit() + ")");
		}
		System.out.println(">> 廃棄ログへ記録完了 (損失額: ¥" + (wasteQty * item.getUnitPrice()) + ")");
	}

	private StockItem findById(int id) {
		for (StockItem item : stockList) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}
}