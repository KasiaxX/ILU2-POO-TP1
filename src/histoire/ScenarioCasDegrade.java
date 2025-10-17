package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {
	
	public static void main(String[] args) {
		System.out.println("=== Test 1 : libérer un étal vide ===");
		try {
			Etal etal = new Etal();
			etal.libererEtal();
			System.out.println("Fin du test");
		} catch (IllegalStateException i) {
			System.err.println("Erreur détectée : " + i.getMessage());
		}

		System.out.println("\n=== Test 2 : acheter avec acheteur null ===");
		try {
			Etal etal = new Etal();
			etal.acheterProduit(5, null);
		} catch (Exception i) {
			System.err.println("Erreur : " + i);
		}

		System.out.println("\n=== Test 3 : quantité négative ===");
		try {
			Gaulois g = new Gaulois("Astérix", 8);
			Etal etal = new Etal();
			etal.occuperEtal(g, "potion", 10);
			etal.acheterProduit(-2, g);
		} catch (IllegalArgumentException i) {
			System.err.println("Erreur détectée : " + i.getMessage());
		}

		System.out.println("\n=== Test 4 : étal vide ===");
		try {
			Gaulois g = new Gaulois("Obélix", 25);
			Etal etal = new Etal(); // jamais occupé
			etal.acheterProduit(5, g);
		} catch (IllegalStateException i) {
			System.err.println("Erreur détectée : " + i.getMessage());
		}
	}

}
