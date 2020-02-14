import java.awt.print.Book;

public class CmdListTableAllocations implements Command
{
    private Day day;
    DailyTableAssignmentController dTACont;
    @Override
    public void execute(String[] cmdParts)
    {
        this.day = new Day(cmdParts[1]);
        dTACont = TableAssignContAccessor.getDailyTableAssignmentController(this.day);
        dTACont.printAllAssignedTablesMsg();
        System.out.println("");
        dTACont.printAllAvailableTablesMsg();
        BookingOffice bo = BookingOffice.getInstance();
        System.out.println("");
        System.out.println( bo.genPendingRequestsMsgForADay(day) );
    }
}
