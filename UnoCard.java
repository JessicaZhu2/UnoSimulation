public class UnoCard {
   private final CardType type;
   private final CardColor color;
   
   public UnoCard(CardType type, CardColor color) {
      this.type = type;
      this.color = color;
   }
   
   public CardType getCardType() {
      return type;
   }
   
   public CardColor getCardColor() {
      return color;
   }
   
   public boolean sameColorAs(UnoCard other) {
      return other.color == this.color;
   }
   
   public boolean sameNumberAs(UnoCard other) {
      return other.type.getNumber() == this.type.getNumber();
   }
   
   public String toString() {
      return this.color.toString() + this.type.toString();
   }
}