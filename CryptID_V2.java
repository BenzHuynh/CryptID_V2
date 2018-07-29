package version2;

import java.util.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;

public class CryptID_V2 extends Application
{
	private TextField userIn, passwordOut, allowedCharacterText;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		//main container and panes in this order
		VBox mainPane = new VBox(10);
		FlowPane empty = new FlowPane();
		FlowPane userPane = new FlowPane();
		FlowPane passwordPane = new FlowPane();
		FlowPane allowedCharsPane = new FlowPane();
		FlowPane allowedCharsPaneText = new FlowPane();
		FlowPane createButtonPane = new FlowPane();
		
		//labels + textfields -> userPane and passwordPane
		Label username = new Label("Username: ");
		Label password = new Label("Password: ");
		userIn = new TextField();
		passwordOut = new TextField();
		passwordOut.setEditable(false);
		
		userPane.setAlignment(Pos.CENTER);
		userPane.getChildren().addAll(username, userIn);
		
		passwordPane.setAlignment(Pos.CENTER);
		passwordPane.getChildren().addAll(password, passwordOut);
		
		//placing check boxes of allowed special characters
		String specChars = " !\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~";
		Map<Integer, CheckBox> charMap = new TreeMap<>();
		
		for(int x = 0; x < specChars.length(); x++)
		{
			char temp = specChars.charAt(x);
			
			if(temp == ' ')
			{
				charMap.put((int) temp, new CheckBox("sp\t"));
			}
			else
			{
				charMap.put((int) temp, new CheckBox(temp + "\t"));
			}
			
			CheckBox curr = charMap.get((int) temp);
			curr.setSelected(true);
			curr.setMnemonicParsing(false);
			allowedCharsPane.getChildren().add(curr);
		}
		
		CheckBox checkAll = new CheckBox("Check All");
		checkAll.setSelected(true);
		checkAll.setOnAction(e ->
		{
			if(checkAll.isSelected() == true)
			{
				setAll(charMap, true);
			}
			else
			{
				setAll(charMap, false);
			}
		});
		
		allowedCharsPane.setAlignment(Pos.CENTER);
		allowedCharsPane.setHgap(-4);
		allowedCharsPane.setVgap(5);
		allowedCharsPane.setPadding(new Insets(0, 0, 0, 10));;
		allowedCharsPane.getChildren().add(checkAll);
		
		//placing create button
		Button create = new Button("Create Password");
		
		create.setOnAction(e ->
		{		
			Set<Character> allowedCharsSet = new TreeSet<>();
			
			for(int i: charMap.keySet())
			{
				if(charMap.get(i).isSelected() == true)
				{
					allowedCharsSet.add((char)i);
				}	
			}
			
			Character[] allowedCharsArr = charToCharacter(allowedCharacterText.getText());
			Arrays.sort(allowedCharsArr);
			
			
			passwordOut.setText(PasswordMaker.createPassword(userIn.getText(), allowedCharsSet, allowedCharsArr));
		});
		
		create.setDefaultButton(true);
		createButtonPane.setAlignment(Pos.CENTER);
		create.setOpacity(0);
		createButtonPane.getChildren().add(create);
		
		//placing text box for allowed special characters
		Label allowedChar = new Label("Allowed Characters: ");
		allowedCharacterText = new TextField(); 
		
		allowedCharsPaneText.setAlignment(Pos.CENTER);
		allowedCharsPaneText.getChildren().addAll(allowedChar, allowedCharacterText);
		
		//adding all panes to main pane
		empty.getChildren().add(new Label());
		mainPane.getChildren().addAll(empty, userPane, passwordPane, allowedCharsPane, allowedCharsPaneText, createButtonPane);
		Scene scene = new Scene(mainPane);
		primaryStage.setTitle("CryptID");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	private static void setAll(Map<Integer, CheckBox> a, boolean b)
	{
		for(int i: a.keySet())
		{
			CheckBox temp = a.get(i);
			temp.setSelected(b);
		}
	}
	
	private static Character[] charToCharacter(String c)
	{
		char[] temp = c.toCharArray();
		Character[] result = new Character[temp.length];
		
		for(int x = 0; x < temp.length; x++)
		{
			result[x] = temp[x];
		}
		
		return result; 
	}
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
	
	
}
