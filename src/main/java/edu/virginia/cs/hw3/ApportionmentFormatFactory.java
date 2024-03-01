package edu.virginia.cs.hw3;

public class ApportionmentFormatFactory {
    ApportionmentFormat getFormat(String formatName){
        if(formatName.equals("alpha")){
            return new AlphabeticalApportionmentFormat();
        }
        else if(formatName.equals("benefit")){
            return new RelativeBenefitFormat();
        }
        else{
            throw new IllegalArgumentException("Error: invalid format type. The system currently supports:\n" +
                    "\talpha, benefit");
        }

    }
}
