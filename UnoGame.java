import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Random;

public class UnoGame {
   public static final int NUM_PLAYERS = 4;
   public static final CardType[] KEEP_TYPES = {CardType.REVERSE, CardType.REVERSE,
                                                CardType.REVERSE, CardType.REVERSE};
   public static final int NUM_ROUNDS = 100000;
   public static final long SEED = 123456789;
   public static void main(String[] args) {
      int[] winCounts = {0,0,0,0};
      Random rand = new Random(SEED);
      for (int n = 0; n < NUM_ROUNDS; n++) { 
         UnoDeck dealingDeck = new UnoDeck(rand);
         UnoDeck playingDeck = new UnoDeck(rand);
         StateOfGame gameState = new StateOfGame();
         // playingDeck.printDeck();
   
         dealingDeck.shuffle();
         // dealingDeck.printDeck();
         
         Queue<UnoPlayer> playingOrder = new LinkedList<UnoPlayer>();
         Stack<UnoPlayer> flipOrder = new Stack<UnoPlayer>();
         
         for (int i = 0; i < NUM_PLAYERS; i++) {
            UnoHand hand = new UnoHand();
            hand.dealNewHand(dealingDeck);
            UnoPlayer player = new UnoPlayer(i+1, hand, KEEP_TYPES[i], rand);
            playingOrder.add(player);
         }
         
   //       for (int i = 0; i < NUM_PLAYERS; i++) {
   //          UnoHand hand = playingOrder.peek().hand();
   //          hand.printHand();
   //          System.out.println(" " + playerOrder.peek().playerNumber());
   //          playingOrder.add(playingOrder.remove());
   //       }
         
         UnoCard discardPileTop = dealingDeck.drawTopCard();
         
         // Classic Uno rules says you can't start with wild draw four card, so we keep drawing
         // until the starting card in the discard pile is not a wild draw four.
         while (discardPileTop.getCardType() == CardType.DRAW_FOUR_WILDCARD) {
            discardPileTop = dealingDeck.drawTopCard();
         }
         
         
         UnoPlayer currentPlayer = playingOrder.peek();
         CardColor discardColor;
         
         switch (discardPileTop.getCardType()) {
            case WILDCARD:
               playingOrder.add(playingOrder.remove());
               discardColor = currentPlayer.chooseColor();
               break;
            case DRAW_TWO:
               playingOrder.add(playingOrder.remove());
               gameState.stackingDrawTwo = true;
               gameState.stackDrawValue += 2;
               discardColor = discardPileTop.getCardColor();
               break;
            case REVERSE:
               playingOrder.add(playingOrder.remove());
               while(!playingOrder.isEmpty()) {
                  flipOrder.push(playingOrder.remove());
               }
               while(!flipOrder.isEmpty()) {
                  playingOrder.add(flipOrder.pop());
               }
               discardColor = discardPileTop.getCardColor();
               break;
            case SKIP:
               playingOrder.add(playingOrder.remove());
               playingOrder.add(playingOrder.remove());
               discardColor = discardPileTop.getCardColor();
               break;
            default:
               playingOrder.add(playingOrder.remove());
               discardColor = discardPileTop.getCardColor();
               break;
         }
         
         // while the game state is false, continue playing uno
         while (!gameState.win) {
            currentPlayer = playingOrder.peek();
            
            //System.out.println("++===++");
            //System.out.println("Current Player: " + currentPlayer.playerNumber());
            //System.out.println("///////////");
            //currentPlayer.hand().printHand();
            //System.out.println("///////////");
            //System.out.println("Discard Pile Top: " + discardPileTop.toString());
            //System.out.println("Current Color: " + discardColor.toString());
            UnoCard newCard = currentPlayer.playCard(discardPileTop.getCardType(), discardColor,
                                                     gameState.stackingDrawFour,
                                                     gameState.stackingDrawTwo);
   
            //If newCard is null, then player has no playable cards in their hand
            if (newCard == null) {
               // If current played card is a draw two or draw four card, then player
               // has to draw ‘stackDrawValue’ number of cards
               if (gameState.stackingDrawTwo || gameState.stackingDrawFour) {
                  //System.out.println("Player " + currentPlayer.playerNumber() + " draws "
                  //                   + gameState.stackDrawValue + " cards");
                  for (int i = 0; i < gameState.stackDrawValue; i++) {
                     UnoCard drawnCard = playingDeck.copyRandomCard();
                     currentPlayer.hand().addCard(drawnCard);
                  }
                  gameState.stackingDrawTwo = false;
                  gameState.stackingDrawFour = false;
                  gameState.stackDrawValue = 0;
               // If not a draw two or draw four card, then player just draw one card from playing Deck
               } else {
                  //System.out.println("Player " + currentPlayer.playerNumber() + " draws a card");
                  UnoCard drawnCard = playingDeck.copyRandomCard();
                  currentPlayer.hand().addCard(drawnCard);
               }
               playingOrder.add(playingOrder.remove());
            } else {
               //System.out.println("Player " + currentPlayer.playerNumber() + " plays a " + newCard.toString());
   
               // If player has playable card in their hand, player plays card on top of discard pile then performs
               // action based on the type of card on the top of the discard pile
               discardPileTop = newCard;
               switch (discardPileTop.getCardType()) {
                  case DRAW_FOUR_WILDCARD:
                     gameState.stackingDrawFour = true;
                     gameState.stackDrawValue += 4;
                     //Set discardColor to the player’s chosen color (choose the color that they have most cards of)
                     discardColor = currentPlayer.chooseColor();
                     playingOrder.add(playingOrder.remove());
                     break;
                  case WILDCARD:
                     //Set discardColor to the player’s chosen color (choose the color that they have most cards of)
                     discardColor = currentPlayer.chooseColor();
                     playingOrder.add(playingOrder.remove());
                     break;
                  case DRAW_TWO:
                     gameState.stackingDrawTwo = true;
                     gameState.stackDrawValue += 2;
                     playingOrder.add(playingOrder.remove());
                     discardColor = discardPileTop.getCardColor();
                     break;
                  case REVERSE:
                     while(!playingOrder.isEmpty()) {
                        flipOrder.push(playingOrder.remove());
                     }
                     while(!flipOrder.isEmpty()) {
                        playingOrder.add(flipOrder.pop());
                     }
                     discardColor = discardPileTop.getCardColor();
                     break;
                  case SKIP:
                     playingOrder.add(playingOrder.remove());
                     playingOrder.add(playingOrder.remove());
                     discardColor = discardPileTop.getCardColor();
                     break;
                  default:
                     playingOrder.add(playingOrder.remove());
                     discardColor = discardPileTop.getCardColor();
                     break;
               }
               //System.out.println("New color: " + discardColor.toString());
            }
            // If currentPlayer has empty hand than that player is the winner  
            if (currentPlayer.hand().isEmpty()) {
               gameState.win = true;
            gameState.playerWinner = currentPlayer.playerNumber();
            }
            //System.out.println("++===++");
         }
         //System.out.println("Winner: Player #" + gameState.playerWinner);
         winCounts[gameState.playerWinner - 1]++;
      }
      System.out.println(Arrays.toString(winCounts));
   }
   
      // Helper class that stores the state of the game
   private static class StateOfGame {
      // The unique identifier of the winning player
      public int playerWinner;
      // The total number of draws that player has to draw from the deck
      public int stackDrawValue;
      // Whether the game has finished or not
      public boolean win;
      // Whether the top card on the discard pile is a draw two card played by the previous player
      public boolean stackingDrawTwo;
      // Whether the top card on the discard pile is a draw four card played by the previous player
      public boolean stackingDrawFour;

      // Constructs the inital state of the game
      public StateOfGame() {
         playerWinner = 0;
         stackDrawValue = 0;
         win = false;
         stackingDrawTwo = false;
         stackingDrawFour = false;
      }    
   }
}