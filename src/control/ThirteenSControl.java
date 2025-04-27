package control;

import card.ListOfCards;
import org.w3c.dom.views.AbstractView;
import player.AbstractPlayer;

import java.util.ArrayList;

public class ThirteenSControl {
    ArrayList<AbstractPlayer> playersInGame = new ArrayList<AbstractPlayer>();
    ArrayList<AbstractPlayer> playersWinGame= new ArrayList<AbstractPlayer>();
    ListOfCards Deck = new ListOfCards();
    public ThirteenSControl() {
        Deck.initializeDeck();

    }
    public class ThirteenSRound {
        ArrayList<AbstractPlayer> playersInRound;
        AbstractPlayer playerWinRound;
        public void initializePlayersInRound(ArrayList<AbstractPlayer> playersInGame) {
            for (AbstractPlayer player : playersInGame) {
                playersInRound.add(player);
            }
        }
        public ThirteenSRound(ArrayList<AbstractPlayer> playersInGame, AbstractPlayer playerStartRound, ListOfCards Deck) {
            initializePlayersInRound(playersInGame);

        }


    }


}
