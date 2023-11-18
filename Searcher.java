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
            ArrayList<Integer> cost = new ArrayList<Integer>(); // list for costs
            for(State state: frontier)
            {
                cost.add(state.getF()); //we get the f cost of every state in the frontier
            }

            //we need to find the minimum cost
            int min_cost = cost.get(0);
            for(int i: cost)
            {
                if(i <= min_cost)    //we find the minimu cost from the states that also have positive value in available time
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
                child.evaluate(child);
                //child.print();
                if (!closedSet.contains(child) && !frontier.contains(child))
                {
                    frontier.add(child);
                }
            }        
        }
        State state_nofound = new State();
        state_nofound.setAvailable_time(-1);
        return state_nofound;
    }
}