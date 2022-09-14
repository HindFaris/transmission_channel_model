/*
 * Ce transmetteur ajoute au signal un bruit
 */
package Transmetteur;

import Destination.DestinationInterface;
import GenerateurBruit.DeformateurInformationInterface;
import GenerateurSignal.BadEntrySignal;
import Information.Information;
import Information.InformationNonConforme;

/**
 *
 * @author manfred
 */
public class TransmetteurAnalogiqueBruite extends Transmetteur<Float, Float> {

    private DeformateurInformationInterface<Float> genDeformation ;
    
    /**
     * Constructeur de la classe.
     * @param noiseGen : le generateur de bruit
     * @throws BadEntrySignal : le generateur de bruit fourni au constructeur est null
     */
    public TransmetteurAnalogiqueBruite(DeformateurInformationInterface<Float> genDeformation) throws BadEntrySignal{
        super();
        
        if(genDeformation == null)
            throw new BadEntrySignal();
        this.genDeformation = genDeformation ;
    }
    
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConforme {
        informationRecue = new Information<>();
        if(information == null)
            throw new InformationNonConforme("L'information recue par le transmetteur est null.");
        for(int i = 0 ; i < information.nbElements() ; i++){
            if(information.iemeElement(i) == null)
                throw new InformationNonConforme("L'information recue par le transmetteur possede des echantillons null.");
            informationRecue.add((Float)information.iemeElement(i));
        }
        this.emettre();
    }

    @Override
    public void emettre() throws InformationNonConforme {
        informationEmise = new Information<>();
        try {        
            informationEmise = this.genDeformation.deformerInformation(informationRecue);
        } catch (BadEntrySignal ex) {
            throw new InformationNonConforme();
        }
        
        for(DestinationInterface <Float> di : destinationsConnectees){
            di.recevoir(informationEmise);
        }
    }
}
