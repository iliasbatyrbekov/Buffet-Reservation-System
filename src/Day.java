import java.security.PublicKey;
import java.util.ArrayList;

public class Day implements Cloneable, Comparable<Day>{
	
	private int year;
	private int month;
	private int day;
	private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
	//private DailyTableAssignmentController assignmentController;
	
	//Constructor
	public Day(int y, int m, int d) {
		this.year=y;
		this.month=m;
		this.day=d;		
	}
	
	// check if a given year is a leap year
	static public boolean isLeapYear(int y)
	{
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	static public boolean valid(int y, int m, int d)
	{
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}

	public void set(String sDay) //Set year,month,day based on a string like 01-Mar-2019
	{		
		String[] sDayParts = sDay.split("-");
		this.day = Integer.parseInt(sDayParts[0]); //Apply Integer.parseInt for sDayParts[0];
		this.year = Integer.parseInt(sDayParts[2]); 
		this.month = MonthNames.indexOf(sDayParts[1])/3 + 1;
//		this.assignmentController = new DailyTableAssignmentController();
	}

	public Day(String sDay) { set(sDay); } //Constructor, simply call set(sDay)

	@Override
	public String toString() 
	{		
		return day+"-"+ MonthNames.substring((month-1)*3,(month)*3) + "-"+ year; // (month-1)*3,(month)*3
	}

	@Override
	public Day clone() 
	{
		Day copy= null;
		try {
			copy = (Day) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copy;
	}

	@Override
	public int hashCode()
	{
		return year*10000 + month*100 + day;
	}

	@Override
	public boolean equals(Object anotherObject) {
		if (anotherObject ==null)
			return false;
		if (this.getClass() != anotherObject.getClass())
			return false;

		Day anotherDay = (Day) anotherObject;

		return (this.year == anotherDay.year && this.month == anotherDay.month && this.day == anotherDay.day);
	}

	@Override
	public int compareTo(Day anotherDay)
	{
		//in comparison year is most important, month is less important, day is least important
		int comparableIntForThisDay = this.year*10000 + this.month*100 + this.day;
		int comparableIntForAnotherDay = anotherDay.year*10000 + anotherDay.month*100 + anotherDay.day;
		if (comparableIntForThisDay == comparableIntForAnotherDay)
			return 0;
		else if(comparableIntForThisDay > comparableIntForAnotherDay)
			return 1;
		else
			return -1;
	}

//	public ArrayList<Table> ReserveTablesByNames(String[] cmdParts)
//	{
//		return this.assignmentController.requestTablesByNames(cmdParts);
//	}
//	public ArrayList<Table> ReserveTables(ArrayList<Table> tables)
//	{
//		return this.assignmentController.requestTables(tables);
//	}
//	public void undoReserveTables(ArrayList<Table> tables)
//	{
//		this.assignmentController.undoRequestTables(tables);
//	}

}