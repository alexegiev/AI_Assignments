import java.util.HashMap;

public class State implements Comparable<State> {
    
    //heuristic score
    private int score;
    private HashMap<String, Integer> people_at_start = new HashMap<String, Integer>();  //this Hashmap represents how many people are at the start
    private HashMap<String, Integer> people_at_finish = new HashMap<String, Integer>(); //this Hashmap represents how many people are at the end
    private int torch_position;                                     // position of torch in binary value. 0 at beginning, 1 at the end
    private int available_time;

    State(HashMap<String, Integer> people_at_start, int available_time)
    {
        this.people_at_start = people_at_start;
        this.torch_position = 0;
        this.available_time = available_time;
    }

    @Override
    public int compareTo(State s)
    {
        return Double.compare(this.score, s.score); // compare based on the heuristic score.
    }
}
