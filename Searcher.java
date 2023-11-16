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
            for (int i = 0; i < cost.size(); i++)
            {
                if (cost.get(i) <= min_cost && frontier.get(i).getAvailable_time() >= 0) //we find the minimu cost from the states that also have positive value in available time
                {
                    min_cost = i;
                    found = true;
                }
            }
            if(found == false)
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
                        // Check if the child is already in the closed set or the frontier
                        if(!closedSet.contains(child) && !frontier.contains(child)){
                            // if not , add it to the frontier and update its information
                            frontier.add(child);
                            child.setG(state.getG());
                            child.setFather(state);
                        }
                        else if(frontier.contains(child) && (state.getG() < child.getG()))
                        {
                            // if the child is in the frontier but the new path is better , update it
                            child.setG(state.getG());
                            child.setFather(state);
                        }
                        //if the child is in the closed set with a better path, update it
                        else if(closedSet.contains(child) && (state.getG() < child.getG()))
                        {
                            child.setG(state.getG());
                            child.setFather(state);
                            // remove it from closedset and move  back to frontier
                            closedSet.remove(child);
                            frontier.add(child);
                        }

                    }
                }
            }
        }
        return state_toReturn;
    }
}