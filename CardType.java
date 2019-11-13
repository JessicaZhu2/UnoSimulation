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
   PLUS_TWO           (12),
   WILDCARD           (13),
   PLUS_FOUR_WILDCARD (14);
   
   private final int number;
   
   CardType(int number) {
      this.number = number;
   }
   
   public int getNumType() {
      return number;
   }
   
   public String toString() {
      switch (this) {
         case SKIP: return "skip";
         case REVERSE: return "reverse";
         case PLUS_TWO: return "plus two";
         case WILDCARD: return "Wildcard";
         case PLUS_FOUR_WILDCARD: return "Plus four wildcard";
         default: return "" + this.number;
      }
   }
}