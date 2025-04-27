package card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListOfCards {
    private ArrayList<Card> cardList=new ArrayList<>();
    private int size=0;
    public int getSize()
    {
        return size;
    }
    public ArrayList<Card> getCardList()
    {
        return cardList;
    }
    public void shuffle() {
        Collections.shuffle(cardList);
    }

    public void sort() {
        cardList.sort(Comparator.comparing(Card::getRank).thenComparing(Card::getSuit));
    }
    public ListOfCards()
    {
        ;
    }
    public ListOfCards(ArrayList<Card> cardList)
    {
        this.cardList=cardList;
        this.size=cardList.size();
    }
    public void addCard(Card card) {
        cardList.add(card);
        size++;
    }

    public boolean removeCard(Card card) {
        size--;
        return cardList.remove(card);

    }
    public Card getCardAt(int index) {
        if (index >= 0 && index < size) {
            return cardList.get(index);
        }
        return null;
    }
    public ListOfCards cardsSelected()
    {
        ArrayList <Card> cardsSelected=new ArrayList<>();
        for (Card card : cardList) {
            if (card.isSelected()) {
                cardsSelected.add(card);

            }
        }
        return new ListOfCards(cardsSelected);
    }
    public ListOfCards cardsNotSelected()
    {
        ArrayList <Card> cardsNotSelected=new ArrayList<>();
        for (Card card : cardList) {
            if (!card.isSelected()) {
                cardsNotSelected.add(card);

            }
        }
        return new ListOfCards(cardsNotSelected);
    }
    public void replacedBy(ListOfCards newList)
    {
        cardList=newList.getCardList();
        size=newList.size;
    }
    public void initializeDeck()
    {
        String[] rank= {"1","2","3","4","5","6","7","8","9","10","J","Q","K","A"};
        String[] suit ={"C","D","H","S"};
        for (int i=0;i<4;i++)
            for (int j=0;j<12;j++)
            {
                cardList.add(new Card(rank[j],suit[i]));

            }
        shuffle();

    }

}
