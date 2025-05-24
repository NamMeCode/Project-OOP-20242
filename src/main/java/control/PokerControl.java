package control;

import card.ListOfCards;
import player.*;
import rule.PokerRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class PokerControl {
    final ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();
    PokerPlayer dealer;
    ListOfCards Deck = new ListOfCards();
    PokerRule rule = new PokerRule();
    Scanner scanner = new Scanner(System.in);
    final static int BIG_BLIND = 20;
    final static int SMALL_BLIND = 10;

    public PokerControl(int numberOfPlayers, int numberOfBots) {
        Deck.initializeDeck("Poker");
        for(int i = 0; i < numberOfPlayers; i++) {
            players.add(new PokerPlayer("Poker", 500));
        }
        for(int i = 0; i < numberOfBots; i++) {
            players.add(new PokerBot("Poker", 500));
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
            for (PokerPlayer player : players) {
                if (player.getChipStack() < BIG_BLIND) {
                    System.out.println("Player " + player.getId() + " is unable to play, game must stop");
                    break outer;
                }
            }
        }
    }

    private class Pot {
        ArrayList<PokerPlayer> playersCanWin = new ArrayList<>();
        int totalAmount;

        Pot() {}
        Pot(ArrayList<PokerPlayer> players) {
            playersCanWin = players;
        }

        void add(PokerPlayer player) {
            playersCanWin.add(player);
        }
    }

    private class PokerGame {
        ArrayList<PokerPlayer> playersInGame;
        ArrayList<PokerPlayer> playersWinGame = new ArrayList<PokerPlayer>();
        ArrayList<Pot> pots = new ArrayList<>();
        Pot currentPot;
        ListOfCards cardsOnTable = new ListOfCards();
        boolean isPreFlop = true;

        public PokerGame(ArrayList<PokerPlayer> players) {
            playersInGame = new ArrayList<>(players);
            currentPot = new Pot(players);
            pots.add(currentPot);
            createGame();
        }

        public void createGame() {
            startGame();
            mainGame();
            endGame();
        }

        public void startGame() {
            PokerPlayer smallBlind = playersInGame.get((playersInGame.indexOf(dealer) + 1) % playersInGame.size());
            PokerPlayer bigBlind = playersInGame.get((playersInGame.indexOf(dealer) + 2) % playersInGame.size());
            smallBlind.setCurrentBet(SMALL_BLIND);
            smallBlind.decreaseChipStack(SMALL_BLIND);
            bigBlind.setCurrentBet(BIG_BLIND);
            bigBlind.decreaseChipStack(BIG_BLIND);
            currentPot.totalAmount += SMALL_BLIND + BIG_BLIND;
        }

        public void mainGame() {
            // pre-flop
            Deck.shuffle();
            for(Actor player: playersInGame) {
                player.setCardsOnHand(Deck.drawCard(2));
            }
            System.out.println("Cards on hands are:");
            for(Actor player: playersInGame) {
                System.out.println("PLayer " + player.getId() + ": " + player.getCardsOnHand().toString());
            }
            bettingRound();
            if(playersInGame.size() == 1) {
                playersWinGame.addAll(playersInGame);
                return;
            }
            // flop
            cardsOnTable.addAll(Deck.drawCard(3));
            if (bettingProcedure()) return;
            // turn
            cardsOnTable.addCard(Deck.drawCard());
            if (bettingProcedure()) return;
            // river
            cardsOnTable.addCard(Deck.drawCard());
            if (bettingProcedure()) return;
            // showdown
            for(PokerPlayer player: playersInGame) {
                rule.checkHandRank(player, cardsOnTable);
            }
            for(PokerPlayer player: playersInGame) {
                System.out.println("Player " + player.getId() + ": " + player.getHandRank() + " " + player.getHandType() + " " + player.getBestCards().toString());
            }
        }

        private boolean bettingProcedure() {
            for(Actor player: playersInGame) {
                System.out.println("PLayer " + player.getId() + ": " + player.getCardsOnHand().toString());
            }
            System.out.println(cardsOnTable.toString());
            bettingRound();
            if(playersInGame.size() == 1) {
                playersWinGame.addAll(playersInGame);
                return true;
            }
            return false;
        }

        public void endGame() {
            // determine winner
            Collections.sort(playersInGame);
            for(int i = playersInGame.size() - 1; i > 0; i--) {
                playersWinGame.add(playersInGame.get(i));
                if(playersInGame.get(i).compareTo(playersInGame.get(i - 1)) != 0) break;
            }
            for(Actor player: playersWinGame) {
                System.out.println("Player " + player.getId() + " wins");
            }
            for(Pot pot: pots) {
                ArrayList<PokerPlayer> playersWinPot = new ArrayList<>();
                for(PokerPlayer player: playersWinGame) {
                    if(pot.playersCanWin.contains(player)) playersWinPot.add(player);
                }
                for(PokerPlayer player: playersWinPot) {
                    // winning amount maybe of float type
                    player.increaseChipStack(pot.totalAmount/playersWinPot.size());
                }
            }
            dealer = players.get((players.indexOf(dealer) + 1) % players.size());
            for(PokerPlayer player: players) {
                System.out.println("Player " + player.getId() + " has " + player.getChipStack());
                player.setAllIn(false);
            }
        }

        public void bettingRound() {
            PokerPlayer playerLastRaise;
            PokerPlayer currentPlayer = null;
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
                    if(currentPlayer instanceof PokerBot) {
                        // check -> call -> fold
                        ((PokerBot) currentPlayer).autoBet();
                    }
                    else if(currentPlayer instanceof PokerPlayer){
                        System.out.println("Player " + currentPlayer.getId() + "'s turn");
                        System.out.println("Choose one action (Fold, Call, Check, Bet, Raise, All-in)");
                        String decision = scanner.next();
                        switch (decision) {
                            // should these actions be implemented in AbstractPlayer class or not? Still do for UI implementation?
                            case "Fold":
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
                                int call = currentPlayer.call(currentBet);
                                if (call == 0) continue;
                                currentPot.totalAmount += call;
                                System.out.println("Current bet is " + currentBet);
                                break;
                            case "Check":
                                if (!currentPlayer.check(currentBet)) continue;
                                break;
                            case "Bet":
                                int bet = currentPlayer.bet(BIG_BLIND, currentBet);
                                if (bet == 0) continue;
                                currentPot.totalAmount += bet;
                                currentBet = currentPlayer.getCurrentBet();
                                lastRaise = BIG_BLIND;
                                System.out.println("Current bet is " + currentBet);
                                break;
                            case "Raise":
                                System.out.println("Please enter your raise amount");
                                int amount = scanner.nextInt();
                                int raise = currentPlayer.raise(amount, currentBet, BIG_BLIND, lastRaise);
                                if (raise == 0) continue;
                                currentPot.totalAmount += raise;
                                currentBet = currentPlayer.getCurrentBet();
                                playerLastRaise = currentPlayer;
                                lastRaise = raise;
                                System.out.println("Current bet is " + currentBet);
                                break;
                            case "All-in":
                                int allIn = currentPlayer.allIn();
                                if (allIn == 0) continue;
                                currentPot.totalAmount += allIn;
                                if (currentBet < currentPlayer.getCurrentBet()) {
                                    currentBet = currentPlayer.getCurrentBet();
                                    playerLastRaise = currentPlayer;
                                }
                                isAllIn = true;
                                System.out.println("Current bet is " + currentBet);
                                break;
                        }
                    }
                }
                currentPlayerIndex = (currentPlayerIndex + 1) % playersInGame.size();
                currentPlayer = playersInGame.get(currentPlayerIndex);
                if(currentPlayer.equals(playerLastRaise)) break;
            } while(playersInGame.size() > 1);

            if(isAllIn) {
                ArrayList<PokerPlayer> playersAllIn = new ArrayList<>();
                for(PokerPlayer player: playersInGame) {
                    if(player.isAllIn()) playersAllIn.add(player);
                }
                playersAllIn.sort(Comparator.comparing(PokerPlayer::getCurrentBet));
                for(PokerPlayer playerAllIn : playersAllIn) {
                    int amountCurrentPot = 0;
                    Pot newPot = new Pot();
                    pots.add(newPot);
                    for(PokerPlayer other: playersInGame) {
                        if(playerAllIn.getCurrentBet() <= other.getCurrentBet()) {
                            if(!other.equals(playerAllIn)) {
                                newPot.add(other);
                                amountCurrentPot += playerAllIn.getCurrentBet();
                                other.setCurrentBet(other.getCurrentBet() - playerAllIn.getCurrentBet());
                            }
                            amountCurrentPot += playerAllIn.getCurrentBet();
                            playerAllIn.setCurrentBet(0);
                        }
                    }
                    newPot.totalAmount = currentPot.totalAmount - amountCurrentPot;
                    currentPot.totalAmount = amountCurrentPot;
                    currentPot = newPot;
                }
            }
            for(PokerPlayer player: playersInGame) player.setCurrentBet(0);
        }
    }
}