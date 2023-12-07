import java.util.ArrayList;

public class Main {
    static String regex = "abc|d|t|rfg|hi";
    static ArrayList<Integer> states;
    static int endState;
    
    public static void main(String[] args) {
        System.out.println("Regex: " + regex.toUpperCase());
        ArrayList<ArrayList<Pair>> pairs = dictionary(regex);
        endState = 0;
        states = new ArrayList<>();
        //print pairs, find end state and states
        for (int i = 0; i < pairs.size(); i++) {
            ArrayList<Pair> pair = pairs.get(i);
            for (int j = 0; j < pair.size(); j++) {
                if (j == pair.size() - 1 && i == 0) {
                    endState = pair.get(j).to;
                }
                if (j == pair.size() - 1) {
                    pair.get(j).to = endState;
                }
                if (!states.contains(pair.get(j).from) && pair.get(j).from != endState && pair.get(j).from != 1) {
                    states.add(pair.get(j).from);
                }
            }
        }
        //change end state to 1
        for (ArrayList<Pair> pair : pairs) {
            for (int j = 0; j < pair.size(); j++) {
                if (pair.get(j).to == endState && pair.size() == 1) {
                    pair.get(j).from = 1;
                }
                else if (j == 0) {
                    pair.get(j).from = 1;
                }
            }
        }
        //print pairs
        for (ArrayList<Pair> pair : pairs) {
            System.out.print(1);
            for (Pair value : pair) {
                if (value.to == endState) {
                    System.out.print("\t" + "⭢" + "\t" + Character.toUpperCase(value.character) + "\t" + "⭢" + "\t" + "⭗");
                }
                else {
                    System.out.print("\t" + "⭢" + "\t" + Character.toUpperCase(value.character) + "\t" + "⭢" + "\t" + value.to);
                }
            }
            System.out.println();
        }
        //print states excluding 1 and end state
        System.out.println("States: " + states);
        printTransitionsTable(pairs);
    }
    
    private static void printTransitionsTable(ArrayList<ArrayList<Pair>> pairs) {
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[33m";
        String BLUE = "\u001B[34m";
        String PURPLE = "\u001B[35m";
        
        String[] reg = regex.replaceAll("\\|", "").split("");
        System.out.print(RED + "State\t" + RESET);
        for (String s : reg) {
            System.out.print(GREEN + s.toUpperCase() + "\t" + RESET + "|" + "\t");
        }
        System.out.println();
        
        //print the first row
        System.out.print(GREEN + "1" + BLUE + "\t⭢\t" + RESET);
        for (ArrayList<Pair> pairArrayList : pairs) {
            for (int j = 0; j < pairArrayList.size(); j++) {
                if (j == 0) {
                    System.out.print(PURPLE + pairArrayList.get(j).to + "\t" + RESET + "|" + "\t");
                }
                else {
                    System.out.print("-" + "\t" + "|" + "\t");
                }
            }
        }
        System.out.println();
        
        //print other rows
        for (Integer state : states) {
            System.out.print(YELLOW + state + BLUE + "\t⭢\t" + RESET);
            for (ArrayList<Pair> pairArrayList : pairs) {
                for (Pair value : pairArrayList) {
                    if (value.from == state) {
                        System.out.print(PURPLE + value.to + "\t" + RESET + "|" + "\t");
                    }
                    else {
                        System.out.print("-" + "\t" + "|" + "\t");
                    }
                }
            }
            System.out.println();
        }
        
        System.out.print(RED + endState + BLUE + "\t⭢\t" + RESET);
        for (ArrayList<Pair> pair : pairs) {
            for (int j = 0; j < pair.size(); j++) {
                System.out.print("-" + "\t" + "|" + "\t");
            }
        }
    }
    
    private static ArrayList<ArrayList<Pair>> dictionary(String regex) {
        int count = 1;
        ArrayList<ArrayList<Pair>> pairs = new ArrayList<>();
        String[] regexArray = regex.split("\\|");
        for (int i = 0; i < regexArray.length; i++) {
            String regexPart = regexArray[i];
            pairs.add(new ArrayList<>());
            for (int j = 0; j < regexPart.length(); j++) {
                pairs.get(i).add(new Pair(regexPart.charAt(j), count, count + 1));
                count++;
            }
            if (i >= 1) {
                count--;
            }
        }
        return pairs;
    }
}

class Pair {
    char character;
    int from;
    int to;
    
    Pair(char character, int from, int to) {
        this.character = character;
        this.from = from;
        this.to = to;
    }
}
