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

        //put initial state in the frontier
        this.frontier.add(initialState);
        //while the frontier is not empty
        while(this.frontier.size() > 0)
        {
            ArrayList<Integer> cost = null; // list for costs
            for(State state: frontier)
            {
                cost.add(state.getF()); //we get the f cost of every state in the frontier
            }
            //we need to find the minimum cost
            int min_cost = 0;
            for(int i: cost)
            {
                if(i <= min_cost)
                {
                    min_cost = i;
                }
            }
            int index = cost.indexOf(min_cost); //we keep the index with the minimum cost
            State state = frontier.remove(index);
            //check if state is final, if it is, return it, else append it to closed set
            if(state.isFinal())
            {
                return state;
            }
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
        return null;
    }
}