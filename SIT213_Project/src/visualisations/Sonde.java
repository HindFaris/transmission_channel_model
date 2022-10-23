package visualisations;

import information.Information;
import destinations.Destination;

/** 
 * Classe Abstraite d'un composant destination realisant un affichage
 * @author prou
 */
public  abstract class Sonde <T> extends Destination <T> {

	/**
	 * nom de la fenetre d'affichage
	 */   
	protected String nom;

	/**
	 * Le constructeur de la sonde
	 * @param nom  le nom de la fenetre d'affichage
	 */   
	public Sonde(String nom) {
		this.nom = nom;
	}

	/**
	 * Un getter pour le nom
	 * @return nom le nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Un setter pour le nom
	 * @param nom le nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * pour recevoir et afficher l'information transmise par la source
	 * qui nous est connectee
	 * @param information  l'information a recevoir
	 */   
	public abstract void recevoir(Information <T> information);     
}
