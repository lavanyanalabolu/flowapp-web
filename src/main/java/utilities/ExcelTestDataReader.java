package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelTestDataReader {
	
	public static int cellNumber=0;
	public static int rowNumber=0;
	public static  boolean colFlag;
	public static  boolean rowFlag;
	public LinkedList<Object[]> getRowDataMap(String inputFile, String sheetName) {
		int count = 0;
		Sheet sheet = null;
		Map<String, String> rowdatamap = null;
		FileInputStream fis = null;
		Workbook workbook = null;
		
		final LinkedList<Object[]> dataBeans = new LinkedList<Object[]>();

		// Creating index map
		Map<String, Integer> index = new HashMap<String, Integer>();

		try {
			fis = new FileInputStream(inputFile);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			workbook.close();

			// mapping column index with column name.
			Row firstRow = sheet.getRow(1);
			for (Cell cell : firstRow) {
				index.put(cell.getStringCellValue(), count);
				count++;
			}

			//get total rows count present in sheet
			int rowCount = sheet.getLastRowNum();
			//running for loop for each excel row and storing values in map
			for (int iCounter = 2; iCounter <= rowCount; iCounter++) {
				//initialize excel row map
				rowdatamap = new HashMap<String, String>();
				Row rowData = sheet.getRow(iCounter);
				for (String key : index.keySet()) {
					int columnnum = (Integer) index.get(key);
              					if(rowData.getCell(columnnum)==null){
						rowdatamap.put(key,null);
					}
					else{
						
						String cellValue = rowData.getCell(columnnum).toString();
						if(cellValue.contains("UNIQUE")){
							cellValue = getUNiqueData(cellValue);
						}
						rowdatamap.put(key,cellValue );
					}
					
				}
				dataBeans.add(new Object[] { rowdatamap });
				break;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataBeans;

	}
	public static String FetchDatfromexcel(String sheetname, String testcaseid, String columnName)
	{
		String cellValue = null; HSSFRow row;
		try{
			FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+ConfigReader.getValue("dataDriver"));
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheet(sheetname);
			for(int y=0;y<worksheet.getRow(0).getLastCellNum();y++)
			{
			 HSSFCell cellA2 = worksheet.getRow(0).getCell(y);
			 cellA2.setCellType(cellA2.CELL_TYPE_STRING);
			 String getCellName= cellA2.getStringCellValue();
			 if(getCellName.contains(columnName))
			 {
				 cellNumber=cellA2.getColumnIndex();
				 colFlag=true;
				 break;
			 }
			 }
			 if(!colFlag)
			 {
				 System.out.println("Column is not present in datasheet:- "+columnName);
				 throw new Exception();
			 }
		
			 for(int i=0;i<=worksheet.getLastRowNum();i++)
			 {
				 HSSFRow row1= worksheet.getRow(i);
				 String temp=row1.getCell(0).getStringCellValue();
				
				 if((temp).equalsIgnoreCase(testcaseid))
				 {
					 rowNumber = i;
					 rowFlag=true;
					 break;
					 }
			 }
			 if(!rowFlag)
			 {
				 System.out.println("testcaseid is not present in datasheet:- "+sheetname);
				 throw new Exception();
			 }
			 row=worksheet.getRow(rowNumber);
			 cellValue=row.getCell(cellNumber).getStringCellValue();
					}
		catch(Exception e)
		{
			System.out.println("Could not read the Excel sheet or could not found the testcaseid/coulmname");
			e.printStackTrace();
		}
		return cellValue;
	}
	
	public String getUNiqueData(String cellValue){
		String randomValue =  System.currentTimeMillis()+"";
		return cellValue.replace("UNIQUE",randomValue);
	}
}

