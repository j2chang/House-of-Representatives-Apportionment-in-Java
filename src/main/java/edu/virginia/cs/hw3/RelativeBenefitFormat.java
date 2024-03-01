package edu.virginia.cs.hw3;

import org.apache.poi.hpsf.Decimal;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelativeBenefitFormat extends ApportionmentFormat{
    private double divisor;
    private DecimalApportionment decimalApportionment;
    private Apportionment apportionment;

    @Override
    public String getApportionmentString(Apportionment apportionment) {
        this.apportionment = apportionment;
        divisor = getDivisor();
        HashMap<State,Double> benefit = getbenefit();
        return getorderedbenefit(benefit);
    }
    private String getorderedbenefit(HashMap<State,Double> benefit) {
        return benefit.entrySet().stream()
                .sorted(Map.Entry.<State,Double>comparingByValue().reversed())
                .map(e -> getbenefitstring(e.getKey()))
                .collect(Collectors.joining("\n"));
    }

    private String getbenefitstring(State state){
        HashMap<State,Double> benefit = getbenefit();
        DecimalFormat form = new DecimalFormat("#.###");
        String statename = state.getName();
        double benefitnum = benefit.get(state);
        int apportionedreps = apportionment.getRepresentativesForState(state);
        if(benefitnum >= 0){
            return statename + " - " + apportionedreps + " - " + "+" + form.format(benefitnum);
        }
        return statename + " - " + apportionedreps + " - " + form.format(benefitnum);
    }

    private HashMap<State,Double> getbenefit(){
        decimalApportionment = getDecimalApportionment();
        HashMap<State, Double> benefit = new HashMap<>();
        for (State state : decimalApportionment.decimalApportionmentMap.keySet()) {
            double benefitvalue = apportionment.getRepresentativesForState(state) - decimalApportionment.decimalApportionmentMap.get(state);
            benefit.put(state, benefitvalue);
        }
        return benefit;
    }

    private DecimalApportionment getDecimalApportionment() {
        DecimalApportionment decimalApportionment = new DecimalApportionment();
        for (State state : apportionment.getStateSet()) {
            double decimalRepresentatives = state.getPopulation() / divisor;
            decimalApportionment.setDecimalApportionmentForState(state, decimalRepresentatives);
        }
        return decimalApportionment;
    }
    private double getDivisor() {
        int totalPopulation = getTotalPopulation();
        return (double) totalPopulation / apportionment.getAllocatedRepresentatives();
    }

    private int getTotalPopulation() {
        int totalPopulation = 0;
        for (State state : apportionment.getStateSet()) {
            totalPopulation += state.getPopulation();
        }
        return totalPopulation;
    }
}
