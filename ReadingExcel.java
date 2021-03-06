package Excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.*;

public class ReadingExcel {

	public static void main(String[] args) throws IOException {
		
		   int numberOfProcesses = 6;  //we have total 6 processes

		   double pid[] = new double[numberOfProcesses];
		   double bt[] = new double[numberOfProcesses];
		   double ar[] = new double[numberOfProcesses];
		   double ct[] = new double[numberOfProcesses];
		   double ta[] = new double[numberOfProcesses];
		   double wt[] = new double[numberOfProcesses];
		   double resp_time[] = new double[numberOfProcesses];
		
		
		//This part of the code is just for reading from file 
		String excelFilePath=".\\datafile\\proj2 input.1 fcfs.xlsx"; //path of data file for fcfs 
		FileInputStream inputstream=new FileInputStream(excelFilePath); 
		
		XSSFWorkbook workbook=new XSSFWorkbook(inputstream);
		XSSFSheet sheet=workbook.getSheetAt(0);	//XSSFSheet sheet=workbook.getSheet("Sheet1");
		
		System.out.println("P.id | A.T | B.T\n");
		
		
		////  USING FOR LOOP
		int rows=sheet.getLastRowNum();         //sheet no of rows = no of processes = 6
		int cols=sheet.getRow(1).getLastCellNum(); // no of column
		
		for(int r=0;r<=rows;r++)
		{
			XSSFRow row=sheet.getRow(r); //0
			
			for(int c=0;c<cols;c++)
			{	
				XSSFCell cell=row.getCell(c);   
				
				switch(cell.getCellType())
				{
				case STRING: System.out.print(cell.getStringCellValue()); break;
				case NUMERIC: {System.out.print(cell.getNumericCellValue());
				if(c == 0) {
					pid[r] = cell.getNumericCellValue();
				}
				if(c == 1) {
					ar[r] = cell.getNumericCellValue();
				}
				
				if(c == 2) {
					bt[r] = cell.getNumericCellValue();
				}
				
				break;}
				case BOOLEAN: System.out.print(cell.getBooleanCellValue()); break;
				}
				System.out.print(" | ");  // this is for printing space between each data
			}
			System.out.println();
		} 
		
		
	

		//this part is use for swap arrival time and pid of processors
        double temp;
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = i+1; j < numberOfProcesses; j++) {

                if(ar[i] > ar[j]) {
                    temp = ar[i];
                    ar[i] = ar[j];
                    ar[j] = temp;

                    temp = pid[i];
                    pid[i] = pid[j];
                    pid[j] = temp;
                    temp = bt[i];
                    bt[i] = bt[j];
                    bt[j] = temp;
                }
            }
        }

        double total_CT =0, total_WT =0, total_TAT = 0, total_resp_time=0, total_BT = 0;
        
        System.out.println();
        ct[0] = bt[0] + ar[0];  // Formulas
        for(int i = 1; i < numberOfProcesses; i++) {
            ct[i] = ct[i - 1] + bt[i];
        }
        for(int i = 0; i < numberOfProcesses; i++) {
            ta[i] = ct[i] - ar[i];
            wt[i] = ta[i] - bt[i];
            resp_time[i] = wt[i];
        }
        System.out.println("Process\t\t\tAT\t\tBT\t\tCT\t\tTAT\t\tWT\n");
        for(int i = 0; i < numberOfProcesses; i++) {
            System.out.println(pid[i]+"\t\t\t" + ar[i] + "\t\t" + bt[i]+ "\t\t" + ct[i]+ "\t\t" + ta[i]+ "\t\t" + wt[i]);
        }

        System.out.println("gantt chart: ");
        for(int i = 0; i < numberOfProcesses; i++) {
            System.out.print("P" + pid[i] +" ");
        }
        System.out.println();
        
        for(int i = 0; i < numberOfProcesses; i++) {
        	total_WT = total_WT + wt[i];
        	total_resp_time = total_resp_time + resp_time[i] ;
        	total_TAT = total_TAT + ta[i];
        }
        System.out.println("Avarage waiting time    :  "+total_WT/6);
        System.out.println("Avarage response time   :  "+total_resp_time/6);
        System.out.println("Avarage turnaround time :  "+total_TAT/6);
        
		
	}

}
