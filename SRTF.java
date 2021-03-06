package Excel;

import java.io.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class SRTF {

	
	public static void main(String args[]) throws IOException
 {
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      
  
  
  
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
  
  
  
  
  
  
  
  
  
  	int n = numberOfProcesses;
  	
       int proc[][] = new int[n + 1][4];//proc[][0] is the AT array,[][1] - BT,[][2] - WT,[][3] - TT
       for(int i = 1; i <= n; i++)
       {
    	   proc[i][0] = (int)ar[i-1];
      
    	   proc[i][1] = (int)bt[i-1];
       }
       System.out.println();
     
       //Calculation of Total Time and Initialization of Time Chart array
     int total_time = 0;
     for(int i = 1; i <= n; i++)
     {
      total_time += proc[i][1];
     }
     int time_chart[] = new int[total_time];
     
     for(int i = 0; i < total_time; i++)
     {
      //Selection of shortest process which has arrived
      int sel_proc = 0;
      int min = 99999;
      for(int j = 1; j <= n; j++)
      {
       if(proc[j][0] <= i)//Condition to check if Process has arrived
       {
        if(proc[j][1] < min && proc[j][1] != 0)
        {
         min = proc[j][1];
         sel_proc = j;
        }
       }
      }
      
      //Assign selected process to current time in the Chart
      time_chart[i] = sel_proc;
      
      //Decrement Remaining Time of selected process by 1 since it has been assigned the CPU for 1 unit of time
      proc[sel_proc][1]--;
      
      //WT and TT Calculation
      for(int j = 1; j <= n; j++)
      {
       if(proc[j][0] <= i)
       {
        if(proc[j][1] != 0)
        {
         proc[j][3]++;//If process has arrived and it has not already completed execution its TT is incremented by 1
            if(j != sel_proc)//If the process has not been currently assigned the CPU and has arrived its WT is incremented by 1
             proc[j][2]++;
        }
        else if(j == sel_proc)//This is a special case in which the process has been assigned CPU and has completed its execution
         proc[j][3]++;
       }
      }
      
      //Printing the Time Chart
      if(i != 0)
      {
       if(sel_proc != time_chart[i - 1])
        //If the CPU has been assigned to a different Process we need to print the current value of time and the name of 
        //the new Process
       {
        System.out.print("--" + i + "--P" + sel_proc);
       }
      }
      else//If the current time is 0 i.e the printing has just started we need to print the name of the First selected Process
       System.out.print(i + "--P" + sel_proc);
      if(i == total_time - 1)//All the process names have been printed now we have to print the time at which execution ends
       System.out.print("--" + (i + 1));
      
     }
     System.out.println();
     System.out.println();
     
     //Printing the WT and TT for each Process
     System.out.println("P\t WT \t TT ");
     for(int i = 1; i <= n; i++)
     {
      System.out.printf("%d\t%2dms\t%2dms",i,proc[i][2],proc[i][3]);
      System.out.println();
     }
     
     System.out.println();
     
     //Printing the average WT & TT
     float WT = 0,TT = 0;
     for(int i = 1; i <= n; i++)
     {
      WT += proc[i][2];
      TT += proc[i][3];
     }
     WT /= n;
     TT /= n;
     
     System.out.println("The Average WT is: " + WT + "ms");
     System.out.println("The Average TT is: " + TT + "ms");
     System.out.println("The Avarage cpu usage is 100 % for this"); 
 }
    
}