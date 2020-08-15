package com.moldovan;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Grammar {
    private Map<String, List<Rule>> grammarDictionary;

    private Grammar(){
        grammarDictionary = new HashMap<>();
    }
    public static Grammar getGrammarInstance(String filepath) {
        /*
        To be implemented
         */
        Grammar grammar = new Grammar();
        BufferedReader bufferedReader;
        try {
            bufferedReader = Files.newBufferedReader(Paths.get(filepath));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.strip();
                if (line.length() == 0) {
                    continue;
                }
                String[] entries = line.split("->");
                String lhs = entries[0].strip();
                for (String rhs : entries[1].split("\\|")) {
                    grammar.add(new Rule(lhs, new ArrayList<String>(Arrays.asList(rhs.strip().split(" ")))));
                }
            }
            return grammar;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the grammar file \n" , e);
        }

    }

    private void add(Rule rule) {
        List<Rule> rules = grammarDictionary.get(rule.getLhs());
        if (rules == null) {
            rules = new ArrayList<>();
        }
        rules.add(rule);
        grammarDictionary.put(rule.getLhs(),rules);
    }

    public List<Rule> getRules(String key){
        return grammarDictionary.get(key);
    }

    public boolean is_tag(String symbol) {
        if (is_terminal(symbol)){
            return false;
        }
        for (Rule r : grammarDictionary.get(symbol)){
            for (String s : r.getRhs()){
                if (!is_terminal(s)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean is_terminal(String symbol) {
        return !grammarDictionary.containsKey(symbol);
    }
}
