/*
 * @(#)GroupAction.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.CompositeFigure;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.GroupFigure;
import org.jhotdraw.util.ResourceBundleUtil;

import java.util.LinkedList;

/**
 * GroupAction.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class GroupAction extends AbstractSelectedAction {

    private static final long serialVersionUID = 1L;
    public static final String ID = "edit.groupSelection";

    private final CompositeFigure prototype;

    private GroupingManager groupingManager;

    /**
     * Creates a new instance.
     */
    @FeatureEntryPoint("Grouping")
    public GroupAction(DrawingEditor editor) {
        this(editor, new GroupFigure());
    }

    @FeatureEntryPoint("Grouping")
    public GroupAction(DrawingEditor editor, CompositeFigure prototype) {
        super(editor);
        this.prototype = prototype;
        ResourceBundleUtil labels
                = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        labels.configureAction(this, ID);
        updateEnabledState();
    }

    public GroupAction(DrawingEditor editor, CompositeFigure prototype, GroupingManager groupingManager) {
        super(editor);
        this.prototype = prototype;
        this.groupingManager = groupingManager;
        ResourceBundleUtil labels
                = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        labels.configureAction(this, ID);
        updateEnabledState();
    }


    @Override
    protected void updateEnabledState() {
        setEnabled(canGroup());
    }

    protected boolean canGroup() {
        return getView() != null && getView().getSelectionCount() > 1;
    }

    @Override
    @FeatureEntryPoint("Grouping")
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (canGroup()) {
            final DrawingView view = getView();
            final LinkedList<Figure> ungroupedFigures = new LinkedList<>(
                    view.getSelectedFigures());
            final CompositeFigure group = (CompositeFigure) prototype.clone();
            if (groupingManager == null) {
                groupingManager = new GroupingManager(view, group,
                        ungroupedFigures);
            }
            UndoableGroupEdit edit = new UndoableGroupEdit(groupingManager);
            groupingManager.groupFigures();
            fireUndoableEditHappened(edit);
        }
    }
}
