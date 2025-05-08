import card.Card;
import card.ListOfCards;
import control.PokerControl;
import player.AbstractPlayer;
import rule.PokerRule;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        PokerControl game = new PokerControl(4,0);
        game.play();
//        ArrayList<Card> cards = new ArrayList<>();
//        // Community cards (drawn last)
//        cards.add(new Card("Q", "S", "Poker")); // Q - S
//        cards.add(new Card("2", "S", "Poker")); // 2 - S
//        cards.add(new Card("3", "D", "Poker")); // 3 - D
//        cards.add(new Card("9", "H", "Poker")); // 9 - H
//        cards.add(new Card("9", "D", "Poker")); // 9 - D
//
//        // Player 3
//        cards.add(new Card("Q", "D", "Poker")); // Q - D
//        cards.add(new Card("A", "D", "Poker")); // A - D
//
//        // Player 2
//        cards.add(new Card("6", "D", "Poker")); // 6 - D
//        cards.add(new Card("8", "S", "Poker")); // 8 - S
//
//        // Player 1
//        cards.add(new Card("5", "D", "Poker")); // 5 - D
//        cards.add(new Card("7", "C", "Poker")); // 7 - C
//
//        // Player 0 (dealt first)
//        cards.add(new Card("4", "C", "Poker")); // 4 - C
//        cards.add(new Card("9", "C", "Poker")); // 9 - C

//        ListOfCards Deck = new ListOfCards();
//        Deck.initializeDeck("Poker");
//        ArrayList<ListOfCards> cardsArrayList = new ArrayList<>();
//        for(int i=0; i<4; i++) {
//            ListOfCards tmp = new ListOfCards();
//            tmp.addAll(Deck.drawCard(2));
//            cardsArrayList.add(tmp);
//        }
//        ListOfCards cardsOnTable = new ListOfCards();
//        cardsOnTable.addAll(Deck.drawCard(5));
//        for(int i=0; i<4; i++) {
//            System.out.println("Player " + i + ": " + cardsArrayList.get(i));
//        }
//        System.out.println(cardsOnTable);
//        AbstractPlayer player = new AbstractPlayer();
//        player.setCardsOnHand(cardsArrayList.getFirst());
//        ListOfCards temp = new ListOfCards();
//        temp.addAll(cardsArrayList.getFirst());
//        temp.addAll(cardsOnTable);
//        ArrayList<AbstractPlayer> players = new ArrayList<AbstractPlayer>();
//        for(int i=0; i<4; i++) {
//            AbstractPlayer player = new AbstractPlayer();
//            player.setCardsOnHand(cardsArrayList.get(i));
//            players.add(player);
//        }
//        PokerRule rule = new PokerRule();
//        for(AbstractPlayer player: players) {
//            rule.checkHandRank(player, cardsOnTable);
//            System.out.println(player.getHandRank() + " " + player.getHandType() + " " + player.getBestCards().toString());
//        }
//        rule.checkHandRank(player, cardsOnTable);
//        ListOfCards bestCards = rule.checkStraightFlush(temp);
//        System.out.println(bestCards);
//        System.out.println(player.getHandRank() + " " + player.getHandType() + " " + player.getBestCards().toString());
    }
}



