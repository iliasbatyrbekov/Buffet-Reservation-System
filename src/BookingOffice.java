import java.util.ArrayList;
import java.util.Collections; //Provides sorting

public class BookingOffice {

	private ArrayList<Reservation> allReservations;
	//private ArrayList<Day> allDineDates;

	private static BookingOffice instance = new BookingOffice(); 
	private BookingOffice() {
		allReservations = new ArrayList<Reservation>();
		//allDineDates = new ArrayList<Day>();
	}
	public static BookingOffice getInstance(){ return instance; }

	public Reservation addReservation(String guestName, String phoneNumber, int totPersons, String sDateDine)  // See how it is called in main() in P.1
	{
		Reservation r = new Reservation(guestName, phoneNumber, totPersons, sDateDine);
		allReservations.add(r);
		//allDineDates.add(r.getDateDine());
		//System.out.println(allDineDates);
		Collections.sort(allReservations); // allReservations
		return r; //Why return?  Ans: You'll see that it is useful in CmdRequest.java in Q2. 
	}

	public void addReservation(Reservation r)  // overloaded
	{
		allReservations.add(r);
		Collections.sort(allReservations); // allReservations
	}

	public void listReservations()
	{
		System.out.println(Reservation.getListingHeader()); // Reservation
		for (Reservation r: allReservations)
			System.out.println(r.toString()); // r or r.toString()
	}

	public void removeReservation(Reservation r) {
		allReservations.remove(r); //.remove is given by ArrayList
	}

	public Reservation findReservation(String[] cmdParts)
	{
		return Reservation.searchReservation(allReservations, cmdParts);
	}

	public Reservation findReservationByDateAndTicket(Day targetDay, int targetTicket){
		return Reservation.searchByDateAndTicket(allReservations, targetDay, targetTicket);
	}

	public String genPendingRequestsMsgForADay(Day d)
	{
		return Reservation.genPendingRequestsMsgFromListAndDay(allReservations, d);
	}
}
