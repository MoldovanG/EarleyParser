package com.moldovan;

import java.util.List;
import java.util.Objects;

public class Rule extends Object {
    private String lhs;
    private List<String> rhs;

    public Rule(String lhs, List<String> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rule){
            return this.lhs.equals(((Rule) obj).lhs) && this.rhs.equals(((Rule) obj).rhs);
        }
        return false;
    }

    public String getLhs() {
        return lhs;
    }

    public void setLhs(String lhs) {
        this.lhs = lhs;
    }

    public List<String> getRhs() {
        return rhs;
    }

    public void setRhs(List<String> rhs) {
        this.rhs = rhs;
    }

    public int getRhsLength() {
        return rhs.size();
    }

    public String getRhsElement(int dot_pos) {
        return rhs.get(dot_pos);
    }
    public Boolean contains(String word) {
        return rhs.contains(word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lhs,rhs);
    }

    public boolean is_lambda() {
        return rhs.contains("\\");
    }
}
