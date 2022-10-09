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
	 * 
	 */
	public Tests () {}

	public static void TestReport(){
		
		System.out.println("Beginning of a new Test Report...  ");
		
		try {
			System.out.println("\nTesting each class individually before testing the whole process (Simulateur) ...");
			System.out.println("\n***************************************************************************************************\n");
			
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
					//TODO Adapter les tests multitrajets
					RecepteurTest.class
					//,SimulateurTest.class
					//TODO SimulateurTest a adapter
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
	 * @param args
	 */
	public static void main(String[] args) {
		TestReport();
	}

}

