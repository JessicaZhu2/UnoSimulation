import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class UnoPlayer {
   private final int playerNumber;
   private final UnoHand hand;
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

   
   public UnoCard playCard(CardType discardType, CardColor discardColor,
                           boolean stackingDrawFour, boolean stackingDrawTwo) {
      Set<UnoCard> playableCards = new HashSet<UnoCard>();

      for (UnoCard card : hand.uniqueCards()) {
         if (card.playableOn(discardType, discardColor, stackingDrawTwo, stackingDrawFour))  {
            playableCards.add(card);
         }
      }
      if (playableCards.isEmpty()) {
         return null;
      } else {
         Set<UnoCard> sameTypeCards = new HashSet<UnoCard>();
         List<UnoCard> notSameTypeCards = new LinkedList<UnoCard>();
         for (UnoCard card : playableCards) {
            if (card.sameTypeAs(keepCardType)) {
               sameTypeCards.add(card);
            } else {
               notSameTypeCards.add(card);
            }
         }
         if (sameTypeCards.isEmpty()) {
            return null;
         } else {
            Random rand = new Random();
            int randIndex = rand.nextInt(notSameTypeCards.size());
            return notSameTypeCards.get(randIndex);
         }
      }
   }
   
   public CardColor chooseColor() {
      // IMPLEMENT SWITCHING TO GREATEST NUMBER NONWILDCARD COLOR AND RAND COLOR IF LAST CARD OR TIED
      int[] numEachColor = hand.numEachColor();
      int maxNumColorIndex = 1;
      for(int i = 2; i < numEachColor.length; i++) {
         if (numEachColor[i] > numEachColor[maxNumColorIndex]) {
            maxNumColorIndex = i;
         }
      }
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