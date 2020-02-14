public class CmdRequest extends RecordedCommand {

    private Reservation r;
    private BookingOffice bo;

    @Override
    public void execute(String[] cmdParts)
    {
        try {
            if (cmdParts.length < 5)
            {
                throw new ExInsufficientArgument();
            }
            if (1 == SystemDate.getInstance().compareTo( new Day(cmdParts[4]))) //will be executed when new date is smaller than system date (already passed)
            {
                throw new ExDateHasAlreadyPassed();
            }
            if (BookingOffice.getInstance().findReservation(cmdParts) != null)
            {
                throw new ExReservationAlreadyExists();
            }
            bo = BookingOffice.getInstance();
            r = bo.addReservation(cmdParts[1], cmdParts[2], Integer.parseInt(cmdParts[3]), cmdParts[4]);
            System.out.println(String.format("Done. Ticket code for %s: %d", cmdParts[4], r.getTicketCode() ));
            addUndoCommand(this);
            clearRedoList();
        }
        catch (ExInsufficientArgument e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e){
            System.out.println("Wrong number format!");
        } catch (ExDateHasAlreadyPassed e) {
            System.out.println(e.getMessage());
        } catch (ExReservationAlreadyExists e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        r.giveTicketBack();
        bo.removeReservation(r);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        r.takeTicketBack();
        bo.addReservation(r);
        addUndoCommand(this);
    }
}
