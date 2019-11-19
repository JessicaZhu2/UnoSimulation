import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// Represents a standard playing deck in Uno.
public class UnoDeck {
   private final List<UnoCard> deck;
   private final Random rand;
   
// Constructs a full standard Uno deck with the following cards
//    - 1 "zero" card from each color
//    - 2 of every number cards 1-9 for every color
//    - 2 skips of every color
//    - 2 reverses of every color
//    - 4 wildcards
//    - 4 draw four wildcards
// Parameter:
//    random = the Random object to use for generating random numbers
   public UnoDeck(Random random) {
      rand = random;
      deck = new ArrayList<UnoCard>();
      for (CardType type : CardType.values()) { // for each Uno card type
         for (CardColor color : CardColor.values()) { // for each Uno card  color
            if (color.getNumColor() != 0) { // if not a wildcard type
               if (type.getNumType() == 0) { // if card is the zero card
                  deck.add(new UnoCard(color, type)); // add one
               } else { // otherwise
                  for (int i = 0; i < 2; i++) { // add two of this card with this type and c 
                     deck.add(new UnoCard(color, type));
                  }
               }
            } else if (color.getNumColor() == 0 && type.getNumType() > 12) {
               for (int i = 0; i < 4; i++) {
                  deck.add(new UnoCard(color, type));
               }               
            }
         }
      }
   }
   
   // Shuffles the Uno deck randomly to get a random order of the deck.
   public void shuffle() {
      for (int i = deck.size(); i > 0; i--) {
         int switchIndex = rand.nextInt(i);
         Collections.swap(deck, i - 1, switchIndex);
      }
      //printDeck();
   }
   
   // Prints out all the cards in the deck.
   public void printDeck() {
      for (int i = 0; i < deck.size(); i++) {
         System.out.println(deck.get(i));
      }
      System.out.println("----------");
   }
   
   // Returns the number of cards in the deck.
   public int size() {
      return deck.size();
   }
   
   // Returns true/false for whether the deck is empty (no cards in the deck).
   public boolean isEmpty() {
        return deck.size() == 0;
   }
   
   // Draws the top card out of the deck, removing it from the deck. Returns the removed UnoCard. 
   public UnoCard drawTopCard() {
      if (isEmpty()) {
         System.out.println("Deck is EMPTY! Dummy");
         throw new IllegalStateException();
      }
      UnoCard card = deck.get(0);
      deck.remove(0);
      return card;
   }
   
   // Returns a random copy of the UnoCard from the deck (deck remains the same).
   public UnoCard copyRandomCard() {
      int index = rand.nextInt(deck.size());
      return deck.get(index);
   }
}