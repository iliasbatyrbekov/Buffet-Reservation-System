import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CmdAssignTable extends RecordedCommand
{
    private BookingOffice bo;
    private Day targetDate;
    private int targetTicketCode;
    private ArrayList<Table> allRequestedTables;
    private Reservation r;
    private DailyTableAssignmentController dTACont;

    @Override
    public void execute(String[] cmdParts)
    {
        try {
            if (cmdParts.length < 4){
                throw new ExInsufficientArgument();
            }
            if (1 == SystemDate.getInstance().compareTo( new Day(cmdParts[1]))) //will be executed when new date is smaller than system date (already passed)
            {
                throw new ExDateHasAlreadyPassed();
            }
            targetDate = new Day(cmdParts[1]);
            targetTicketCode = Integer.parseInt(cmdParts[2]);
            bo = BookingOffice.getInstance();
            this.r = bo.findReservationByDateAndTicket(targetDate, targetTicketCode);
            if (r == null)
            {
                throw new ExBookingNotFound();
            }
            if (r.getStatus() instanceof RStateTableAllocated)
            {
                throw new ExTableAlreadyAssignedForThisBooking();
            }
            if (!r.isNumOfSeatsEnough(Table.maxCapacityOfTables(Arrays.copyOfRange(cmdParts,3, cmdParts.length)) ))
            {
                throw new ExNotEnoughSeatsForBooking();
            }
            /*
            *Request table allocator only if booking is found, tables have not beet allocated yet
            */
            dTACont = TableAssignContAccessor.getDailyTableAssignmentController(targetDate);
            allRequestedTables = dTACont.requestTablesByNames(cmdParts, targetTicketCode);
            //update status
            r.setStatus(new RStateTableAllocated(allRequestedTables));

            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        }catch (NumberFormatException e){
            System.out.println("Wrong number format!");
        } catch (ExBookingNotFound e) {
            System.out.println(e.getMessage());
        }catch (ExInsufficientArgument e) {
            System.out.println(e.getMessage());
        } catch (ExTableAlreadyAssignedForThisBooking e) {
            System.out.println(e.getMessage());
        }catch (ExDateHasAlreadyPassed e) {
            System.out.println(e.getMessage());
        } catch (ExTableAlreadyReserved e){
            System.out.println(e.getMessage());
        } catch (ExNotEnoughSeatsForBooking e) {
            System.out.println(e.getMessage());
        } catch (ExTableNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe()
    {
        dTACont.undoRequestTables(allRequestedTables);
        r.setStatus( new RStatePending());
        addRedoCommand(this);
    }

    @Override
    public void redoMe()
    {
        dTACont.requestTables(allRequestedTables, targetTicketCode);
        r.setStatus(new RStateTableAllocated(allRequestedTables));
        addUndoCommand(this);
    }

}
