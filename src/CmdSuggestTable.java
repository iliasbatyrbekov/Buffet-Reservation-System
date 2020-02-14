import java.util.ArrayList;

public class CmdSuggestTable implements Command
{
    @Override
    public void execute(String[] cmdParts)
    {
        try {
            if (cmdParts.length < 3)
            {
                throw new ExInsufficientArgument();
            }
            BookingOffice bo = BookingOffice.getInstance();
            Reservation r = bo.findReservationByDateAndTicket(new Day(cmdParts[1]), Integer.parseInt(cmdParts[2]));
            if (r == null)
            {
                throw new ExBookingNotFound();
            }
            if (r.getStatus() instanceof RStateTableAllocated)
            {
                throw new ExTableAlreadyAssignedForThisBooking();
            }
            DailyTableAssignmentController dTACont = TableAssignContAccessor.getDailyTableAssignmentController(r.getDateDine());
            int totalPersons = r.getTotPersons();
            ArrayList<String> allSuggestedTableNames = new ArrayList<String>();
            allSuggestedTableNames = dTACont.getSuggestedTableNames(totalPersons);
            if(allSuggestedTableNames == null)
            {
                throw new ExNotEnoughTablesToSuggest(totalPersons);
            }
            String suggestion = String.format("Suggestion for %d persons: ", totalPersons);
            for (String s:
                    allSuggestedTableNames) {
                suggestion += s + " ";
            }
            System.out.println(suggestion);
        }catch (ExInsufficientArgument e) {
            System.out.println(e.getMessage());
        }catch (NumberFormatException e){
            System.out.println("Wrong number format!");
        } catch (ExBookingNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExTableAlreadyAssignedForThisBooking e) {
            System.out.println(e.getMessage());
        }catch (ExNotEnoughTablesToSuggest e) {
            System.out.println(e.getMessage());
        }
    }
}
