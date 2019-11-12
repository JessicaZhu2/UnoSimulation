public enum CardColor {
   RED    (1),
   BLUE   (2),
   GREEN  (3),
   YELLOW (4),
   WILD   (5);
   
   private final int color;
   
   CardColor(int color) {
      this.color = color;
   }
   
   public int getNumColor() {
      return color;
   }
   
   public String toString() {
      switch (this) {
         case RED: return "Red";
         case BLUE: return "Blue";
         case GREEN: return "Green";
         case YELLOW: return "Yellow";
         case WILD: return "";
         default: throw new IllegalStateException("Invalid color");
      }
   }
}