package control;

import card.ListOfCards;
import org.w3c.dom.views.AbstractView;
import player.AbstractPlayer;
import player.Player;
import player.Bot;
import java.util.ArrayList;

public class ThirteenSControl {
    ArrayList<AbstractPlayer> playersInGame = new ArrayList<AbstractPlayer>();
    ArrayList<AbstractPlayer> playersWinGame= new ArrayList<AbstractPlayer>();
    private String gameType="ThirteenS";
    ListOfCards Deck = new ListOfCards();
    public void startGame()
    {
        AbstractPlayer recentPlayerWinningRound=playersInGame.get(0);
        while (playersInGame.size()>0)
        {

            ThirteenSRound currentRound= new ThirteenSRound(playersInGame,recentPlayerWinningRound,Deck);
            recentPlayerWinningRound=currentRound.getPlayerWinRound();
        }


    }
    public ThirteenSControl(int numberOfPlayers, int numberOfBots) {
        Deck.initializeDeck();
        for (int i = 0; i < numberOfPlayers; i++) {
            playersInGame.add(new Player(gameType));
        }
        for (int i = 0; i < numberOfBots; i++) {
            playersWinGame.add(new Bot(gameType));
        }
        for (AbstractPlayer player : playersInGame) {
            player.setCardsOnHand(Deck.drawCard(13));
        }
        startGame();
    }

    public class ThirteenSRound {
        ArrayList<AbstractPlayer> playersInRound;
        AbstractPlayer playerWinRound;

        public void initializePlayersInRound(ArrayList<AbstractPlayer> playersInGame) {
            for (AbstractPlayer player : playersInGame) {
                playersInRound.add(player);
            }
        }
        public AbstractPlayer getPlayerWinRound() {
            return playerWinRound;
        }
        public ThirteenSRound(ArrayList<AbstractPlayer> playersInGame, AbstractPlayer playerStartRound, ListOfCards Deck) {
            initializePlayersInRound(playersInGame);

        }
        public void MenuOfChoice(AbstractPlayer player)
        {
            if (player instanceof Player)
            {
                
            }
            if (player instanceof Bot)
            {
                System.out.println("Bot Is Playing..");
            }
        }


    }


}
