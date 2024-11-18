import java.util.LinkedList;

public class Player {
    private LinkedList<Card> hand;
    private String name;
    private int score;
    private double balance;

    public Player(String name, double initialBalance) {
        this.name = name;
        this.hand = new LinkedList<>();
        this.score = 0;
        this.balance = initialBalance;
    }

    public void addCard(Card card) {
        hand.add(card);
        calculateScore();
    }

    private void calculateScore() {
        score = 0;
        int numAces = 0;

        for (Card card : hand) {
            if (card.getValue() == 11) numAces++;
            score += card.getValue();
        }

        // Handle aces if score is over 21
        while (score > 21 && numAces > 0) {
            score -= 10;  // Convert ace from 11 to 1
            numAces--;
        }
    }

    public int getScore() {
        return score;
    }

    public void showHand(boolean hideSecondCard) {
        if (hideSecondCard && hand.size() > 1) {
            System.out.println(name + "'s hand: [" + hand.getFirst() + ", Hidden Card]");
        } else {
            System.out.println(name + "'s hand: " + hand + " (Score: " + score + ")");
        }
    }

    public double getBalance() {
        return balance;
    }

    public void adjustBalance(double amount) {
        this.balance += amount;
    }

    public void showBalance() {
        System.out.printf("%s's balance: $%.2f%n", name, balance);
    }
} 