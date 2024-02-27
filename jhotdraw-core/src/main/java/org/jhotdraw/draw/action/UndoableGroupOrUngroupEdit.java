package org.jhotdraw.draw.action;

import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.undo.AbstractUndoableEdit;

public abstract class UndoableGroupOrUngroupEdit extends AbstractUndoableEdit {
    private static final long serialVersionUID = 1L;
    protected transient GroupingManager groupingManager;

    protected UndoableGroupOrUngroupEdit(GroupingManager groupingManager) {
        if (groupingManager == null) {
            throw new IllegalArgumentException("groupingManager must be nonnull");
        }
        this.groupingManager = groupingManager;
    }

    @Override
    public String getPresentationName() {
        ResourceBundleUtil labels
                = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        return labels.getString("edit.groupSelection.text");
    }
}
