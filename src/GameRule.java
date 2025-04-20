import java.util.ArrayList;
import java.util.Comparator;

public class GameRule {
    public void sort(ArrayList<Card> cards) {
        cards.sort(Comparator.comparing(Card::getRank).thenComparing(Card::getSuit));
    }

    public boolean checkPair(ArrayList<Card> cards) {
        return cards.getFirst().equals(cards.getLast());
    }

    public boolean checkThreeOfAKind(ArrayList<Card> cards) {
        return cards.get(0).equals(cards.get(1)) && cards.get(1).equals(cards.get(2));
    }

    public boolean checkFourOfAKind(ArrayList<Card> cards) {
        return cards.get(0).equals(cards.get(1)) && cards.get(1).equals(cards.get(2)) &&
                cards.get(2).equals(cards.get(3));
    }

    public boolean checkSequence(ArrayList<Card> cards) {
        for(int i=0; i<cards.size()-1; i++) {
            if(cards.get(i).getRank() != cards.get(i+1).getRank()) return false;
        }
        return true;
    }

    public boolean checkDoubleSequence(ArrayList<Card> cards) {
        if(cards.size() % 2 == 1) return false;
        for(int i=0; i<cards.size()-1; i++) {
            if(i % 2 == 0) if(cards.get(i).getRank() != cards.get(i+1).getRank()) return false;
            else if(cards.get(i).getRank() != (cards.get(i+1).getRank() + 1)) return false;
        }
        return true;
    }

    public String handType(ArrayList<Card> cards) {
        switch (cards.size()) {
            case 1: return "Single";
            case 2: if(checkPair(cards)) return "Pair";
            case 3:
                if(checkThreeOfAKind(cards)) return "ThreeOfAKind";
                else if(checkSequence(cards)) return "Sequence";
                else return "Invalid";
            case 4:
                if(checkFourOfAKind(cards)) return "FourOfAKind";
                else if(checkSequence(cards)) return "Sequence";
                else return "Invalid";
            case 5:
                if(checkSequence(cards)) return "Sequence";
                else return "Invalid";
            default:
                if(checkSequence(cards)) return "Sequence";
                else if(checkDoubleSequence(cards)) return "DoubleSequence";
                else return "Invalid";
        }
    }

    public boolean checkValidPlay(ArrayList<Card> playCards, ArrayList<Card> tableCards) {
        if(tableCards.isEmpty()) {
            sort(playCards);
            return !handType(playCards).equals("Invalid");
        }
        if(playCards.size() != tableCards.size()) return false;

        sort(playCards); sort(tableCards);
        String typePlayCards = handType(playCards);
        String typeTableCards = handType(tableCards);
        if(!typePlayCards.equals(typeTableCards)) return false;

        return playCards.getLast().compareCard(tableCards.getLast()) > 0;
    }
}
