package mathgame;

import java.util.Random;

public class GameLogic {
    private int num1;
    private int num2;
    private int correctAnswer;
    private int score;
    private Random random;
    private char operator;
    
    public GameLogic() {
        random = new Random();
        score = 0;
    }
    
    public void generateQuestion() {
        num1 = random.nextInt(10) + 1;
        num2 = random.nextInt(10) + 1;
        
        // Memilih operator secara random (+, -, *)
        int op = random.nextInt(3);
        switch (op) {
            case 0:
                operator = '+';
                correctAnswer = num1 + num2;
                break;
            case 1:
                operator = '-';
                correctAnswer = num1 - num2;
                break;
            case 2:
                operator = '*';
                correctAnswer = num1 * num2;
                break;
        }
    }
    
    public String getCurrentQuestion() {
        return num1 + " " + operator + " " + num2 + " = ?";
    }
    
    public boolean checkAnswer(int userAnswer) {
        if (userAnswer == correctAnswer) {
            score += 10;
            return true;
        }
        return false;
    }
    
    public int getScore() {
        return score;
    }
    
    public void resetScore() {
        score = 0;
    }
}