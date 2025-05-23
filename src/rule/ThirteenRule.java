package rule;

import card.ListOfCards;

abstract public class ThirteenRule extends GameRule {

    abstract public boolean checkDoubleSequence(ListOfCards playCards) ;
    abstract String handType(ListOfCards playCards) ;

}
