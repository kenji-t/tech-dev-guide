import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.util.Scanner;

public class Hangman extends ConsoleProgram {
  private HangmanCanvas canvas;
  private HangmanLexicon hl;
  private Scanner scan;

  public void init() {
    canvas = new HangmanCanvas();
    add(canvas);
  }

  public void run() {
    hl = new HangmanLexicon();
    canvas = new HangmanCanvas();
    scan = new Scanner(System.in);

    canvas.reset();

    int guessCountLimit = 8;
    int rand = (int)(Math.random() * hl.getWordCount());
    String word = hl.getWord(rand);
    String secretWord = word;
    String displayWord = secretWord.replaceAll(".", "-");

    println("Welcome to Hangman!");

    while (guessCountLimit > 0) {
      println("The word now looks like this: " + displayWord);
      println("You have " + String.valueOf(guessCountLimit) + " guesses left.");

      String answer = scan.next();
      println("Your guess: " + answer.toUpperCase());

      int index = secretWord.indexOf(answer.toUpperCase());
      if (index > -1) {
        println("The guess is correct.");
        if (index == 0) {
          secretWord = "-" + secretWord.substring(index+1, secretWord.length());
          displayWord = answer + displayWord.substring(index+1, displayWord.length());
        } else {
          secretWord = secretWord.substring(0, index) + "-" + secretWord.substring(index+1, secretWord.length());
          displayWord = displayWord.substring(0, index) + answer + displayWord.substring(index+1, displayWord.length());
        }
      } else {
        println("There are no " + answer.toUpperCase() + "'s in the word.");
        guessCountLimit--;
      }

      if (!displayWord.contains("-")) {
        println("You guessed the word: " + displayWord);
        println("You win.");
        return;
      }
    }

    println("You're completely hung.");
    println("The word was: " + word.toUpperCase());
    println("You lose.");
  }
}
