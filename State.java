import java.util.HashMap;

public class State implements Comparable<State> {
    
    //heuristic score
    private int score;
    private HashMap<String, Integer> people_at_start = new HashMap<String, Integer>();
    private HashMap<String, Integer> people_at_finish = new HashMap<String, Integer>();

    State(HashMap<String, Integer> people_at_start)
    {
        this.people_at_start = people_at_start;
    }

    @Override
    public int compareTo(State s)
    {
        return Double.compare(this.score, s.score); // compare based on the heuristic score.
    }
}
