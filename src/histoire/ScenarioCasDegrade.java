package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {
	
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois acheteur = new Gaulois("Acheteur", 0);
		etal.libererEtal();
		try {
			etal.acheterProduit(10, acheteur);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		System.out.println("Fin du test");
	}

}
