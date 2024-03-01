package edu.virginia.cs.hw3;

import java.util.*;
import com.google.common.primitives.Doubles;


public class HuntingtonHillApportionmentStrategy extends ApportionmentStrategy {

    private List<State> stateList;
    private int targetRepresentatives;
    private Apportionment apportionment;


    @Override
    public Apportionment getApportionment(List<State> stateList, int representatives) {

        //TODO: Unimplemented stub
        initializeFields(stateList, representatives);
        apportionment = addonereptoallstates();
        apportionment = addtohighestpriority();
        return apportionment;
    }

    private void initializeFields(List<State> stateList, int representatives) {
        this.stateList = stateList;
        targetRepresentatives = representatives;
    }

    private Apportionment addonereptoallstates(){
        Apportionment onetoallapp = new Apportionment();
        for(State state : stateList){
            onetoallapp.addRepresentativesToState(state, 1);
            targetRepresentatives--;
        }
        return onetoallapp;
    }

    private Apportionment addtohighestpriority(){
        Map<State, Double> prioritymap = new HashMap<>();
        while(targetRepresentatives != 0){
            for(State state: stateList){
                prioritymap.put(state, state.getPopulation() / (Math.sqrt(apportionment.getRepresentativesForState(state) * (apportionment.getRepresentativesForState(state) + 1))));
            }
            double highestpriority = (Collections.max(prioritymap.values()));

            for(Map.Entry<State, Double> entry : prioritymap.entrySet()){
                if(entry.getValue() == highestpriority){
                    apportionment.addRepresentativesToState(entry.getKey(), 1);
                    targetRepresentatives--;
                }
            }
        }
        return apportionment;
    }
}