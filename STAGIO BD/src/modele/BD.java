package modele;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import controleur.saisie;

public class BD {
	/**
	 * 
	 * @param url
	 * @return
	 */
	public static Connection openConnection(String url) {
		Connection co = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			co = DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			System.out.println("il manque le driver oracle");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("impossible de se connecter à l'url : " + url);
			System.exit(1);
		}
		return co;
	}

	/**
	 * 
	 * @param requete
	 * @param co
	 * @param type
	 * @return
	 */
	public static ResultSet exec1Requete(String requete, Connection co, int type) {
		ResultSet res = null;
		try {
			Statement st;
			if (type == 0) {
				st = co.createStatement();
			} else {
				st = co.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			}
			;
			res = st.executeQuery(requete);

		} catch (SQLException e) {
			System.out.println("Problème lors de l'exécution de la requete : "
					+ requete);
		}
		;
		return res;
	}

	/**
	 * 
	 * @param co
	 */
	public static void closeConnection(Connection co) {
		try {
			co.close();
			System.out.println("");
		} catch (SQLException e) {
			System.out.println("Impossible de fermer la connexion");
		}
	}

	/**
	 * Fonction pour effectuer une requête qui selectionne le nombre d'etudiant
	 * qui ont un stage
	 * 
	 * @param nbEtudiant
	 * @throws SQLException
	 */
	public static void nbEtuStageAnneeDonner(ArrayList<Integer> nbEtudiant)
			throws SQLException {
		/*
		 * Connection myconnexion = DriverManager.getConnection(url); Statement
		 * stmt = myconnexion.createStatement ( ); ResultSet rs =
		 * stmt.executeQuery ("SELECT * FROM STAGE");
		 * ResultSetMetaData rsmd = rs.getMetaData ( ); int numberOfColumns =
		 * rsmd.getColumnCount ( );
		 */
		
		String url = "jdbc:oracle:thin:jnadara/jnforce95@oracle.iut-orsay.fr:1521:etudom";
		Connection co = BD.openConnection(url);
		/*
		System.out
				.println("Veuillez saisir l'annee souhaite pour la recherche: \t");
		int annee = saisie.entier();

		String req = "SELECT COUNT(numEtudiant) FROM Stage WHERE annee="
				+ annee + "ORDER BY COUNT(numEtudiant) DESC";
		ResultSet rs = exec1Requete(req, co, 0);
		while (rs.next()) {
			nbEtudiant.add(rs.getInt(1));
		}*/
		System.out.println("Veuillez saisir l'annee souhaite pour la recherche: \t");
		Scanner sc=new Scanner(System.in);
		String annee=sc.nextLine();
		// Appel de la fonction PL_SQL
		CallableStatement cst = co.prepareCall(" {call nbEtu ( ?, ? ) } ");
		cst.setString(1, annee);
		cst.registerOutParameter (2,java.sql.Types.VARCHAR);
		cst.execute();
		nbEtudiant.add(cst.getInt(2));
		System.out.println("Prenom : "+nbEtudiant);
		cst.close();
		closeConnection(co);
		System.out
				.println("Récupération du nombre d'etudiant ayant un stage : OK !");

	}

	/**
	 * Fonction pour effectuer une requête qui selectionne le nombre d'etudiant pour l'année courrante
	 * qui ont un stage
	 * 
	 * @param nbEtudiant
	 * @throws SQLException
	 */
	public static void nbEtuStage(ArrayList<Integer> nbEtudiant)
			throws SQLException {
		/*
		 * Connection myconnexion = DriverManager.getConnection(url); Statement
		 * stmt = myconnexion.createStatement ( ); ResultSet rs =
		 * stmt.executeQuery ("SELECT * FROM ENS2004.EXEMPLAIRE");
		 * ResultSetMetaData rsmd = rs.getMetaData ( ); int numberOfColumns =
		 * rsmd.getColumnCount ( );
		 */
		String url = "jdbc:oracle:thin:jnadara/jnforce95@oracle.iut-orsay.fr:1521:etudom";
		Connection co = BD.openConnection(url);

		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		System.out.println("Les etudiants ayant un stage cette annee sont : \t");
		/*
		String req = "SELECT COUNT(numEtudiant) FROM Stage WHERE annee="
				+ curYear + "ORDER BY COUNT(numEtudiant) DESC";
		ResultSet rs = exec1Requete(req, co, 0);
		while (rs.next()) {
			nbEtudiant.add(rs.getInt(1));
		}
		closeConnection(co);
		*/
		System.out.println("Récupération du nombre d'etudiant ayant un stage cette annee : OK !");
		
		// Appel de la fonction PL_SQL
		CallableStatement cst = co.prepareCall(" {call nbEtuAnn ( ?, ?) } ");
		cst.setInt(1, curYear);
		cst.registerOutParameter (2,java.sql.Types.VARCHAR);
		cst.execute();
		nbEtudiant.add(cst.getInt(2));

		System.out.println("nb Etudiant : "+nbEtudiant);

		cst.close();
		closeConnection(co);
	}
	/**
	 * Récupére le nombre d'étudiants sans stage de cette année
	 * @param nbEtudiant
	 */
	public static void nbEtuSansStage(ArrayList<Integer> nbEtudiant){
		
	}
	public static void main(String[] args) throws SQLException {
		
	}
}
