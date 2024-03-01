package edu.virginia.cs.hw3;

import java.util.List;

public class JeffersonApportionmentStrategy extends ApportionmentStrategy{
    private List<State> stateList;
    private int targetRepresentatives;
    private double divisor;
    private DecimalApportionment decimalApportionment;
    private Apportionment apportionment;


    @Override
    public Apportionment getApportionment(List<State> stateList, int representatives) {
        initializeFields(stateList, representatives);
        divisor = getDivisor();
        apportionment = defaultapportionment();
        while(apportionment.getAllocatedRepresentatives() != targetRepresentatives){
            decimalApportionment = getDecimalApportionment();
            apportionment = getrounddownapportionment();
            if(apportionment.getAllocatedRepresentatives() < targetRepresentatives){
                divisor = divisor - 1000;
            }
            else{
                divisor = divisor + 1000;
            }
        }
        return apportionment;
    }

    private void initializeFields(List<State> stateList, int representatives) {
        this.stateList = stateList;
        targetRepresentatives = representatives;
    }

    private DecimalApportionment getDecimalApportionment() {
        DecimalApportionment decimalApportionment = new DecimalApportionment();
        for (State state : stateList) {
            double decimalRepresentatives = state.getPopulation() / divisor;
            decimalApportionment.setDecimalApportionmentForState(state, decimalRepresentatives);
        }
        return decimalApportionment;
    }

    private Apportionment getrounddownapportionment() {
        return decimalApportionment.getRoundedDownApportionment();
    }


    private Apportionment defaultapportionment(){
        Apportionment defaultapportionment = new Apportionment();
        for(State state : stateList){
            defaultapportionment.addRepresentativesToState(state, 0);
        }
        return defaultapportionment;
    }

    public double getDivisor() {
        int totalPopulation = getTotalPopulation();
        return (double) totalPopulation / targetRepresentatives;
    }

    public int getTotalPopulation() {
        int totalPopulation = 0;
        for (State state : stateList) {
            totalPopulation += state.getPopulation();
        }
        return totalPopulation;
    }

}
