import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("**********WELCOME**********");
        System.out.println("Please enter the NFA alphabet(Σ):");
        String[] alphabetArray = scanner.nextLine().split(" ");
        ArrayList<Character> alphabet = new ArrayList<>(alphabetArray.length);
        for (String s : alphabetArray) {
            alphabet.add(s.charAt(0));
        }
        System.out.println("Please enter the NFA states:");
        ArrayList<String> states = new ArrayList<>(Arrays.asList(scanner.nextLine().split(" ")));
        System.out.println("Please enter the initial state:");
        String initialState = scanner.nextLine();
        while (!states.contains(initialState)) {
            System.out.println("This state doesn't exist! Please enter a proper initial state:");
            initialState = scanner.nextLine();
        }
        System.out.println("Please enter the final states:");
        ArrayList<String> finalStates = new ArrayList<>(Arrays.asList(scanner.nextLine().split(" ")));
        while (!states.containsAll(finalStates) && !finalStates.contains("")) {
            System.out.println("Some states don't exist! Please enter proper final states:");
            finalStates = new ArrayList<>(Arrays.asList(scanner.nextLine().split(" ")));
        }
        System.out.println("Please enter number of transitions:");
        int transitionSize = scanner.nextInt();
        Map<String, Map<Character, ArrayList<String>>> transitions = new HashMap<>(transitionSize);
        scanner.nextLine();
        if(transitionSize != 0) {
            System.out.println("Please enter the NFA transitions:\n*** For example:δ(q0, a) = q2 or f(q0, a) = q2");
        }
        scanTransitions(transitionSize, states, alphabet, scanner, transitions);
        NFA nfa = new NFA(alphabet, states, initialState, finalStates, transitions);
        System.out.println("Please enter the string:");
        String input = scanner.nextLine();
        for (int i = 0; i < input.length(); i++) {
            if (!alphabet.contains(input.charAt(i))) {
                System.out.println("This input doesn't exist in the alphabet! Please enter a proper input:");
                input = scanner.nextLine();
                i = 0;
            }
        }
        nfa.acceptString(input);
        scanner.close();
    }

    static void scanTransitions(int transitionSize, ArrayList<String> states, ArrayList<Character> alphabet,
                                Scanner scanner, Map<String, Map<Character, ArrayList<String>>> transitions) {
        for (int i = 0; i < transitionSize; i++) {
            String[] input = scanner.nextLine().split(" |f\\(|δ\\(|=|\\)|,");
            String[] line = new String[3];
            int index = 0;
            for (int j = 0; j < input.length && index < 3; j++) {
                if (!input[j].equals("")) {
                    line[index] = input[j];
                    index++;
                }
            }
            if ((!alphabet.contains(line[1].charAt(0)) && !line[1].equals("λ") && !line[1].equals("ε"))
                    || !states.contains(line[0]) || !states.contains(line[2])) {
                System.out.println("Invalid transition! Please re-enter this transition:");
                i--;
                continue;
            }
            ArrayList<String> newArray = new ArrayList<>();
            newArray.add(line[2]);
            if (transitions.containsKey(line[0])) {
                Map<Character, ArrayList<String>> curr = transitions.get(line[0]);
                if (curr.containsKey(line[1].charAt(0))) {
                    curr.get(line[1].charAt(0)).add(line[2]);
                } else {
                    curr.put(line[1].charAt(0), newArray);
                }
            } else {
                Map<Character, ArrayList<String>> newMap = new HashMap<>();
                newMap.put(line[1].charAt(0), newArray);
                transitions.put(line[0], newMap);
            }
        }
    }
}