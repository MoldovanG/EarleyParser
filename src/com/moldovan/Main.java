package com.moldovan;
/**
 * Earley Parser implementation
 * University of Bucharest, FMI
 * @author: Moldovan George
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Please input the path to the grammar file:");
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        String grammarFile = scanner.next();
        System.out.println("Please input the sentence: ");
        String sentence = scanner.next();
        Grammar grammar = Grammar.getGrammarInstance(grammarFile);
        EarleyParser parser = new EarleyParser(sentence,grammar);
        parser.parse();
        System.out.println(parser.getChart().toString());
        System.out.println(parser.hasParse());
    }
}
