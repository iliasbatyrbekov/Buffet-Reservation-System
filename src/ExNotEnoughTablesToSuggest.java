public class ExNotEnoughTablesToSuggest extends Exception
{
    private static final long serialVersionUID = 1L;
    public ExNotEnoughTablesToSuggest()
    {
        super("Suggestion for persons: Not enough tables");
    }
    public ExNotEnoughTablesToSuggest(int numOfPersons)
    {
        super(String.format("Suggestion for %d persons: Not enough tables", numOfPersons) );
    }
    public ExNotEnoughTablesToSuggest(String message)
    {
        super(message);
    }
}
