/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GenerateurSignal;

/**
 * Interface à implémenter par les différents signaux : Permet d'appeler la méthode decoder concernée. (En fonction du signal)
 * @author Clément
 */
public interface DestinationGenerateurInterface  {
    /**
     * Permet d'obtenir une valeur binaire à partir d'un tableau de valeurs numériques. (Décodeur analogique numérique)
     * @param tableauDeFloats tableau de valeurs numériques (échantillons du signal analogique)
     * @param min valeur maximum du signal
     * @param max valeur minimum du signal
     * @return un booléen correspondant à la valeur binaire du tableau d'échantillons
     * @throws BadEntrySignal 
     */
    public boolean decoder(float[] tableauDeFloats) throws BadEntrySignal ;
}
