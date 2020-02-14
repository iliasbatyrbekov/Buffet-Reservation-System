
public class ExTableAlreadyReserved extends Exception
{
    private static final long serialVersionUID = 1L;
    public ExTableAlreadyReserved()
    {
        super("Table is already reserved by another booking!");
    }
    public ExTableAlreadyReserved(String message)
    {
        super(String.format("Table %s is already reserved by another booking!", message));
    }
}