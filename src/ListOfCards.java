import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ListOfCards {
    private ArrayList<Card> cardList=new ArrayList<>();

    public void shuffle() {
        Collections.shuffle(cardList);
    }

    public void sort() {
        cardList.sort(Comparator.comparing(Card::getRank).thenComparing(Card::getSuit));
    }

    public void addCard(Card card) {
        cardList.add(card);
    }

    public boolean removeCard(Card card) {
        return cardList.remove(card);
    }
}
