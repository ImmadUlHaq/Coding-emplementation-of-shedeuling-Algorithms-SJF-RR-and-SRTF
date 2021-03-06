package Excel;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.*;


//Java program for implementation of RR scheduling 

public class Round_Robin  
{ 
 // Method to find the waiting time for all 
 // processes 
 static void findWaitingTime(double processes[], int n, 
              double[] bt, int[] wt, int quantum) 
 { 
     // Make a copy of burst times bt[] to store remaining 
     // burst times. 
     double rem_bt[] = new double[n]; 
     for (int i = 0 ; i < n ; i++) 
         rem_bt[i] =  bt[i]; 
    
     double t = 0; // Current time 
    
     // Keep traversing processes in round robin manner 
     // until all of them are not done. 
     while(true) 
     { 
         boolean done = true; 
    
         // Traverse all processes one by one repeatedly 
         for (int i = 0 ; i < n; i++) 
         { 
             // If burst time of a process is greater than 0 
             // then only need to process further 
             if (rem_bt[i] > 0) 
             { 
                 done = false; // There is a pending process 
    
                 if (rem_bt[i] > quantum) 
                 { 
                     // Increase the value of t i.e. shows 
                     // how much time a process has been processed 
                     t += quantum; 
    
                     // Decrease the burst_time of current process 
                     // by quantum 
                     rem_bt[i] -= quantum; 
                 } 
    
                 // If burst time is smaller than or equal to 
                 // quantum. Last cycle for this process 
                 else
                 { 
                     // Increase the value of t i.e. shows 
                     // how much time a process has been processed 
                     t = t + rem_bt[i]; 
    
                     // Waiting time is current time minus time 
                     // used by this process 
                     wt[i] = (int) (t - bt[i]); 
    
                     // As the process gets fully executed 
                     // make its remaining burst time = 0 
                     rem_bt[i] = 0; 
                 } 
             } 
         } 
    
         // If all processes are done 
         if (done == true) 
           break; 
     } 
 } 
    
 // Method to calculate turn around time 
 static void findTurnAroundTime(double[] pid, int n, 
                         double[] bt, int wt[], int tat[]) 
 { 
     // calculating turnaround time by adding 
     // bt[i] + wt[i] 
     for (int i = 0; i < n ; i++) 
         tat[i] = (int) (bt[i] + wt[i]); 
 } 
    
 // Method to calculate average time 
 static void findavgTime(double[] pid, int n, double[] bt, 
                                      int quantum) 
 { 
     int wt[] = new int[n], tat[] = new int[n]; 
     int total_wt = 0, total_tat = 0; 
    
     // Function to find waiting time of all processes 
     findWaitingTime(pid, n, bt, wt, quantum); 
    
     // Function to find turn around time for all processes 
     findTurnAroundTime(pid, n, bt, wt, tat); 
    
     // Display processes along with all details 
     System.out.println("Processes " + " Burst time " + 
                   " Waiting time " + " Turn around time"); 
    
     // Calculate total waiting time and total turn 
     // around time 
     for (int i=0; i<n; i++) 
     { 
         total_wt = total_wt + wt[i]; 
         total_tat = total_tat + tat[i]; 
         System.out.println(" " + (i+1) + "\t\t" + bt[i] +"\t " + 
                           wt[i] +"\t\t " + tat[i]); 
     } 
     System.out.println("In this Shedeuling CPU usage is 100 % ");
     System.out.println("Average waiting time = " + 
                       (float)total_wt / (float)n); 
     System.out.println("Average turn around time = " + 
                        (float)total_tat / (float)n); 
 }
   
 // Driver Method 
 public static void main(String[] args) throws IOException 
 { 
	 
	 int numberOfProcesses = 6;  //we have total 6 processes

	   double pid[] = new double[numberOfProcesses];
	   double bt[] = new double[numberOfProcesses];
	   double ar[] = new double[numberOfProcesses];
	   double ct[] = new double[numberOfProcesses];
	   double ta[] = new double[numberOfProcesses];
	   double wt[] = new double[numberOfProcesses];
	   double resp_time[] = new double[numberOfProcesses];
	
	
	//This part of the code is just for reading from file 
	String excelFilePath=".\\datafile\\Input.1 RR Q=2.xlsx"; //path of data file for Round Robin 
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

	
    
     // Time quantum 
     int quantum = 2; 
     findavgTime(pid, numberOfProcesses , bt, quantum); 
 } 
} 