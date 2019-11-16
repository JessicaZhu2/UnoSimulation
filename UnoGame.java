import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

public class UnoGame {
   public static final int NUM_PLAYERS = 4;
   public static void main(String[] args) {
      UnoDeck dealingDeck = new UnoDeck();
      UnoDeck playingDeck = new UnoDeck();
      StateOfGame gameState = new StateOfGame();
      // playingDeck.printDeck();

      dealingDeck.shuffle();
      // dealingDeck.printDeck();
      
      Queue<UnoPlayer> playingOrder = new LinkedList<UnoPlayer>();
      Stack<UnoPlayer> flipOrder = new Stack<UnoPlayer>();
      
      for (int i = 0; i < NUM_PLAYERS; i++) {
         UnoHand hand = new UnoHand();
         hand.dealNewHand(dealingDeck);
         UnoPlayer player = new UnoPlayer(i, hand, 12);
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
      
      switch (discardPileTop.getCardType()) {
         case DRAW_FOUR_WILDCARD:
            gameState.stackingDrawFour = true;
            gameState.stackDrawValue += 4;
            discardColor = currentPlayer.chooseColor();
            playingOrder.add(playingOrder.remove());
            break;
         case WILDCARD:
            discardColor = currentPlayer.chooseColor();
            playingOrder.add(playingOrder.remove());
            break;
         case DRAW_TWO:
            gameState.stackingDrawTwo = true;
            gameState.stackDrawValue += 2;
            playingOrder.add(playingOrder.remove());
            break;
         case REVERSE:
            while(!playingOrder.isEmpty()) {
               flipOrder.push(playingOrder.remove());
            }
            while(!flipOrder.isEmpty()) {
               playingOrder.add(flipOrder.pop());
            }
            break;
         case SKIP:
            playingOrder.add(playingOrder.remove());
            playingOrder.add(playingOrder.remove());
            break;
         default:
            playingOrder.add(playingOrder.remove());
            break;
      }

      
      while (!gameState.win) {
         UnoPlayer currentPlayer = playingOrder.peek();
         UnoCard newCard = currentPlayer.playCard(discardPileTop,
                                                  gameState.stackingDrawTwo,
                                                  gameState.stackingDrawTwo);
         if (newCard == null) {
            if (gameState.stackingDrawTwo || gameState.stackingDrawFour) {
               for (int i = 0; i < gameState.stackDrawValue; i++) {
                  UnoCard drawnCard = playingDeck.copyRandomCard();
                  currentPlayer.hand().addCard(drawnCard);
               }
               gameState.stackingDrawTwo = false;
               gameState.stackingDrawFour = false;
               gameState.stackDrawValue = 0;
            } else {
               UnoCard drawnCard = playingDeck.copyRandomCard();
               currentPlayer.hand().addCard(drawnCard);
            }
            playingOrder.add(playingOrder.remove());
         } else {
            discardPileTop = newCard;
            switch (discardPileTop.getCardType()) {
               case DRAW_FOUR_WILDCARD:
                  gameState.stackingDrawFour = true;
                  gameState.stackDrawValue += 4;
                  discardColor = currentPlayer.chooseColor();
                  playingOrder.add(playingOrder.remove());
                  break;
               case WILDCARD:
                  discardColor = currentPlayer.chooseColor();
                  playingOrder.add(playingOrder.remove());
                  break;
               case DRAW_TWO:
                  gameState.stackingDrawTwo = true;
                  gameState.stackDrawValue += 2;
                  playingOrder.add(playingOrder.remove());
                  break;
               case REVERSE:
                  while(!playingOrder.isEmpty()) {
                     flipOrder.push(playingOrder.remove());
                  }
                  while(!flipOrder.isEmpty()) {
                     playingOrder.add(flipOrder.pop());
                  }
                  break;
               case SKIP:
                  playingOrder.add(playingOrder.remove());
                  playingOrder.add(playingOrder.remove());
                  break;
               default:
                  playingOrder.add(playingOrder.remove());
                  break;
               }
            }
            if (currentPlayer.hand().isEmpty()) {
            gameState.win = true;
            gameState.playerWinner = currentPlayer.playerNumber();
         }
      }
      
      System.out.println("Winner: Player #" + gameState.playerWinner);
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
         stackingDrawTwo = false;
         stackingDrawFour = false;
      }    
   }
}