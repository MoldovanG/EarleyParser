package com.moldovan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class Chart {
    private List<ChartEntry> chartEntryList;

    private Chart(List<ChartEntry> chartEntryList) {
        this.chartEntryList = chartEntryList;
    }

    public static Chart getChartInstance(int l) {
        List<ChartEntry> chartEntryList = new ArrayList<>();
        LinkedHashSet<EarleyState> initSet =  new LinkedHashSet<>();
        initSet.add(EarleyState.init());
        chartEntryList.add(new ChartEntry(initSet));
        for (int i =1;i<l;i++) {
            chartEntryList.add(new ChartEntry());
        }
        return new Chart(chartEntryList);
    }

    public ChartEntry getChartEntry(int i){
        return chartEntryList.get(i);
    }

    public int size(){
        return chartEntryList.size();
    }

    @Override
    public String toString() {
        List<String> printableRows = new ArrayList<>();
        for(int i =0; i < chartEntryList.size(); i++){
            printableRows.add("Chart["+String.valueOf(i)+"]:\n"+ chartEntryList.get(i).toString());
        }
        return String.join("\n\n", printableRows);
    }
}
