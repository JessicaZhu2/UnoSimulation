public class UnoPlayer {
   private final int playerNumber;
   private final UnoHand hand;
   private final int keepCardType;
   
   public UnoPlayer(int playerNumber, UnoHand hand, int numCardType) {
      this.playerNumber = playerNumber;
      this.hand = hand;
      this.keepCardType = numCardType;
   }
   
   public int playerNumber() {
      return this.playerNumber;
   }
   
   public UnoHand hand() {
      return this.hand;
   }
   
   public UnoCard playCard() {
      //if () {
      //   hand.playCard();
      //else {
         return null;
      //}
   }
   
   public CardColor chooseColor() {
      // IMPLEMENT SWITCHING TO GREATEST NUMBER NONWILDCARD COLOR AND RAND COLOR IF LAST CARD OR TIED
      return CardColor.RED;
   }
}