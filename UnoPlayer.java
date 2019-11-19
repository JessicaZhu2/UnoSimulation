import java.util.TreeSet;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class UnoPlayer {
   private final int playerNumber;
   private final UnoHand hand;
   // The action card type the player's is saving until they don't have any playable card
   private final CardType keepCardType;
   private final Random rand;  

   // Constructs an UnoPlayer with the given playerNumber, hand, and cardType
   // Parameters:
   //    playerNumber = the unique number used to identify the player
   //    hand = the player's hand
   //    cardType = card type of the action card that player's is saving until they don't have any playable card
   public UnoPlayer(int playerNumber, UnoHand hand, CardType cardType, Random random) {
      this.playerNumber = playerNumber;
      this.hand = hand;
      this.keepCardType = cardType;
      this.rand = random;
   }

   // Returns the player unique number
   public int playerNumber() {
      return this.playerNumber;
   }

   // Returns the player Uno hand
   public UnoHand hand() {
      return this.hand;
   }


   // Returns the uno card the player will play based on the given card type of the discard card,
   // color of the discard card, whether draw fours or draw twos are being stacked
   // If there is no playable card, then return null
   public UnoCard playCard(CardType discardType, CardColor discardColor,
                           boolean stackingDrawFour, boolean stackingDrawTwo) {
      Set<UnoCard> playableCards = new TreeSet<UnoCard>();
      // If the top card on discard pile is a draw four card and player has a draw four card in their hand
      // player will stack the draw four card
      if (stackingDrawFour) {
         UnoCard drawFourWildcard = new UnoCard(CardColor.NONE, CardType.DRAW_FOUR_WILDCARD);
         if (hand.uniqueCards().contains(drawFourWildcard)) {
            hand.playCard(drawFourWildcard);
            return drawFourWildcard; 
         } else {       // Otherwise return null
            return null; 
         }
         // If the top card on the discard pile is a draw two card and player has a draw two card in their hand
         // player will stack the it with a draw two card they have most colors of
         // Follows the assumption that the player will choose color that they have the most cards
         // If the player has equal amounts of draw two card for each color, it will play a random draw two card
      } else if (stackingDrawTwo) {
         for (UnoCard card : hand.uniqueCards()) {
            if (card.sameTypeAs(CardType.DRAW_TWO))  {
               playableCards.add(card);
            }
         }
         // Otherwise return null, if there is no playable card
         if (playableCards.isEmpty()) {
            return null;
         } else {
            // Find the draw two card that the player has the most color of
            UnoCard mostColorsPlusTwos = null;
            int[] numEachColor = hand.numEachColor();
            List<Integer> maxNumColorIndexes = new LinkedList<Integer>();
            for (UnoCard playableCard : playableCards) {
               int numColor = playableCard.getCardColor().getNumColor();
               if (maxNumColorIndexes.isEmpty()) {
                  maxNumColorIndexes.add(numColor);
               } else {
                  // If player has equal amounts of draw two cards for each color, add it to the maxNumColorIndexes
                  if (numEachColor[numColor] == numEachColor[maxNumColorIndexes.get(0)]) {
                     maxNumColorIndexes.add(numColor);
                     // If player has more draw two cards for a specific color, clear the maxNumColorIndexes list
                     // and add numColor index to maxNumColorIndexes
                  } else if (numEachColor[numColor] > numEachColor[maxNumColorIndexes.get(0)]) {
                     maxNumColorIndexes.clear();
                     maxNumColorIndexes.add(numColor);
                  }
               }
            }
            // Randominize which color is chosen
            int randomNum = rand.nextInt(maxNumColorIndexes.size());
            int maxNumRandomColorIndex = maxNumColorIndexes.get(randomNum);
               
            CardColor randMostColors = CardColor.NONE;

            if (maxNumRandomColorIndex == 1) {
               randMostColors = CardColor.RED;
            } else if (maxNumRandomColorIndex == 2) {
               randMostColors = CardColor.BLUE;
            } else if (maxNumRandomColorIndex == 3) { 
               randMostColors = CardColor.GREEN;
            } else {
               randMostColors = CardColor.YELLOW;
            }
            // draw two card that is played of randMostColors
            UnoCard cardToPlay = new UnoCard(randMostColors, CardType.DRAW_TWO);
            hand.playCard(cardToPlay);
            return cardToPlay;
         }
      } else {
         // Go through all the unique cards to find all the playable cards
         for (UnoCard card : hand.uniqueCards()) {
            if (card.playableOn(discardType, discardColor, stackingDrawTwo, stackingDrawFour))  {
               playableCards.add(card);
            }
         }
         // If there is no playable cards, return null
         if (playableCards.isEmpty()) {
            return null;
         } else {  // If there is playable cards
            // List of cards that are the same action card type as keepCardType
            List<UnoCard> sameKeepTypeCards = new LinkedList<UnoCard>();
            // List of other playable cards that are not the same card type as keepCardType
            List<UnoCard> notSameKeepTypeCards = new LinkedList<UnoCard>();
      
            // populate the sameKeepTypeCards and notSameKeepTypeCards list
            for (UnoCard card : playableCards) {
               if (card.sameTypeAs(keepCardType)) {
                  sameKeepTypeCards.add(card);
               } else {
                  notSameKeepTypeCards.add(card);
               }
            }
      
            // If there are only playble cards from the notSameKeepTypeCards list, play random card from notSameKeepTypeCards
            if (sameKeepTypeCards.isEmpty() || !notSameKeepTypeCards.isEmpty()) {
               int randIndex = rand.nextInt(notSameKeepTypeCards.size());
               UnoCard cardToPlay = notSameKeepTypeCards.get(randIndex);
               hand.playCard(cardToPlay);
               return cardToPlay;
            } else {
               // If there are playble cards from the sameKeepTypeCards list, play random card from sameKeepTypeCards
               int randIndex = rand.nextInt(sameKeepTypeCards.size());
               UnoCard cardToPlay = sameKeepTypeCards.get(randIndex);
               hand.playCard(cardToPlay);
               return cardToPlay;
            }
         }
      }
   }

   // Return the card color that they player has the most color of
   // When a player plays a wildcard, it chooses the color that they have the most color of
   // If there is equal amount of card per color, choose random color out of the colors they currently have
   public CardColor chooseColor() {
      // IMPLEMENT SWITCHING TO GREATEST NUMBER NONWILDCARD COLOR AND RAND COLOR IF LAST CARD OR TIED
      int[] numEachColor = hand.numEachColor();
      List<Integer> maxNumColorIndexes = new LinkedList<Integer>();
      maxNumColorIndexes.add(1);
     
      // loop through each color to find the index of the color the player has the most color of
      for (int i = 2; i < numEachColor.length; i++) {
         if (numEachColor[i] == numEachColor[maxNumColorIndexes.get(0)]) {
            maxNumColorIndexes.add(i);
         } else if (numEachColor[i] > numEachColor[maxNumColorIndexes.get(0)]) {
            maxNumColorIndexes.clear();
            maxNumColorIndexes.add(i);
         }
      }
      int randomColor = rand.nextInt(maxNumColorIndexes.size());
      int maxNumColorIndex = maxNumColorIndexes.get(randomColor);
      
      if (maxNumColorIndex == 1) {
         return CardColor.RED;
      } else if (maxNumColorIndex == 2) {
         return CardColor.BLUE;
      } else if (maxNumColorIndex == 3) { 
         return CardColor.GREEN;
      } else {
         return CardColor.YELLOW;
      }
   }
}