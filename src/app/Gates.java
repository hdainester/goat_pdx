package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gates {
    public static final String WIN_SYM = "O";
    public static final String LOSS_SYM = "X";

    private List<Integer> closed;
    private String[] gates;
    private int selected;
    private Random rng;

    public Gates() {
        closed = new ArrayList<>();
        gates = new String[3];
        rng = new Random();
        selected = -1;
    }

    /**
     * Randomly shuffles exactly one price within
     * the gates and marks all gates as closed.
     */
    public void shuffle() {
        closed.clear();
        selected = -1;

        int i = rng.nextInt(3);
        for(int j = 0; j < 3; ++j) {
            if(j != i) gates[j]  = LOSS_SYM;
            else gates[i] = WIN_SYM;
            closed.add(j);
        }
    }

    /**
     * Opens the selected closed gate
     * @return true if gate contains price
     */
    public boolean open() {
        if(selected < 0)
            throw new IllegalStateException("no gate has been selected");

        closed.remove(new Integer(selected));
        return gates[selected].equals(WIN_SYM);
    }

    /**
     * Player may select a closed gate
     * @param i - gate index
     */
    public void select(int g) {
        int i = g-1;
        if(i < 0 || i >= gates.length)
            throw new IllegalArgumentException("invalid gate: " + g);

        if(!closed.contains(i))
            throw new IllegalArgumentException("gate " + g + " already open");

        selected = i;
    }

    /**
     * Gamemaster randomly chooses and reveals
     * any of the closed gates without the price.
     */
    public void reveal() {
        if(this.closed.size() < 2)
            throw new IllegalStateException("cannot open any more gates");

        int i, g; String v;
        List<Integer> closed = new ArrayList<>(this.closed);
        closed.remove(new Integer(selected));

        do {
            i = rng.nextInt(closed.size());
            g = closed.remove(i);
            v = gates[g];
        } while(v.equals(WIN_SYM));

        this.closed.remove(new Integer(g));
    }

    @Override
    public String toString() {
        return String.format("[%s] [%s] [%s]",
            closed.contains(0) ? "1" : gates[0],
            closed.contains(1) ? "2" : gates[1],
            closed.contains(2) ? "3" : gates[2]);
    }
}