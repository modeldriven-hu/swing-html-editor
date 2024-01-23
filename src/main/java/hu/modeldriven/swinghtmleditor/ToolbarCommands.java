package hu.modeldriven.swinghtmleditor;

import hu.modeldriven.swinghtmleditor.command.*;

import javax.swing.text.html.HTMLDocument;
import javax.swing.undo.UndoManager;
import java.util.ArrayList;
import java.util.List;

public class ToolbarCommands {

    HTMLDocument doc;

    public ToolbarCommands(HTMLDocument doc){
        this.doc = doc;
    }

    public List<CommandGroup> getCommandGroups() {

        UndoManager undoManager = new UndoManager();
        doc.addUndoableEditListener(undoManager);

        List<CommandGroup> commandGroups = new ArrayList<>();

        commandGroups.add(new CommandGroup(
                new BoldCommand(),
                new ItalicCommand(),
                new UnderlineCommand(),
                new StrikethroughCommand()
        ));

        // add separator

        commandGroups.add(new CommandGroup(
                new IncreaseIndentCommand(),
                new ReduceIndentCommand()
        ));

        // add separator

        commandGroups.add(new CommandGroup(
                new AlignLeftCommand(),
                new AlignCenterCommand(),
                new AlignRightCommand(),
                new AlignJustifiedCommand()
        ));

        // add separator

        commandGroups.add(new CommandGroup(
                new LinkCommand(),
                new UnlinkCommand()
        ));

        // add separator

        commandGroups.add(new CommandGroup(
                new InsertHorizontalRuleCommand(),
                new InsertImageCommand()
        ));

        // add separator

        commandGroups.add(new CommandGroup(
                new UndoCommand(undoManager),
                new RedoCommand(undoManager)
        ));

        return commandGroups;
    }

}
