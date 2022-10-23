package visualisations;

import java.util.*;
import javax.swing.*;

/** 
 * Une classe des plus palpitantes
 * @author B. Prou
 * Updated by E. Cousin - 2021
 *
 */	
public class Vue extends JFrame{
	private static LinkedList<Vue> lesVues=new LinkedList<Vue>();
	private static final long serialVersionUID = 1917L;

	protected  static int xPosition = 0;
	protected  static int yPosition = 0;
	private static int yDecalage = 200;
	
	/**
	 * Un getter pour les vues
	 * @return les vues
	 */
	public static LinkedList<Vue> getLesVues() {
		return lesVues;
	}

	/**
	 * un setter pour les vues
	 * @param lesVues les vues
	 */
	public static void setLesVues(LinkedList<Vue> lesVues) {
		Vue.lesVues = lesVues;
	}

	/**
	 * un getter pour la position en x
	 * @return xPosition la position en x
	 */
	public static int getxPosition() {
		return xPosition;
	}

	/**
	 * un setter pour la position en x
	 * @param xPosition la position en x
	 */
	public static void setxPosition(int xPosition) {
		Vue.xPosition = xPosition;
	}

	/**
	 * Un getter pour la position en y
	 * @return yPosition la position en y
	 */
	public static int getyPosition() {
		return yPosition;
	}

	/**
	 * un setter pour la position en y
	 * @param yPosition la position en y
	 */
	public static void setyPosition(int yPosition) {
		Vue.yPosition = yPosition;
	}

	/**
	 * un getter pour le decalage en y
	 * @return yDecalage le decalage en y
	 */
	public static int getyDecalage() {
		return yDecalage;
	}

	/**
	 * Un setter pour le decalage en y
	 * @param yDecalage le decalage en y
	 */
	public static void setyDecalage(int yDecalage) {
		Vue.yDecalage = yDecalage;
	}

	/**
	 * un getter pour la version UID
	 * @return serialVersionUID la version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * un getter pour la position en x
	 * @return la position en x
	 */
	public static int getXPosition() {
		xPosition += 0;
		return xPosition - 0;
	}  

	/**
	 * un getter pour la position en y
	 * @return la position en y
	 */
	public static int getYPosition() {
		yPosition += yDecalage;
		return yPosition - yDecalage;
	}  

	/**
	 * Le constructeur
	 * @param nom le nom
	 */
	public  Vue (String nom) {          
		super(nom);
		lesVues.add(this);
	}
	
	/**
	 * reinitialise la position en y
	 */
	public static void resetPosition(){
		yPosition = 0;
	}
	
	/**
	 * reinitialise la position en x
	 * @param x la position en x
	 */
	public static void setXPosition(int x){
		xPosition = x;
	}
	
	/**
	 * supprime les vues et reinitialise la position en y
	 */
	public static void kill(){
		for(Vue v:lesVues)
			v.dispose();
		lesVues.clear();
		resetPosition();
	}

}
