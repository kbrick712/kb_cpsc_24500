package baseball_standings_analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.*;
import java.util.*;

public class baseball_standings_analyzer {
	
	public static int displayMenu(Scanner sc) {
		
		System.out.println("Which standings would you like to see?\n");
		System.out.println("1. AL East\n");
		System.out.println("2. AL Central\n");
		System.out.println("3. AL West\n");
		System.out.println("4. NL East\n");
		System.out.println("5. NL Central\n");
		System.out.println("6. NL West\n");
		System.out.println("7. Overall\n");
		System.out.println("8. Exit\n");
		System.out.println("Enter the number of your choice: ");
		int choice = sc.nextInt();
		
		return choice;
		
	}
	
	public static ArrayList<String[]> getGB(ArrayList<String[]> teamlist) {
		
		String[] specificteam;
		double GB;
		ArrayList<String[]> sortedfinal = new ArrayList<String[]>();
		String[] firstplace = teamlist.get(0);
		
		for (int i=0; i<teamlist.size(); i++) {
			specificteam = new String[5];
			String[] teamstats = teamlist.get(i);
			specificteam[0] = teamstats[0];
			specificteam[1] = teamstats[1];
			specificteam[2] = teamstats[2];
			specificteam[3] = teamstats[3];
			if (i == 0) {
				GB = 0;
			} else {
				GB = (Double.parseDouble(firstplace[1]) - Double.parseDouble(specificteam[1])) + (Double.parseDouble(firstplace[2]) - Double.parseDouble(specificteam[2])) / 2;
			}
			specificteam[4] = String.valueOf(GB);
			sortedfinal.add(specificteam);
		}
		return sortedfinal;
	}
	
	
	public static ArrayList<String[]> getWPandSort(ArrayList<String[]> teamlist) {
		
		String[] specificteam;
		double wr;
		ArrayList<String[]> sortedresult = new ArrayList<String[]>();
		TreeMap<Double, String[]> sorter = new TreeMap<Double, String[]>(Collections.reverseOrder());
		
		for (int i=0; i<teamlist.size(); i++) {
			specificteam = new String[4];
			String[] tempteam = teamlist.get(i);
			specificteam[0] = tempteam[0];
			specificteam[1] = tempteam[1];
			specificteam[2] = tempteam[2];
			
			wr = (Double.parseDouble(specificteam[1]) / (Double.parseDouble(specificteam[2]) + Double.parseDouble(specificteam[1])));
			specificteam[3] = String.valueOf(wr);
			sorter.put(wr, specificteam);
		}
		
		for (Map.Entry<Double, String[]>
				entry : sorter.entrySet()) {
			sortedresult.add((String[]) entry.getValue());
		}
		
		return sortedresult;
	}
	
	public static void printStandings(ArrayList<String[]> teams) {
		System.out.println("Team\t\t\tWins\tLosses\tPct.\tBehind\n");
		System.out.println("----------------------------------------------------------\n");
		for (int i=0; i<teams.size(); i++) {
			String[] currentteam = teams.get(i);
			System.out.printf("%-10s\t\t%6s\t%6s\t%.5s%6s\n", currentteam[0], currentteam[1], currentteam[2], currentteam[3], currentteam[4]);
		}
	}
	
	public static void printOverall(ArrayList<String[]> allmlb) {
		System.out.println("Team\t\t\tWins\tLosses\n");
		System.out.println("---------------------------------------\n");
		for (int i=0; i<allmlb.size(); i++) {
			String[] currentteam = allmlb.get(i);
			System.out.printf("%-10s\t%s\t%s\n", currentteam[0], currentteam[1], currentteam[2]);
		}
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("********************************************\n");
		System.out.print("BASEBALL STANDINGS ANALYZER\n");
		System.out.print("********************************************\n\n");
		System.out.print("This program reads a file that contains\ncurrent baseball standings and adds to\nmore detailed statistics.  It also prints\noverall standings in the American and\nnational leagues.\n\n");
		System.out.print("Enter the name of the standings file:\n");
		String fname = sc.nextLine();
		
		ArrayList<String[]> aleast = new ArrayList<String[]>();
		ArrayList<String[]> alcentral = new ArrayList<String[]>();
		ArrayList<String[]> alwest = new ArrayList<String[]>();
		ArrayList<String[]> nleast = new ArrayList<String[]>();
		ArrayList<String[]> nlcentral = new ArrayList<String[]>();
		ArrayList<String[]> nlwest = new ArrayList<String[]>();
		ArrayList<String[]> overall = new ArrayList<String[]>();
		
		String line;
		String[] parts;
		String division = " ";
		boolean canContinue;
		int choice;
		
		try {
			Scanner fsc = new Scanner(new File(fname));
			while (fsc.hasNextLine()) {
				line = fsc.nextLine();
				parts = line.split("\t");
				if (parts[0].equalsIgnoreCase("LEAGUE")) {
					division = parts[1].toUpperCase();
				}
					
				else {
					
					if (division.equalsIgnoreCase("al east")) {
						aleast.add(parts);
					} else if (division.equalsIgnoreCase("al central")) {
						alcentral.add(parts);
					} else if (division.equalsIgnoreCase("al west")) {
						alwest.add(parts);
					} else if (division.equalsIgnoreCase("nl east")) {
						nleast.add(parts);
					} else if (division.equalsIgnoreCase("nl central")) {
						nlcentral.add(parts);
					} else if (division.equalsIgnoreCase("nl west")) {
						nlwest.add(parts);
					} 
					overall.add(parts);
				}
			}
			
			aleast = getWPandSort(aleast);
			alcentral = getWPandSort(alcentral);
			alwest = getWPandSort(alwest);
			nleast = getWPandSort(nleast);
			nlcentral = getWPandSort(nlcentral);
			nlwest = getWPandSort(nlwest);
			overall = getWPandSort(overall);
			
			aleast = getGB(aleast);
			alcentral = getGB(alcentral);
			alwest = getGB(alwest);
			nleast = getGB(nleast);
			nlcentral = getGB(nlcentral);
			nlwest = getGB(nlwest);
			
			fsc.close();
			canContinue = true;
			
		} catch(Exception ex) {
			System.out.println("Couldn't read the file.");
			canContinue = false;
		}

		if (canContinue) {
			
			do {
				
				choice = displayMenu(sc);
				if (choice == 1) {
					printStandings(aleast);
				} else if (choice == 2) {
					printStandings(alcentral);
				} else if (choice == 3) {
					printStandings(alwest);
				} else if (choice == 4) {
					printStandings(nleast);
				} else if (choice == 5) {
					printStandings(nlcentral);
				} else if (choice == 6) {
					printStandings(nlwest);
				} else if (choice == 7) {
					printOverall(overall);
				} else {
					System.out.println("Please enter a parameter between 1-8.");
				}
				
			} while (choice != 8);
			
		}
	}
}