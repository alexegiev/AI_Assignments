import java.util.HashMap;

public class State implements Comparable<State> {

    private HashMap<String, Integer> people_at_start = new HashMap<String, Integer>();  //this Hashmap represents how many people are at the start
    private HashMap<String, Integer> people_at_finish = new HashMap<String, Integer>(); //this Hashmap represents how many people are at the end
    private int torch_position;                                     // position of torch in binary value. 0 at beginning, 1 at the end
    private int available_time;                                     // the available time to pass the bridge
    private State father = null;
    private int score;                                              //heuristic score
    private int f, h, g;

    public State()
    {
        this.f = 0;
        this.h = 0;
        this.g = 0;
        this.father = null;
        this.available_time = 0;
    }

    State(HashMap<String, Integer> people_at_start, int available_time)
    {
        this.f = 0;
        this.h = 0;
        this.g = 0;
        this.people_at_start = people_at_start;
        this.torch_position = 0;
        this.available_time = available_time;
    }

    public int getF()
    {
        return this.f;
    }

    public int getG()
    {
        return this.g;
    }

    public int getH()
    {
        return this.h;
    }

    public State getFather()
    {
        return this.father;
    }

    public void setF(int f)
    {
        this.f = f;
    }

    public void setG(int g)
    {
        this.g = g;
    }

    public void setH(int h)
    {
        this.h = h;
    }

    public void setFather(State f)
    {
        this.father = f;
    }

    public int getTotalTime()
    {
        return this.totalTime;
    }

    public void setTotalTime(int time)
    {
        this.totalTime = time;
    }

    public void evaluate()
    {
        //calculate f...
    }

    public void print() {}

    public ArrayList<State> getChildren() {return null;}

    public boolean isFinal() {return true;}

    @Override
    public boolean equals(Object obj) {return true;}

    @Override
    public int hashCode() {return 0;}

    @Override
    public int compareTo(State s)
    {
        return Double.compare(this.score, s.score); // compare based on the heuristic score.
    }
}
