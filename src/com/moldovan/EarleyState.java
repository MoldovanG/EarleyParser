package com.moldovan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EarleyState {
    public static String GAM = "GAM";
    private Rule rule;
    private int dot_pos;
    private int sentence_pos;
    private int chart_pos;
    private String comment;

    public EarleyState(Rule rule, int dot_pos, int sentence_pos, int chart_pos, String comment) {
        this.rule = rule;
        this.dot_pos = dot_pos;
        this.sentence_pos = sentence_pos;
        this.chart_pos = chart_pos;
        this.comment = comment;
    }

    public static EarleyState init (){
        List<String> init_rhs = new ArrayList<String>();
        init_rhs.add("S");
        return new EarleyState(new Rule(EarleyState.GAM,init_rhs),0 , 0, 0, "Starea de initializare");
    }

    public String nextCharacterToParse(){
        if (this.dot_pos < rule.getRhsLength())
            return rule.getRhsElement(dot_pos);
        else{
            throw new RuntimeException("No elements left for parsing");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  EarleyState) {
            return (this.rule.equals(((EarleyState) obj).getRule())
                    && this.dot_pos == ((EarleyState) obj).getDot_pos()
                    && this.sentence_pos == ((EarleyState) obj).getSentence_pos());
        }
        return false;
    }

    public Boolean isComplete(){
        if (rule.is_lambda()) {
            return true;
        }
        return dot_pos == rule.getRhsLength();
    }

    public Rule getRule() {
        return rule;
    }

    public int getDot_pos() {
        return dot_pos;
    }

    public int getSentence_pos() {
        return sentence_pos;
    }

    public int getChart_pos() {
        return chart_pos;
    }

    public String toStringWithoutComment() {
        return "(" + rule.getLhs() + "->" + String.join(" ", rule.getRhs().subList(0,dot_pos) )
                + "*" + String.join(" ", rule.getRhs().subList(dot_pos,rule.getRhsLength()))
                + ", ["+ sentence_pos + ", " + chart_pos + "])";
    }

    @Override
    public String toString() {
        return "(" + rule.getLhs() + "->" + String.join(" ", rule.getRhs().subList(0,dot_pos) )
                + "*" + String.join(" ", rule.getRhs().subList(dot_pos,rule.getRhsLength()))
        + ", ["+ sentence_pos + ", " + chart_pos + "])    " + comment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rule,dot_pos,sentence_pos,chart_pos);
    }
}
