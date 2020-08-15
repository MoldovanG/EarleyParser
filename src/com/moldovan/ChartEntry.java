package com.moldovan;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ChartEntry {
    Set<EarleyState> earleyStateSet;

    public ChartEntry() {
        earleyStateSet = new LinkedHashSet<>();
    }

    public ChartEntry(Set<EarleyState> earleyStateSet) {
        this.earleyStateSet = earleyStateSet;
    }

    public void add(EarleyState earleyState){
        earleyStateSet.add(earleyState);
    }

    public Set<EarleyState> getEarleyStateSet() {
        return earleyStateSet;
    }

    @Override
    public String toString() {
        List<String> printableStates = new ArrayList<>();
        for (EarleyState state : earleyStateSet) {
            printableStates.add(state.toString());
        }
        return String.join("\n", printableStates);
    }
}
