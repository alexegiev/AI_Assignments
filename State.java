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
    private static int transfer_limit = 2;

    public State()
    {
        this.f = 0;
        this.h = 0;
        this.g = 0;
        this.father = null;
    }

    State(HashMap<String, Integer> people_at_start, HashMap<String, Integer> people_at_finish, int available_time)
    {
        this.f = 0;
        this.h = 0;
        this.g = 0;
        this.people_at_start = people_at_start;
        this.people_at_finish = people_at_finish;
        this.available_time = available_time;
        evaluate();
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
        evaluate();
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
        count_people_at_start();
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
        if (state.torch_position == 0)    //checking if torch is at start
        {
            //we create a copy of the state hashmap so we can iterate it 
            HashMap<String, Integer> people_at_start_iteration = new  HashMap<String, Integer>(state.people_at_start);

            //the game allows a SINGLE person with the torch to cross the bridge, so we will create a child with the state of one person only
            for (String person: people_at_start_iteration.keySet())
            {
                if(move_to_end(state, person))
                {
<<<<<<< HEAD
                        State child = new State(new HashMap<>(state.people_at_start), state.available_time);
                        child.setFather(state);
                        child.getDepth(state);
                        child.move_person_to_end(person);
                        child.available_time -= people_at_start.get(person);
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
                        State child = new State(new HashMap<>(state.people_at_start), state.available_time);
                        child.setFather(state);
                        child.getDepth(state);
                        child.move_person_to_end(first_person);
                        child.move_person_to_end(second_person);
                        int time_person1 = people_at_start.get(first_person);
                        int time_person2 = people_at_start.get(second_person);
                        int max_time;
                        if(time_person1 >= time_person2)
                        {
                            max_time = time_person1;
                        }
                        else
                        {
                            max_time = time_person2;
                        }
                        child.available_time -= max_time;
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
                    State child = new State(new HashMap<>(state.people_at_start), state.available_time);
=======
                    State child = new State(state.people_at_start, state.people_at_finish, state.available_time);
>>>>>>> 05c08203a92e4ff0eee9e813508b401bc948137f
                    child.setFather(state);
                    child.getDepth(state);
                    child.available_time -= child.people_at_start.get(person);
                    child.move_person_to_end(child, person);
                    child.evaluate();
                    transfer_limit -= 1;
                    children.add(child);
                }
            }
            transfer_limit = 2;
            //we create two copies of the state hashmap so we can iterate them 
            HashMap<String, Integer> people_at_start_iteration_by_pair_one = new  HashMap<String, Integer>(state.people_at_start);
            HashMap<String, Integer> people_at_start_iteration_by_pair_two = new  HashMap<String, Integer>(state.people_at_start);

            for (String first_person: people_at_start_iteration_by_pair_one.keySet())
            {
                for (String second_person: people_at_start_iteration_by_pair_two.keySet())
                {
                    //we will check all possible transfers by pair, we create a child, set all his characteristics and then append to children list
                    if(first_person != second_person && move_to_end_by_pair(state, first_person, second_person))
                    {
<<<<<<< HEAD
                        State child = new State(new HashMap<>(state.people_at_start), state.available_time);
=======
                        State child = new State(state.people_at_start, state.people_at_finish, state.available_time);
>>>>>>> 05c08203a92e4ff0eee9e813508b401bc948137f
                        child.setFather(state);
                        child.getDepth(state);
                        int time_person1 = child.people_at_start.get(first_person);
                        int time_person2 = child.people_at_start.get(second_person);
                        int max_time;
                        if(time_person1 >= time_person2)
                        {
                            max_time = time_person1;
                        }
                        else
                        {
                            max_time = time_person2;
                        }
                        child.available_time -= max_time;
<<<<<<< HEAD
=======
                        child.move_person_to_end(child, first_person);
                        child.move_person_to_end(child, second_person);
                        transfer_limit = 0;
                        child.evaluate();
>>>>>>> 05c08203a92e4ff0eee9e813508b401bc948137f
                        children.add(child);
                    }
                }
                transfer_limit = 2;
            }
        }
        else if (state.torch_position == 1)   //checking if torch is at the end
        {
            //we create a copy of the state hashmap so we can iterate it 
            HashMap<String, Integer> people_at_finish_iteration = new  HashMap<String, Integer>(state.people_at_finish);

            //the game allows a SINGLE person with the torch to cross the bridge, so we will create a child with the state of one person only
            for (String person: people_at_finish_iteration.keySet())
            {
                if (move_to_start(state))
                {
                    State child = state;
                    child.setFather(state);
                    child.getDepth(state);
                    child.available_time -= child.people_at_finish.get(person);
                    child.move_person_to_start(state, person);
                    transfer_limit -= 1;
                    child.evaluate();
                    children.add(child);
                }
            }
            transfer_limit = 2;
            //we create two copies of the state hashmap so we can iterate them 
            HashMap<String, Integer> people_at_finish_iteration_by_pair_one = new  HashMap<String, Integer>(state.people_at_finish);
            HashMap<String, Integer> people_at_finish_iteration_by_pair_two = new  HashMap<String, Integer>(state.people_at_finish);

            for (String first_person: people_at_finish_iteration_by_pair_one.keySet())
            {
                for (String second_person: people_at_finish_iteration_by_pair_two.keySet())
                {
                    //we will check all possible transfers by pair, we create a child, set all his characteristics and then append to children list
                    if(first_person != second_person && move_to_start_by_pair(state, first_person, second_person))
                    {
                        State child = state;
                        child.setFather(state);
                        child.getDepth(state);
                        int time_person1 = child.people_at_finish.get(first_person);
                        int time_person2 = child.people_at_finish.get(second_person);
                        int max_time;
                        if(time_person1 >= time_person2)
                        {
                            max_time = time_person1;
                        }
                        else
                        {
                            max_time = time_person2;
                        }
                        child.available_time -= max_time;
                        child.move_person_to_start(state, first_person);
                        child.move_person_to_start(state, second_person);
                        transfer_limit = 0;
                        child.evaluate();
                        children.add(child);
                    }
                }
                transfer_limit = 2;
            }
        }
        return children;
    }


    //moves one person and the torch to the end, this function only operates as a checker, function move_person_to_end(person) implements the transfer
    private boolean move_to_end(State state, String person)
    {
        if(state.torch_position == 1 && transfer_limit > 0)
        {
            return false;
        }
        return true;
    }

    //moves person and the torch to the start, this function only operates as a checker, function move_person_to_start(person) implements the transfer
    private boolean move_to_start(State state)
    {
        if(state.torch_position == 0 && transfer_limit > 0)
        {
            return false;
        }
        return true;
    }


    //moves a pair of people and the torch to the end, this function only operates as a checker, function move_person_to_end(person) implements the transfer
    private boolean move_to_end_by_pair(State state, String person_one, String person_two)
    {
        if(state.torch_position == 1 && transfer_limit > 0)
        {
            return false;
        }
        if(state.people_at_start.get(person_one) + state.people_at_start.get(person_two) > state.available_time && transfer_limit > 0)
        {
            return false;
        }
        return true;
    }

    //moves a pair of people and the torch to the start, this function only operates as a checker, function move_person_to_start(person) implements the transfer
    private boolean move_to_start_by_pair(State state, String person_one, String person_two)
    {
        if(state.torch_position == 0 && transfer_limit > 0)
        {
            return false;
        }
        if(state.people_at_start.get(person_one) + state.people_at_start.get(person_two) > state.available_time && transfer_limit > 0)
        {
            return false;
        }
        return true;
    }

    //this function actually impliments the transfer of one person and the torch to the end
    private void move_person_to_end(State state, String person)
    {
        String person_name = person;
        Integer person_time = state.people_at_start.get(person_name);
        state.people_at_finish.put(person_name, person_time);
        state.people_at_start.remove(person_name);
        state.torch_position = 1;
    }    

    //this function actually impliments the transfer of one person and the torch to the start
    private void move_person_to_start(State state, String person)
    {
<<<<<<< HEAD
        Integer person_time = people_at_finish.get(person);
        people_at_start.put(person, person_time);
        people_at_finish.remove(person);
        torch_position = 0;
=======
        String person_name = person;
        Integer person_time = state.people_at_finish.get(person_name);
        state.people_at_start.put(person_name, person_time);
        state.people_at_finish.remove(person_name);
        state.torch_position = 0;
>>>>>>> 05c08203a92e4ff0eee9e813508b401bc948137f
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
    public int compareTo(State s)
    {
        return Double.compare(this.f, s.f); // compare based on the heuristic score.
    }
}
