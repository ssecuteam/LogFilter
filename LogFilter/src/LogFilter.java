import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class LogFilter {
	static class cPliki {
		static String fullLogFilename;
		static String fullBlackListFilename;
		static String fullRegExListFilename;
	}
	
	
	public LogFilter() throws IOException {
	/*	BufferedReader logFileReader = new BufferedReader(new FileReader(fullLogFilename));

		// wczytywanie blacklisty do programu
		BufferedReader blackListReader = new BufferedReader(new FileReader(fullBlackListFilename));
		ArrayList<String> blackListArray = new ArrayList<String>();
		String blackListFileLine;
		while ((blackListFileLine = blackListReader.readLine()) != null) {
			blackListArray.add(blackListFileLine);
			// System.out.println("line added to blacklist : "+blackListFileLine);
		}
		blackListReader.close();

		// wczytywanie RegEx listy do programu
		BufferedReader regExReader = new BufferedReader(new FileReader(fullRegExListFilename));
		ArrayList<String> regExArray = new ArrayList<String>();
		String regExFileLine;
		while ((regExFileLine = regExReader.readLine()) != null) {
			regExArray.add(regExFileLine);
			System.out.println(regExFileLine);
		}
		regExReader.close();

		// g��wna metoda kt�ra usuwa duplikaty, sortuje i wycina zb�dna linie
		removeDuplicatesAndUselessAndSortAll(fullLogFilename, logFileReader, blackListArray, regExArray);*/

	}

	static ArrayList<String> wczytaniePliku(String plik, byte rodzajPliku) throws IOException {
		BufferedReader logFileReader=new BufferedReader(new FileReader(plik));
		Set<String> logFileLinesSet = new LinkedHashSet<String>(100000); // maybe bigger
																			
		String currentLogfileLine;
		//dodanie do Set usunie duplikaty
		while ((currentLogfileLine = logFileReader.readLine()) != null) {
			logFileLinesSet.add(currentLogfileLine);
		}
		logFileReader.close();
		
		ArrayList<String> newAllLogcatLinesList = new ArrayList<String>();
		for (String currLine : logFileLinesSet) {
			newAllLogcatLinesList.add(currLine);
		}
		logFileLinesSet=null;
		//sortowanie
		Collections.sort(newAllLogcatLinesList);
		
		return newAllLogcatLinesList;
	}
	
/*
	// g��wna metoda
	public static void removeDuplicatesAndUselessAndSortAll(String fullLogFilename, BufferedReader logFileReader,
			ArrayList<String> blackListArray, ArrayList<String> regExArray) throws IOException {

		// wczytywanie pliku logcata i od razu usuwanie duplikatow
		Set<String> logFileLinesSet = new LinkedHashSet<String>(100000); // maybe
																			// should
																			// be
																			// bigger
		String currentLogfileLine;
		while ((currentLogfileLine = logFileReader.readLine()) != null) {
			logFileLinesSet.add(currentLogfileLine);
		}
		logFileReader.close();

		// sortowanie
		ArrayList<String> newAllLogcatLinesList = new ArrayList<String>();
		for (String currLine : logFileLinesSet) {
			newAllLogcatLinesList.add(currLine);
		}
		Collections.sort(newAllLogcatLinesList);
		
		
		ArrayList<String> regExProcessingResultList = new ArrayList<String>();
		regExProcessingResultList = filterWithRegExExpressions(newAllLogcatLinesList, regExArray);

		// zapisywanie pliku, i sprawdzanie wszystkich warunkow do zapisania
		// linii
		BufferedWriter logFileWriter = new BufferedWriter(new FileWriter(fullLogFilename));
		for (String currentLogcatLine : regExProcessingResultList) {
			boolean interesting = true;
			// Boolean interesting = writeOrNot(currentLogcatLine,
			// blackListArray);
			if (interesting == false) {
				continue;
			}
			logFileWriter.write(currentLogcatLine);
			logFileWriter.newLine();
		}
		logFileWriter.close();
	}

	private static ArrayList<String> filterWithRegExExpressions(ArrayList<String> logcatList, ArrayList<String> regExArray) {

		for (String currRegEx : regExArray) {
			Pattern currentPattern = Pattern.compile(currRegEx);

			// for(int i = 0; i<logcatList.size(); i++)
			for (int i = 0; i < 100; i++) {
				Matcher currentMatcher = currentPattern.matcher(logcatList.get(i));
				boolean pass = currentMatcher.matches();
				System.out.println(pass);
				if (pass) {
					logcatList.remove(i);
					i--;
				}
				// currentMatcher.reset();
			}
		}
		return logcatList;
	}

	public static Boolean writeOrNot(String currentlyChacekdLogLine, ArrayList<String> blackListArray) throws IOException {
		for (String currentBlackListLine : blackListArray) {
			if (currentlyChacekdLogLine.length() == currentBlackListLine.length()) {
				// TODO: --------- beginning of /dev/log/main - tu nie ma
				// delimitera, trzeba to obs�u�y�
				String sr = "\\): ";
				String[] arr = new String[2];
				arr = currentBlackListLine.split(sr);

				if (currentlyChacekdLogLine.contains(arr[1])) {
					return false;
				}
			}

		}
		return true;

	}*/

}
