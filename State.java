import java.util.*;

public class State implements Comparable<State> {

    private HashMap<String, Integer> people_at_start = new HashMap<String, Integer>();  //this Hashmap represents how many people are at the start
    private HashMap<String, Integer> people_at_finish = new HashMap<String, Integer>(); //this Hashmap represents how many people are at the end
    private int torch_position = 0;                                     // position of torch in binary value. 0 at beginning, 1 at the end
    private int available_time = 0;                                     // the available time to pass the bridge
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
    }

    State(HashMap<String, Integer> people_at_start, HashMap<String, Integer> people_at_finish, int available_time)
    {
        this.people_at_start = people_at_start;
        this.people_at_finish = people_at_finish;
        this.available_time = available_time;
    }

    public int getAvailable_time()
    {
        return this.available_time;
    }

    public void setAvailable_time(int time)
    {
        this.available_time = time;
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

    public void evaluate(State state)
    {
        getDepth(state);
        count_people_at_start(state);
        this.f = this.g + this.h;
    }

    public void print() {
        System.out.println("People at start: " + this.people_at_start);
        System.out.println("People at finish: " + this.people_at_finish);
        System.out.println("Torch position: " + this.torch_position);
        System.out.println("Available time: " + this.available_time);
        System.out.println("Heuristic score (h): " + this.h);
        System.out.println("Cost to reach this node from the initial node (g): " + this.g);
        System.out.println("Heuristic score + Cost to reach this node (f): " + this.f);
    }

    public ArrayList<State> getChildren(State state)
    {   
        ArrayList<State> children = new ArrayList<State>(); //list of states for children
        
        //checks if to torch is at start
        if(state.torch_position == 0)
        {
            HashMap<String, Integer> iterator_people_at_start = new HashMap<>();   //creating an iterator hashmap
            iterator_people_at_start.putAll(state.people_at_start);
            for (Map.Entry<String, Integer> iterable_state : iterator_people_at_start.entrySet())
            {   
                if(iterable_state.getValue() <= state.available_time) //check if transfer is possible
                {
                    State child = new State();   //first make a copy of state that we will change
                    child.people_at_start.putAll(iterator_people_at_start);
                    child.people_at_finish.putAll(state.people_at_finish);
                    child.available_time = state.available_time;
                    String person_name = iterable_state.getKey();
                    Integer person_time = iterable_state.getValue();
                    child.people_at_finish.put(person_name, person_time);
                    child.available_time -= person_time;
                    child.people_at_start.remove(person_name);
                    child.torch_position = 1;
                    child.setFather(state);
                    child.evaluate(child);
                    // child.print();
                    children.add(child);                        //add child to ArrayList children
                }
            }

            HashMap<String, Integer> iterator_people_at_start_one = new HashMap<>();   //creating first iterator hashmap
            HashMap<String, Integer> iterator_people_at_start_two = new HashMap<>();   //creating second iterator hashmap
            iterator_people_at_start_one.putAll(state.people_at_start);
            iterator_people_at_start_two.putAll(state.people_at_start);
            for (Map.Entry<String, Integer> iterable_state_one : iterator_people_at_start_one.entrySet())
            {
                for (Map.Entry<String, Integer> iterable_state_two : iterator_people_at_start_two.entrySet())
                {
                    if(!(iterable_state_one.getKey() == iterable_state_two.getKey()) && iterable_state_one.getValue() + iterable_state_two.getValue() <= state.available_time) // check if transfer is possible
                    {
                        State child = new State();   //first make a copy of state that we will change
                        child.people_at_start.putAll(iterator_people_at_start_one);
                        child.people_at_finish.putAll(state.people_at_finish);
                        child.available_time = state.available_time;
                        String person_name_one = iterable_state_one.getKey();
                        String person_name_two = iterable_state_two.getKey();
                        Integer person_time_one = iterable_state_one.getValue();
                        Integer person_time_two = iterable_state_two.getValue();
                        child.people_at_finish.put(person_name_one, person_time_one);
                        child.people_at_finish.put(person_name_two, person_time_two);
                        if(person_time_one >= person_time_two)
                        {
                            child.available_time -= person_time_one;
                        }
                        else
                        {
                            child.available_time -= person_time_two;
                        }
                        child.people_at_start.remove(person_name_one);
                        child.people_at_start.remove(person_name_two);
                        child.torch_position = 1;
                        child.setFather(state);
                        child.evaluate(child);
                        // child.print();
                        children.add(child);
                    }
                }
            }

        }


        //checks if to torch is at end
        if(state.torch_position == 1)
        {
            HashMap<String, Integer> iterator_people_at_finish = new HashMap<>();   //creating an iterator hashmap
            iterator_people_at_finish.putAll(state.people_at_finish);
            for (Map.Entry<String, Integer> iterable_state : iterator_people_at_finish.entrySet())
            {   
                if(iterable_state.getValue() <= state.available_time) //check if transfer is possible
                {
                    State child = new State();   //first make a copy of state that we will change
                    child.people_at_finish.putAll(iterator_people_at_finish);
                    child.people_at_start.putAll(state.people_at_start);
                    child.available_time = state.available_time;
                    String person_name = iterable_state.getKey();
                    Integer person_time = iterable_state.getValue();
                    child.people_at_start.put(person_name, person_time);
                    child.available_time -= person_time;
                    child.people_at_finish.remove(person_name);
                    child.torch_position = 0;
                    child.setFather(state);
                    child.evaluate(child);
                    children.add(child);                        //add child to ArrayList children
                }
            }

            HashMap<String, Integer> iterator_people_at_finish_one = new HashMap<>();   //creating first iterator hashmap
            HashMap<String, Integer> iterator_people_at_finish_two = new HashMap<>();   //creating second iterator hashmap
            iterator_people_at_finish_one.putAll(state.people_at_finish);
            iterator_people_at_finish_one.putAll(state.people_at_finish);
            for (Map.Entry<String, Integer> iterable_state_one : iterator_people_at_finish_one.entrySet())
            {
                for (Map.Entry<String, Integer> iterable_state_two : iterator_people_at_finish_two.entrySet())
                {
                    if(!(iterable_state_one.getKey() == iterable_state_two.getKey()) && iterable_state_one.getValue() + iterable_state_two.getValue() <= state.available_time) // check if transfer is possible
                    {
                        State child = new State();   //first make a copy of state that we will change
                        child.people_at_finish.putAll(iterator_people_at_finish_one);
                        child.people_at_start.putAll(state.people_at_start);
                        child.available_time = state.available_time;
                        String person_name_one = iterable_state_one.getKey();
                        String person_name_two = iterable_state_two.getKey();
                        Integer person_time_one = iterable_state_one.getValue();
                        Integer person_time_two = iterable_state_two.getValue();
                        child.people_at_start.put(person_name_one, person_time_one);
                        child.people_at_start.put(person_name_two, person_time_two);
                        if(person_time_one >= person_time_two)
                        {
                            child.available_time -= person_time_one;
                        }
                        else
                        {
                            child.available_time -= person_time_two;
                        }
                        child.people_at_finish.remove(person_name_one);
                        child.people_at_finish.remove(person_name_two);
                        child.torch_position = 0;
                        child.setFather(state);
                        child.evaluate(child);
                        children.add(child);
                    }
                }
            }
        }

        return children;
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
    private void count_people_at_start(State state)
    {
        setH(state.people_at_start.size());
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
    public int compareTo(State s)
    {
        return Double.compare(this.f, s.f); // compare based on the heuristic score.
    }
}
