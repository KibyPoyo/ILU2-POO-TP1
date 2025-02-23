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

	public class VillageSansChefException extends Exception {
	    public VillageSansChefException(String message) {
	        super(message);
	    }
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
		if (nom == null) {
            throw new VillageSansChefException("Village sans chef");
		}
		
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
			affichage.append("Seul le vendeur " + etals[0].getVendeur().getNom() + " propose des " + produit + " au marche.\n");
		} else {
			affichage.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < etals.length; i++) {
				affichage.append("- " + etals[i].getVendeur().getNom() + "\n");
			}
		} 
		return affichage.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder affichage = new StringBuilder();
		affichage.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int etalLibreNumero = marche.trouverEtalLibre();
		if (etalLibreNumero == -1) {
			affichage.append("Il n'y a plus d'etals disponnibles.\n");
		} else {
			affichage.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " a l'etal n°" + (etalLibreNumero+1) + ".\n");
			marche.utiliserEtal(etalLibreNumero,vendeur,produit,nbProduit);
		}
		return affichage.toString();
	}
	
	public Etal rechercherEtal(Gaulois gaulois) {
		return marche.trouverVendeur(gaulois);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		String affichage = etal.libererEtal();
		return affichage;
	}
	
	public String afficherMarche() {
		StringBuilder affichage = new StringBuilder();
		affichage.append("Le marche du village \"" + this.getNom() + "\" possede plusieurs etals :\n");
		affichage.append(marche.afficherMarche());
		return affichage.toString();
	}
		
	private static class Marche {
		private Etal[] etals;
		
		public Marche(int nombreEtals) {
			etals = new Etal[nombreEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
			
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nombreEtalsRecherches = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nombreEtalsRecherches++;
				}
			}		
			
			Etal[] etalsRecherches = new Etal[nombreEtalsRecherches];
			int j = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsRecherches[j] = etals[i];
					j++;
				}
			}
			
			return etalsRecherches;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois){
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			int nbEtalVide = 0;
			StringBuilder affichage = new StringBuilder();
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()){
					nbEtalVide++;
				} else {
					affichage.append(etals[i].afficherEtal());
				}
			}
			if (nbEtalVide != 0) {
				affichage.append("Il reste " + nbEtalVide + " etals non utilises dans le marche.\n");
			}
			return affichage.toString();
		}
	}
	
}