package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
		try {
			etalOccupe = false;
			StringBuilder chaine = new StringBuilder(
					"Le vendeur " + vendeur.getNom() + " quitte son etal, ");
			int produitVendu = quantiteDebutMarche - quantite;
			if (produitVendu > 0) {
				chaine.append(
						"il a vendu " + produitVendu + " parmi " + produit + ".\n");
			} else {
				chaine.append("il n'a malheureusement rien vendu.\n");
			}
			return chaine.toString();
		} catch (Exception NullPointerException) {
			return "L'etal est deja vide !";
		}
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'etal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'etal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
		try {
			if (acheteur == null) {
	            throw new NullPointerException("L'acheteur ne doit pas être null.");
			} else if (quantiteAcheter < 1) {
	            throw new IllegalArgumentException("La quantite achetée doit être supérieure ou egale à 1.");
	        } else if (vendeur == null) {
	            throw new IllegalStateException("L'etal doit etre occupe.");
			}
			
			StringBuilder chaine = new StringBuilder();
			chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
					+ " " + produit + " a " + vendeur.getNom());
			if (quantite == 0) {
				chaine.append(", malheureusement il n'y en a plus !");
				quantiteAcheter = 0;
			}
			if (quantiteAcheter > quantite) {
				chaine.append(", comme il n'y en a plus que " + quantite + ", "
						+ acheteur.getNom() + " vide l'etal de "
						+ vendeur.getNom() + ".\n");
				quantiteAcheter = quantite;
				quantite = 0;
			}
			if (quantite != 0) {
				quantite -= quantiteAcheter;
				chaine.append(". " + acheteur.getNom()
						+ ", est ravi de tout trouver sur l'etal de "
						+ vendeur.getNom() + "\n");
			}
			return chaine.toString();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return "";
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return "";
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return "";
		}
	}

	public boolean contientProduit(String produit) {
		return produit.equals(this.produit);
	}

}
