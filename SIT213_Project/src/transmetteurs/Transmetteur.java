package transmetteurs;

import sources.*;
import destinations.*;
import information.*;

import java.util.*;

/** 
 * Classe Abstraite d'un composant transmetteur d'informations dont
 * les elements sont de type R en entree et de type E en sortie;
 * l'entree du transmetteur implemente l'interface
 * DestinationInterface, la sortie du transmetteur implemente
 * l'interface SourceInterface
 * @author prou
 */
public abstract  class Transmetteur <R,E> implements  DestinationInterface <R>, SourceInterface <E> {

	/**
	 * Un getter retournant la liste des destinations connectees
	 * @return destinationsConnectees la liste des destinations connectees
	 */
	public LinkedList<DestinationInterface<E>> getDestinationsConnectees() {
		return destinationsConnectees;
	}


	/** 
	 * la liste des composants destination connectes en sortie du transmetteur 
	 */
	protected LinkedList <DestinationInterface <E>> destinationsConnectees;

	/** 
	 * l'information recue en entree du transmetteur 
	 */
	protected Information <R>  informationRecue;

	/** 
	 * l'information emise en sortie du transmetteur
	 */		
	protected Information <E>  informationEmise;

	/** 
	 * un constructeur factorisant les initialisations communes aux
	 * realisations de la classe abstraite Transmetteur
	 */
	public Transmetteur() {
		destinationsConnectees = new LinkedList <DestinationInterface <E>> ();
		informationRecue = null;
		informationEmise = null;
	}

	/**
	 * retourne la derniere information recue en entree du
	 * transmetteur
	 * @return informationRecue l'information recue  
	 */
	public Information <R>  getInformationRecue() {
		return informationRecue;
	}

	/**
	 * retourne la derniere information émise en sortie du
	 * transmetteur
	 * @return informationEmise l'information emise  
	 */
	public Information <E>  getInformationEmise() {
		return informationEmise;
	}

	/**
	 * connecter une destination a la sortie du transmetteur
	 * @param destination la destination a connecter
	 */
	public void connecter (DestinationInterface <E> destination) {
		destinationsConnectees.add(destination);
	}
	
	/**
	 * Un getter retournant la taille de la liste des destinations connectees
	 * @return le nombre de destinations connectees
	 */
	public int getNbDestinationsConnectees() {
		return destinationsConnectees.size();
	}

	/**
	 * deconnecte une destination de la sortie du transmetteur
	 * @param destination la destination a deconnecter
	 */
	public void deconnecter (DestinationInterface <E> destination) {
		destinationsConnectees.remove(destination); 
	}

	/**
	 * recoit une information.  Cette methode, en fin d'execution,
	 * appelle la methode emettre.
	 * @param information l'information recue
	 */
	public  abstract void recevoir(Information <R> information) throws InformationNonConformeException;

	/**
	 * emet l'information construite par le transmetteur
	 * @throws InformationNonConformeException l'exception pouvant etre levee
	 */
	public  abstract void emettre() throws InformationNonConformeException; 
	
	/**
	 * Un setter pour fixer l'information emise
	 * @param informationEmise l'inforamion que l'on veut emettre
	 */
	public void setInformationEmise(Information<E> informationEmise) {
		this.informationEmise = informationEmise;
	}
	
	/**
	 * Un setter pour fixer l'information recue
	 * @param informationRecue l'infroamtion que l'on recoit
	 */
	public void setInformationRecue(Information<R> informationRecue) {
		this.informationRecue = informationRecue;
	}
}