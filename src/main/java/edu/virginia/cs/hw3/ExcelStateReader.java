package edu.virginia.cs.hw3;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;

public class ExcelStateReader extends StateReader{
    private String filename;

    public ExcelStateReader(String filename) {
        if (!filename.toLowerCase().endsWith(".xlsx")) {
            throw new IllegalArgumentException("Error: cannot open non xlsx file " + filename);
        }
        this.filename = filename;
    }

    @Override
    public void readStates() {
        //TODO: unimplemented stub
        //https://www.java67.com/2014/09/how-to-read-write-xlsx-file-in-java-apache-poi-example.html
        //This link guided me on how I should be reading values from xlsx files
        //it gave me a general structure of what my code should look like
        try{
            FileInputStream inputStream = new FileInputStream(filename);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet worksheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = worksheet.iterator();
            if(!rowIterator.hasNext()){
                throw new RuntimeException("Empty spreadsheet");
            }
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                if(row.getRowNum() == 0){
                    continue;
                }
                try{
                    String state2 = row.getCell(0).getStringCellValue();
                    int population2 = new BigDecimal(String.valueOf(row.getCell(1).getNumericCellValue())).intValue();
                    if(state2 == ""){
                        continue;
                    }
                    else{
                        if(population2 < 1){
                            continue;
                        }
                    }
                    State newstate = new State(state2, population2);
                    stateList.add(newstate);
                }
                catch(NumberFormatException e){
                    continue;
                }
            }
        }
        catch(FileNotFoundException e){
            throw new RuntimeException("Error file not found");
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
