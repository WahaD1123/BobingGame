import java.util.Random;

public class Dice {
    private int num;

    public Dice(){
        roll();
    }

    public void roll(){
        Random rand = new Random();
        this.num = rand.nextInt(6) + 1;
    }

    public int getNum() {
        return num;
    }
}
