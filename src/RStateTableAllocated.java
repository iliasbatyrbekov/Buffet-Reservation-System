import java.util.ArrayList;

public class RStateTableAllocated implements RState
{
    private ArrayList<Table> allAssignedTables;

    public RStateTableAllocated(ArrayList<Table> anAllAssignedTables)
    {
        this.allAssignedTables = anAllAssignedTables;
    }
    //check for error
    @Override
    public String genStatusMessage()
    {
        String msg = "Table assigned:";
        for (Table t:
             allAssignedTables) {
            msg += " " + t.getTableName();
        }
        return msg;
    }

    public ArrayList<Table> getAllAssignedTables()
    {
        return allAssignedTables;
    }
}
