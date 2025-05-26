package player;

import card.ListOfCards;
import card.Card;
import rule.ThirteenSRule;
import rule.ThirteenNRule;

import java.util.ArrayList;
import java.util.List;

public class ThirteenBot extends Actor {
    public ThirteenBot(String gameType) {
        super(gameType);
        setRule(gameType);
    }

    public boolean autoPlayCards(ListOfCards cardsOnTable) {
        clearSelected();
        cardsOnHand.sortRankSuit();

        int targetSize = cardsOnTable.getSize();

        // Nếu bàn trống thì đánh 1 lá đầu tiên
        if (targetSize == 0) {
            if (cardsOnHand.getSize() > 0) {
                cardsOnHand.getCardAt(0).setSelected(true);
                cardsOnTable.replacedBy(getCardsSelected());
                cardsOnHand.removeCardList(getCardsSelected());
                return true;
            }
            return false;
        }

        // Tìm các tổ hợp cùng kích thước
        List<ListOfCards> combinations = generateCombinationsOfSize(targetSize);

        for (ListOfCards combo : combinations) {
            if (canBeat(combo, cardsOnTable)) {
                selectCards(combo);
                cardsOnTable.replacedBy(combo);
                cardsOnHand.removeCardList(getCardsSelected());
                return true;
            }
        }

        return false;
    }

    private List<ListOfCards> generateCombinationsOfSize(int size) {
        List<ListOfCards> result = new ArrayList<>();
        if (cardsOnHand.getSize() < size) return result;
        backtrack(result, new ArrayList<>(), 0, size);
        return result;
    }

    private void backtrack(List<ListOfCards> result, List<Card> temp, int start, int size) {
        if (temp.size() == size) {
            ListOfCards list = new ListOfCards();
            for (Card c : temp) list.addCard(c);
            result.add(list);
            return;
        }

        for (int i = start; i < cardsOnHand.getSize(); i++) {
            temp.add(cardsOnHand.getCardAt(i));
            backtrack(result, temp, i + 1, size);
            temp.remove(temp.size() - 1);
        }
    }

    private boolean canBeat(ListOfCards candidate, ListOfCards table) {
        if (candidate.getSize() != table.getSize()) return false;
        if (rule instanceof ThirteenSRule) {
            return ((ThirteenSRule) rule).checkValidPlay(candidate, table);
        }
        if (rule instanceof ThirteenNRule) {
            return ((ThirteenNRule) rule).checkValidPlay(candidate, table);
        }
        return false;
    }

    private void selectCards(ListOfCards selected) {
        for (int i = 0; i < selected.getSize(); i++) {
            Card target = selected.getCardAt(i);
            for (int j = 0; j < cardsOnHand.getSize(); j++) {
                Card hand = cardsOnHand.getCardAt(j);
                if (hand.getRank() == target.getRank() && hand.getSuit() == target.getSuit()) {
                    hand.setSelected(true);
                    break;
                }
            }
        }
    }

    @Override
    public void clearSelected() {
        super.clearSelected();
    }
}
