public class ExReservationAlreadyExists extends Exception
{
    private static final long serialVersionUID = 1L;
    public ExReservationAlreadyExists()
    {
        super("Booking by the same person for the dining date already exists!");
    }
    public ExReservationAlreadyExists(String message)
    {
        super(message);
    }
}
