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
            int min_cost = 0;
            boolean found = false;
            for (int i = 0; i < cost.size(); i++)
            {
                if (cost.get(i) <= min_cost && frontier.get(i).getAvailable_time() >= 0) //we find the minimu cost from the states that also have positive value in available time
=======
            int min_cost = cost.get(0);
            for(int i: cost)
            {
                if(i <= min_cost)    //we find the minimu cost from the states that also have positive value in available time
>>>>>>> 05c08203a92e4ff0eee9e813508b401bc948137f
                {
                    min_cost = i;
                }
            }
<<<<<<< HEAD
            if(found == false)
=======

            int index = cost.indexOf(min_cost); //we keep the index with the minimum cost
            State state = frontier.remove(index);
            //check if state is final, if it is, return it, else append it to closed set
            if(state.isFinal())
>>>>>>> 05c08203a92e4ff0eee9e813508b401bc948137f
            {
                return state;
            }

            closedSet.add(state);
            ArrayList<State> children = state.getChildren(state);    //find all children of the state with the minimum cost
            for (State child: children)
            {
                child.evaluate(child);
                //child.print();
                //if child already in the closed set and above the state
                //we put it under the state
                if(closedSet.contains(child) && (state.getG() < child.getG()))
                {
                    child.setG(state.getG());
                    child.setFather(state);
                }
<<<<<<< HEAD
                if(!found){
                    closedSet.add(state);
                    ArrayList<State> children = state.getChildren(state);    //find all children of the state with the minimum cost
                    for (State child: children)
                    {
<<<<<<< HEAD
                        // Check if the child is already in the closed set or the frontier
                        if(!closedSet.contains(child) && !frontier.contains(child)){
                            // if not , add it to the frontier and update its information
=======
                        System.out.println("--------Childeer--------");
                        child.print();
                        System.out.println("--------Childeer--------");
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
>>>>>>> 45b2c4fb9abfed462f96fd935047e31c66f5fec5
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
=======
                //if the child is in the frontier and above the state
                //we put it under the state
                else if(frontier.contains(child) && (state.getG() < child.getG()))
                {
                    child.setG(state.getG());
                    child.setFather(state);
>>>>>>> 05c08203a92e4ff0eee9e813508b401bc948137f
                }
                else
                {
                    frontier.add(child);
                    child.setG(state.getG());
                }
            }        
        }
        State state_nofound = new State();
        state_nofound.setAvailable_time(-1);
        return state_nofound;
    }
}