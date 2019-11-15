// All possible standard types of Uno cards

public enum CardType {
   ZERO               (0),
   ONE                (1),
   TWO                (2),
   THREE              (3),
   FOUR               (4),
   FIVE               (5),
   SIX                (6),
   SEVEN              (7),
   EIGHT              (8),
   NINE               (9),
   SKIP               (10),
   REVERSE            (11),
   DRAW_TWO           (12),
   WILDCARD           (13),
   DRAW_FOUR_WILDCARD (14);
   
   private final int number; // integer corresponding to Uno card type
   
   // Constructs a CardType
   // Parameter:
   //    number = integer corresponding to Uno card type
   CardType(int number) {
      this.number = number;
   }
   
   // Returns the integer that represents the card type.
   public int getNumType() {
      return number;
   }
   
   // Returns a string with the name of the card type. 
   public String toString() {
      switch (this) {
         case SKIP: return "skip";
         case REVERSE: return "reverse";
         case DRAW_TWO: return "draw two";
         case WILDCARD: return "Wildcard";
         case DRAW_FOUR_WILDCARD: return "Draw four wildcard";
         default: return "" + this.number;
      }
   }
}