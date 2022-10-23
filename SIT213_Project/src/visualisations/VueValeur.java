package visualisations;

import javax.swing.*;
/** 
 * @author B. Prou
 * Updated by E. Cousin - 2021
 *
 */
public class VueValeur  extends Vue {

	private static final long serialVersionUID = 1917L;
	/**
	 * le jLabel en question
	 */
	private JLabel jLabel;


	/**
	 * Constructeur
	 * @param valeur la valeur
	 * @param nom le nom
	 */
	public  VueValeur (Object valeur, String nom) {   

		super(nom); 
		String s = " " + valeur;
		jLabel = new JLabel(s);

		int xPosition = Vue.getXPosition();
		int yPosition = Vue.getYPosition();
		setLocation(xPosition, yPosition);

		add(jLabel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);  
		setSize(300, 100);
		setVisible(true);  
		repaint();
	}


/**
 * Un getter retournant jLabel
 * @return jLabel
 */
	public JLabel getjLabel() {
		return jLabel;
	}


/**
 * un setter pour jLabel
 * @param jLabel le jLabel a set
 */
	public void setjLabel(JLabel jLabel) {
		this.jLabel = jLabel;
	}


/**
 * Un getter pour la version de l'UID 
 * @return	serialVersionUID la version de l'UID
 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
