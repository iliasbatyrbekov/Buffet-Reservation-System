public class RStatePending implements RState
{
    //no need for constructor
    @Override
    public String genStatusMessage()
    {
        return "Pending";
    }
}
