/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GenerateurSignal;

/**
 * Interface à implémenter par les différents signaux : Permet d'appeler la méthode genrer concernée. (En fonction du signal)
 * @author Clément
 */
public interface SourceGenerateurInterface {
    /**
     * Permet de génerer un signal analogique à partir du bit concerné. (bit précédent et suivant pour information)
     * @param bit0 bit précédent (0 si on traite le premier bit du signal)
     * @param bit1 bit à générer
     * @param bit2 bit suivant (0 si on traite le dernier bit du signal)
     * @param echantillons nombre d'échantillons par bit
     * @param min valeur minimum du signal
     * @param max valeur maximum du signal
     * @return un tableau de valeurs numériques du signal analogique échantillonné.
     * @throws BadEntrySignal 
     */
    public float[] generer(boolean bitPrecedent, boolean bitActuel, boolean bitSuivant) throws BadEntrySignal ;
}
