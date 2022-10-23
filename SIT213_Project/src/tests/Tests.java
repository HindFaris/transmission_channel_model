package tests;

import org.junit.runner.JUnitCore;		
import org.junit.runner.Result;		
import org.junit.runner.notification.Failure;

/**
 * La classe execute toutes les classes de tests unitaire, d'un seul coup.
 * Elle affiche chaque erreur JUnit en liste, avec leur raison presumee.
 * @author Cudennec Gael
 *
 */
public class Tests {
	
	/**
	 * a
	 */
	public Tests () {}

	/**
	 * teste global
	 */
	public static void TestReport(){
		
		System.out.println("Beginning of a new Test Report...  ");
		
		try {
			System.out.println("\nTesting each class individually before testing the whole process (Simulateur) ...\n");
			
			Result result = JUnitCore.runClasses(
					SourceFixeTest.class, 
					SourceAleatoireTest.class,
					CodeurTest.class,
					DecodeurTest.class,
					EmetteurTest.class,
					SignalTest.class,
					BruitTest.class,
					TransmetteurAnalogiqueBruiteTest.class,
					TransmetteurAnalogiqueMultiTrajetsBruiteTest.class,
					RecepteurTest.class,
					SimulateurTest.class
					);
			
			for (Failure failure : result.getFailures()) {							
		         System.out.println(failure.toString()+"\n");
		      }
		    System.out.println("Result=="+result.wasSuccessful());	
			
		}catch (Exception e) {
			System.out.println("Err : Unexpected exception : " + e);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Lance test()
	 * @param args arguments du main
	 */
	public static void main(String[] args) {
		TestReport();
	}

}

