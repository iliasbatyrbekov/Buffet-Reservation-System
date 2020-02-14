public class ExTableNotFound extends Exception
{
    private static final long serialVersionUID = 1L;
    public ExTableNotFound()
    {
        super("Table code T11 not found!");
    }
    public ExTableNotFound(String message)
    {
        super(String.format("Table code %s not found!", message));
    }
}
