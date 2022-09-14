package Recepteurs;

import Destination.DestinationInterface;
import GenerateurSignal.BadEntrySignal;
import GenerateurSignal.DestinationGenerateurInterface;
import Information.Information;
import Information.InformationNonConforme;
import Transmetteur.Transmetteur;


/**
 *
 * @author juliengambier
 */
/** 
 * Classe qui simule un convertisseur analogique numerique
 */
public class RecepteurAnalogique extends Transmetteur<Float,Boolean>{

        private int pas;
	private DestinationGenerateurInterface gen;
        
	/** 
	 * Constructeur de Recepteur Analogique
	 * @param pas le nombre d'echantillon par bit
	 * @param gen le generateur avec lequel le signal est genere
	 */
	public RecepteurAnalogique(DestinationGenerateurInterface gen, int pas) throws BadEntrySignal{
            if(pas <= 0)
                throw new BadEntrySignal("Construction du recepteur analogique : le nombre d'echantillons est <= 0");
            if(gen == null)
                throw new BadEntrySignal("Construction du recepteur analogique : DestinationGenerateurInterface null");

            this.pas=pas;
            this.gen=gen;
	}
    
	@Override
	/** 
	 * Methode de reception et de traduction du signal
	 * @param information Information a decoder
	 */
	public void recevoir(Information<Float> information) throws InformationNonConforme{            
            this.informationRecue = new Information<>();
            this.informationEmise = new Information<>();
            
            if(information == null)
                throw new InformationNonConforme("Erreur : la chaine en entree du recepteur est null.");
            
            for(int i=0 ; i < information.nbElements() ; i++){
                if(information.iemeElement(i) == null){
                    throw new InformationNonConforme("Erreur : Une partie de la"
                       + " chaine en entree du recepteur est null.");
                }
            }
            
            int sampleSize=information.nbElements()/pas;
            
            float[] code;
            
            for(int i=0;i<sampleSize;i++){
                code = new float[pas];
			
                for(int j=0;j<pas;j++)
                    code[j]=information.iemeElement((i*pas)+j);
            
                try{
                    informationEmise.add(gen.decoder(code));
                }catch (BadEntrySignal e){
                    throw new InformationNonConforme();
                }
            }
	this.emettre();
        }
    
	/** 
	 * Methode de transmission du signal a la brique suivante
	 */
	@Override
	public void emettre() throws InformationNonConforme {
            for(DestinationInterface<Boolean> dest : this.destinationsConnectees)
                if(dest != null)
                    dest.recevoir(informationEmise);
	}
}