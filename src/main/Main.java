package main;

import service.StockService;
import service.WasteService;
import util.InputUtil;

public class Main {
	public static void main(String[] args) {
		WasteService wasteService = new WasteService();
		StockService stockService = new StockService(wasteService);

		System.out.println("**************************************************");
		System.out.println("  店舗食材・賞味期限＆廃棄ロス管理システム (FreshOps)  ");
		System.out.println("**************************************************");

		boolean running = true;
		while (running) {
			printMenu();
			int choice = InputUtil.readInt("メニュー番号を入力してください: ");

			switch (choice) {
			case 1 -> stockService.addStock();
			case 2 -> stockService.showAllStocks();
			case 3 -> stockService.updateStock();
			case 4 -> stockService.wasteStock();
			case 5 -> wasteService.showWasteReport();
			case 0 -> {
				System.out.println("\n>> システムを終了します。お疲れ様でした！");
				running = false;
			}
			default -> System.out.println("0〜5の有効なメニュー番号を入力してください。");
			}
		}
	}

	private static void printMenu() {
		System.out.println("\n==================================================");
		System.out.println("[1] 食材・仕込み品の登録 (Create)");
		System.out.println("[2] 在庫一覧・期限アラート表示 (Read)");
		System.out.println("[3] 在庫数量・期限の更新 (Update)");
		System.out.println("[4] 廃棄処理の実行 (Delete / ログ記録)");
		System.out.println("[5] 廃棄ロス集計レポート (Report)");
		System.out.println("[0] 終了");
		System.out.println("==================================================");
	}
}
