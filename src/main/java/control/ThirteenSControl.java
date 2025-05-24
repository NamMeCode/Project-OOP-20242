package control;

import card.ListOfCards;
import player.AbstractPlayer;
import player.Bot;
import player.Player;

import java.util.ArrayList;

public class ThirteenSControl {
    ArrayList<AbstractPlayer> playersInGame = new ArrayList<AbstractPlayer>();
    ArrayList<AbstractPlayer> playersWinGame = new ArrayList<AbstractPlayer>();
    ListOfCards Deck = new ListOfCards();
    private String gameType = "ThirteenS";

    public ThirteenSControl(int numberOfPlayers, int numberOfBots) {
        Deck.initializeDeck();
        for (int i = 0; i < numberOfPlayers; i++) {
            playersInGame.add(new Player(gameType));
        }
        for (int i = 0; i < numberOfBots; i++) {
            playersInGame.add(new Bot(gameType));
        }
        for (AbstractPlayer player : playersInGame) {
            player.setCardsOnHand(Deck.drawCard(13));
        }
        mainGame();
    }

    public void mainGame() {
        AbstractPlayer recentPlayerWinningRound = playersInGame.get(0);
        while (playersInGame.size() > 0) {

            ThirteenSRound currentRound = new ThirteenSRound(playersInGame, recentPlayerWinningRound, Deck);
            recentPlayerWinningRound = currentRound.getPlayerWinRound();
        }


    }

    public class ThirteenSRound {
        ArrayList<AbstractPlayer> playersInRound;
        AbstractPlayer playerWinRound;

        public ThirteenSRound(ArrayList<AbstractPlayer> playersInGame, AbstractPlayer playerStartRound, ListOfCards Deck) {
            initializePlayersInRound(playersInGame);

        }

        public void initializePlayersInRound(ArrayList<AbstractPlayer> playersInGame) {
            for (AbstractPlayer player : playersInGame) {
                playersInRound.add(player);
            }
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
                System.out.println("To Sort Cards On Your Hand, Use Instruction: Sort");

            }
            if (player instanceof Bot) {
                System.out.println("Bot Is Playing..");
                //remaining code
            }
        }


    }


}
