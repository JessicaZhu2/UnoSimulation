public class UnoCard {
   private final CardType type;
   private final CardColor color;
   
   public UnoCard(CardColor color, CardType type) {
      this.color = color;
      this.type = type;
   }
   
   public CardColor getCardColor() {
      return color;
   }
   
   public CardType getCardType() {
      return type;
   }
   
   public boolean sameColorAs(UnoCard other) {
      return other.color == this.color;
   }
   
   public boolean sameNumberAs(UnoCard other) {
      return other.type.getNumType() == this.type.getNumType();
   }
   
   public String toString() {
      return this.color.toString() + this.type.toString();
   }
}