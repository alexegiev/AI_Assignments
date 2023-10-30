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

    State BestFS(State initialState, int heuristic)
    {
        if(initialState.isFinal()) return initialState;
        // step 1: put initial state in the frontier.
        this.frontier.add(initialState);
        // step 2: check for empty frontier.
        while(this.frontier.size() > 0)
        {
            // step 3: get the first node out of the frontier.
            State currentState = this.frontier.remove(0);
            // step 4: if final state, return.
            if(currentState.isFinal()) return currentState;
            // step 5: put the children at the frontier
            this.frontier.addAll(currentState.getChildren(heuristic));
            // step 6: sort the frontier based on the heuristic score to get best as first
            Collections.sort(this.frontier);
        }
        return null;
    }
}