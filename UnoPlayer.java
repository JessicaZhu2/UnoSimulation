import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class UnoPlayer {
   private final int playerNumber;
   private final UnoHand hand;
   private final CardType keepCardType;
   
   public UnoPlayer(int playerNumber, UnoHand hand, CardType cardType) {
      this.playerNumber = playerNumber;
      this.hand = hand;
      this.keepCardType = cardType;
   }
   
   public int playerNumber() {
      return this.playerNumber;
   }
   
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
         List<UnoCard> sameTypeCards = new LinkedList<UnoCard>();
         List<UnoCard> notSameTypeCards = new LinkedList<UnoCard>();
         for (UnoCard card : playableCards) {
            if (card.sameTypeAs(keepCardType)) {
               sameTypeCards.add(card);
            } else {
               notSameTypeCards.add(card);
            }
         }
         Random rand = new Random();
         if (sameTypeCards.isEmpty() || !notSameTypeCards.isEmpty()) {
            int randIndex = rand.nextInt(notSameTypeCards.size());
            return notSameTypeCards.get(randIndex);
         } else {
            int randIndex = rand.nextInt(sameTypeCards.size());
            return sameTypeCards.get(randIndex);
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