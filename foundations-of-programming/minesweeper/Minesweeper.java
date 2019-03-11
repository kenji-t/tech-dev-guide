import java.util.*;

public class Minesweeper {
  public static void main(String args[]) {

    Matrix matrix;
    Scanner scan;

    matrix = new Matrix(9, 9, 4);
    matrix.show();

    while (!matrix.win && !matrix.lose) {
      System.out.println("Select a row and column (e.g. '1-1').");
      scan = new Scanner(System.in);
      String answer = scan.next();

      if (answer.matches("[0-9]{1,}-[0-9]{1,}")) {
        int y = Integer.parseInt(answer.split("-")[0]);
        int x = Integer.parseInt(answer.split("-")[1]);
        matrix.openCell(x, y);
        matrix.checkOpenCount();
      } else {
        System.out.println("Invalid format.");
      }

      matrix.show();
    }

    if (matrix.win) {
      System.out.println("You win.");
    } else {
      System.out.println("You lose.");
    }

    matrix.show();
  }
}
