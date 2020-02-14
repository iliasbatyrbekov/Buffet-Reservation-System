import java.util.ArrayList;
import java.util.Collections;

public class DailyTableAssignmentController
{
    private ArrayList<Table> allAvailableTables;
    private ArrayList<Table> allAssignedTables;

    public DailyTableAssignmentController(){
        allAvailableTables = new ArrayList<Table>();
        allAssignedTables = new ArrayList<Table>();
        allAvailableTables = Table.createListOfTablesForADay(allAvailableTables);
    }

    public ArrayList<Table> requestTablesByNames(String[] cmdParts, int ticket) throws ExTableAlreadyReserved, ExTableNotFound
    {
        ArrayList<Table> allRequestedTables = new ArrayList<>();
        for (int i = 3; i < cmdParts.length; i++) //previous Strings are description of command
        {
            if (!Table.isTableCodeValid(cmdParts[i]))
            {
                throw new ExTableNotFound(cmdParts[i]);
            }
            Table aRequestedTable = Table.findTableByName(allAvailableTables,cmdParts[i]);
            if (aRequestedTable == null)
            {
                throw new ExTableAlreadyReserved(cmdParts[i]);
            }
            allRequestedTables.add(aRequestedTable);
        }
        return requestTables(allRequestedTables, ticket);
    }

    public ArrayList<Table> requestTables(ArrayList<Table> allRequestedTables, int ticket)
    {
        for (Table t:
             allRequestedTables) {
            t.setTicket(ticket);
            allAvailableTables.remove(t);
            allAssignedTables.add(t);
            Collections.sort(allAssignedTables);
        }
        return allRequestedTables;
    }

    public void undoRequestTables(ArrayList<Table> tables)
    {
        for (Table t:
             tables) {
            Table currentTable = Table.findTable(allAssignedTables, t);
            allAvailableTables.add(currentTable);
            allAssignedTables.remove(currentTable);
        }
        Collections.sort(allAvailableTables);
    }

    public void printAllAssignedTablesMsg()
    {
        System.out.println("Allocated tables: ");
        if (allAssignedTables.size() != 0) {
            for (Table t :
                    allAssignedTables) {
                System.out.println(String.format("%s (Ticket %d)", t.getTableName(), t.getTicket()));
            }
        }
        else {
            System.out.println("[None]");
        }
    }

    public void printAllAvailableTablesMsg()
    {
        System.out.println("Available tables: ");
        String allTableNames = "";
        for (Table t:
            allAvailableTables) {
            allTableNames += t.getTableName() + " ";
        }
        System.out.println(allTableNames);
    }

    public Table findAvailableTable(Table t){
        return Table.findTable(allAvailableTables, t);
    }

    public ArrayList<String> getSuggestedTableNames(int totalPersons)
    {
        ArrayList<String> allAvailableTableNames = new ArrayList<String>();
        for (Table t:
             allAvailableTables) {
            allAvailableTableNames.add(t.getTableName());
        }
        ArrayList<String> result = new ArrayList<String>();
        int numSeatsNeeded = totalPersons;
        String aTableName;
        while(numSeatsNeeded > 0){
            if (numSeatsNeeded > 4){
                aTableName = Table.getTableNamesOfSize(allAvailableTableNames, 'H');
                if (aTableName != null){
                    allAvailableTableNames.remove(aTableName);
                    result.add(aTableName);
                    numSeatsNeeded -= 8;
                    continue;
                }
                else{
                    aTableName = Table.getTableNamesOfSize(allAvailableTableNames, 'F');
                    if (aTableName != null){
                        allAvailableTableNames.remove(aTableName);
                        result.add(aTableName);
                        numSeatsNeeded -= 4;
                        continue;
                    }
                    else{
                        aTableName = Table.getTableNamesOfSize(allAvailableTableNames, 'T');
                        if (aTableName != null){
                            allAvailableTableNames.remove(aTableName);
                            result.add(aTableName);
                            numSeatsNeeded -= 2;
                            continue;
                        }
                        else{
                            //give up
                            break;
                        }
                    }
                }
            }
            else if (numSeatsNeeded > 2){
                aTableName = Table.getTableNamesOfSize(allAvailableTableNames, 'F');
                if (aTableName != null){
                    allAvailableTableNames.remove(aTableName);
                    result.add(aTableName);
                    numSeatsNeeded -= 4;
                    continue;
                }
                else{
                    aTableName = Table.getTableNamesOfSize(allAvailableTableNames, 'T');
                    if (aTableName != null){
                        allAvailableTableNames.remove(aTableName);
                        result.add(aTableName);
                        numSeatsNeeded -= 2;
                        continue;
                    }
                    //else give up
                    break;
                }
            }else{
                aTableName = Table.getTableNamesOfSize(allAvailableTableNames, 'T');
                if (aTableName != null){
                    allAvailableTableNames.remove(aTableName);
                    result.add(aTableName);
                    numSeatsNeeded -= 2;
                    continue;
                }
                //else give up
                break;
            }
        }

        if (numSeatsNeeded <= 0)
        {
            return result;
        }
        else {
            return null;
        }
    }
}
