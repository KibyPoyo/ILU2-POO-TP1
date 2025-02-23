package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois asterix = new Gaulois("Asterix", 8);
		System.out.println(etal.acheterProduit(3, asterix));
		System.out.println("Fin du test");
	}
}
