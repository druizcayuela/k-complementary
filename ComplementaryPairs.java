import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComplementaryPairs {
	
	private static final Logger logger = Logger.getLogger(ComplementaryPairs.class.getName());

	private static final String FILE_NAME = "test/ComplementaryPairs.txt";
	private static final int K = 11; //pair example

	public static void main(String[] args) throws FileNotFoundException {
		
		// String buffer for storing the output
		StringBuilder output = new StringBuilder();

		try (Scanner reader = new Scanner(new File(FILE_NAME))) {

			String strLine;
			
			int activeTestCaseNumber = 0;
			while (reader.hasNextLine()) {

				strLine = reader.nextLine();
				activeTestCaseNumber++;
				
				checkIsPalindrome(output, strLine, activeTestCaseNumber);
			}
			// Pass output string to method to write to file
			writeOutputToFile(output.toString());
		}

	}


	private static void checkIsPalindrome(StringBuilder output, String strLine, int activeTestCaseNumber) {
		try{
			
			//Convert the case line into array integers
			int[] noOfIntegers = Arrays.stream(strLine.split(" ")).map(String::trim).mapToInt(Integer::parseInt).toArray();
			
			// Invoke algorithm here
			noOfComplementaryPairs(noOfIntegers, output, activeTestCaseNumber);
			
		}catch(NumberFormatException ex){
			
			logger.log(Level.FINE, ex.toString(), ex);
			// Prepare output string in case error, invalid characters.
			output.append("Case #").append(activeTestCaseNumber).append(": ").append("Error, invalid characters.").append(System.lineSeparator());;
		}
	}

	
	/**
	 * 
	 * We can do here is to store all numbers in a hashtable and just check if it contains second value in a pair. 
	 * For example, if given sum is 4 and one number in pair is 3, then other must be 1 or -7.
	 * 
	 * Only N to iterate through array and insert values in a Set because add() and contains() both O(1) operation in hash table. 
	 * So total complexity of solution would be O(N).
	 * 
	 * @param arr
	 * @param output
	 */
	private static void noOfComplementaryPairs(int[] arr, StringBuilder output, int activeTestCaseNumber) {
		if (arr.length < 2) {
			// Prepare output string
			output.append("Case #").append(activeTestCaseNumber).append(": ").append("Not complementary pairs.").append(System.lineSeparator());;
			return;
		}
		Set<Integer> set = new HashSet<>(arr.length);
		
		StringBuilder results = new StringBuilder();

		int noOfResults = 0;
		for (int value : arr) {
			int target = K - value; // if target number is not in set then add
			if (!set.contains(target)) {
				set.add(value);
			} else {
				results.append("(").append(value).append(" - ").append(target).append(")");
				results.append(System.lineSeparator());	
				noOfResults++;
			}
		}
		if(noOfResults>0){
			output.append("Case #").append(activeTestCaseNumber).append(" There is complementary. The number of results is ").append(noOfResults).append(System.lineSeparator()).append(results);
		}else{
			// Prepare output string
			output.append("Case #").append(activeTestCaseNumber).append(": ").append("Not complementary pairs.").append(System.lineSeparator());;
		}
		
	}
	
	private static void writeOutputToFile(String str) {
		Path file = Paths.get(FILE_NAME);
		try {
			Files.write(file, str.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
