import java.util.Scanner;

public class BlackjackGame {
    private Deck deck;
    private Player player;
    private Player dealer;
    private Scanner scanner;
    private double currentBet;

    public BlackjackGame() {
        deck = new Deck();
        player = new Player("Player", 125.00);
        dealer = new Player("Dealer", 0);
        scanner = new Scanner(System.in);
        currentBet = 0;
    }

    private boolean placeBet() {
        player.showBalance();
        
        while (true) {
            System.out.println("\nHow much would you like to bet?");
            System.out.println("(Enter 'all' to bet everything, or '0' to quit)");
            
            String input = scanner.nextLine().toLowerCase();
            
            try {
                if (input.equals("all")) {
                    currentBet = player.getBalance();
                } else {
                    currentBet = Double.parseDouble(input);
                }
                
                if (currentBet == 0) return false;
                
                if (currentBet > player.getBalance()) {
                    System.out.println("Insufficient funds! Your balance is $" + player.getBalance());
                    continue;
                }
                
                if (currentBet < 1) {
                    System.out.println("Minimum bet is $1.");
                    continue;
                }
                
                player.adjustBalance(-currentBet);
                System.out.printf("Betting: $%.2f%n", currentBet);
                return true;
                
            } catch (NumberFormatException e) {
                if (!input.equals("all")) {
                    System.out.println("Please enter a valid number or 'all'.");
                }
            }
        }
    }

    public void startGame() {
        while (player.getBalance() > 0) {
            resetGame();
            
            if (!placeBet()) {
                System.out.println("Thanks for playing!");
                break;
            }
            
            play();
            
            if (player.getBalance() <= 0) {
                System.out.println("\n💸 You're broke! Game over! 💸");
                break;
            }
            
            System.out.println("\nWould you like to play again? (y/n)");
            String choice = scanner.nextLine().toLowerCase();
            if (!choice.equals("y")) break;
        }
        
        System.out.printf("\nFinal balance: $%.2f%n", player.getBalance());
        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private void resetGame() {
        // Create new deck and shuffle
        deck = new Deck();
        
        // Clear hands
        player = new Player("Player", 125.00);
        dealer = new Player("Dealer", 0);
        
        System.out.println("\n=== New Game ===");
    }

    public void play() {
        // Initial deal
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());

        // Show hands (hide dealer's second card)
        System.out.println("\nInitial deal:");
        player.showHand(false);
        dealer.showHand(true);

        // Player's turn
        playerTurn();

        // Dealer's turn
        if (player.getScore() <= 21) {
            System.out.println("\nDealer reveals second card:");
            dealer.showHand(false);  // Show full hand
            dealerTurn();
        }

        determineWinner();
    }

    private void playerTurn() {
        String choice;
        do {
            if (player.getScore() >= 21) break;  // Exit if bust or blackjack
            
            System.out.println("\nHit or Stand? (h/s)");
            choice = scanner.nextLine().toLowerCase();
            
            if (choice.equals("h")) {
                player.addCard(deck.drawCard());
                System.out.println("\nCurrent hands:");
                player.showHand(false);
                dealer.showHand(true);
            }
        } while (!choice.equals("s"));
    }

    private void dealerTurn() {
        System.out.println("\nDealer's turn:");
        while (dealer.getScore() < 17) {  // Dealer must hit on 16 and below
            dealer.addCard(deck.drawCard());
            System.out.println("\nDealer hits:");
            dealer.showHand(false);
        }
        if (dealer.getScore() <= 21) {
            System.out.println("\nDealer stands.");
        }
    }

    private void determineWinner() {
        System.out.println("\nFinal hands:");
        player.showHand(false);
        dealer.showHand(false);
        
        int playerScore = player.getScore();
        int dealerScore = dealer.getScore();

        System.out.println("\nResults:");
        if (playerScore > 21) {
            System.out.println("Player busts! Dealer wins!");
        } else if (dealerScore > 21) {
            System.out.println("Dealer busts! Player wins!");
        } else if (playerScore > dealerScore) {
            System.out.println("Player wins!");
        } else if (dealerScore > playerScore) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame();
        game.startGame();
    }
} 