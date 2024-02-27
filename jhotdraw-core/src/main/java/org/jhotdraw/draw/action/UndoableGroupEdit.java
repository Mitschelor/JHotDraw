package org.jhotdraw.draw.action;

public class UndoableGroupEdit extends UndoableGroupOrUngroupEdit {

    public UndoableGroupEdit(GroupingManager groupingManager) {
        super(groupingManager);
        if (groupingManager == null) {
            throw new IllegalArgumentException("groupingManager must be nonnull");
        }
    }

    @Override
    public void undo() {
        super.undo();
        groupingManager.ungroupFigures();
    }

    @Override
    public void redo() {
        super.redo();
        groupingManager.groupFigures();
    }
}
