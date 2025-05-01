package control;

import card.ListOfCards;
import player.AbstractPlayer;
import player.Player;
import player.Bot;
import rule.ThirteenSRule;

import java.util.ArrayList;

public class ThirteenSGame {
    final ArrayList<AbstractPlayer> playersInGame = new ArrayList<AbstractPlayer>();
    ArrayList<AbstractPlayer> playersWinGame = new ArrayList<AbstractPlayer>();
    private String gameType = "ThirteenS";
    ListOfCards Deck = new ListOfCards();
    ThirteenSRule rule = new ThirteenSRule();

    public ThirteenSGame(int numberOfPlayers, int numberOfBots) {
        Deck.initializeDeck("ThirteenS");
        for (int i = 0; i < numberOfPlayers; i++) {
            playersInGame.add(new Player(gameType));
        }
        for (int i = 0; i < numberOfBots; i++) {
            playersInGame.add(new Bot(gameType));
        }
        for (AbstractPlayer player : playersInGame) {
            player.setCardsOnHand(Deck.drawCard(13));
        }
        for(AbstractPlayer player: playersInGame) {
            if(rule.checkWinCondition(player.getCardsOnHand())) {
                playersWinGame.add(player);
                playersInGame.remove(player);
            }
        }
        mainGame();
    }

    public void mainGame() {
        AbstractPlayer playerWinLastRound = playersInGame.getFirst();
        while (playersInGame.size() > 1) {
            ThirteenSRound currentRound = new ThirteenSRound(playersInGame, playerWinLastRound);
            playerWinLastRound = currentRound.getPlayerWinRound();
        }
        playersWinGame.add(playersInGame.getFirst());
        System.out.println();

    }

    private class ThirteenSRound {
        ArrayList<AbstractPlayer> playersInRound;
        AbstractPlayer playerWinRound;
        ListOfCards cardsOnTable = new ListOfCards();

        public ThirteenSRound(ArrayList<AbstractPlayer> playersInGame, AbstractPlayer playerStartRound) {
            initializePlayersInRound(playersInGame);
            int playerIndex = playersInRound.indexOf(playerStartRound);
            AbstractPlayer currentPlayer = playerStartRound;
            while(playersInRound.size() > 1) {
                while(true) {
                    // player selects cards
                    boolean decision;
                    // player clicks button play
                    decision = currentPlayer.playCards(cardsOnTable);
                    if(decision) {
                        if (rule.checkWinCondition(currentPlayer.getCardsOnHand())) {
                            playersWinGame.add(currentPlayer);
                            playersInRound.remove(currentPlayer);
                            playersInGame.remove(currentPlayer);
                        }
                    }
                    // player clicks button pass
//                    playersInRound.remove(currentPlayer);
//                    playerIndex--;
//                    decision = true;
                            if(decision) break;
                }
                currentPlayer = playersInRound.get((playerIndex + 1) % playersInRound.size());
            }
            playerWinRound = playersInRound.getFirst();
        }

        public void initializePlayersInRound(ArrayList<AbstractPlayer> playersInGame) {
            playersInRound.addAll(playersInGame);
        }

        public AbstractPlayer getPlayerWinRound() {
            return playerWinRound;
        }

        public void MenuOfPlayer(AbstractPlayer player) {
            if (player instanceof Player) {

                System.out.println("The cards on hand are: " + player.toStringCardsOnHand());
                System.out.println("Your selected cards are: " + player.toStringCardsSelected());
                System.out.println("To Select A Card On Your Hand, Use Instruction: Select [order of card] ");
                System.out.println("To Stop Selecting A Card On Your Hand, Use Instruction: Unselect [order of card] ");
                System.out.println("To Play Your Selected Cards, Use Instruction: Play");
                System.out.println("To pass the round, use Instruction: Pass");

            }
            if (player instanceof Bot)
            {
                System.out.println("Bot Is Playing..");
                //remaining code
            }
        }


    }


}
