package player;

public abstract class ChipActor extends Actor{
    int chipStack;

    public ChipActor(String gameType, int initialStack) {
        super(gameType);
        this.chipStack = initialStack;
    }

    public void decreaseChipStack(int amount) {
        chipStack -= amount;

    }
    public void increaseChipStack(int amount) {
        chipStack += amount;
    }

    public void setChipStack(int amount) {
        this.chipStack = amount;
    }

    public int getChipStack() {
        return chipStack;
    }
}
