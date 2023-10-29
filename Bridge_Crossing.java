import java.util.Scanner;
import java.util.HashMap;

public class Bridge_Crossing
{
    public static void main(String[] args) {

        //used to get the number of people and the time that each one takes to cross the bridge
        Scanner user_input = new Scanner(System.in); 

        System.out.println("How many poeple need to cross the bridge? ");
        int number_of_people = user_input.nextInt();                        //number of pleople
        
        HashMap<String, Integer> people_time = new HashMap<String, Integer>();
        for(int i = 1; i <= number_of_people; i++)
        {
            System.out.println("How much time does the " + i + " person need in minutes? ");
            Integer time = user_input.nextInt();
            people_time.put(Integer.toString(i), time);
        }

        System.out.println("What is the available time in minutes? ");
        int available_time = user_input.nextInt();

        user_input.close();
        State initilState = new State(people_time, available_time);
    }
}
