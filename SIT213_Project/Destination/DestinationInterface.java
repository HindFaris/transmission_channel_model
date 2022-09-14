package Destination;

import Information.Information;
import Information.InformationNonConforme;

/** 
 * Interface d'un composant ayant le comportement d'une destination d'informations dont les �l�ments sont de type T 
 * @param <T> 
 * @author prou
 */
    public  interface DestinationInterface <T>  {   
   
   /**
    * pour obtenir la derni�re information re�ue par une destination.
    * @return une information   
    */  
       public Information <T>  getInformationRecue(); 
   	 
   /**
    * pour recevoir une information  de la source qui nous est connect�e 
    * @param information  l'information  � recevoir
    * @throws InformationNonConforme  
    */
       public void recevoir(Information <T> information) throws InformationNonConforme;
   	    
   
   }