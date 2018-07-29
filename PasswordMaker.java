/* Benz Huynh
 * This program is meant to create a 
 * password based on a given user name.
 * Version 2
 */
package version2;
import java.util.*;

public class PasswordMaker 
{
	/**
	 * Creates a password based on a given input
	 * <p>
	 * "even characters" + "odd characters"
	 * + (special character)
	 * + "number of even letters" + "number of odd letters" + "number of numbers" 
	 * @param plainText - original text taken from GUI
	 * @param allowedChars - set of special characters allowed in the password through check boxes
	 * @param indexed - Character[] of allowed special characters through text box
	 * @return
	 */
	public static String createPassword(String plainText, Set<Character> allowedChars, Character[] indexed)
	{
		String cipherText = "";
		try
		{
			cipherText += getEvensOdds(plainText) 
					+ getSpecChar(plainText, allowedChars, indexed) 
					+ getEndNumbers(plainText);
		}
		catch(Exception e)
		{
			cipherText = "INVALID INPUTS";
		}
		return cipherText;
	}
	
	/**
	 * Get all even characters, then all odd characters
	 * <p>
	 * "even characters" + "odd characters"
	 * @param plainText - original text taken from GUI
	 * @return String - even and odd characters
	 */
	private static String getEvensOdds(String plainText)
	{
		String evens = "";
		String odds = "";
		boolean first = true;
		
		for(int x = 0; x < plainText.length(); x++)
		{
			String curr = plainText.substring(x, x + 1);
			if(x % 2 == 0)
			{
				if(first == true)
				{
					evens += curr.toUpperCase();
				}
				else
				{
					evens += curr;
				}
				first = false;
			}
			else
			{
				odds += curr;
			}
		}
		return evens + odds;
	}

	/**
	 * Creates a special character based on the number
	 * of letters and the number of numbers
	 * <p>
	 * Special character ASCII value = "(number of letters)" + "(number of numbers)"
	 * @param plainText - original text taken from GUI
	 * @param allowedChars - defines characters this method is allowed to return
	 * @param indexed - Character[] of allowed special characters through text box
	 * @return char - special character for the password
	 */
	private static char getSpecChar(String plainText, Set<Character> allowedChars, Character[] indexed)
	{
		int numLtrs = 0;
		int numNums = 0;
		
		for(char c: plainText.toCharArray())
		{
			if("0123456789".indexOf(c) == -1)
			{
				numLtrs++;
			}
			else
			{
				numNums++;
			}
		}
		
		int specNum = Integer.parseInt(numLtrs + "" + numNums);
		char specChar = (char) specNum;
		
		if(indexed.length == 0)
		{
			indexed = allowedChars.toArray(new Character[0]);
		}
		else
		{
			allowedChars = new TreeSet<>(Arrays.asList(indexed));
		}
		
		while(allowedChars.contains(specChar) != true)
		{
			specNum = specNum % allowedChars.size();
			specChar =  indexed[specNum];
		}
		
		return specChar;
	}

	/**
	 * Calculates the numbers that will be put at the end of the password
	 * <p>
	 * "number of even letters" + "number of odd letters" + "number of numbers" 
	 * @param plainText - original text taken from GUI
	 * @return String - final numbers in the password
	 */
	private static String getEndNumbers(String plainText)
	{
		int evenLtrs = 0;
		int oddLtrs = 0;
		int numNums = 0;

		for(int x = 0; x < plainText.length(); x++)
		{
			String curr = plainText.substring(x, x + 1);
			if("0123456789".indexOf(curr) != -1)
			{
				numNums++;
			}
			else
			{
				if(x % 2 == 0)
				{
					evenLtrs++;
				}
				else
				{
					oddLtrs++;
				}
			}
		}
		
		return evenLtrs + ""  + oddLtrs + ""  + numNums;
	}
}