import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UnoDeck {
   private List<UnoCard> deck;
   
   public UnoDeck() {
      deck = new ArrayList<UnoCard>();
      for (CardType type : CardType.values()) {
         for (CardColor color : CardColor.values()) {
            if (color.getNumColor() != 5) {
               if (type.getNumType() == 0) {
                  deck.add(new UnoCard(color, type));
               } else if (type.getNumType() < 13) {
                  for (int i = 0; i < 2; i++) {
                     deck.add(new UnoCard(color, type));
                  }
               }
            } else if (color.getNumColor() == 5 && type.getNumType() > 12) {
               for (int i = 0; i < 4; i++) {
                  deck.add(new UnoCard(color, type));
               }               
            }
         }
      }
      //printDeck();
   }
   
   public void shuffle() {
      Random rand = new Random();
      for (int i = deck.size(); i > 0; i--) {
         int switchIndex = rand.nextInt(i);
         Collections.swap(deck, i - 1, switchIndex);
      }
      printDeck();
   }
   
   public void printDeck() {
      for (int i = 0; i < deck.size(); i++) {
         System.out.println(deck.get(i));
      }
      System.out.println("----------");
   }
   
   public int size() {
      return deck.size();
   }
   
   public boolean isEmpty() {
        return deck.size() == 0;
   }
   
   public UnoCard drawTopCard() {
      if (isEmpty()) {
         System.out.println("Deck is EMPTY! Dummy");
         throw new IllegalStateException();
      }
      UnoCard card = deck.get(0);
      deck.remove(0);
      return card;
   }
}