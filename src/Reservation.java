import java.util.ArrayList;

public class Reservation implements Comparable<Reservation> {
	private String guestName;
	private String phoneNumber;
	private int totPersons;
	private Day dateDine;
	private Day dateRequest;
	private int ticketCode;
	private RState status;

	public Reservation(String guestName, String phoneNumber, int totPersons, String sDateDine)
	{	
		this.guestName = guestName;
		this.phoneNumber = phoneNumber;
		this.totPersons = totPersons;
		this.dateDine = new Day(sDateDine);
		this.dateRequest = SystemDate.getInstance().clone();
		this.ticketCode = BookingTicketController.takeTicket(this.dateDine);
		this.status = new RStatePending();
	}

	public static String genPendingRequestsMsgFromListAndDay(ArrayList<Reservation> allReservations, Day d)
	{
		int numPendingRequests = 0;
		int totNumPersons = 0;
		for (Reservation r:
			 allReservations) {
			if (r.dateDine.equals(d) && r.getStatus() instanceof RStatePending){
				numPendingRequests++;
				totNumPersons += r.totPersons;
			}
		}
		return String.format("Total number of pending requests = %d (Total number of persons = %d)", numPendingRequests, totNumPersons);
	}

	@Override
	public String toString() 
	{
		//Learn: "-" means left-aligned
		return String.format("%-13s%-11s%-14s%-25s%4d       %s", guestName, phoneNumber, dateRequest, dateDine+String.format(" (Ticket %d)", ticketCode), totPersons, status.genStatusMessage());

	}
	public static String getListingHeader() 
	{
		return String.format("%-13s%-11s%-14s%-25s%-11s%s", "Guest Name", "Phone", "Request Date", "Dining Date and Ticket", "#Persons", "Status");
	}
	@Override
	public int compareTo(Reservation another) {
		if(this.guestName.equals(another.guestName))//name is same
		{
			if (this.phoneNumber.equals(another.phoneNumber))//name and phone are same
			{
				if (0 == this.dateDine.compareTo(another.dateDine)) //dine date, name, and phone are same
				{
					return 0;
				}
				else if (this.dateDine.compareTo(another.dateDine) > 0)
				{
					return 1;
				}
			}
			else if (this.phoneNumber.compareTo(another.phoneNumber)>0)
			{
				return 1;
			}
		}
		else if (this.guestName.compareTo(another.guestName)>0)
		{
			return 1;
		}
		return -1; //when this reservation is neither equal nor bigger
	}

	public static Reservation searchReservation(ArrayList<Reservation> list, String[] cmdParts)
	{
		for (Reservation r:
			 list) {
			if (r.guestName.equals(cmdParts[1]))
			{
				if (r.phoneNumber.equals(cmdParts[2]))
				{
					if (r.dateDine.equals(new Day(cmdParts[4])))
					{
						return r;
					}
				}
			}
		}
		return null;
	}

	public static Reservation searchByDateAndTicket(ArrayList<Reservation> list, Day targetDay, int targetTicket)
	{
		for (Reservation r:
			 list) {
			if (r.dateDine.equals(targetDay) && r.ticketCode == targetTicket)
				return r;
		}
		return null;
	}

    public int getTicketCode()
    {
        return ticketCode;
    }

	public void takeTicketBack()
	{
		this.ticketCode = BookingTicketController.takeTicket(dateDine);
	}
	public void giveTicketBack()
    {
        BookingTicketController.undoTakeTicket(dateDine);
    }

    public void setStatus(RState newStatus)
	{
		this.status = newStatus;
	}

	public RState getStatus()
	{
		return status;
	}

	public Day getDateDine()
	{
		return dateDine;
	}

	public int getTotPersons()
	{
		return totPersons;
	}

	public boolean isNumOfSeatsEnough(int numOfSeats)
	{
		if (this.totPersons <= numOfSeats)
			return true;
		else
			return false;
	}
}
