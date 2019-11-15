import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

public class UnoGame {
   public static final int NUM_PLAYERS = 4;
   public static void main(String[] args) {
      UnoDeck dealingDeck = new UnoDeck();
      UnoDeck playingDeck = new UnoDeck();
      StateOfGame gameState = new StateOfGame();
      //playingDeck.printDeck();

      dealingDeck.shuffle();
      //dealDeck.printDeck();
      
      Queue<UnoPlayer> playingOrder = new LinkedList<UnoPlayer>();
      
      for (int i = 0; i < NUM_PLAYERS; i++) {
         UnoHand hand = new UnoHand();
         hand.dealNewHand(dealingDeck);
         UnoPlayer player = new UnoPlayer(i, hand);
         playingOrder.add(player);
      }
      
//       for (int i = 0; i < NUM_PLAYERS; i++) {
//          UnoHand hand = playingOrder.peek();
//          hand.printHand();
//          System.out.println(" " + playerNumber.peek());
//          playingOrder.add(playingOrder.remove());
//          playerNumber.add(playerNumber.remove());
//       }
      
      UnoCard dicardPileTop = dealingDeck.drawTopCard();
      
      // Classic Uno rules says you can't start with wild draw four card, so we keep drawing
      // until the starting card in the discard pile is not a wild draw four.
      while (dicardPileTop.getCardType().getNumType() == 14) {
         dicardPileTop = dealingDeck.drawTopCard();
      }
      
      playingOrder.add(playingOrder.remove());
      
      // INSERT MISSING FIRST TURN RULES
      
      while (!gameState.win) {
         CardType dicardType = dicardPileTop.getCardType();
         CardColor dicardColor = dicardPileTop.getCardColor();
         UnoPlayer currentPlayer = playingOrder.peek();
         // discardPileTop = playCard(currentHand, stackDrawTwo, stackDrawFour)
         
         
         switch (discardTypeNum) {
            case 14:
               discardColorNum = playerNumber.peek(); //
               break;
            case 13:
               discardColorNum = playerNumber.peek();
               break;
            case 12:
               discardColorNum = playerNumber.peek();
               break;
            case 11:
               discardColorNum = playerNumber.peek();
               break;
            case 10:
               discardColorNum = playerNumber.peek();
               break;
            default: // Card is number card 0-9
            
            // Let player decide base on strategy;
         }
         if (currentHand.isEmpty()) {
            gameState.win = true;
            gameState.playerWinner = currentPlayer;
         }
      }
      
      System.out.println("Winner: Player #" + currentPlayer);
   }
   
   private static class StateOfGame {
      public int playerWinner;
      public int stackDrawValue;
      public boolean win;
      public boolean skip;
      public boolean stackingDrawTwo;
      public boolean stackingDrawFour;
      
      public StateOfGame() {
         playerWinner = 0;
         stackDrawValue = 0;
         win = false;
         skip = false;
         stackingDrawTwo = false;
         stackingDrawFour = false;
      }    
   }
}