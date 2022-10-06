package information;

import java.util.*;
import java.util.spi.LocaleServiceProvider;

import javax.lang.model.element.Element;

/** 
 *  
 * @author prou
 */	
public  class Information <T>  implements Iterable <T> {

	private LinkedList <T> content;

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
	
	public void setContent(LinkedList<T> content) {
		this.content = content;
	}
	
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
		
		infosCopie[infos.length+1]= (Information<Float>) this; //On ajoute l'information courante afin de l'ajouter egalement
		int tailleInformations = infos[0].nbElements();	//La taille du contenu des informations (toutes les informations doivent etre de la meme taille) 
		float aAjouter;	//variable qui additionnera tous les elements
		
		for(int indice = 0; indice < tailleInformations; indice++) {	//C'est ici qu'on additionne toutes les variables des differentes informations
			
			aAjouter = 0;
			
			for(Information<Float> infoCourante : infosCopie) {	//On ajoute les elements de toutes les informations entre eux
				aAjouter += infoCourante.iemeElement(0);
			}
			
			infoARetourner.add(aAjouter);	//On ajoute la somme a la fin de l'information a retourner
			
			for(Information<Float> infoCourante : infosCopie) {
				infoCourante.remove(0);
			}
			
		}
		return infoARetourner;
		
	}
	
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
	 * pour construire a  partir d'un tableau de T une information
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
	 * @return le ieme element de l'information
	 */
	public T iemeElement(int i) {
		return this.content.get(i);
	}

	/**
	 * pour modifier le ieme element d'une information
	 */
	public void setIemeElement(int i, T v) {
		this.content.set(i, v);
	}

	/**
	 * pour ajouter un element a la fin de l'information 
	 * @param valeur  l'element a rajouter
	 */
	public void add(T valeur) {
		this.content.add(valeur);
	}

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
