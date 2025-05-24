package control;

import card.ListOfCards;
import player.AbstractPlayer;
import player.Bot;
import player.Player;
import rule.PokerRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class PokerControl {
    final ArrayList<AbstractPlayer> players = new ArrayList<AbstractPlayer>();
    AbstractPlayer dealer;
    ListOfCards Deck = new ListOfCards();
    PokerRule rule = new PokerRule();
    Scanner scanner = new Scanner(System.in);
    final static int BIG_BLIND = 20;
    final static int SMALL_BLIND = 10;

    public PokerControl(int numberOfPlayers, int numberOfBots) {
        Deck.initializeDeck("Poker");
        for(int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player("Poker", 500));
        }
        for(int i = 0; i < numberOfBots; i++) {
            // fix Bot
            players.add(new Bot("Poker"));
        }
        this.dealer = players.getFirst();
    }

    public void play() {
        String command;
        outer: while(true) {
            new PokerGame(players);
            System.out.println("Do you want to continue? (Yes, No)");
            command = scanner.next();
            if(command.equals("No")) break;
            for (AbstractPlayer player : players) {
                if (player.getChipStack() < BIG_BLIND) {
                    System.out.println("Player " + player.getId() + " is unable to play, game must stop");
                    break outer;
                }
            }
        }
    }

    private class Pot {
        ArrayList<AbstractPlayer> playersCanWin = new ArrayList<>();
        int totalAmount;

        Pot() {}
        Pot(ArrayList<AbstractPlayer> players) {
            playersCanWin = players;
        }

        void add(AbstractPlayer player) {
            playersCanWin.add(player);
        }
    }

    private class PokerGame {
        ArrayList<AbstractPlayer> playersInGame;
        ArrayList<AbstractPlayer> playersWinGame = new ArrayList<AbstractPlayer>();
        ArrayList<Pot> pots = new ArrayList<>();
        Pot currentPot;
        ListOfCards cardsOnTable = new ListOfCards();
        boolean isPreFlop = true;

        public PokerGame(ArrayList<AbstractPlayer> players) {
            playersInGame = new ArrayList<>(players);
            currentPot = new Pot(players);
            pots.add(currentPot);
            System.out.println(playersInGame.indexOf(dealer));
            createGame();
        }

        public void createGame() {
            startGame();
            mainGame();
            endGame();
        }

        public void startGame() {
            // player may be bot -> runtime error
            AbstractPlayer smallBlind = playersInGame.get((playersInGame.indexOf(dealer) + 1) % playersInGame.size());
            AbstractPlayer bigBlind = playersInGame.get((playersInGame.indexOf(dealer) + 2) % playersInGame.size());
            smallBlind.setCurrentBet(SMALL_BLIND);
            smallBlind.setChipStack(smallBlind.getChipStack() - SMALL_BLIND);
            bigBlind.setCurrentBet(BIG_BLIND);
            bigBlind.setChipStack(bigBlind.getChipStack() - BIG_BLIND);
            currentPot.totalAmount += SMALL_BLIND + BIG_BLIND;
        }

        public void mainGame() {
            // pre-flop
            Deck.shuffle();
            for(AbstractPlayer player: playersInGame) {
                player.setCardsOnHand(Deck.drawCard(2));
            }
            System.out.println("Cards on hands are:");
            for(AbstractPlayer player: playersInGame) {
                System.out.println("PLayer " + player.getId() + ": " + player.getCardsOnHand().toString());
            }
            bettingRound();
            if(playersInGame.size() == 1) {
                playersWinGame.addAll(playersInGame);
                return;
            }
            // flop
            cardsOnTable.addAll(Deck.drawCard(3));
            for(AbstractPlayer player: playersInGame) {
                System.out.println("PLayer " + player.getId() + ": " + player.getCardsOnHand().toString());
            }
            System.out.println(cardsOnTable.toString());
            bettingRound();
            if(playersInGame.size() == 1) {
                playersWinGame.addAll(playersInGame);
                return;
            }
            // turn
            cardsOnTable.addCard(Deck.drawCard());
            for(AbstractPlayer player: playersInGame) {
                System.out.println("PLayer " + player.getId() + ": " + player.getCardsOnHand().toString());
            }
            System.out.println(cardsOnTable.toString());
            bettingRound();
            if(playersInGame.size() == 1) {
                playersWinGame.addAll(playersInGame);
                return;
            }
            // river
            cardsOnTable.addCard(Deck.drawCard());
            for(AbstractPlayer player: playersInGame) {
                System.out.println("PLayer " + player.getId() + ": " + player.getCardsOnHand().toString());
            }
            System.out.println(cardsOnTable.toString());
            bettingRound();
            if(playersInGame.size() == 1) {
                playersWinGame.addAll(playersInGame);
                return;
            }
            // showdown
            for(AbstractPlayer player: playersInGame) {
                rule.checkHandRank(player, cardsOnTable);
            }
            for(AbstractPlayer player: playersInGame) {
                System.out.println("Player " + player.getId() + ": " + player.getHandRank() + " " + player.getHandType() + " " + player.getBestCards().toString());
            }
            // determine winner
            Collections.sort(playersInGame);
            for(int i = playersInGame.size() - 1; i > 0; i--) {
                playersWinGame.add(playersInGame.get(i));
                if(playersInGame.get(i).compareTo(playersInGame.get(i - 1)) != 0) break;
            }
        }

        public void endGame() {
            for(AbstractPlayer player: playersWinGame) {
                System.out.println("Player " + player.getId() + " wins");
            }
            for(Pot pot: pots) {
                ArrayList<AbstractPlayer> playersWinPot = new ArrayList<>();
                for(AbstractPlayer player: playersWinGame) {
                    if(pot.playersCanWin.contains(player)) playersWinPot.add(player);
                }
                for(AbstractPlayer player: playersWinPot) {
                    // winning amount maybe of float type
                    player.setChipStack(player.getChipStack() + pot.totalAmount/playersWinPot.size());
                }
            }
            dealer = players.get((players.indexOf(dealer) + 1) % players.size());
            for(AbstractPlayer player: players) {
                System.out.println("Player " + player.getId() + " has " + player.getChipStack());
                player.setAllIn(false);
            }
        }

        public void bettingRound() {
            AbstractPlayer playerLastRaise;
            AbstractPlayer currentPlayer = null;
            int currentPlayerIndex = 0;
            int currentBet = 0;
            int lastRaise = 0;
            boolean isAllIn = false;

            if(isPreFlop) {
                isPreFlop = false;
                currentPlayerIndex = (playersInGame.indexOf(dealer) + 3) % playersInGame.size();
                currentPlayer = playersInGame.get(currentPlayerIndex);
                currentBet = BIG_BLIND;
                lastRaise = BIG_BLIND;
            }
            else {
                for(int i = 1; i < players.size(); i++) {
                    currentPlayer = players.get((players.indexOf(dealer) + i) % players.size());
                    if(playersInGame.contains(currentPlayer)) {
                        currentPlayerIndex = playersInGame.indexOf(currentPlayer);
                        break;
                    }
                }
            }
            playerLastRaise = currentPlayer;
            System.out.println("Current bet is " + currentBet);
            do {
                if(!currentPlayer.isAllIn()) {
                    System.out.println("Player " + currentPlayer.getId() + "'s turn");
                    System.out.println("Choose one action (Fold, Call, Check, Bet, Raise, All-in)");
                    String decision = scanner.next();
                    switch (decision) {
                        // should these actions be implemented in AbstractPlayer class or not? Still do for UI implementation?
                        case "Fold":
                            System.out.println("Player " + currentPlayer.getId() + " fold");
                            currentPlayer.fold();
                            if (currentPlayer.equals(playerLastRaise)) {
                                playerLastRaise = playersInGame.get((currentPlayerIndex + 1) % playersInGame.size());
                                currentPlayerIndex--;
                                playersInGame.remove(currentPlayer);
                                currentPlayer = playerLastRaise;
                                currentPlayerIndex = (currentPlayerIndex + 1) % playersInGame.size();
                                continue;
                            }
                            currentPlayerIndex--;
                            playersInGame.remove(currentPlayer);
                            break;
                        case "Call":
                            if (currentPlayer.getCurrentBet() == currentBet ||
                                    currentBet - currentPlayer.getCurrentBet() > currentPlayer.getChipStack()) {
                                System.out.println("Sorry you can't call");
                                continue;
                            }
                            System.out.println("Player " + currentPlayer.getId() + " call");
                            currentPot.totalAmount += currentPlayer.call(currentBet);
                            System.out.println("Current bet is " + currentBet);
                            break;
                        case "Check":
                            if (currentPlayer.getCurrentBet() != currentBet) {
                                System.out.println("Sorry you can't check");
                                continue;
                            }
                            System.out.println("Player " + currentPlayer.getId() + " check");
                            currentPlayer.check();
                            break;
                        case "Bet":
                            if (currentBet != 0 || currentPlayer.getChipStack() < BIG_BLIND) {
                                System.out.println("Sorry you can't bet");
                                continue;
                            }
                            System.out.println("Player " + currentPlayer.getId() + " bet");
                            currentPot.totalAmount += currentPlayer.bet(BIG_BLIND);
                            currentBet = currentPlayer.getCurrentBet();
                            lastRaise = BIG_BLIND;
                            System.out.println("Current bet is " + currentBet);
                            break;
                        case "Raise":
                            System.out.println("Please enter your raise amount");
                            int amount = scanner.nextInt();
                            if (amount < BIG_BLIND || amount < lastRaise ||
                                    currentBet + amount - currentPlayer.getCurrentBet() > currentPlayer.getChipStack()) {
                                System.out.println("Sorry you can't raise by this amount");
                                continue;
                            }
                            System.out.println("Player " + currentPlayer.getId() + " raises by " + amount);
                            currentPot.totalAmount += currentPlayer.raise(amount, currentBet);
                            currentBet = currentPlayer.getCurrentBet();
                            playerLastRaise = currentPlayer;
                            lastRaise = amount;
                            System.out.println("Current bet is " + currentBet);
                            break;
                        case "All-in":
                            if (currentPlayer.getChipStack() == 0) {
                                System.out.println("Sorry you can't all in");
                                continue;
                            }
                            System.out.println("Player " + currentPlayer.getId() + " all in");
                            currentPot.totalAmount += currentPlayer.allIn();
                            if (currentBet < currentPlayer.getCurrentBet()) {
                                currentBet = currentPlayer.getCurrentBet();
                                playerLastRaise = currentPlayer;
                            }
                            currentPlayer.setAllIn(true);
                            isAllIn = true;
                            System.out.println("Current bet is " + currentBet);
                            break;
                    }
                }
                currentPlayerIndex = (currentPlayerIndex + 1) % playersInGame.size();
                currentPlayer = playersInGame.get(currentPlayerIndex);
                if(currentPlayer.equals(playerLastRaise)) break;
            } while(playersInGame.size() > 1);

            if(isAllIn) {
                ArrayList<AbstractPlayer> playersAllIn = new ArrayList<>();
                for(AbstractPlayer player: playersInGame) {
                    if(player.isAllIn()) playersAllIn.add(player);
                }
                playersAllIn.sort(Comparator.comparing(AbstractPlayer::getCurrentBet));
                for(AbstractPlayer playerAllIn : playersAllIn) {
                    int amountCurrentPot = 0;
                    Pot newPot = new Pot();
                    pots.add(newPot);
                    for(AbstractPlayer other: playersInGame) {
                        if(playerAllIn.getCurrentBet() <= other.getCurrentBet()) {
                            if(!other.equals(playerAllIn)) newPot.add(other);
                            amountCurrentPot += playerAllIn.getCurrentBet();
                            other.setCurrentBet(other.getCurrentBet() - playerAllIn.getCurrentBet());
                        }
                    }
                    newPot.totalAmount = currentPot.totalAmount - amountCurrentPot;
                    currentPot.totalAmount = amountCurrentPot;
                    currentPot = newPot;
                }
            }
            for(AbstractPlayer player: playersInGame) player.setCurrentBet(0);
        }
    }
}
