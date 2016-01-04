package controleur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class saisie{
public static String chaine() {
		String valeur;
		try {
			BufferedReader entree= new BufferedReader(new InputStreamReader(System.in));
			valeur = entree.readLine();
			return (valeur);
		}
		catch (IOException e){
			System.out.println("Probleme de lecture");
			return(" ");
		}
	}
	public static int entier (){
		String valeur;
		try {
			BufferedReader entree= new BufferedReader(new InputStreamReader(System.in));
			valeur = entree.readLine();
			int ent=Integer.parseInt(valeur,10);
			return ent;
		}
		catch (IOException e){
			System.out.println("Probleme de lecture");
			return(0);
		}
	}
}