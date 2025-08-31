package flashcards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FlashCards extends JFrame implements ActionListener {
    private JTextArea cardText;
    private JButton flipButton, nextButton, prevButton, addButton;
    private ArrayList<String[]> cards;
    private int currentIndex;
    private boolean showingFront;

    public FlashCards() {
        setTitle("Flash Cards Application");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardText = new JTextArea("Welcome! Add your first card.");
        cardText.setFont(new Font("Arial", Font.PLAIN, 20));
        cardText.setEditable(false);
        add(cardText, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        flipButton = new JButton("Flip");
        nextButton = new JButton("Next");
        prevButton = new JButton("Previous");
        addButton = new JButton("Add Card");

        flipButton.addActionListener(this);
        nextButton.addActionListener(this);
        prevButton.addActionListener(this);
        addButton.addActionListener(this);

        buttonPanel.add(prevButton);
        buttonPanel.add(flipButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(addButton);

        add(buttonPanel, BorderLayout.SOUTH);

        cards = new ArrayList<>();
        currentIndex = -1;
        showingFront = true;

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String front = JOptionPane.showInputDialog(this, "Enter card front:");
            String back = JOptionPane.showInputDialog(this, "Enter card back:");
            cards.add(new String[]{front, back});
            if (cards.size() == 1) {
                currentIndex = 0;
                cardText.setText(cards.get(currentIndex)[0]);
            }
        } else if (e.getSource() == flipButton) {
            if (currentIndex >= 0) {
                showingFront = !showingFront;
                cardText.setText(cards.get(currentIndex)[showingFront ? 0 : 1]);
            }
        } else if (e.getSource() == nextButton) {
            if (currentIndex < cards.size() - 1) {
                currentIndex++;
                showingFront = true;
                cardText.setText(cards.get(currentIndex)[0]);
            }
        } else if (e.getSource() == prevButton) {
            if (currentIndex > 0) {
                currentIndex--;
                showingFront = true;
                cardText.setText(cards.get(currentIndex)[0]);
            }
        }
    }

    public static void main(String[] args) {
        new FlashCards();
    }
}
