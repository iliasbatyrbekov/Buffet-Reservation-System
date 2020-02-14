import java.util.ArrayList;

public class CmdCancel extends RecordedCommand
{
    private Reservation r;
    private int ticket;
    private BookingOffice bo;
    private DailyTableAssignmentController dTACont;
    private ArrayList<Table> allRequestedTables;

    @Override
    public void execute(String[] cmdParts)
    {
        try
        {
            if (cmdParts.length < 3)
            {
                throw new ExInsufficientArgument();
            }
            bo = BookingOffice.getInstance();
            r = bo.findReservationByDateAndTicket(new Day(cmdParts[1]), Integer.parseInt(cmdParts[2]));
            if (r == null)
            {
                throw new ExBookingNotFound();
            }
            if (1 == SystemDate.getInstance().compareTo( new Day(cmdParts[1]))) //will be executed when new date is smaller than system date (already passed)
            {
                throw new ExDateHasAlreadyPassed();
            }
            if (r.getStatus() instanceof RStateTableAllocated){
                dTACont = TableAssignContAccessor.getDailyTableAssignmentController(r.getDateDine());
                allRequestedTables = ((RStateTableAllocated) r.getStatus()).getAllAssignedTables(); //down-casting because status is definitely "allocated"
                dTACont.undoRequestTables(allRequestedTables);
                bo.removeReservation(r);
            }
            else { //pending
                bo.removeReservation(r);
            }
            ticket = r.getTicketCode();
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        }
        catch (ExBookingNotFound e) {
        System.out.println(e.getMessage());
        }catch (ExInsufficientArgument e) {
            System.out.println(e.getMessage());
        }catch (NumberFormatException e){
            System.out.println("Wrong number format!");
        }catch (ExDateHasAlreadyPassed e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe()
    {
        try{
            if (r.getStatus() instanceof RStateTableAllocated){
                for (Table t:       //check if all Tables are still available
                        allRequestedTables) {
                    if (dTACont.findAvailableTable(t) == null)
                        throw new ExTableAlreadyReserved(t.getTableName());
                }
                dTACont.requestTables(allRequestedTables, ticket); //request if tables are still available
            }
            bo.addReservation(r);
            addRedoCommand(this);
        } catch (ExTableAlreadyReserved e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void redoMe()
    {
        if (r.getStatus() instanceof RStateTableAllocated){
            dTACont.undoRequestTables(allRequestedTables);
            bo.removeReservation(r);
        }
        else { //pending
            bo.removeReservation(r);
        }
        addUndoCommand(this);
    }
}
