import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFilter {
	static class cFiles {
		static String fullLogFilename;
		static String fullBlackListFilename;
		static String fullRegExListFilename;
		
		final static byte LogFile=1;
		final static byte BlackListFile=2;
		final static byte RegExFile=3;
	}

	static ArrayList<String> wczytaniePliku(String file) throws IOException {
		BufferedReader	fileReader=new BufferedReader(new FileReader(file));
			Set<String> logFileLinesSet = new LinkedHashSet<String>(100000); // maybe bigger
																				
			String currentLogfileLine;
			//dodanie do Set usunie duplikaty
			while ((currentLogfileLine = fileReader.readLine()) != null) {
				logFileLinesSet.add(currentLogfileLine);
			}
			fileReader.close();
			
			ArrayList<String> tempList = new ArrayList<String>();
			for (String currLine : logFileLinesSet) {
				tempList.add(currLine);
			}
			logFileLinesSet=null;
			//sortowanie
			Collections.sort(tempList);
		
		return tempList;
	}
	
	
	static ArrayList<String> filterWithBlackList(ArrayList<String> logcatList, ArrayList<String> blackListArray) throws IOException {
	ArrayList<String> result=new ArrayList<String>();
	
	for (String currentLogcatLine : logcatList) {
		
		 Boolean interesting= writeOrNot(currentLogcatLine, blackListArray);
		if (interesting == false) {
			continue;
		}
		result.add(currentLogcatLine);
	}
	
	return result;
	}
	

	 static ArrayList<String> filterWithRegExExpressions(ArrayList<String> logcatList, ArrayList<String> regExArray) {
		 System.out.println("filterWithRegExExpressions");
		for (String currRegEx : regExArray) {
			System.out.println("RegEx: " + currRegEx);
			Pattern currentPattern = Pattern.compile(currRegEx);
		
			for (int i = 0; i < logcatList.size(); i++) {
				Matcher currentMatcher = currentPattern.matcher(logcatList.get(i));
				boolean pass = currentMatcher.matches();
				if (pass) {
					logcatList.remove(i);
					i--;
				}
				 currentMatcher.reset();
			}
		}
		return logcatList;
	}

	 static Boolean writeOrNot(String currentlyChacekdLogLine, ArrayList<String> blackListArray) throws IOException {
		for (String currentBlackListLine : blackListArray) {
			if (currentlyChacekdLogLine.length() == currentBlackListLine.length()) {
				// TODO: --------- beginning of /dev/log/main - tu nie ma
				// delimitera, trzeba to obs³u¿yæ
				String sr = "\\): ";
				String[] arr = new String[2];
				arr = currentBlackListLine.split(sr);
			
				if (currentlyChacekdLogLine.contains(arr[1])) {
					return false;
				}
			}

		}
		return true;

	}

}
