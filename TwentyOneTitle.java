import javafx.scene.control.TextField;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TwentyOneTitle extends TwentyOne {
	//override the start method
	//use @Override methods when programming in teams
    public static RandomAccessFile raf;
    public static String plName = "You";
    private ArrayList<String> playerNames = null;
    private Stage titleStage, pUp, instructionStage;
    Scene popUp, instrScene;
    BorderPane popUpPane;
    

	@Override
	public void start(Stage primaryStage){
		titleStage = primaryStage;
		
		BorderPane borderPane = new BorderPane();
		borderPane.setStyle("-fx-background-color: radial-gradient(center 50% -40%, radius 200%, #355e3b 45%, #1d3420 50%)");
		borderPane.setPadding(new Insets(120, 20, 20, 20));
		
		borderPane.setCenter(getGridCenter());
		borderPane.setBottom(getHBoxBottom());
		borderPane.setTop(getVBoxTop());

		Scene scene = new Scene(borderPane, 800, 600);
		primaryStage.setTitle("21: The Game");
		primaryStage.setScene(scene);
		primaryStage.show();	
		
		displayInstructions();
	}
	
	private void displayInstructions() {
		
		instructionStage = new Stage();
		BorderPane pane = new BorderPane();
		
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(0, 0, 0, 40));
		vbox.setAlignment(Pos.CENTER_LEFT);
		
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(20, 20, 20, 20));
		hbox.setAlignment(Pos.CENTER);
		
		VBox vbox2 = new VBox();
		vbox2.setPadding(new Insets(100, 0, 0, 0));
		vbox2.setAlignment(Pos.CENTER);
		
		Label lT = new Label("Welcome to 21: The Game");
		lT.setFont(Font.font("Courier", FontWeight.BOLD,14));
		
		Label lT2 = new Label("Player Instructions");
		lT2.setFont(Font.font("Courier", 12));
		
		Label l1 = new Label("1. Enter a username then press \"+ Add\"");
		l1.setFont(Font.font("Courier", 12));
		
		Label l2 = new Label("2. If loading a previous game, find name in "
				+ "\nthe drop down menu");
		l2.setFont(Font.font("Courier", 12));
		
		Label l3 = new Label("3. Choose name then press the select button");
		l3.setFont(Font.font("Courier", 12));
		
		Label l4 = new Label("4. Then press start to begin the game");
		l4.setFont(Font.font("Courier", 12));
		
		Label l5 = new Label("5. If you dont want to save your data, "
				+ "\njust press start without typing or selecting a username");
		l5.setFont(Font.font("Courier", 12));
		
		Button gotIt = new Button("Got It!");
		gotIt.setStyle("-fx-border-color: green");
		
		gotIt.setOnAction(e -> instructionStage.close());
		
		vbox.getChildren().addAll(l1, l2, l3, l4, l5);
		hbox.getChildren().add(gotIt);
	    vbox2.getChildren().addAll(lT, lT2);
	    
		pane.setBottom(hbox);
		pane.setCenter(vbox);
		pane.setTop(vbox2);
		
		instrScene = new Scene(pane, 475, 400);
		instructionStage.setScene(instrScene);
		instructionStage.initModality(Modality.APPLICATION_MODAL);
		instructionStage.setTitle("21: The Game");
		instructionStage.showAndWait();
		
	}
	public GridPane getGridCenter(){
		try {
			//writes all the game data to the file gamedata.dat
			raf = new RandomAccessFile("gamedata.dat", "rw");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			e1.getMessage();
		}
		GridPane pane = new GridPane();
	    pane.setAlignment(Pos.CENTER);
	    pane.setHgap(5.5);
	    pane.setVgap(5.5);
	    
	    // Place nodes in the pane
	    Label user = new Label("Username:");
	    user.setFont(Font.font("Courier", FontWeight.BOLD,12));
	    user.setTextFill(Color.WHITE);
	    pane.add(user, 0, 15);
	    TextField playerTxt = new TextField();
	    playerTxt.setStyle("-fx-border-color: green");
	    playerTxt.setPrefWidth(200);
	    pane.add(playerTxt, 1, 15);
	    
	    try {
	    	playerNames = readFile(raf);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    
	    ComboBox<String> cbo = new ComboBox<>();
	    cbo.setStyle("-fx-border-color: green");
	    cbo.setPrefWidth(200);
	    pane.add(cbo,1,17);
	    
	    //reads all existing user names into the file
	    try {
			readFile(raf, cbo);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	       
	    Button addBtn = new Button("+ Add");
	    addBtn.setStyle("-fx-border-color: green");
	    addBtn.setPrefWidth(200);
	    pane.add(addBtn,1,16);
	    
	    addBtn.setOnAction(e -> {
	    	//check the name entered and compare it to all the user names in the file
	    	//if it doesn't match, then add then name to the file and combo box
	    	if(checkPlayerName(playerTxt.getText(), playerNames) == false){
		    							plName = playerTxt.getText();
										System.out.println(plName);
										cbo.getItems().add(plName);
										System.out.println("...");
										try {
											add2File(raf, plName);
										} catch (IOException e1) {
											e1.printStackTrace();
										}
	    							}
	    							//if it does display pop up
								  	else{
								  		plName = playerTxt.getText();
								  		popUp();	
								    }
								});
	    
	    Button selectBtn = new Button("Select");
	    selectBtn.setStyle("-fx-border-color: green");
	    selectBtn.setOnAction(e -> {plName = cbo.getValue();
	    							System.out.println("PLAYER SELECTED");
	    							//add method to get player info (wins, rounds, etc.)
	    							});
	    pane.add(selectBtn,0,17);
	       
	    Label author = new Label("Created By: Elio Gerges");
		author.setFont(Font.font("Courier",12));
		author.setTextFill(Color.WHITE);
		pane.add(author, 1 , 40);
		
		return pane;		
	}
	
	private boolean checkPlayerName(String name, ArrayList<String> names) {
		ArrayList<String> playerNames = names;
		String playerName = name;
		for(int i = 0; i < playerNames.size(); i++){	
			if(playerName.equals(playerNames.get(i).trim())){
				return true;
			}
		}
		return false;
	}
	
	//if the user name matches already existing player
	private void popUp(){
		pUp = new Stage();
  		popUpPane = new BorderPane();
  		popUpPane.setPadding(new Insets(20, 20, 20, 20));
  		HBox btnPane = new HBox(10);
  		VBox text = new VBox(2);
  		btnPane.setAlignment(Pos.CENTER);
  		text.setAlignment(Pos.CENTER);

         
        Text t1 = new Text("The username already exists.");
        t1.setFont(Font.font("Courier",12));
        
        Text t2 = new Text("Would you like to override player data? ");
        t2.setFont(Font.font("Courier",12));
        
        text.getChildren().addAll(t1,t2);
        
        Button yesBtn = new Button("Yes");
        Button noBtn = new Button("No");
        
        yesBtn.setStyle("-fx-border-color: green");
		noBtn.setStyle("-fx-border-color: green");
        
        yesBtn.setMaxWidth(50);
        noBtn.setMaxWidth(50);
        
        noBtn.setOnAction(e -> pUp.close());
        
        //re-write player information
        yesBtn.setOnAction(e -> {pUp.close();
        						//call the update method
        });
        
        
        btnPane.getChildren().addAll(yesBtn, noBtn);
        
        popUpPane.setCenter(text);
        popUpPane.setBottom(btnPane);
        
        popUp = new Scene(popUpPane, 350, 250);
        pUp.setScene(popUp);
        pUp.initModality(Modality.APPLICATION_MODAL);
        pUp.setTitle("21: The Game");
        pUp.showAndWait();	
	}
	
	private HBox getHBoxBottom(){
		Button strBtn = new Button("Start");
		Button exBtn = new Button("Exit");
		
		strBtn.setStyle("-fx-border-color: green");
		exBtn.setStyle("-fx-border-color: green");
		
		strBtn.setPrefWidth(120);
		exBtn.setPrefWidth(120);

		HBox hBox = new HBox();
		hBox.setSpacing(10);
		hBox.setAlignment(Pos.CENTER);
			    
	    hBox.getChildren().addAll(strBtn,exBtn);
	    
	    //when start is pressed, the title screen closes and the game appears
	    strBtn.setOnAction(e -> {super.start(gameStage); 
	    							titleStage.close();
	    							});
	  //exits the game if pressed
	    exBtn.setOnAction(e -> { //save player information
	    						System.exit(0);});
		return hBox;
	}

	private VBox getVBoxTop(){
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		Label label = new Label("21");
		label.setTextFill(Color.WHITE);
		label.setFont(Font.font("Courier", FontWeight.BOLD,55));
		
		vBox.getChildren().add(label);		
		return vBox;
	}
	
	public static String getPlayerName(){
		//removes the extra spaces
		return plName.trim();
		}
	
	 private static void add2File(RandomAccessFile raf, String name) throws IOException
	 {
		//add name
		raf.writeUTF(format(name,32));	 
		//add rounds
		//add wins
		System.out.println("ADDED " + name);
	 }
	 
	 
	 private static void readFile(RandomAccessFile raf, ComboBox<String> c) throws IOException
	 {

	   //move pointer to beginning
	   try {
		   raf.seek(0);
	   } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
	   
	   //Below is a cleaner way of reading the data
	   try{
		   do {
			   c.getItems().add(raf.readUTF());
		   }
		   while(raf.getFilePointer() < raf.length());
	   }
	   catch(IOException e){
		   e.getMessage();
	   }
	 }
	 
	 //reads and returns an arraylist of a all the names in a file
	 private static ArrayList<String> readFile(RandomAccessFile raf) throws IOException
	 {
	   ArrayList<String> names = new ArrayList<>();
	   //move pointer to beginning
	   try {
		   raf.seek(0);
	   } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
	   
	   //Below is a cleaner way of reading the data
	   try{
		   do {
			   names.add(raf.readUTF());
		   }
		   while(raf.getFilePointer() < raf.length());
	   }
	   catch(IOException e){
		   e.getMessage();
	   }
	   return names;
	 }
	 
	 //add a getname to the parameter
	 public static void updateFile(RandomAccessFile raf,int n) throws IOException{
		 
		   //move pointer to specific location (2 + size of previous byte length)
		   //n-1 when 0, seek is 0, meaning 1st contact
		   //32+5+5 = 42
		   //(2*3)*(n-1). The five is for all 5 variable (Name, Wins, Rounds)
		   raf.seek((n-1)*(42)+(6*(n-1)));
		   add2File(raf, plName);
	 }
	 
	 
	 private static String format(String s, int length)
	 {
	   return String.format("%-" + length + "s" , s);
	 }
	 	
	public static void main(String[] args){
		Application.launch(args);
	}
}

