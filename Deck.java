import java.util.LinkedList;
import java.util.Collections;

public class Deck {
    private LinkedList<Card> cards;

    public Deck() {
        cards = new LinkedList<>();
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        
        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                int value = i + 1;
                if (value > 10) value = 10;  // Face cards are worth 10
                if (value == 1) value = 11;  // Aces are worth 11 (can be changed to 1 later)
                cards.add(new Card(suit, ranks[i], value));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.poll();  // Removes and returns the first card
    }
} 