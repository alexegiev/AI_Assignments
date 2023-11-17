import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Bridge_Crossing
{

    public static void print_path(State final_state)
    {
        ArrayList<State> solution_path = new ArrayList<State>();
        solution_path.add(final_state);
        State current_node = final_state;
        if(final_state.getAvailable_time()<0)
        {
            System.out.println("Solution not found");
        }
        else
        {
            while(current_node.getFather()!=null)
            {
                current_node = current_node.getFather();
                solution_path.add(current_node);
            }
            System.out.println("This is the transfer order:");
            for(int i = solution_path.size() - 1; i >= 0; i--)
            {
                solution_path.get(i).print();
            }
        }        
    }
    public static void main(String[] args) {

        System.out.println("Do you want to make a custom game (press 1), or play the default settings (press 2)?");
        Scanner user_input = new Scanner(System.in);
        int user_option = user_input.nextInt();
        HashMap<String, Integer> people_time = new HashMap<String, Integer>();
        int available_time = 0;
        //check user input
        while(user_option != 1 && user_option != 2)
        {
            System.out.println("Wrong input, try again ");
            user_option = user_input.nextInt();
        }
        //custom game
        if(user_option == 1)
        {         
            //used to get the number of people and the time that each one takes to cross the bridge
            System.out.println("How many people need to cross the bridge? ");
            int number_of_people = user_input.nextInt();                        //number of pleople
            
            
            String name;
            for(int i = 1; i <= number_of_people; i++)
            {
                System.out.println("What is the name of the person? ");
                name = user_input.next();
                System.out.println("How much time does " + name + " need in minutes? ");
                Integer time = user_input.nextInt();
                people_time.put(name, time);
            }

            System.out.println("What is the available time in minutes? ");
            available_time = user_input.nextInt();
        }
        //default game
        else if(user_option == 2)
        {
            people_time.put("Bob", 1);
            people_time.put("Alice", 3);
            people_time.put("Hercules", 6);
            people_time.put("Marios", 8);
            people_time.put("Aunty", 12);
            available_time = 30;
        }
        HashMap<String, Integer> people_time_finish = new HashMap<String, Integer>();
        State initialState = new State(people_time, people_time_finish, available_time);
        Searcher searcher = new Searcher();
        long start = System.currentTimeMillis();
        State result = searcher.a_Star(initialState);
        print_path(result);
        long end = System.currentTimeMillis();
        System.out.println("Search time:" + (double)(end - start) / 1000 + " sec.");  // total time of searching in seconds.
        user_input.close();
    }
    
}
