package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	private static class Marche {
		private Etal[] etals;
		
		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit,
				int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			int indiceEtalLibre = -1;
			for (int i = 0; i < etals.length && indiceEtalLibre == -1; i++) {
				if (!etals[i].isEtalOccupe()) {
					indiceEtalLibre = i;
				}
			}
			return indiceEtalLibre;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalsRecherche = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbEtalsRecherche++;
				}
			}
			int indiceEtalRecherche = 0;
			Etal[] etalsRecherche = new Etal[nbEtalsRecherche];
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalsRecherche[indiceEtalRecherche] = etals[i];
					indiceEtalRecherche++;
				}
			}
			return etalsRecherche;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			Etal etalCherche = null;
			for (int i = 0; i < etals.length && etalCherche == null; i++) {
				if (etals[i].getVendeur().getNom().equals(gaulois.getNom())) {
					etalCherche = etals[i];
				}
			}
			return etalCherche;
		}
		
		private String afficherMarche() {
			String affichageMarche = "";
			int nbEtalsVide = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					affichageMarche += etals[i].afficherEtal();
				} else {
					nbEtalsVide++;
				}
			}
			return affichageMarche + "Il reste " + nbEtalsVide + " etals non utilises dans le marche.\n";
		}
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int indiceLibre = marche.trouverEtalLibre();
		if (indiceLibre == -1) {
			chaine.append("Il n'y a aucun etal libre!\n");
		} else {
			marche.utiliserEtal(indiceLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit +
					" a l'etal numero " + (indiceLibre + 1) + ".\n");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsAvecProduit = marche.trouverEtals(produit);
		if (etalsAvecProduit != null) {
			chaine.append("Les vendeurs qui proposent des fleurs sont :\n");
			for (int i = 0; i < etalsAvecProduit.length; i++) {
				chaine.append(" - " + etalsAvecProduit[i].getVendeur().getNom() + "\n");
			}
		} else {
			chaine.append("Aucun vendeur propose des " + produit + "!\n");
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etalTrouve = marche.trouverVendeur(vendeur);
		System.out.println(etalTrouve.afficherEtal());
		return etalTrouve;
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalOccupe = rechercherEtal(vendeur);
		return etalOccupe.libererEtal();	
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marche du village '" + this.getNom() + "' possede plusieurs etals :\n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
}