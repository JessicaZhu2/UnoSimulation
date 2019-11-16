import java.util.Map;
import java.util.HashMap;
import java.util.Set;

// Represents a hand of Uno Card.
public class UnoHand {
   private final int[] numEachType;
   private final int[] numEachColor;
   private final Map<UnoCard, Integer> cardCounts;
   
   // Constructs an UnoHand.
   public UnoHand() {
      numEachType = new int[15];
      numEachColor = new int[5];
      cardCounts = new HashMap<UnoCard, Integer>();
   } 
   
   // Deals a new starting hand of 7 cards from an UnoDeck.
   // Parameter:
   //    deck = UnoDeck to deal out cards from
   public void dealNewHand(UnoDeck deck) {
      for (int i = 0; i < 7; i++) {
         UnoCard card = deck.drawTopCard();
         addCard(card);
      }
   }
   
   // Adds an UnoCard to the hand
   // Parameter:
   //    card = UnoCard to add to the deck
   public void addCard(UnoCard card) {
      int colorIndex = card.getCardColor().getNumColor();
      int typeIndex = card.getCardType().getNumType();
      numEachColor[colorIndex]++;
      numEachType[typeIndex]++;
      
      if (!cardCounts.containsKey(card)) {
         cardCounts.put(card, 1);
      } else {
         cardCounts.put(card,cardCounts.get(card) + 1);
      }
   }
   
   // Plays a selected card from the hand
   // Parameter:
   //    selectedCard = selected card to play
   public void playCard(UnoCard selectedCard) {
      if (!cardCounts.keySet().contains(selectedCard)) {     
         throw new IllegalArgumentException("Card does not exist in hand");
      }
      int colorIndex = selectedCard.getCardColor().getNumColor();
      int typeIndex = selectedCard.getCardType().getNumType();
      numEachType[typeIndex]--;
      numEachColor[colorIndex]--;
      int currentcount = cardCounts.get(selectedCard);
      if (currentcount == 1) {
         cardCounts.remove(selectedCard);
      } else {
         cardCounts.put(selectedCard, currentcount - 1);
      }
   }
   
   // Returns true/false for whether the hand contains a color
   // Parameter:
   //    color = UnoColor to locate in hand
   public boolean hasColor(CardColor color) {
      return numEachColor[color.getNumColor()] > 0;
   }
   
   // Returns true/false for whether the hand contains an Uno card type
   // Parameter:
   //    type = Uno card type to locate in hand
   public boolean hasType(CardType type) {
      return numEachType[type.getNumType()] > 0;
   }
   
   // Returns true/false for whether the hand is empty
   public boolean isEmpty() {
      return cardCounts.isEmpty();
   }
   
   // 
   public Set<UnoCard> uniqueCards() {
      return cardCounts.keySet();
   }
   
   // Prints out all the cards in the hand.
   public void printHand() {
      for (UnoCard card : cardCounts.keySet()) {
          int numcards = cardCounts.get(card);
          for (int i = 0; i < numcards; i++) {
            System.out.println(card.toString() + " | " + cardCounts.get(card));
          }
      }
      System.out.println("++===++");
   }
}