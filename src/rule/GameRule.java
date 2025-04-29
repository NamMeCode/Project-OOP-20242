package rule;

import card.Card;
import card.ListOfCards;

import java.util.ArrayList;

public abstract class GameRule {
    public abstract boolean checkValidPlay(ListOfCards playCards, ListOfCards tableCards);
    public abstract boolean checkWinCondition(ListOfCards handCards);
}
