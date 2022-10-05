package tests;

import org.junit.runner.JUnitCore;		
import org.junit.runner.Result;		
import org.junit.runner.notification.Failure;

/**
 * La classe definissant les tests, et execute tous les tests de classes individuelles d'affilees.
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
					SourceFixeTest.class, //Je ne comprends pas pourquoi il passe deux fois dans le constructeur de SourceFixe....
					SourceAleatoireTest.class,
					EmetteurTest.class,
					SignalTest.class,
					BruitTest.class,
					TransmetteurAnalogiqueBruiteTest.class,
					//TransmetteurAnalogiqueMultiTrajetsBruiteTest.class,
					RecepteurTest.class,
					SimulateurTest.class
					);
			
			for (Failure failure : result.getFailures()) {							
		         System.out.println(failure.toString()+"\n");
		      }
		    System.out.println("Result=="+result.wasSuccessful());	
		      
		   /* System.out.println("Testing SourceFixe \n");
			System.out.println("\n\n***************************************************************************************************\n");
			
			System.out.println("Testing SourceAleatoire \n");
			System.out.println("\n\n***************************************************************************************************\n");
			
			System.out.println("Testing Signal \n");
			System.out.println("\n\n***************************************************************************************************\n");
			
			System.out.println("Testing Bruit \n");
			System.out.println("\n\n***************************************************************************************************\n");
			
			System.out.println("Testing TransmetteurAnalogiqueBruite \n");
			System.out.println("\n\n***************************************************************************************************\n");
			
			System.out.println("Testing Recepteur \n");
			System.out.println("\n\n***************************************************************************************************\n");
			*/
			
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

