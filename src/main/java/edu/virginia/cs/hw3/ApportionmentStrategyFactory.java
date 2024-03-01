package edu.virginia.cs.hw3;

public class ApportionmentStrategyFactory {
    ApportionmentStrategy getStrategy(String algorithmName){
        if(algorithmName.equals("hamilton")){
            return new HamiltonApportionmentStrategy();
        }
        else if(algorithmName.equals("jefferson")){
            return new JeffersonApportionmentStrategy();
        }
        else if(algorithmName.equals("huntington")){
            return new HuntingtonHillApportionmentStrategy();
        }
        else{
            throw new IllegalArgumentException("Error: invalid algorithm type. The system currently supports:\n" +
                    "\thamilton, jefferson, huntington");
        }
    }
}
