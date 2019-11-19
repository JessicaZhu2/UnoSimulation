// Represents an standard Uno card
public class UnoCard {
   private final CardType type;
   private final CardColor color;
   
   // Constructs an UnoCard with specified color and type.
   // Parameters:
   //    color = intended color of the Uno card
   //    type = intended type of Uno card
   public UnoCard(CardColor color, CardType type) {
      this.color = color;
      this.type = type;
   }
   
   // Returns the CardColor of the Uno card.
   public CardColor getCardColor() {
      return color;
   }
   
   //// Returns the integer number that represents the Uno card's CardColor.
   //public int getCardColorNum() {
   //   return color.getNumColor();
   //}
   
   // Returns the CardType of the Uno card.
   public CardType getCardType() {
      return type;
   }
   
   // Returns the integer number that represents the Uno card's CardType.
   //public int getCardTypeNum() {
   //   return type.getNumType();
   //}
   
   
   // Returns true/false for whether two cards have the same CardColor.
   public boolean sameColorAs(UnoCard other) {
      return other.color == this.color;
   }
   
   // Returns true/false for whether the card is the specified CardColor.
   public boolean sameColorAs(CardColor color) {
      return color == this.color;
   }
   
   // Returns true/false for whether two cards have the same CardType.
   public boolean sameTypeAs(UnoCard other) {
      return other.type == this.type;
   }
   
   // Returns true/false for whether the card is the specified CardType.
   public boolean sameTypeAs(CardType type) {
      return type == this.type;
   }
   
   // Returns true/false for whether the card is a wildcard.
   public boolean isWildcard() {
      return getCardColor().getNumColor() == 0;
   }
   
   // Returns if the card is playable given a situation in Uno
   // Parameters:
   //    currentType      = type of UnoCard 
   //    currentColor     = color of UnoCard
   //                       (chosen color if wildcard) 
   //    stackingDrawTwo  = whether draw twos are being stacked
   //    stackingDrawFour = whether draw fours are being stacked
   public boolean playableOn(CardType currentType, CardColor currentColor,
                             boolean stackingDrawTwo, boolean stackingDrawFour) {
      if (stackingDrawTwo) {
         return currentType == CardType.DRAW_TWO;
      } else if (stackingDrawFour) {
         return currentType == CardType.DRAW_FOUR_WILDCARD;
      } else {
         return (this.isWildcard() || this.sameTypeAs(currentType) ||
                 this.sameColorAs(currentColor));
      }
   }
   
   // Returns true/false for whether the other object is the same as the UnoCard.
   // Parameters:
   //    other - other object to compare to this instance of UnoCard
   @Override
   public boolean equals(Object other) {
      if (!(other instanceof UnoCard)) {
          return false;
      }
      UnoCard card = (UnoCard) other;
      return (this.sameColorAs(card) && this.sameTypeAs(card));
   }
   
   // Returns integer hash code for this UnoCard. (If two cards are the same color and type,
   //  then the hash code should be the same: different otherwise)
   @Override
   public int hashCode() {
      return color.hashCode() + type.hashCode();
   }
   
   // Returns the string for the Uno card's name.
   public String toString() {
      return this.color.toString() + this.type.toString();
   }
}