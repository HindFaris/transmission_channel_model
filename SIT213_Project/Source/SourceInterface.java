package Source;


import Information.Information;
import Information.InformationNonConforme;
import Destination.DestinationInterface;

/** 
 * Interface d'un composant ayant le comportement d'une source d'informations dont les �l�ments sont de type T 
 * @param <T> 
 * @author prou
 */
    public interface SourceInterface <T>  {
   
   /**
    * pour obtenir la derni�re information �mise par une source.
    * @return une information   
    */
       public Information <T>  getInformationEmise();
   
   /**
    * pour connecter une  destination à la source 
    * @param destination  la destination à connecter
	 */
       public void connecter (DestinationInterface <T> destination);
   
   /**
    * pour mettre l'information  contenue dans une source  
    * @throws InformationNonConforme 
    */
       public void emettre() throws InformationNonConforme; 
   
   
   }