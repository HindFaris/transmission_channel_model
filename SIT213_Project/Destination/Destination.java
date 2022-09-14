package Destination;

import Information.Information;
import Information.InformationNonConforme;

/** 
 * Classe Abstraite d'un composant destination d'informations dont les �l�ments sont de type T 
 * @param <T> 
 * @author prou
 */
    public  abstract class Destination <T> implements DestinationInterface <T> {
   
   
   
   /** 
   * l'information re�ue par la destination 
   */
      protected Information <T>  informationRecue;
   	 
   /** 
   * un constructeur factorisant les initialisations communes aux r�alisations de la classe abstraite Destination 
   */
       public Destination() {
         informationRecue = null;
      }

   /**
    * pour obtenir la derni�re information re�ue par la destination
    * @return une information   
    */
       public Information  <T>  getInformationRecue() {
         return this.informationRecue;
      }
   	    
   /**
    * pour recevoir une information  de la source qui nous est connect�e 
    * @param information  l'information  � recevoir
    * @throws InformationNonConforme  
    */
       public  abstract void recevoir(Information <T> information) throws InformationNonConforme;  
   
   
   
   }