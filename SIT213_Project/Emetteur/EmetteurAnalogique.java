package Emetteur;

import Destination.DestinationInterface;
import GenerateurSignal.BadEntrySignal;
import GenerateurSignal.SourceGenerateurInterface;
import Information.Information;
import Information.InformationNonConforme;
import Transmetteur.Transmetteur;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Cette classe convertit un signal logique en un signal analogique
 * @author manfred
 */
public class EmetteurAnalogique extends Transmetteur<Boolean, Float> {
    
    private int nbEch ;
    private SourceGenerateurInterface gen ;

    /**
     * Recupere les parametres du signal a generer
     * @param gen_ : le type de codage du signal (NRZ, RZ, NRZT)
     * @param min_ : le seuil d'amplitude minimum
     * @param max_ : le seuil d'amplitude maximum
     * @param nbEch_ : le nombre d'echantillons par symbole
     */
    public EmetteurAnalogique(SourceGenerateurInterface gen_, int nbEch_) throws BadEntrySignal{
        if(gen_ == null)
            throw new BadEntrySignal();

        gen = gen_ ;
        nbEch = nbEch_;
        
        informationRecue = new Information<>();
    }
    
    /**
     * Recupere le signal logique provenant de la source
     */
    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConforme {
        informationRecue = new Information<>();
        for(int i=0 ; i < information.nbElements() ; i++){
            if(information.iemeElement(i) != null){
                informationRecue.add(information.iemeElement(i));
            }else{
               throw new InformationNonConforme("Erreur : la chaine ou une "
                       + "partie de la chaine en entree de l'emetteur est nulle.");
            }
        }
        this.emettre();
    }

    /**
     * Utilise le Generateur de Signal pour convertir le signal et l'envoyer aux destinations connectees
     */
    @Override
    public void emettre() throws InformationNonConforme {
        float[] temp = new float[nbEch];
        informationEmise = new Information<>();
        
        boolean bitPrecedent = false;
        boolean bitActuel ;
        boolean bitSuivant ;
        
        for(int i=0 ; i < informationRecue.nbElements() ; i++)
        {            
            bitActuel = informationRecue.iemeElement(i);
            
            if(i==informationRecue.nbElements()-1){
                bitSuivant = false;
            }else{
                bitSuivant = informationRecue.iemeElement(i+1);
            }
            
            try {
                // On convertit un boolean en signal
                temp = gen.generer(bitPrecedent, bitActuel, bitSuivant);
            } catch (BadEntrySignal ex) {
                throw new InformationNonConforme();
            }
            
            // On place le signal analogique dans informationEmise
            for(int j=0 ; j < nbEch ; j++){
                informationEmise.add(temp[j]);
            }
            
            // On décale les bits
            bitPrecedent = bitActuel;
        }
        
        // Pour chaque destination connectée
        for(DestinationInterface<Float> d: destinationsConnectees){
            d.recevoir(informationEmise);
        }
    }
}