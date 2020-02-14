public class ExNotEnoughSeatsForBooking extends Exception
{
    private static final long serialVersionUID = 1L;
    public ExNotEnoughSeatsForBooking()
    {
        super("Not enough seats for the booking!");
    }
    public ExNotEnoughSeatsForBooking(String message)
    {
        super(message);
    }
}
