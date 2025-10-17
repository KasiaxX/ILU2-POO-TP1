package villagegaulois;

import java.util.Iterator;
import java.util.stream.Sink.ChainedDouble;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche; // les attributs sont pour stocker

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
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

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null) {
			throw new VillageSansChefException("Le village n’a pas encore de chef !");
		}
		
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	// class interne
	private class Marche {

		private Etal[] etals; // l'attribut (tableau) de type Etal;

		private Marche(int NbEtals) {
			etals = new Etal[NbEtals];
			for (int i = 0; i < NbEtals; i++) {
				etals[i] = new Etal();
				 
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++)
				if (etals[i].isEtalOccupe() == false) {
					return i;
				}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {

			int NbOccupe = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					NbOccupe++;
				}
			}

			Etal[] tab = new Etal[NbOccupe];
			int j = 0;

			for (int i = 0; i < NbOccupe; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					tab[j] = etals[i];
					j++;
				}
			}
			return tab;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			 
			int nbEtalsVide = 0;
			
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				}else{
					nbEtalsVide++;
				}
			}
			
			chaine.append("Il reste " + nbEtalsVide + " étals non utilisés dans le marché.\n");
			return chaine.toString();
		
		}	
	}
	
	//installerVendeur	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		
		int indiceEtalLibre = marche.trouverEtalLibre();
		
		if (indiceEtalLibre == -1) {
			chaine.append("Malheureusement, il n’y a plus d’étal libre pour " + vendeur.getNom() + ".\n");
		} else {
			marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (indiceEtalLibre + 1) + ".\n");
		}
		return chaine.toString();
	}
	
	// b) rechercherVendeursProduit
	public String rechercherVendeursProduit(String produit) {
			
		StringBuilder chaine = new StringBuilder();			
		Etal[] etalsTrouves = marche.trouverEtals(produit);

		//il y a pas un vendeur avec le produit
		if (etalsTrouves.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.\n");
		} 
		//il y a un vendeur avec le produit
		else if (etalsTrouves.length == 1) {
			chaine.append("Seul le vendeur " + etalsTrouves[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < etalsTrouves.length; i++) {
				chaine.append("- " + etalsTrouves[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
		
	// c) rechercherEtal  = trouver etal avec le vendeur 
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	// d) partirVendeur
	public String partirVendeur(Gaulois vendeur) {
		//marche.trouverVendeur(vendeur) retourne etal avec le vendeur
		Etal etal = marche.trouverVendeur(vendeur);
		if (etal != null) {
			return etal.libererEtal();
		}
		return vendeur.getNom() + " n’a pas d’étal au marché.\n";
	}
	
	// e) afficherMarche
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		
		chaine.append("Le marché du village \"" + nom + "\" possède plusieurs étals :\n");
		chaine.append(marche.afficherMarche());
		
		return chaine.toString();
	}

}	