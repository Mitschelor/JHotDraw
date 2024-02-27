/*
 * @(#)UngroupAction.java
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
import org.jhotdraw.util.ResourceBundleUtil;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * UngroupAction.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class UngroupAction extends AbstractSelectedAction {

    private static final long serialVersionUID = 1L;
    public static final String ID = "edit.ungroupSelection";
    /**
     * Creates a new instance.
     */
    private CompositeFigure prototype;
    private GroupingManager groupingManager;

    /**
     * Creates a new instance.
     */
    @FeatureEntryPoint("Grouping")
    public UngroupAction(DrawingEditor editor) {
        super(editor);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle(
                "org.jhotdraw.draw.Labels");
        labels.configureAction(this, ID);
        updateEnabledState();
    }

    @FeatureEntryPoint("Grouping")
    public UngroupAction(DrawingEditor editor, CompositeFigure prototype) {
        super(editor);
        this.prototype = prototype;
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle(
                "org.jhotdraw.draw.Labels");
        labels.configureAction(this, ID);
        updateEnabledState();
    }

    public UngroupAction(DrawingEditor editor, CompositeFigure prototype, GroupingManager groupingManager) {
        super(editor);
        this.prototype = prototype;
        this.groupingManager = groupingManager;
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle(
                "org.jhotdraw.draw.Labels");
        labels.configureAction(this, ID);
        updateEnabledState();
    }

    protected boolean canUngroup() {
        if (prototype == null) {
            return false;
        }
        DrawingView view = getView();
        Set<Figure> selectedFigures = view.getSelectedFigures();
        Iterator<Figure> i = selectedFigures.iterator();
        Figure figure = i.next();
        boolean equalClasses = figure.getClass()
                .equals(
                        prototype.getClass());
        return getView() != null
                && getView().getSelectionCount() == 1
                && equalClasses;
    }

    @Override
    protected void updateEnabledState() {
        setEnabled(canUngroup());
    }

    @Override
    @FeatureEntryPoint("Grouping")
    public void actionPerformed(ActionEvent e) {
        if (canUngroup()) {
            final DrawingView view = getView();
            final CompositeFigure group = (CompositeFigure) view.getSelectedFigures()
                    .iterator().next();
            final LinkedList<Figure> ungroupedFigures = new LinkedList<>();
            if (groupingManager == null) {
                groupingManager = new GroupingManager(view, group,
                        ungroupedFigures);
            }
            UndoableUngroupEdit edit = new UndoableUngroupEdit(groupingManager);
            ungroupedFigures.addAll(groupingManager.ungroupFigures());
            fireUndoableEditHappened(edit);
        }
    }
}
