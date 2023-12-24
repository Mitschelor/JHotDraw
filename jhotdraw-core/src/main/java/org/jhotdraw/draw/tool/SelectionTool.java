/*
 * @(#)SelectionTool.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.tool;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.draw.figure.Figure;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.event.ToolAdapter;
import org.jhotdraw.draw.event.ToolEvent;
import org.jhotdraw.draw.handle.Handle;

/**
 * Tool to select and manipulate figures.
 * <p>
 * A selection tool is in one of three states: 1) area
 * selection, 2) figure dragging, 3) handle manipulation. The different
 * states are handled by different tracker objects: the
 * <code>DefaultSelectAreaTracker</code>, the <code>DefaultDragTracker</code> and the
 * <code>DefaultHandleTracker</code>.
 * <p>
 * A Figure can be selected by clicking at it. Holding the alt key or the
 * ctrl key down, selects the Figure behind it.
 * <hr>
 * <b>Design Patterns</b>
 *
 * <p>
 * <em>Strategy</em><br>
 * The different behavior states of the selection tool are implemented by
 * trackers.<br>
 * Context: {@link SelectionTool}; State: {@link DragTracker},
 * {@link HandleTracker}, {@link SelectAreaTracker}.
 *
 * <p>
 * <em>Chain of responsibility</em><br>
 * Mouse and keyboard events of the user occur on the drawing view, and are
 * preprocessed by the {@code DragTracker} of a {@code SelectionTool}. In
 * turn {@code DragTracker} invokes "track" methods on a {@code Handle} which in
 * turn changes an aspect of a figure.<br>
 * Client: {@link SelectionTool}; Handler: {@link DragTracker}, {@link Handle}.
 * <hr>
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class SelectionTool extends AbstractTool {

    private static final long serialVersionUID = 1L;
    /**
     * The tracker encapsulates the current state of the SelectionTool.
     */
    private Tool tracker;
    /**
     * The tracker encapsulates the current state of the SelectionTool.
     */
    private HandleTracker handleTracker;
    /**
     * The tracker encapsulates the current state of the SelectionTool.
     */
    private SelectAreaTracker selectAreaTracker;
    /**
     * The tracker encapsulates the current state of the SelectionTool.
     */
    private DragTracker dragTracker;

    private class TrackerHandler extends ToolAdapter {

        @Override
        public void toolDone(ToolEvent event) {
            // Empty
            Tool newTracker = getSelectAreaTracker();
            if (newTracker != null) {
                if (tracker != null) {
                    tracker.deactivate(getEditor());
                    tracker.removeToolListener(this);
                }
                tracker = newTracker;
                tracker.activate(getEditor());
                tracker.addToolListener(this);
            }
            fireToolDone();
        }

        /**
         * Sent when an area of the drawing view needs to be repainted.
         */
        @Override
        public void areaInvalidated(ToolEvent e) {
            fireAreaInvalidated(e.getInvalidatedArea());
        }

        /**
         * Sent when the bounds need to be revalidated.
         */
        @Override
        public void boundsInvalidated(ToolEvent e) {
            fireBoundsInvalidated(e.getInvalidatedArea());
        }
    }
    private TrackerHandler trackerHandler;
    /**
     * Constant for the name of the selectBehindEnabled property.
     */
    public static final String SELECT_BEHIND_ENABLED_PROPERTY = "selectBehindEnabled";
    /**
     * Represents the state of the selectBehindEnabled property.
     * By default, this property is set to true.
     */
    private boolean isSelectBehindEnabled = true;

    /**
     * Creates a new instance.
     */
    @FeatureEntryPoint(value = "SelectionTool")
    public SelectionTool() {
        tracker = getSelectAreaTracker();
        trackerHandler = new TrackerHandler();
        tracker.addToolListener(trackerHandler);
    }

    /**
     * Sets the selectBehindEnabled property.
     * This is a bound property.
     *
     * @param newValue The new value.
     */
    public void setSelectBehindEnabled(boolean newValue) {
        boolean oldValue = isSelectBehindEnabled;
        isSelectBehindEnabled = newValue;
        firePropertyChange(SELECT_BEHIND_ENABLED_PROPERTY, oldValue, newValue);
    }

    /**
     * Returns the value of the selectBehindEnabled property.
     * This is a bound property.
     *
     * @return The property value.
     */
    public boolean isSelectBehindEnabled() {
        return isSelectBehindEnabled;
    }

    @FeatureEntryPoint(value = "SelectionTool")
    @Override
    public void activate(DrawingEditor editor) {
        super.activate(editor);
        tracker.activate(editor);
    }

    @FeatureEntryPoint(value = "SelectionTool")
    @Override
    public void deactivate(DrawingEditor editor) {
        super.deactivate(editor);
        tracker.deactivate(editor);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isViewNotNullAndEnabled()) {
            tracker.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        if (isViewNotNullAndEnabled()) {
            tracker.keyReleased(evt);
        }
    }

    @Override
    public void keyTyped(KeyEvent evt) {
        if (isViewNotNullAndEnabled()) {
            tracker.keyTyped(evt);
        }
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        if (isViewNotNullAndEnabled()) {
            tracker.mouseClicked(evt);
        }
    }

    @FeatureEntryPoint(value = "SelectionTool")
    @Override
    public void mouseDragged(MouseEvent evt) {
        if (isViewNotNullAndEnabled()) {
            tracker.mouseDragged(evt);
        }
    }

    @FeatureEntryPoint(value = "SelectionTool")
    @Override
    public void mouseEntered(MouseEvent evt) {
        super.mouseEntered(evt);
        tracker.mouseEntered(evt);
    }

    @FeatureEntryPoint(value = "SelectionTool")
    @Override
    public void mouseExited(MouseEvent evt) {
        super.mouseExited(evt);
        tracker.mouseExited(evt);
    }

    @FeatureEntryPoint(value = "SelectionTool")
    @Override
    public void mouseMoved(MouseEvent evt) {
        tracker.mouseMoved(evt);
    }

    @FeatureEntryPoint(value = "SelectionTool")
    @Override
    public void mouseReleased(MouseEvent evt) {
        if (isViewNotNullAndEnabled()) {
            tracker.mouseReleased(evt);
        }
    }

    @FeatureEntryPoint(value = "SelectionTool")
    @Override
    public void draw(Graphics2D g) {
        tracker.draw(g);
    }

    /**
     * Checks whether the associated drawing {@code view} is not null and is currently enabled.
     * 
     * @return {@code true} if the drawing view is not null and is enabled; otherwise, {@code false}.
     * 
     * @author Paweł Kasztura
     */
    private boolean isViewNotNullAndEnabled() {
        return getView() != null && getView().isEnabled();
    }

    /**
     * Checks whether the given {@code figure} is not null and is selectable.
     * 
     * @param figure Considered {@code Figure}
     * 
     * @return {@code true} if the figure is not null and is selectable; otherwise, {@code false}.
     * 
     * @author Paweł Kasztura
     */
    private boolean isFigureNotNullAndSelectable(Figure figure) {
        return figure != null && figure.isSelectable();
    }

    /**
     * Checks whether the given {@code figure} is not null and is not selectable.
     * 
     * @param figure Considered {@code Figure}
     * 
     * @return {@code true} if the figure is not null and is not selectable; otherwise, {@code false}.
     * 
     * @author Paweł Kasztura
     */
    private boolean isFigureNotNullAndNotSelectable(Figure figure) {
        return figure != null && !figure.isSelectable();
    }

    /**
     * Invoked when a mouse button is pressed within the context of the {@code SelectionTool}.
     * This method handles the mouse press event for selection operations and delegates
     * processing to the appropriate tools and handles within the associated drawing {@code view}.
     *
     * @param evt The {@code MouseEvent} representing the mouse press event.
     * 
     * @author Paweł Kasztura
     */
    @FeatureEntryPoint(value = "SelectionTool")
    @Override
    public void mousePressed(MouseEvent evt) {
        if (isViewNotNullAndEnabled()) {
            super.mousePressed(evt);
            DrawingView view = getView();
            Handle handle = view.findHandle(anchor);

            Tool newTracker = createNewTracker(handle, evt, view);
            
            setTracker(newTracker);
            tracker.mousePressed(evt);
        }
    }

    /**
     * Creates a new tracker tool based on the provided handle, mouse event, and drawing {@code view}. This
     * method determines the appropriate tracker based on the handle, figure, and user interaction,
     * ensuring dynamic tool selection within the context of the {@code SelectionTool}.
     * 
     * @param handle The handle associated with the mouse event, or null if none.
     * @param evt The {@code MouseEvent} representing the user's interaction.
     * @param view The {@code DrawingView} where the interaction occurs.
     * 
     * @return A {@code Tool} instance representing the newly created tracker for the given context.
     * 
     * @author Paweł Kasztura
     */
    @FeatureEntryPoint(value = "SelectionTool")
    private Tool createNewTracker(Handle handle, MouseEvent evt, DrawingView view) {
        Point2D.Double viewCoordinates = view.viewToDrawing(anchor);
    
        if (handle != null) {
            return getHandleTracker(handle);
        }

        Figure figure = getFigure(view, viewCoordinates, evt);

        if (isFigureNotNullAndSelectable(figure)) {
            return getDragTracker(figure);
        }

        if (!evt.isShiftDown()) {
            view.clearSelection();
            view.setHandleDetailLevel(0);
        }
        
        return getSelectAreaTracker();
    }

    /**
     * Returns the {@code Figure} under the specified {@code view} coordinates within the given {@code DrawingView}.
     * The method considers the option to select figures behind others when enabled and conditions for
     * selecting behind are met. If the option is disabled or conditions are not met, it determines the
     * {@code figure} in the current selection or the entire {@code drawing} at the specified {@code view} coordinates.
     *
     * @param view The {@code DrawingView} where the interaction occurs.
     * @param viewCoordinates The {@code view} coordinates representing the point of interest.
     * @param evt The {@code MouseEvent} triggering the {@code figure} retrieval.
     * 
     * @return The {@code Figure} under the specified coordinates, considering selection options.
     * 
     * @author Paweł Kasztura
     */
    @FeatureEntryPoint(value = "SelectionTool")
    private Figure getFigure(DrawingView view, Point2D.Double viewCoordinates, MouseEvent evt) {
        Drawing drawing = view.getDrawing();
    
        if (shouldSelectBehind(evt)) {
            return selectFigureBehind(view, viewCoordinates);
        }
        
        return getFigureFromCurrentSelectionOrDrawing(view, viewCoordinates, drawing);
    }

    /**
     * Determines whether the selection behind other figures is appropriate based on the
     * current state of the mouse event. Selection behind is enabled
     * if the {@code ALT} or {@code CTRL} key is pressed during the mouse event.
     * 
     * @param evt The {@code MouseEvent} to evaluate for key modifiers and selection behind conditions.
     * 
     * @return {@code true} if selection behind is enabled and the {@code ALT} or {@code CTRL} key is pressed; 
     *          otherwise, {@code false}.
     * 
     * @author Paweł Kasztura
     */
    private boolean shouldSelectBehind(MouseEvent evt) {
        return (isSelectBehindEnabled() && (evt.isAltDown() || evt.isControlDown()));
    }
    
    /**
     * Selects and returns the {@code figure} behind the specified anchor point within the given {@code DrawingView}.
     * The method iteratively finds the nearest selectable {@code figure} behind the anchor point, excluding
     * any figures already selected. If no {@code figure} is found behind, it considers the entire drawing.
     *
     * @param view The {@code DrawingView} where the interaction occurs.
     * @param viewCoordinates The {@code view} coordinates representing the anchor point.
     * 
     * @return The {@code Figure} behind the anchor point, excluding selected figures, or the nearest
     *         selectable {@code figure} in the entire drawing. {@code Null} if no such {@code figure} is found.
     * 
     * @author Paweł Kasztura
     */
    @FeatureEntryPoint(value = "SelectionTool")
    private Figure selectFigureBehind(DrawingView view, Point2D.Double viewCoordinates) {
        Figure figure = view.findFigure(anchor);
        
        while (isFigureNotNullAndNotSelectable(figure)) {
            figure = view.getDrawing().findFigureBehind(viewCoordinates, figure);
        }
    
        HashSet<Figure> ignoredFigures = new HashSet<>(view.getSelectedFigures());
        
        ignoredFigures.add(figure);
        
        Figure figureBehind = view.getDrawing().findFigureBehind(view.viewToDrawing(anchor), ignoredFigures);
    
        return (figureBehind != null) ? figureBehind : figure;
    }
    
    /**
     * Determines and returns the {@code Figure} under the specified {@code view} coordinates within the given {@code DrawingView},
     * considering the current selection first. If no {@code figure} is found in the current selection, the method
     * iteratively finds the nearest selectable {@code figure} behind the anchor point in the entire drawing.
     *
     * @param view The {@code DrawingView} where the interaction occurs.
     * @param viewCoordinates The {@code view} coordinates representing the point of interest.
     * @param drawing The {@code Drawing} containing the figures.
     * 
     * @return The {@code Figure} under the specified coordinates within the current selection, or the nearest
     *         selectable {@code figure} in the entire drawing. {@code Null} if no such {@code figure} is found.
     * 
     * @author Paweł Kasztura
     */
    @FeatureEntryPoint(value = "SelectionTool")
    private Figure getFigureFromCurrentSelectionOrDrawing(DrawingView view, Point2D.Double viewCoordinates, Drawing drawing) {
        Figure figure = findFigureInCurrentSelection(view, viewCoordinates);
    
        if (figure == null) {
            figure = view.findFigure(anchor);
            
            while (isFigureNotNullAndNotSelectable(figure)) {
                figure = drawing.findFigureBehind(viewCoordinates, figure);
            }
        }
    
        return figure;
    }
    
    /**
     * Finds and returns the {@code Figure} within the current selection that contains the specified {@code view} coordinates.
     * If selection behind is enabled, the method iterates through the selected figures, checking if each
     * figure contains the given coordinates and returns the first match.
     *
     * @param view The {@code DrawingView} containing the current selection.
     * @param viewCoordinates The {@code view} coordinates representing the point of interest.
     * 
     * @return The {@code Figure} within the current selection containing the specified coordinates, or {@code null} if not found.
     * 
     * @author Paweł Kasztura
     */
    @FeatureEntryPoint(value = "SelectionTool")
    private Figure findFigureInCurrentSelection(DrawingView view, Point2D.Double viewCoordinates) {
        if (isSelectBehindEnabled()) {
            for (Figure f : view.getSelectedFigures()) {
                if (f.contains(viewCoordinates)) {
                    return f;
                }
            }
        }
        return null;
    }

    /**
     * Sets the active {@code tool} for tracking user interactions.
     * 
     * @param newTracker The new {@code Tool} instance to be set as the active tracker.
     */
    @FeatureEntryPoint(value = "SelectionTool")
    protected void setTracker(Tool newTracker) {
        if (tracker != null) {
            tracker.deactivate(getEditor());
            tracker.removeToolListener(trackerHandler);
        }
        tracker = newTracker;
        if (tracker != null) {
            tracker.activate(getEditor());
            tracker.addToolListener(trackerHandler);
        }
    }

    /**
     * Method to get a {@code HandleTracker} which handles user interaction
     * for the specified handle.
     */
    @FeatureEntryPoint(value = "SelectionTool")
    protected HandleTracker getHandleTracker(Handle handle) {
        if (handleTracker == null) {
            handleTracker = new DefaultHandleTracker();
        }
        handleTracker.setHandles(handle, getView().getCompatibleHandles(handle));
        return handleTracker;
    }

    /**
     * Method to get a {@code DragTracker} which handles user interaction
     * for dragging the specified figure.
     */
    protected DragTracker getDragTracker(Figure f) {
        if (dragTracker == null) {
            dragTracker = new DefaultDragTracker();
        }
        dragTracker.setDraggedFigure(f);
        return dragTracker;
    }

    /**
     * Method to get a {@code SelectAreaTracker} which handles user interaction
     * for selecting an area on the drawing.
     */
    @FeatureEntryPoint(value = "SelectionTool")
    protected SelectAreaTracker getSelectAreaTracker() {
        if (selectAreaTracker == null) {
            selectAreaTracker = new DefaultSelectAreaTracker();
        }
        return selectAreaTracker;
    }

    /**
     * Method to set a {@code HandleTracker}. If you specify null, the
     * {@code SelectionTool} uses the {@code DefaultHandleTracker}.
     */
    public void setHandleTracker(HandleTracker newValue) {
        handleTracker = newValue;
    }

    /**
     * Method to set a {@code SelectAreaTracker}. If you specify null, the
     * {@code SelectionTool} uses the {@code DefaultSelectAreaTracker}.
     */
    public void setSelectAreaTracker(SelectAreaTracker newValue) {
        selectAreaTracker = newValue;
    }

    /**
     * Method to set a {@code DragTracker}. If you specify null, the
     * {@code SelectionTool} uses the {@code DefaultDragTracker}.
     */
    public void setDragTracker(DragTracker newValue) {
        dragTracker = newValue;
    }

    /**
     * Returns true, if this tool lets the user interact with handles.
     * <p>
     * Handles may draw differently, if interaction is not possible.
     *
     * @return True, if this tool supports interaction with the handles.
     */
    @Override
    public boolean supportsHandleInteraction() {
        return true;
    }
}
