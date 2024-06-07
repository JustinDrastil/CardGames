import java.util.*;

class Card {
    private String suit;
    private String rank;
    private int value;

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

class Deck {
    private List<Card> cards;

    public Deck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        cards = new ArrayList<>();
        
        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(suit, ranks[i], i + 2));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public List<Card> dealHalf() {
        List<Card> hand = new ArrayList<>(cards.subList(0, 26));
        cards = new ArrayList<>(cards.subList(26, cards.size()));
        return hand;
    }
}

class Player {
    private Queue<Card> hand;

    public Player(List<Card> hand) {
        this.hand = new LinkedList<>(hand);
    }

    public boolean hasCards() {
        return !hand.isEmpty();
    }

    public Card playCard() {
        return hand.poll();
    }

    public void addCards(List<Card> cards) {
        hand.addAll(cards);
    }
}

class WarGame {
    private Player player1;
    private Player player2;

    public WarGame() {
        Deck deck = new Deck();
        player1 = new Player(deck.dealHalf());
        player2 = new Player(deck.dealHalf());
    }

    public void play() {
        int round = 1;
        while (player1.hasCards() && player2.hasCards()) {
            System.out.println("Round " + round);
            playRound();
            round++;
        }

        if (player1.hasCards()) {
            System.out.println("Player 1 wins the game!");
        } else {
            System.out.println("Player 2 wins the game!");
        }
    }

    private void playRound() {
        List<Card> cardsOnTable = new ArrayList<>();
        Card card1 = player1.playCard();
        Card card2 = player2.playCard();

        cardsOnTable.add(card1);
        cardsOnTable.add(card2);

        System.out.println("Player 1 plays: " + card1);
        System.out.println("Player 2 plays: " + card2);

        if (card1.getValue() > card2.getValue()) {
            player1.addCards(cardsOnTable);
            System.out.println("Player 1 wins the round.");
        } else if (card1.getValue() < card2.getValue()) {
            player2.addCards(cardsOnTable);
            System.out.println("Player 2 wins the round.");
        } else {
            System.out.println("War!");
            resolveWar(cardsOnTable);
        }
    }

    private void resolveWar(List<Card> cardsOnTable) {
        List<Card> warPile = new ArrayList<>(cardsOnTable);

        while (true) {
            if (!player1.hasCards() || !player2.hasCards()) {
                return;
            }

            for (int i = 0; i < 3; i++) {
                if (player1.hasCards()) warPile.add(player1.playCard());
                if (player2.hasCards()) warPile.add(player2.playCard());
            }

            Card warCard1 = player1.playCard();
            Card warCard2 = player2.playCard();

            warPile.add(warCard1);
            warPile.add(warCard2);

            System.out.println("Player 1 plays: " + warCard1);
            System.out.println("Player 2 plays: " + warCard2);

            if (warCard1.getValue() > warCard2.getValue()) {
                player1.addCards(warPile);
                System.out.println("Player 1 wins the war.");
                break;
            } else if (warCard1.getValue() < warCard2.getValue()) {
                player2.addCards(warPile);
                System.out.println("Player 2 wins the war.");
                break;
            } else {
                System.out.println("War continues!");
            }
        }
    }
}

public class StartWarGame {
    public static void main(String[] args) {
        WarGame game = new WarGame();
        game.play();
    }
}
