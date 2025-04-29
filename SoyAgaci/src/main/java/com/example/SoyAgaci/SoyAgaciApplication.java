package com.example.SoyAgaci;

import com.example.SoyAgaci.Service.FamilyTreeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SoyAgaciApplication {
	public static void main(String[] args) {
		SpringApplication.run(SoyAgaciApplication.class, args);
		FamilyTreeService service = new FamilyTreeService();

		// Kök kişi oluştur
		service.createRootPerson(1, "Ahmet", "1970-01-01", "Erkek");

		// Eş ekle
		service.addSpouse(1, 2, "Ayşe", "1975-05-15", "Kadın");

		// Çocuk ekle
		service.addChild(1, 3, "Mehmet", "2000-10-20", "Erkek");

		// Ağacı konsola yazdır
		service.printTree();

	}
}