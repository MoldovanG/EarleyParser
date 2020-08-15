package com.moldovan;

import java.util.*;

public class EarleyParser {
    List<String> words;
    Grammar grammar;
    Chart chart;

    public EarleyParser(String sentence, Grammar grammar) {
        this.words = new ArrayList<>(Arrays.asList(sentence.split(" ")));
        this.grammar = grammar;
        this.chart = Chart.getChartInstance(words.size() + 1);
    }

    public void predictor(EarleyState state, int pos) {
        for (Rule rule : grammar.getRules(state.nextCharacterToParse())){
            chart.getChartEntry(pos).add(new EarleyState(rule,
                    0,
                    state.getChart_pos(),
                    state.getChart_pos(),
                    "Predicted from : " + state.toStringWithoutComment()));
        }
    }

    public void scanner(EarleyState state, int pos) {
        if (state.getChart_pos() < words.size()){
            String word = words.get(state.getChart_pos());
            boolean wordInRule = false;
            for (Rule r : grammar.getRules(state.nextCharacterToParse())){
                if (r.contains(word)){
                    wordInRule = true;
                }
            }
            if (wordInRule) {
                chart.getChartEntry(pos+ 1).add(new EarleyState(
                        new Rule(state.nextCharacterToParse(), new ArrayList<>(Arrays.asList(word))),
                        1,
                        state.getChart_pos(),
                        state.getChart_pos() + 1,
                        "Scan from :" + state.toStringWithoutComment())
                );
            }
        }
    }

    public void completer(EarleyState state, int pos) {
        Set<EarleyState> iterationSet = new LinkedHashSet<>(chart.getChartEntry(state.getSentence_pos()).getEarleyStateSet());
        for (EarleyState prevState : iterationSet) {
            if (!prevState.isComplete()) {
                if (prevState.nextCharacterToParse().equals(state.getRule().getLhs())) {
                    chart.getChartEntry(pos).add(new EarleyState(prevState.getRule(),
                            prevState.getDot_pos() + 1,
                            prevState.getSentence_pos(),
                            pos,
                            "Complete from : " + state.toStringWithoutComment()));
                }
            }
        }
    }
    public boolean isTag(EarleyState state) {
        return grammar.is_tag(state.nextCharacterToParse());
    }
    public void parse() {
        for (int i = 0; i < chart.size(); i++) {
            int oldStateSet;
            int newStateSet;
             do {
                oldStateSet = chart.getChartEntry(i).getEarleyStateSet().size();
                Set<EarleyState> iterationSet = new LinkedHashSet<>(chart.getChartEntry(i).getEarleyStateSet());
                for (EarleyState state : iterationSet) {
                    if (!state.isComplete()) {
                        if (isTag(state)) {
                            scanner(state, i);
                        } else {
                            predictor(state, i);
                        }
                    } else {
                        completer(state, i);
                    }
                }
                newStateSet = chart.getChartEntry(i).getEarleyStateSet().size();
            } while (oldStateSet != newStateSet);
        }
    }

    public boolean hasParse(){
        for (EarleyState state : chart.getChartEntry(chart.size()-1).getEarleyStateSet()){
            if (state.isComplete() && state.getRule().getLhs().equals("S")
            && state.getSentence_pos() == 0 && state.getChart_pos() == words.size()) {
                return true;
            }
        }
        return false;
    }

    public Chart getChart() {
        return chart;
    }
}
