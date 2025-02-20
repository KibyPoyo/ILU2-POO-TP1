package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nombreEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nombreEtals);		
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
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etals = marche.trouverEtals(produit);
		StringBuilder affichage = new StringBuilder();
		if (etals.length == 0) {
			affichage.append("Il n'y a pas de vendeur qui propose des " + produit + " au marche.\n");
		} else if (etals.length == 1) {
			affichage.append("Seul le vendeur " + etals[0].getVendeur() + " propose des " + produit + " au marche.\n");
		} else {
			affichage.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < etals.length; i++) {
				affichage.append("- " + etals[i].getVendeur() + "\n");
			}
		} 
		return affichage.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int quantite) {
		StringBuilder affichage = new StringBuilder();
		affichage.append(vendeur.getNom() + " cherche un endroit pour vendre " + quantite + " " + produit + ".\n");
	}
	
	private static class Marche {
		private Etal[] etals;
		
		public Marche(int nombreEtals) {
			etals = new Etal[nombreEtals];
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
			
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i] != null && !etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nombreEtalsRecherches = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i] != null && etals[i].contientProduit(produit)) {
					nombreEtalsRecherches++;
				}
			}		
			
			Etal[] etalsRecherches = new Etal[nombreEtalsRecherches];
			int j = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i] != null && etals[i].contientProduit(produit)) {
					etalsRecherches[j] = etals[i];
					j++;
				}
			}
			
			return etalsRecherches;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i] != null && etals[i].getVendeur() == gaulois){
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			int nbEtalVide = 0;
			StringBuilder affichage = new StringBuilder();
			for (int i = 0; i < etals.length; i++) {
				if (etals[i] == null){
					nbEtalVide++;
				} else {
					affichage.append(etals[i].afficherEtal());
				}
			}
			if (nbEtalVide != 0) {
				affichage.append("Il reste " + nbEtalVide + " �tals non utilis�s dans le march�.\n");
			}
			return affichage.toString();
		}
	}
	
}