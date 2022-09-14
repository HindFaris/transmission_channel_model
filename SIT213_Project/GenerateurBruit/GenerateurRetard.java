/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GenerateurBruit;

import GenerateurSignal.BadEntrySignal;
import Information.Information;

/**
 * @author juliengambier
 */
/**
 * Classe g�n�rant une version retard�e d'un signal
 * @author Julien
 */
public class GenerateurRetard implements DeformateurInformationInterface <Float> {
    
    private final int[] echantillonsRetard;
    private final float[] attenuationRetard;
    
    public GenerateurRetard(int[] echantillons,float[] attenuation){
        
        this.attenuationRetard=attenuation;
        this.echantillonsRetard=echantillons;
        
        
    } 
    /**
     * M�thode de g�n�ration du signal retard�
     * @param signal Le signal contenant l'information � retarder.
     * @return Le signal d�cal� et att�nu�.
     */
   
    @Override
    public Information<Float> deformerInformation(Information signal) throws BadEntrySignal
    {
        float[] signalFinal;
        float[][] signauxDecales = new float[echantillonsRetard.length][];
        float[] signalRecu = new float[signal.nbElements()];
        int decalageMax=0;
        
        
        
        for(int i = 0 ; i < signal.nbElements() ; i++)
        {
            signalRecu[i]=(float)signal.iemeElement(i);
        }
        
        
        
        
        for(int i=0 ; i<this.echantillonsRetard.length ; i++)
        {
            if(echantillonsRetard[i]>decalageMax)
                decalageMax = echantillonsRetard[i];
            signauxDecales[i] = decalerSignal(signalRecu, echantillonsRetard[i], attenuationRetard[i]);
        }
        
        
        
        
        signalFinal = new float[signal.nbElements() + decalageMax];
        for(int i = 0 ; i<signalFinal.length ; i++)
        {
            signalFinal[i]= i<signalRecu.length ? signalRecu[i] : 0;
            for(int j=0 ; j<this.echantillonsRetard.length ; j++)
            {
                signalFinal[i]+= i<signauxDecales[j].length ? signauxDecales[j][i] : 0;
            }
        }
        
        
        
        
        Information<Float> infoSignalFinal = new Information<>();
        
        for(int i = 0 ; i<signalFinal.length ; i++)
        {
            infoSignalFinal.add((float)signalFinal[i]);
        }
        return infoSignalFinal;
    }
    /**
     * Methode pour decaler le signal
     * @param signal le signal a decaler
     * @param decalage le nombre d'echantillon de decalage 
     * @param amplitude l'amplitude du signal decale
     * @return les valeurs du signal avec le decalage
     */
    
    public static float[] decalerSignal(float[] signal, int decalage, float amplitude)
    {
        float[] signalDecale = new float[signal.length + decalage];
        
        for(int i=0 ; i<decalage ; i++)
        {
           signalDecale[i]=0;
        }
        
        for(int i=0; i<signal.length ; i++)
        {
            signalDecale[decalage+i]=signal[i]*amplitude;
        }
       
        return signalDecale;
    }
}
