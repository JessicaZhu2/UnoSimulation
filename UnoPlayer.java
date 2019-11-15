public class UnoPlayer {
   private final int playerNumber;
   private final UnoHand hand;
   
   public UnoPlayer(int playerNumber, UnoHand hand) {
      this.playerNumber = playerNumber;
      this.hand = hand;
   }
   
   public int playerNumber() {
      return this.playerNumber;
   }
   
   public UnoHand hand() {
      return this.hand;
   }
   
   //public UnoCard playCard() {
   //   return 
   //}
}