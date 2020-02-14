import java.util.ArrayList;

public class Table implements Comparable<Table>
{
    private int ticket;
    private String tableName;

    public Table(String aTableName, int aTicket)
    {
        this.tableName = aTableName;
        this.ticket = aTicket;
    }

    public static String getTableNamesOfSize(ArrayList<String> allAvailableTableNames, char h)
    {
        for (String s:
             allAvailableTableNames) {
            if (s.charAt(0) == h)
                return s;
        }
        return null;
    }

    public String getTableName()
    {
        return tableName;
    }

    public int getTicket()
    {
        return ticket;
    }

    public void setTicket(int ticket)
    {
        this.ticket = ticket;
    }

    public static ArrayList<Table> createListOfTablesForADay(ArrayList<Table> allTables)
    {
        for (int i = 1; i <= 10; i++) {
            allTables.add(new Table(String.format("T%02d", i), -1));
        }
        for (int i = 1; i <= 6; i++) {
            allTables.add(new Table(String.format("F%02d", i),-1));
        }
        for (int i = 1; i <= 3; i++) {
            allTables.add(new Table(String.format("H%02d", i),-1));
        }
        return allTables;
    }
    //find a table
    public static Table findTable(ArrayList<Table> allAssignedTables, Table tableToBeSearched)
    {
        return findTableByName(allAssignedTables, tableToBeSearched.tableName);
    }

    public static Table findTableByName(ArrayList<Table> allTables, String tableName)
    {
        for (Table t:
                allTables) {
            if (t.tableName.equals(tableName))
                return t;
        }
        return null;
    }

    @Override
    public int compareTo(Table t)
    {
        if ( (this.tableName.charAt(0) == t.tableName.charAt(0)) )
        {
            return this.tableName.compareTo(t.tableName);
        }
        else if(this.tableName.charAt(0) == 'T'){
            return -1;
        }
        else if(this.tableName.charAt(0) == 'H'){
            return 1;
        }
        else{ //this.tableName.charAt(0) == 'F'
            if (t.tableName.charAt(0) == 'T')
                return 1;
            else
                return -1;
        }
    }

    public static int maxCapacityOfTables(String[] allTableNames){
        int result = 0;
        for (String s:
             allTableNames) {
            switch (s.charAt(0)){
                case 'T':
                    result += 2;
                    break;
                case 'F':
                    result += 4;
                    break;
                case 'H':
                    result += 8;
                    break;
            }
        }
        return result;
    }

    public static boolean isTableCodeValid(String tableCodeToBeValidated)
    {
        boolean result = false;
        ArrayList<Table> allTables = new ArrayList<Table>();
        allTables =  Table.createListOfTablesForADay(allTables);
        for (Table t:
             allTables) {
            if (t.tableName.equals(tableCodeToBeValidated))
                result = true;
        }
        return result;
    }
}
