/* =============================================================
   Object Oriented Programming
   Author: Miguel Marines
   Activity: Pokemon (Inheritance and Polymorphism)
   =========================================================== */

// Library
import java.util.ArrayList;

// Class
public class TestPokemon
{
	public static void main(String[] args)
	{
		// Array
		ArrayList<Pokemon> arrayPokemon = new ArrayList<Pokemon>();
		
		// Creation of objects.
		Pokemon pokemon = new Pokemon();
		Pokemon firePokemon = new FirePokemon();
		Pokemon waterPokemon = new WaterPokemon();
		Pokemon electricPokemon = new ElectricPokemon();

		// Add the created objects to the arrayPokemon.
		arrayPokemon.add(pokemon);
		arrayPokemon.add(firePokemon);
		arrayPokemon.add(waterPokemon);
		arrayPokemon.add(electricPokemon);

		// Loop
		for(int i = 0; i < arrayPokemon.size(); i++)
		{
			// Print the elements from the array and and method to attack.
			System.out.println(arrayPokemon.get(i).attack());	
		}
	}

}