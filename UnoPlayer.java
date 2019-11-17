import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class UnoPlayer {
   private final int playerNumber;
   private final UnoHand hand;
   // The action card type the player's is saving until they don't have any playable card
   private final CardType keepCardType;

   // Constructs an UnoPlayer with the given playerNumber, hand, and cardType
   // Parameters:
   //    playerNumber = the unique number used to identify the player
   //    hand = the player's hand
   //    cardType = card type of the action card that player's is saving until they don't have any playable card
   public UnoPlayer(int playerNumber, UnoHand hand, CardType cardType) {
      this.playerNumber = playerNumber;
      this.hand = hand;
      this.keepCardType = cardType;
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
   public UnoCard playCard(CardType discardType, CardColor discardColor,
                           boolean stackingDrawFour, boolean stackingDrawTwo) {
      Set<UnoCard> playableCards = new HashSet<UnoCard>();

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

         Random rand = new Random();
         // If there are only playble cards from the notSameKeepTypeCards list, play random card from notSameKeepTypeCards
         if (sameKeepTypeCards.isEmpty() || !notSameKeepTypeCards.isEmpty()) {
            int randIndex = rand.nextInt(notSameKeepTypeCards.size());
            return notSameKeepTypeCards.get(randIndex);
         } else {
            // If there are playble cards from the sameKeepTypeCards list, play random card from sameKeepTypeCards
            int randIndex = rand.nextInt(sameKeepTypeCards.size());
            return sameKeepTypeCards.get(randIndex);
         }
      }
   }

   // Return the card color that they player has the most color of
   // When a player plays a wildcard, it chooses the color that they have the most color of
   // If there is equal amount of card per color, choose random color out of the colors they currently have
   public CardColor chooseColor() {
      // IMPLEMENT SWITCHING TO GREATEST NUMBER NONWILDCARD COLOR AND RAND COLOR IF LAST CARD OR TIED
      int[] numEachColor = hand.numEachColor();
      int maxNumColorIndex = 1;
      // loop through each color to find the index of the color the player has the most color of
      for(int i = 2; i < numEachColor.length; i++) {
         if (numEachColor[i] > numEachColor[maxNumColorIndex]) {
            maxNumColorIndex = i;
         }
      }
      //
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