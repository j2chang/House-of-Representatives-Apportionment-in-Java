package edu.virginia.cs.hw3;

import java.util.*;
import java.io.*;

public class ArgumentsHandler {

    public static final int FILENAME_INDEX = 0;
    public static final int REPRESENTATIVES_INDEX = 1;
    private final List<String> arguments;
    private Configuration config;

    public ArgumentsHandler(List<String> arguments) {
        if (arguments.size() < 1) {
            throw new IllegalArgumentException("Error: No arguments were included at runtime. Arguments expected\n" +
                    "statePopulationFilename [number of representatives] [--hamilton]");
        }
        this.arguments = arguments;
    }

    public ArgumentsHandler(String[] args) {
        this(Arrays.asList(args));
    }

    public Configuration getConfiguration() {
        setDefaultConfiguration();
//        configureStateReader();
        checkforstatereader();
        checkForRepresentativeCount();
        checkforformat();
        checkforstrategy();
        return config;
    }

    private void setDefaultConfiguration() {
        config = new Configuration();
        config.setApportionmentStrategy(new HamiltonApportionmentStrategy());
        config.setRepresentatives(435);
        config.setApportionmentFormat(new AlphabeticalApportionmentFormat());
    }

    private void configureStateReader() {
        String filename = arguments.get(FILENAME_INDEX);
        setStateReaderFromFilename(filename);
    }


//    private void checkForRepresentativeCount() {
//        if (arguments.size() < 2) {
//            return;
//        }
//        try {
//            int representativeCount = Integer.parseInt(arguments.get(REPRESENTATIVES_INDEX));
//            if (representativeCount <= 0) {
//                throw new IllegalArgumentException("Error: Invalid representative count : " + representativeCount + " - number must be positive");
//            }
//            config.setRepresentatives(representativeCount);
//        } catch (NumberFormatException ignored) {
//        }
//    }
    private String getvalue(String item){
        int index = arguments.indexOf(item);
        String value = arguments.get(index + 1);
        return value;
    }

    private void checkforstatereader(){
        if(arguments.size() < 1){
            throw new IllegalArgumentException("Error: no file was given");
        }
        String filename = arguments.get(0);
        StateReaderFactory factory = new StateReaderFactory();
        StateReader reader;
        if(filenameExists(filename)){
            reader = factory.getStateReader(filename);
            config.setStateReader(reader);
        }
    }

    private void checkForRepresentativeCount(){
        if(arguments.size() < 2){
            return;
        }
        try{
            String stringreps;
            if(arguments.size() <= 7){
                if(arguments.contains("--reps")){
                    stringreps = getvalue("--reps");
                }
                else if(arguments.contains("-r")){
                    stringreps = getvalue("-r");
                }
                else if(arguments.get(1).length() > 0 && arguments.get(1).length() < 5){
                    int index = arguments.get(1).indexOf("r");
                    stringreps = arguments.get(index + 1);
                }
                else{
                    return;
                }
                int representativecount = Integer.parseInt(stringreps);
                if(representativecount <= 0){
                    throw new IllegalArgumentException("Error: Invalid representative count : " + representativecount + " - number must be positive");
                }
                config.setRepresentatives(representativecount);
            }
            else{
                throw new IllegalArgumentException("Error: invalid flag type");
            }

        }
        catch (NumberFormatException ignored){
        }
    }

    private void checkforformat(){
        if(arguments.size() < 2){
            return;
        }
        String stringformat;
        ApportionmentFormatFactory factory = new ApportionmentFormatFactory();
        ApportionmentFormat format;
        if(arguments.size() <= 7){
            if(arguments.contains("--format")){
                stringformat = getvalue("--format");
                format = factory.getFormat(stringformat);
            }
            else if(arguments.contains("-f")){
                stringformat = getvalue("-f");
                format = factory.getFormat(stringformat);
            }
            else if(arguments.get(1).length() > 0 && arguments.get(1).length() < 5){
                int index = arguments.get(1).indexOf("f");
                stringformat = arguments.get(index + 1);
                format = factory.getFormat(stringformat);
            }
            else{
                return;
            }
            config.setApportionmentFormat(format);
        }
        else{
            throw new IllegalArgumentException("Error: invalid flag type");
        }
    }

    private void checkforstrategy(){
        if(arguments.size() < 2){
            return;
        }
        String stringstrat;
        ApportionmentStrategyFactory factory = new ApportionmentStrategyFactory();
        ApportionmentStrategy strat;
        if(arguments.size() <= 7){
            if(arguments.contains("--algorithm")){
                stringstrat = getvalue("--algorithm");
                strat = factory.getStrategy(stringstrat);
            }
            else if(arguments.contains("-a")){
                stringstrat = getvalue("-a");
                strat = factory.getStrategy(stringstrat);
            }
            else if(arguments.get(1).length() > 0 && arguments.get(1).length() < 5){
                int index = arguments.get(1).indexOf("a");
                stringstrat = arguments.get(index + 1);
                strat = factory.getStrategy(stringstrat);
            }
            else{
                return;
            }
            config.setApportionmentStrategy(strat);
        }
        else{
            throw new IllegalArgumentException("Error: invalid flag type");
        }
    }

    private void setStateReaderFromFilename(String filename) {
        if (filename.toLowerCase().endsWith(".csv")) {
            setConfigurationToCSVReader(filename);
        } else if (filename.toLowerCase().endsWith(".xlsx")) {
            setConfigurationToXLSXReader(filename);
        } else {
            throw new IllegalArgumentException("Error: invalid file type. The system currently supports:\n" +
                    "\t.csv, .xlsx");
        }
    }

    private boolean filenameExists(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    private void setConfigurationToCSVReader(String filename) {
        config.setStateReader(new CSVStateReader(filename));
    }

    private void setConfigurationToXLSXReader(String filename) {
        config.setStateReader(new ExcelStateReader(filename));
    }
}
