public class TestUno {
   public static void main(String[] args) {
      UnoDeck deck = new UnoDeck();
      deck.shuffle();
      for (int i = 0; i < 109; i++) {
         deck.drawTopCard();
      }
      deck.printDeck();
   }
}