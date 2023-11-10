import java.util.*;

public class State implements Comparable<State> {

    private HashMap<String, Integer> people_at_start = new HashMap<String, Integer>();  //this Hashmap represents how many people are at the start
    private HashMap<String, Integer> people_at_finish = new HashMap<String, Integer>(); //this Hashmap represents how many people are at the end
    private int torch_position;                                     // position of torch in binary value. 0 at beginning, 1 at the end
    private int available_time;                                     // the available time to pass the bridge
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
        ArrayList<State> children = new ArrayList<State>(); //list of states for children
        if (torch_position == 0)    //checking if torch is at start
        {
            //the game allows a SINGLE person with the torch to cross the bridge, so we will create a child with the state of one person only
            for (String person: people_at_start.keySet())
            {
                if(move_to_end(people_at_start.get(person)))
                {
                        State child = new State();
                        child.setFather(state);
                        child.getDepth(state);
                        child.move_person_to_end(person);
                        children.add(child);
                }
            }
            for (String first_person: people_at_start.keySet())
            {
                for (String second_person: people_at_start.keySet())
                {
                    //we will check all possible transfers by pair, we create a child, set all his characteristics and then append to children list
                    if(first_person != second_person && move_to_end_by_pair(people_at_start.get(first_person), people_at_start.get(second_person)))
                    {
                        State child = new State();
                        child.setFather(state);
                        child.getDepth(state);
                        child.move_person_to_end(first_person);
                        child.move_person_to_end(second_person);
                        children.add(child);
                    }
                }
            }
        }
        else if (torch_position == 1)   //checking if torch is at the end
        {
            //the game allows a SINGLE person with the torch to cross the bridge, so we will create a child with the state of one person only
            for (String person: people_at_finish.keySet())
            {
                if (move_to_start(people_at_finish.get(person)))
                {
                        State child = new State();
                        child.setFather(state);
                        child.getDepth(state);
                        child.move_person_to_start(person);
                        children.add(child);
                }
            }
            for (String first_person: people_at_finish.keySet())
            {
                for (String second_person: people_at_finish.keySet())
                {
                    //we will check all possible transfers by pair, we create a child, set all his characteristics and then append to children list
                    if(first_person != second_person && move_to_start_by_pair(people_at_finish.get(first_person), people_at_finish.get(second_person)))
                    {
                        State child = new State();
                        child.setFather(state);
                        child.getDepth(state);
                        child.move_person_to_start(first_person);
                        child.move_person_to_start(second_person);
                        children.add(child);
                    }
                }
            }
        }
        return children;
    }


    //moves one person and the torch to the end, this function only operates as a checker, function move_person_to_end(person) implements the transfer
    private boolean move_to_end(Integer person_time)
    {
        if(torch_position == 1)
        {
            return false;
        }
        if(person_time > available_time)
        {
            return false;
        }
        return true;
    }

    //moves person and the torch to the start, this function only operates as a checker, function move_person_to_start(person) implements the transfer
    private boolean move_to_start(Integer person_time)
    {
        if(torch_position == 0)
        {
            return false;
        }
        if(person_time > available_time)
        {
            return false;
        }
        return true;
    }


    //moves a pair of people and the torch to the end, this function only operates as a checker, function move_person_to_end(person) implements the transfer
    private boolean move_to_end_by_pair(Integer person_one_time, Integer person_two_time)
    {
        if(torch_position == 1)
        {
            return false;
        }
        if(person_one_time + person_two_time > available_time)
        {
            return false;
        }
        return true;
    }

    //moves a pair of people and the torch to the start, this function only operates as a checker, function move_person_to_start(person) implements the transfer
    private boolean move_to_start_by_pair(Integer person_one_time, Integer person_two_time)
    {
        if(torch_position == 0)
        {
            return false;
        }
        if(person_one_time + person_two_time > available_time)
        {
            return false;
        }
        return true;
    }

    //this function actually impliments the transfer of one person and the torch to the end
    private void move_person_to_end(String person)
    {
        Integer person_time = people_at_start.get(person);
        people_at_finish.put(person, person_time);
        people_at_start.remove(person);
        torch_position = 1;
    }    

    //this function actually impliments the transfer of one person and the torch to the start
    private void move_person_to_start(String person)
    {
        Integer person_time = people_at_finish.get(person);
        people_at_start.put(person, person_time);
        people_at_start.remove(person);
        torch_position = 0;
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
