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

    public ArrayList<State> getChildren(State state)
    {
        ArrayList<State> children =null; //list of states for children
        if (torch_position == 0)    //checking if torch is at start
        {
            for (String first_person: people_at_start.keySet())
            {
                for (String second_person: people_at_start.keySet())
                {
                    //we will check all possible transfers by pair, we create a child, set all his characteristics and then append to children list
                    if(move_to_end(people_at_start.get(first_person), people_at_start.get(second_person)))
                    {
                        State child = new State();
                        child.setFather(state);
                        child.getDepth(state);
                        children.add(child);
                    }
                }
            }
        }
        else if (torch_position == 1)   //checking if torch is at the end
        {
            for (String first_person: people_at_finish.keySet())
            {
                for (String second_person: people_at_finish.keySet())
                {
                    //we will check all possible transfers by pair, we create a child, set all his characteristics and then append to children list
                    if(move_to_end(people_at_finish.get(first_person), people_at_finish.get(second_person)))
                    {
                        State child = new State();
                        child.setFather(state);
                        child.getDepth(state);
                        children.add(child);
                    }
                }
            }
        }
        return children;
    }

    //moves people and the torch to the end
    private boolean move_to_end(Integer person_one_time, Integer person_two_time)
    {
        if(torch_position == 1)
        {
            return false;
        }
        if(movement <= 0)
        {
            return false;
        }
        if(person_one_time + person_two_time > available_time)
        {
            return false;
        }
        return true;
    }

    //moves people and the torch to the start
    private boolean move_to_start(Integer person_one_time, Integer person_two_time)
    {
        if(torch_position == 0)
        {
            return false;
        }
        if(movement <= 0)
        {
            return false;
        }
        if(person_one_time + person_two_time > available_time)
        {
            return false;
        }
        return true;
    }

    

    //get the depth of the state
    private void getDepth(State current_state)
    {
        int g = 0;
        while (current_state.getFather() != null)
        {
            g++;
            current_state = current_state.getFather();
        }
        setG(g);
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
