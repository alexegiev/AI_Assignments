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

    State a_Star(State initialState, int heuristic)
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
            ArrayList<int> cost; // list for costs
            for(State state: frontier)
            {
                cost.add(state.getF()); //we get the f cost of every state in the frontier
            }
        }
    }
}