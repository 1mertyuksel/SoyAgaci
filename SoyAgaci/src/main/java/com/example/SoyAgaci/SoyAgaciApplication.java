package com.example.SoyAgaci;

import com.example.SoyAgaci.Service.FamilyTreeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SoyAgaciApplication {
	public static void main(String[] args) {
		SpringApplication.run(SoyAgaciApplication.class, args);

		FamilyTreeService service = new FamilyTreeService();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\n=== Soy Ağacı Yönetim Sistemi ===");
			System.out.println("1. Kök Kişi Oluştur");
			System.out.println("2. Eş Ekle");
			System.out.println("3. Çocuk Ekle");
			System.out.println("4. Kişi Sil");
			System.out.println("5. Soy Ağacını Yazdır");
			System.out.println("6. Çıkış");
			System.out.print("Seçiminizi yapın (1-6): ");

			int choice;
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Geçersiz giriş! Lütfen bir sayı girin.");
				continue;
			}

			switch (choice) {
				case 1:
					// Kök kişi oluştur
					System.out.print("Kişi ID'si: ");
					int rootId = getValidId(scanner);
					System.out.print("Ad: ");
					String rootName = scanner.nextLine();
					System.out.print("Doğum Tarihi (YYYY-MM-DD): ");
					String rootBirthDate = scanner.nextLine();
					System.out.print("Cinsiyet (Erkek/Kadın): ");
					String rootGender = scanner.nextLine();
					service.createRootPerson(rootId, rootName, rootBirthDate, rootGender);
					System.out.println("Kök kişi oluşturuldu: " + rootName);
					break;

				case 2:
					// Eş ekle
					System.out.print("Kişi ID'si: ");
					int personId = getValidId(scanner);
					System.out.print("Eş ID'si: ");
					int spouseId = getValidId(scanner);
					System.out.print("Eş Adı: ");
					String spouseName = scanner.nextLine();
					System.out.print("Eş Doğum Tarihi (YYYY-MM-DD): ");
					String spouseBirthDate = scanner.nextLine();
					System.out.print("Eş Cinsiyet (Erkek/Kadın): ");
					String spouseGender = scanner.nextLine();
					service.addSpouse(personId, spouseId, spouseName, spouseBirthDate, spouseGender);
					System.out.println("Eş eklendi: " + spouseName);
					break;

				case 3:
					// Çocuk ekle
					System.out.print("Ebeveyn ID'si: ");
					int parentId = getValidId(scanner);
					System.out.print("Çocuk ID'si: ");
					int childId = getValidId(scanner);
					System.out.print("Çocuk Adı: ");
					String childName = scanner.nextLine();
					System.out.print("Çocuk Doğum Tarihi (YYYY-MM-DD): ");
					String childBirthDate = scanner.nextLine();
					System.out.print("Çocuk Cinsiyet (Erkek/Kadın): ");
					String childGender = scanner.nextLine();
					service.addChild(parentId, childId, childName, childBirthDate, childGender);
					System.out.println("Çocuk eklendi: " + childName);
					break;

				case 4:
					// Kişi sil
					System.out.print("Silinecek Kişi ID'si: ");
					int removeId = getValidId(scanner);
					service.removePerson(removeId);
					break;

				case 5:
					// Soy ağacını yazdır
					System.out.println("\n=== Soy Ağacı ===");
					service.printTree();
					break;

				case 6:
					// Çıkış
					System.out.println("Programdan çıkılıyor...");
					scanner.close();
					return;

				default:
					System.out.println("Geçersiz seçim! Lütfen 1-6 arasında bir sayı girin.");
			}
		}
	}

	// Geçerli bir ID almak için yardımcı metod
	private static int getValidId(Scanner scanner) {
		while (true) {
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.print("Geçersiz ID! Lütfen bir sayı girin: ");
			}
		}
	}
}