/*
 * Genere un bruit blanc centre gaussien
 */
package GenerateurBruit;

import GenerateurSignal.BadEntrySignal;
import Information.Information;

/**
 *
 * @author manfred
 */
public class BruitGaussien implements DeformateurInformationInterface<Float> {
    
    private static final double pi = Math.PI ;
    
    private float snr;
    private boolean puissanceParSNR=false;
    private float puissance=0;

    /**
     * Methode pour renseigner le snr
     * @param snr Le snr du bruit gaussien
     */
    public void setSNR(float snr)
    {
        this.snr=snr;
        this.puissanceParSNR=true;
    }
    
    /**
     * Methode pour renseigner la puissance de bruit
     * @param puissance La puissance de bruit voulue
     */
    public void setPuissanceBruit(float puissance) throws BadEntrySignal
    {
        if(puissance < 0)
            throw new BadEntrySignal("La puissance ne peut pas être inférieur à 0");
        this.puissance=puissance;
        this.puissanceParSNR=false;
    }
    
    @Override
    public Information<Float> deformerInformation(Information<Float> signal) throws BadEntrySignal {
        
        if(signal==null || signal.nbElements() <= 0)
            throw new BadEntrySignal("Le nombre d'échantillons du bruit doit être strictement positif.");
        
        Information<Float> infoBruit = new Information<>();
        
        if(puissanceParSNR)
            puissance=this.calculPuissanceBruit(calculPuissanceSignal(signal));
        
        // genere une gaussienne selon la formule suivante : b = std * sqtr(-2 ln(1-a1)) * cos(2*pi*a2)
        float ecartType = (float) Math.sqrt(puissance) ;
        for(int i=0 ; i < signal.nbElements() ; i++){
            double aUn = Math.random() ;
            double aDeux = Math.random() ;
            
            infoBruit.add((float)(ecartType * Math.sqrt(-2*Math.log(1-aUn)) * Math.cos(2*pi * aDeux)) + signal.iemeElement(i));
        }
        return infoBruit ;
    }
    
    /**
     * Calcule la puissance du signal selon la formule Ps = (1/N) sum(Sn^2)
     * 
     * @param information : le signal dont on souhaite calculer la puissance
     * @throws BadEntrySignal : le signal ou une partie est null
     * @return la puissance du signal (en lineaire)
     */
    public static float calculPuissanceSignal(Information<Float> information) throws BadEntrySignal{
        float puissanceSignal = 0 ;
        if(information == null || information.nbElements()==0)
            throw new BadEntrySignal("Le signal est null.");
        for(int i=0 ; i < information.nbElements() ; i++){
            if(information.iemeElement(i)==null)
                throw new BadEntrySignal("Une partie du signal est null");
            
            puissanceSignal += Math.pow(information.iemeElement(i), 2);
        }
        puissanceSignal = puissanceSignal / (float)information.nbElements() ;
        return puissanceSignal ;
    }
    
    /**
     * Calcule la puissance de bruit necessaire pour correspondre au SNR donne
     * @param puissanceSignal en lineaire, doit-être >0
     * @throws BadEntrySignal : la puissance du signal doit etre > 0
     */
    public float calculPuissanceBruit(float puissanceSignal) throws BadEntrySignal
    {
        if(puissanceSignal < 0)
            throw new BadEntrySignal("La puissance du signal doit-être superieure a zero.");
        float puissanceBruit = (float) Math.pow(10, (Math.log10(puissanceSignal) - (this.snr/10.0)));
        if(puissanceBruit < 0)
            throw new BadEntrySignal("La puissance du signal doit-être superieure a zero.");
        
        return puissanceBruit;
    }
}
