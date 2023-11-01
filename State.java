import java.util.*;

public class State implements Comparable<State> {

    private HashMap<String, Integer> people_at_start = new HashMap<String, Integer>();  //this Hashmap represents how many people are at the start
    private HashMap<String, Integer> people_at_finish = new HashMap<String, Integer>(); //this Hashmap represents how many people are at the end
    private int torch_position;                                     // position of torch in binary value. 0 at beginning, 1 at the end
    private int available_time;                                     // the available time to pass the bridge
    int movement = 2;                                       //how many time is acceptable to move
    private State father = null;
    private int f;      //f: heuristic score
    private int h;      //h: cost of reaching the goal from current node
    private int g;      //g: cost of reaching the current node from the initial node

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
        return this.available_time;
    }

    public void setTotalTime(int time)
    {
        this.available_time = time;
    }

    public void evaluate()
    {
        this.f = this.g + this.h;
    }

    public void print() {}

    public ArrayList<State> getChildren(int heuristic)
    {
        return null;
    }

    //moves people and the torch to the end
    private boolean move_to_end(String person1)
    {
        if(torch_position == 1)
        {
            return false;
        }
        if(movement <= 0)
        {
            return false;
        }
        return true;
    }

    //moves people and the torch to the start
    private boolean move_to_start(String person1)
    {
        if(torch_position == 0)
        {
            return false;
        }
        if(movement <= 0)
        {
            return false;
        }
        return true;
    }

    //moves ba at the beginning one person and the torch
    private void move_at_beginning()
    {

    }

    //get the depth of the state
    private void getDepth(State current_state)
    {
        setG(0);
        while (current_state.getFather() != null)
        {
            setG(g++);
            current_state = current_state.getFather();
        }
        getG();
    }

    //the heuristic function
    private void count_people_at_start()
    {
        setH(people_at_start.size());
    }

    // if people_at_start is empty that means that everyone passed the bridge.
    public boolean isFinal()
    {
        if (people_at_start.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {return true;}

    @Override
    public int hashCode() {return 0;}

    @Override
    public int compareTo(State s)
    {
        return Double.compare(this.f, s.f); // compare based on the heuristic score.
    }
}
