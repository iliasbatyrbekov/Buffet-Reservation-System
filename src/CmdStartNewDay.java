public class CmdStartNewDay extends RecordedCommand{
    private Day  old_Day;
    private Day new_Day;

    @Override
    public void undoMe() {
        SystemDate.getInstance().set(old_Day.toString());
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        SystemDate.getInstance().set(new_Day.toString());
        addUndoCommand(this);
    }

    @Override
    public void execute(String[] cmdParts) {
        try
        {
            if (cmdParts.length < 2)
            {
                throw new ExInsufficientArgument();
            }
            new_Day = new Day(cmdParts[1]);
            old_Day = SystemDate.getInstance().clone();
            SystemDate.getInstance().set(cmdParts[1]);
            System.out.println("Done.");
            addUndoCommand(this);
            clearRedoList();
        }catch (ExInsufficientArgument e)
        {
            System.out.println(e.getMessage());
        }

    }
}
