package Source;

import Information.Information;
import Information.InformationNonConforme;
import Destination.DestinationInterface;
   import java.util.*;

/** 
 * Classe Abstraite d'un composant source d'informations dont les �l�ments sont de type T 
 * @param <T> 
 * @author prou
 */
    public  abstract class Source <T> implements  SourceInterface <T> {
   
   
   /** 
   * la liste des composants destination connect�s 
   */
      protected LinkedList <DestinationInterface <T>> destinationsConnectees;
   
   /** 
   * l'information g�n�r�e par la source 
   */
      protected Information <T>  informationGeneree;
   	
   /** 
   * l'information �mise par la source 
   */
      protected Information <T>  informationEmise;
   	
   
   /** 
   * un constructeur factorisant les initialisations communes aux r�alisations de la classe abstraite Source 
   */
       public Source () {
         destinationsConnectees = new LinkedList <DestinationInterface <T>> ();
         informationGeneree = null;
         informationEmise = null;
      }
     
   /**
    * pour obtenir la derni�re information �mise par la source
	 * @return une information   
    */
       public Information <T>  getInformationEmise() {
         return this.informationEmise;
      }
   
   
   /**
    * pour connecter une  destination � la source 
    * @param destination  la destination � connecter
    */
       public void connecter (DestinationInterface <T> destination) {
         destinationsConnectees.add(destination); 
      }

   
   /**
    * pour d�connecter une  destination de la source 
    * @param destination  la destination � connecter
    */
       public void deconnecter (DestinationInterface <T> destination) {
         destinationsConnectees.remove(destination); 
      }
   
   /**
    * pour �mettre l'information  contenue dans la source  
    * @throws InformationNonConforme 
    */
       public  abstract void emettre() throws InformationNonConforme;
     
   }