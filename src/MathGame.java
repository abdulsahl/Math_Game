package mathgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MathGame extends JFrame {
    private GameLogic gameLogic;
    private JLabel questionLabel;
    private JTextField answerField;
    private JLabel scoreLabel;
    private JLabel timerLabel;
    private JButton submitButton;
    private Timer timer;
    private int timeLeft;
    
    public MathGame() {
        gameLogic = new GameLogic();
        initComponents();
        initTimer();
    }
    
    private void initComponents() {
        setTitle("Game Hitung Cepat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);
        
        // Panel atas untuk timer dan score
        JPanel topPanel = new JPanel(new FlowLayout());
        timerLabel = new JLabel("Waktu: 30");
        scoreLabel = new JLabel("Skor: 0");
        topPanel.add(timerLabel);
        topPanel.add(Box.createHorizontalStrut(50));
        topPanel.add(scoreLabel);
        
        // Panel tengah untuk pertanyaan
        JPanel centerPanel = new JPanel(new FlowLayout());
        questionLabel = new JLabel("");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        centerPanel.add(questionLabel);
        
        // Panel bawah untuk input jawaban
        JPanel bottomPanel = new JPanel(new FlowLayout());
        answerField = new JTextField(10);
        submitButton = new JButton("Jawab");
        bottomPanel.add(new JLabel("Jawaban: "));
        bottomPanel.add(answerField);
        bottomPanel.add(submitButton);
        
        // Menambahkan semua panel ke frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Event handlers
        submitButton.addActionListener(e -> checkAnswer());
        answerField.addActionListener(e -> checkAnswer());
        
        // Memulai pertanyaan pertama
        showNextQuestion();
        
        // Setting frame
        pack();
        setLocationRelativeTo(null);
    }
    
    private void initTimer() {
        timeLeft = 30;
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Waktu: " + timeLeft);
            
            if (timeLeft <= 0) {
                ((Timer)e.getSource()).stop();
                gameOver();
            }
        });
        timer.start();
    }
    
    private void showNextQuestion() {
        gameLogic.generateQuestion();
        questionLabel.setText(gameLogic.getCurrentQuestion());
        answerField.setText("");
        answerField.requestFocus();
    }
    
    private void checkAnswer() {
        try {
            int userAnswer = Integer.parseInt(answerField.getText());
            if (gameLogic.checkAnswer(userAnswer)) {
                scoreLabel.setText("Skor: " + gameLogic.getScore());
                showNextQuestion();
            } else {
                JOptionPane.showMessageDialog(this, "Jawaban Salah!");
                answerField.setText("");
                answerField.requestFocus();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid!");
            answerField.setText("");
            answerField.requestFocus();
        }
    }
    
    private void gameOver() {
        JOptionPane.showMessageDialog(this, 
            "Game Over!\nSkor akhir: " + gameLogic.getScore(),
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE);
        
        int option = JOptionPane.showConfirmDialog(this,
            "Main lagi?",
            "Game Over",
            JOptionPane.YES_NO_OPTION);
            
        if (option == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }
    
    private void resetGame() {
        gameLogic.resetScore();
        scoreLabel.setText("Skor: 0");
        timeLeft = 30;
        timer.start();
        showNextQuestion();
    }
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            new MathGame().setVisible(true);
        });
    }
}