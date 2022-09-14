package Sonde;

import Information.*;

/** 
 * Classe r�alisant l'affichage (textuel) d'information compos�e d'�l�ments de type T
 * @param <T> 
 * @author prou
 */
    public class SondeTextuelle <T> extends Sonde <T> {
   
       /**
     *
     * @param nom
     */
    public SondeTextuelle(String nom) {
         super(nom);
      }
   
       public void recevoir (Information <T> information) { 		 		 	
         informationRecue = information;
         System.out.println(nom + " : " + information);
      }
   	 
   	
   
   
   }