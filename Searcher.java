import java.util.*;

public class Searcher
{
    private ArrayList<State> frontier;
    private HashSet<State> closedSet;

    Searcher()
    {
        this.frontier = new ArrayList<>();
        this.closedSet = new HashSet<>();
    }

    State a_Star(State initialState)
    {
        if (initialState.isFinal())
        {
            return initialState;
        }
        State state_toReturn = new State();
        //put initial state in the frontier
        this.frontier.add(initialState);
        //while the frontier is not empty
        while(this.frontier.size() > 0)
        {
            ArrayList<Integer> cost = new ArrayList<Integer>(); // list for costs
            for(State state: frontier)
            {
                cost.add(state.getF()); //we get the f cost of every state in the frontier
            }
            //we need to find the minimum cost
            int min_cost = 0;
            boolean found = false;
            for(int i: cost)
            {
                if(i <= min_cost && frontier.get(i).getAvailable_time() >= 0)    //we find the minimu cost from the states that also have positive value in available time
                {
                    min_cost = i;
                    found = true;
                }
            }
            if(found = false)
            {
                State false_state = new State();
                false_state.setAvailable_time(-1);
                return false_state;
            }
            else
            {
                System.out.println("BlaBla");
                int index = cost.indexOf(min_cost); //we keep the index with the minimum cost
                State state = frontier.remove(index);
                //check if state is final, if it is, return it, else append it to closed set
                if(state.isFinal())
                {
                    state_toReturn = state;
                    found = true;
                    state.print();
                }
                if(!found){
                    closedSet.add(state);
                    ArrayList<State> children = state.getChildren(state);    //find all children of the state with the minimum cost
                    for (State child: children)
                    {
                        //if child already in the closed set and above the state
                        //we put it under the state
                        if(closedSet.contains(child) && (state.getG() < child.getG()))
                        {
                            child.setG(state.getG());
                            child.setFather(state);
                        }
                        //if the child is in the frontier and above the state
                        //we put it under the state
                        else if(frontier.contains(child) && (state.getG() < child.getG()))
                        {
                            child.setG(state.getG());
                            child.setFather(state);
                        }
                        else
                        {
                            frontier.add(child);
                            child.setG(state.getG());
                        }
                    }
                }
            }
        }
        return state_toReturn;
    }
}