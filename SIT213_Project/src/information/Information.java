package information;

import java.lang.reflect.Array;
import java.time.zone.ZoneOffsetTransitionRule;
import java.util.*;
import java.util.spi.LocaleServiceProvider;

import javax.lang.model.element.Element;
import javax.swing.table.TableColumn;

/** 
 * classe qui gere les informations 
 * @author prou
 */	
public  class Information <T>  implements Iterable <T> {

	private LinkedList <T> content;

	/**
	 * clone une liste
	 * @return une liste d'element
	 * @throws Exception probleme dans le clonage
	 */
	public LinkedList<T> cloneInformation() throws Exception{
		if(content.clone() instanceof LinkedList<?>) {
			@SuppressWarnings("unchecked")
			LinkedList<T> test = (LinkedList<T>)content.clone();
			return test;
		}
		else {
			throw new Exception();
		}
		
	}
	
	/**
	 * clone une liste de float dans un tableau de float
	 * @return tableau de float
	 */
	public Float[] clonerDansTableau() {
		Object[] tableauARetourner = content.toArray();
		Float[] tableauRetourne = Arrays.copyOf(tableauARetourner, tableauARetourner.length, Float[].class);
		return tableauRetourne;
	}	
	
	/**
	 * initialise un tableau vide de taille taux
	 * @param taux les retards
	 * @return tableau de taux
	 */
	public Float[] clonerDansTableau(int taux) {
		Object[] tableauACopier = content.toArray();
		Float[] tableauCopier = Arrays.copyOf(tableauACopier, tableauACopier.length, Float[].class); 
		Float[] tableauARetourner = new Float[content.size()+taux];
		int taille = content.size();
		for(int indice = 0; indice < taille; indice ++) {
			tableauARetourner[indice] = tableauCopier[indice];
		}
		for(int indice = taille; indice < taille + taux; indice ++) {
			tableauARetourner[indice] = 0f; 
		}		
		return tableauARetourner;
	}
	
	/**
	 * prend en parametre le contenu a mettre dans la liste
	 * @param content contenu a mettre dans la liste
	 */
	public void setContent(LinkedList<T> content) {
		this.content = content;
	}
	
	/**
	 * methode qui ajoute les tableaux de float et retourne une liste de float
	 * @param infos les tableaux de float
	 * @return Information de Float
	 */
	public Information<Float> addInformations(Information<Float>[] infos){
		
		Information<Float>[] infosCopie = new Information[infos.length+1];	//Copie des infos pour ne pas modifier celles passees en parametre
		Information<Float> infoARetourner = new Information<Float>();	//L'information qui correspondra a l'addition de toutes les informations
		
		for(int indice = 0; indice < infos.length; indice++) {	//On copie les informations en les clonants
			infosCopie[indice] = new Information<Float>();
			try {
				infosCopie[indice].setContent(infos[indice].cloneInformation());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		infosCopie[infos.length]= (Information<Float>) this; //On ajoute l'information courante afin de l'ajouter egalement
		int tailleInformations = infos[0].nbElements();	//La taille du contenu des informations (toutes les informations doivent etre de la meme taille) 
		float aAjouter;	//variable qui additionnera tous les elements
		
		for(int indice = 0; indice < tailleInformations; indice++) {	//C'est ici qu'on additionne toutes les variables des differentes informations
			
			aAjouter = 0;
			
			for(Information<Float> infoCourante : infosCopie) {	//On ajoute les elements de toutes les informations entre eux
				aAjouter += infoCourante.iemeElement(0);
			}
			
			infoARetourner.add(aAjouter);	//On ajoute la somme a la fin de l'information a retourner.
			
			for(Information<Float> infoCourante : infosCopie) {
				infoCourante.remove(0);
			}
			
		}
		return infoARetourner;
		
	}
	
	/**
	 * retourne contenu de la liste
	 * @return content le contenu de la liste
	 */
	public LinkedList<T> getContent() {
		return content;
	}

	/**
	 * pour construire une information vide
	 */
	public Information() {
		this.content = new LinkedList <T> (); 
	}

	/**
	 * pour construire a� partir d'un tableau de T une information
	 * @param content le tableau d'elements pour initialiser l'information construite
	 */
	public Information(T [] content) {
		this.content = new LinkedList <T> (); 
		for (int i = 0; i < content.length; i++) {
			this.content.addLast(content[i]);
		}
	}

	/**
	 * pour connaitre le nombre d'elements d'une information
	 * @return le nombre d'elements de l'information
	 */
	public int nbElements() {
		return this.content.size();
	}

	/**
	 * pour renvoyer un element d'une information
	 * @param i l'endroit a venir chercher
	 * @return le ieme element de l'information
	 */
	public T iemeElement(int i) {
		return this.content.get(i);
	}

	/**
	 * pour modifier le ieme element d'une information
	 * @param i l'element dans l'information
	 * @param v La valeur a positionner
	 */
	public void setIemeElement(int i, T v) {
		this.content.set(i, v);
	}

	/**
	 * pour ajouter un element a�la fin de l'information 
	 * @param valeur  l'element a rajouter
	 */
	public void add(T valeur) {
		this.content.add(valeur);
	}
	/**
	 * rajoute les trois valeur a l'information
	 * @param valeur1 1ere valeur
	 * @param valeur2 2eme valeur
	 * @param valeur3 3eme valeur
	 */
	public void add(T valeur1, T valeur2, T valeur3) {
		this.content.add(valeur1);
		this.content.add(valeur2);
		this.content.add(valeur3);
	}

	/**
	 * pour comparer l'information courante avec une autre information
	 * @param o  l'information  avec laquelle se comparer
	 * @return "true" si les 2 informations contiennent les memes
	 * elements aux memes places; "false" dans les autres cas
	 */	 
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (!(o instanceof Information))
			return false;
		Information <T> information =  (Information <T>) o;
		if (this.nbElements() != information.nbElements())
			return false;
		for (int i = 0; i < this.nbElements(); i++) {
			if (!this.iemeElement(i).equals(information.iemeElement(i)))
				return false;
		}
		return true;
	}

	/**
	 * pour afficher une information
	 */
	public String toString() {
		String s = "";
		for (int i = 0; i < this.nbElements(); i++) {
			s += " " + this.iemeElement(i);
		}
		return s;
	}

	/**
	 * On viens enlever un element de l'information
	 * @param index l'element de l'information a enlever
	 */
	public void remove(int index) {
		content.remove(index);
	}
	
	/**
	 * pour utilisation du "for each" 
	 */
	public Iterator <T> iterator() {
		return content.iterator();
	}
}
