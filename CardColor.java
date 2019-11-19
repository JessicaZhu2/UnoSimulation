// All possible standard colors of Uno card
public enum CardColor {
   NONE   (0), // Wildcard
   RED    (1),
   BLUE   (2),
   GREEN  (3),
   YELLOW (4);
   
   private final int number; // integer corresponding to Uno color
   
  // Constructs a CardColor.
  // Parameter:
  //    number = integer representing Uno color
   CardColor(int number) {
      this.number = number;
   }
   
   // Returns the integer that represents the color.
   public int getNumColor() {
      return number;
   }
   
   // Returns a string with the name of the card color
   public String toString() {
      switch (this) {
         case RED: return "Red ";
         case BLUE: return "Blue ";
         case GREEN: return "Green ";
         case YELLOW: return "Yellow ";
         case NONE: return "";
         default: throw new IllegalStateException("Invalid color");
      }
   }
}