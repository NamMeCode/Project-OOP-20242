package rule;

import card.ListOfCards;

public abstract class GameRule {
    public abstract boolean checkValidPlay(ListOfCards playCards, ListOfCards tableCards);
}
