public enum CardColor {
   RED    (1),
   BLUE   (2),
   GREEN  (3),
   YELLOW (4),
   NONE   (5);
   
   private final int number;
   
   CardColor(int number) {
      this.number = number;
   }
   
   public int getNumColor() {
      return number;
   }
   
   public String toString() {
      switch (this) {
         case RED: return "RED ";
         case BLUE: return "BLUE ";
         case GREEN: return "GREEN ";
         case YELLOW: return "YELLOW ";
         case NONE: return "";
         default: throw new IllegalStateException("Invalid color");
      }
   }
}