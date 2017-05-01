import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.shape.Rectangle;

/**
 * @author ElioGerges
 * 
 *  Card, Deck and Player Classes 
 *  are included in this file
 */
public class TwentyOne extends Application
{	
	public Stage gameStage = new Stage();
	public Stage gameStatus = new Stage();
	private Stage gameplayIStage;
	private Scene gameplayISence, gameStatusScene;
	public int numOfRoundWins = 0;
	public int numOfTotalWins = 0;
	public int numOfRoundWinsPC = 0;
	public int numOfTotalWinsPC = 0;

	public void start(Stage pStage)
	{
	  Button shuffle = new Button("Shuffle");
	  Deck myDeck = new Deck(true);
	  gameStage = pStage;
	  BorderPane pane = new BorderPane();
	  pane.setTop(getTop(numOfTotalWinsPC, numOfRoundWinsPC));
	  pane.setBottom(getBottom(numOfTotalWins, numOfRoundWins));
      pane.setCenter(getMiddle(myDeck));
      
      shuffle.setOnAction(e -> {
    	  pane.setCenter(getMiddle(new Deck(true)));
    	  pane.setTop(getTop(numOfTotalWinsPC,numOfRoundWinsPC));
    	  pane.setBottom(getBottom(numOfTotalWins, numOfRoundWins));
      });
       
      pane.setLeft(getLeft(shuffle));
      pane.setStyle("-fx-background-color: radial-gradient(center 50% -40%, radius 200%, #355e3b 45%, #1d3420 50%)");   
      
      Scene scene = new Scene(pane);
      pStage.setTitle("21");
      pStage.setScene(scene);
      pStage.setMaximized(true);
      pStage.show();

      displayInstructions();
   }
	
	private HBox getLeft(Button b){
		HBox h = new HBox();
		h.setPadding(new Insets(20,20,20,20));
		h.setAlignment(Pos.CENTER);
		h.getChildren().add(b);
		
		return h;
	}

   private void displayInstructions() {
	    gameplayIStage = new Stage();
		BorderPane pane = new BorderPane();
		
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(0, 0, 0, 30));
		vbox.setAlignment(Pos.CENTER_LEFT);
		
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(20, 20, 20, 20));
		hbox.setAlignment(Pos.CENTER);
		
		VBox vbox2 = new VBox();
		vbox2.setPadding(new Insets(100, 0, 0, 0));
		vbox2.setAlignment(Pos.CENTER);
		
		Label lT = new Label("Welcome to 21: The Game");
		lT.setFont(Font.font("Courier", FontWeight.BOLD,14));
		
		Label lT2 = new Label("Gameplay Instructions");
		lT2.setFont(Font.font("Courier", 12));
			
		Label l1 = new Label("1. The ojective is for the sum of your cards to "
				+ "\nequal or be under 21");
		l1.setFont(Font.font("Courier", 12));
		
		Label l2 = new Label("2. If the you and the computer both have a sum "
				+ "\nunder 21,the player with the closest amount to 21 wins");
		l2.setFont(Font.font("Courier", 12));
		
		Label l3 = new Label("3. If the computer goes above 21, you win and vise versa");
		l3.setFont(Font.font("Courier", 12));
		
		Label l4 = new Label("4. Its your turn first");
		l4.setFont(Font.font("Courier", 12));
		
		Label l5 = new Label("5. Win 5 rounds to win 1 game");
		l5.setFont(Font.font("Courier", 12));
		
		Label l6 = new Label("6. Good Luck!");
		l6.setFont(Font.font("Courier", 12));
		
		Button gotIt = new Button("Got It!");
		gotIt.setStyle("-fx-border-color: green");

		
		gotIt.setOnAction(e -> gameplayIStage.close());
		
		vbox.getChildren().addAll(l1, l2, l3, l4, l5, l6);
		hbox.getChildren().add(gotIt);
	    vbox2.getChildren().addAll(lT, lT2);
	    
		pane.setBottom(hbox);
		pane.setCenter(vbox);
		pane.setTop(vbox2);
		
		gameplayISence = new Scene(pane, 475, 400);
		gameplayIStage.setScene(gameplayISence);
		gameplayIStage.initModality(Modality.APPLICATION_MODAL);
		gameplayIStage.setTitle("21: The Game");
		gameplayIStage.showAndWait();
		
	}
   
   private void displayStatus(int i) {
	    Stage gameStatus = new Stage();
		BorderPane pane = new BorderPane();
		Button close = new Button("Close");
		
		VBox vbox = new VBox(5);
		vbox.setAlignment(Pos.CENTER);
		
		HBox hbox = new HBox(10);
		hbox.setPadding(new Insets(20, 20, 20, 20));
		hbox.setAlignment(Pos.CENTER);

		close.setPrefWidth(80);
		
		close.setOnAction(e -> gameStatus.close()
		);

		hbox.getChildren().add(close);

		switch(i){
		case 1:
			Label l1 = new Label("You won the round!");
			l1.setFont(Font.font("Courier", FontWeight.BOLD,13));
			vbox.getChildren().add(l1);
			playGoodSound();
			break;
		case 2:
			Label l2 = new Label("You lost the round!");
			l2.setFont(Font.font("Courier", FontWeight.BOLD,13));
			vbox.getChildren().add(l2);
			playBadSound();
			break;
		case 3:
			Label l3 = new Label("You won the game!");
			l3.setFont(Font.font("Courier", FontWeight.BOLD,13));
			vbox.getChildren().add(l3);
			playGreatSound();
			break;
		case 4:
			Label l4 = new Label("You lost the game!");
			l4.setFont(Font.font("Courier", FontWeight.BOLD,13));
			vbox.getChildren().add(l4);
			playHorribleSound();
			break;
	}	
		Label msg = new Label("Press Close then Shuffle");
		msg.setFont(Font.font("Courier", 11));
		vbox.getChildren().add(msg);
		
		pane.setBottom(hbox);
		pane.setCenter(vbox);
		
		gameStatusScene = new Scene(pane, 275, 100);
		gameStatus.setScene(gameStatusScene);
		gameStatus.initModality(Modality.APPLICATION_MODAL);
		gameStatus.setTitle("21: The Game");
		gameStatus.showAndWait();
		
	}

private GridPane getMiddle(Deck deck)
   {  
       GridPane gp = new GridPane();
       gp.setAlignment(Pos.CENTER);
       gp.setPadding(new Insets(11.5, 12.5, 11.5, 14.5));
       gp.setHgap(5.5);
       gp.setVgap(5.5);
      
       Button stay = new Button("Stay");
	   Button hit = new Button("Hit");
	   
	   Label turn = new Label("");
	   turn.setTextFill(Color.WHITE);
	   turn.setFont(Font.font("Courier", 15));
	   gp.add(turn, 11, 0);
	   
	   
	   stay.setPrefWidth(150);
	   hit.setPrefWidth(150);
	   
	   stay.setPrefHeight(30);
	   hit.setPrefHeight(30);
	  
	   gp.add(stay, 2, 2);
	   gp.add(hit, 3, 2);
	  
	   Deck myDeck = new Deck(true);
	   
	   Player user = new Player(TwentyOneTitle.getPlayerName());
	   Player computer = new Player("Computer");
	   
	   playShuffleSound();
	   
	   user.addCard(myDeck.dealCard());
	   computer.addCard(myDeck.dealCard());
	   user.addCard(myDeck.dealCard());
	   computer.addCard(myDeck.dealCard());
	   
	   gp.add(addCards("?"), 0, 0);
	   gp.add(addCards(computer.getHand(1)), 1, 0);
	    
	   Text pcSum = new Text("? + "+computer.getHandSum(1)+"/21");
	   pcSum.setFill(Color.WHITE);
	   pcSum.setFont(Font.font("Courier", FontWeight.BOLD, 25));
	   gp.add(pcSum, 10, 0);
	      
	   gp.add(addCards(user.getHand(0)), 0, 1);
	   gp.add(addCards(user.getHand(1)), 1, 1);

	   Text playerSum = new Text(user.getHandSum()+"/21");
	   playerSum.setFill(Color.WHITE);
	   playerSum.setFont(Font.font("Courier", FontWeight.BOLD, 25));
	   gp.add(playerSum, 10, 1);
	   
	   hit.setOnAction(e -> {
		   if(user.getHandPos() <= 6 && user.getHandSum() <= 21){
			   user.addCard(myDeck.dealCard());
			   gp.add(addCards(user.getHand(user.getHandPos())), user.getHandPos(), 1);
			   playerSum.setText(user.getHandSum()+"/21");
		   }
		   if(computer.getHandSum() <= 17){
			   computer.addCard(myDeck.dealCard());
			   turn.setText("    Computer Hit!");
			   gp.add(addCards(computer.getHand(computer.getHandPos())), computer.getHandPos(), 0);
			   
			   		if(computer.getHandSum() > 21){
			   			pcSum.setText("? + "+computer.getHandSum(1)+"/21");
			   		}
		   }
		   else{
			   turn.setText("    Computer Stayed!");
		   }
	   });
	   
	   stay.setOnAction(e -> {
			if(user.getHandSum() > computer.getHandSum() && user.getHandSum() <= 21){
				pcSum.setText(computer.getHandSum()+"/21");
				numOfRoundWins++;
				displayStatus(1);
					if((double)numOfRoundWinsPC % 5 == 0){
						numOfTotalWins++;
   			   			displayStatus(3);
					}
		   	}
		   	if(computer.getHandSum() > user.getHandSum() && computer.getHandSum() <= 21){
		   		pcSum.setText(computer.getHandSum()+"/21");
		   		numOfRoundWinsPC++;
		   		displayStatus(2);
		   				if((double)numOfRoundWinsPC % 5 == 0){
		   					numOfTotalWinsPC++;
		   			   		displayStatus(4);
		   				}
		   	}
	   });
	   return gp;
	  }   

   private StackPane addCards(String s)
   {
      StackPane sp = new StackPane();
      
      Rectangle r = new Rectangle(0, 0, 150, 200);
      r.setFill(Color.WHITE);
      Text text = new Text(5, 10, s);
      text.setFont(Font.font("Courier", FontWeight.BOLD, 35));
      sp.getChildren().add(r);
      sp.getChildren().add(text);
      
      return sp;
   }
   
   private FlowPane getTop(int w, int r)
   {
      FlowPane fp = new FlowPane();
      Label l = new Label();
      l.setFont(Font.font("Courier", FontWeight.BOLD, 40));
      l.setTextFill(Color.BLACK);
      l.setText("[Computer] Games: "+w+ " Rounds: "+r);
      
      fp.getChildren().add(l);
      
      return fp;
   }
   
   private FlowPane getBottom(int w, int r)
   {
      FlowPane fp = new FlowPane();
      Label l = new Label();
      l.setFont(Font.font("Courier", FontWeight.BOLD, 40));
      l.setTextFill(Color.WHITE);
      l.setText("["+TwentyOneTitle.getPlayerName()+"] Games: "+w+ " Rounds: "+r);
      
      fp.getChildren().add(l);
      
      return fp;
   }
   
   public void playShuffleSound(){
	   String soundFile = "shufflecards.mp3"; 
	   Media sound = new Media(new File(soundFile).toURI().toString());
	   MediaPlayer mediaPlayer = new MediaPlayer(sound);
	   mediaPlayer.play();
   }
   
   public void playGoodSound(){
	   String soundFile = "goodsound.mp3"; 
	   Media sound = new Media(new File(soundFile).toURI().toString());
	   MediaPlayer mediaPlayer = new MediaPlayer(sound);
	   mediaPlayer.play();
   }
   
   public void playGreatSound(){
	   String soundFile = "greatsound.mp3"; 
	   Media sound = new Media(new File(soundFile).toURI().toString());
	   MediaPlayer mediaPlayer = new MediaPlayer(sound);
	   mediaPlayer.play();
   }
   
   public void playBadSound(){
	   String soundFile = "badsound.mp3"; 
	   Media sound = new Media(new File(soundFile).toURI().toString());
	   MediaPlayer mediaPlayer = new MediaPlayer(sound);
	   mediaPlayer.play();
   }
   
   public void playHorribleSound(){
	   String soundFile = "losingsound.mp3"; 
	   Media sound = new Media(new File(soundFile).toURI().toString());
	   MediaPlayer mediaPlayer = new MediaPlayer(sound);
	   mediaPlayer.play();
   }
   
   public static void main(String[] args){	  
	   Application.launch(args);	
		}
	}


//Creating a card object
class Card{
	private int cardNumber;
	
	public Card(){
		
	}
	
	public Card(int num){
		this.cardNumber = num;
	}
	
	public int getCardNumber(){
		return cardNumber;
	}
	
	@Override
	public String toString(){
		String number = "";
		
		switch(cardNumber){
			case 1:
				number = "1";
				break;
			case 2:
				number = "2";
				break;
			case 3:
				number = "3";
				break;
			case 4:
				number = "4";
				break;
			case 5:
				number = "5";
				break;
			case 6:
				number = "6";
				break;
			case 7:
				number = "7";
				break;
			case 8:
				number = "8";
				break;
			case 9:
				number = "9";
				break;
			case 10:
				number = "10";
				break;
			case 11:
				number = "11";
				break;
		}
		
		return number;
	}
	
}

//Creats the deck of cards
class Deck{
	private ArrayList<Card> myCards = new ArrayList<Card>();
	
	private final static int NUM_OF_CARDS = 11;
	
	public Deck(){
		Deck d = new Deck(false);
	}
	
	public Deck(boolean shuffle){
		
		for(int i = 1; i <= NUM_OF_CARDS; i++){
			myCards.add(new Card(i));
		}
		
		if(shuffle == true){
			Collections.shuffle(myCards);
		}
	}
	//returns a card
	public Card dealCard(){
		Card topCard = myCards.get(0);	
		
		myCards.remove(0);
		
		return topCard;
		
	}
	
	public void printdeck(){
		System.out.println(myCards.toString());
	}
	
	public static int getDeckSize(){
		return NUM_OF_CARDS;
	}
}

class Player extends TwentyOne{
	
	private String playerName;
	
	private ArrayList<Card> hand = new ArrayList<>();
	
	private int numCardsInHand;
	
	public Player(String name){
		this.playerName = name;
		
		//set a player with an empty hand
		this.emptyHand();
	}
	public void emptyHand(){
		
		hand.clear();
		numCardsInHand = 0;
		}
	public boolean addCard(Card card){
		hand.add(card);
		
		return (getHandSum() <= 21);
		}
	public int getHandSum(){
		int cardSum = 0;
		
		for(int i = 0; i < hand.size(); i++){
			cardSum += hand.get(i).getCardNumber();
		}
		return cardSum;
	}
		
	public int getHandSum(int index){
			int cardSum = 0;
			
			for(int i = index; i < hand.size(); i++){
				cardSum += hand.get(i).getCardNumber();
			}
		return cardSum;
		
		}
	
	public void printHand(){
		System.out.println(hand.toString());
	}
	
	//get the specfic card in the hand
	public String getHand(int index){
		String s = hand.get(index).toString();
		return s;
	}
	
	//returns how many cards are in hand
	public int getHandPos(){
		return hand.size() - 1;
	}
}
