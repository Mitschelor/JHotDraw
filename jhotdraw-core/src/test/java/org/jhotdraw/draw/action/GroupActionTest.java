package org.jhotdraw.draw.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;

public class GroupActionTest {

    private DrawingEditor editor;
    private DrawingView view;
    private GroupingManager groupingManager;

    @Before
    public void setUp() {
        editor = mock(DrawingEditor.class);
        view = mock(DrawingView.class);
        groupingManager = mock(GroupingManager.class);
        Drawing drawing = mock(Drawing.class);
        when(editor.getActiveView()).thenReturn(view);
        when(view.getSelectedFigures()).thenReturn(new HashSet<>());
        when(view.getDrawing()).thenReturn(drawing);
        doNothing().when(groupingManager).groupFigures();
    }

    @Test
    public void testConstructor() {
        GroupAction groupAction = new GroupAction(editor);

        assertNotNull(groupAction);
    }

    @Test
    public void testUpdateEnabledState() {
        GroupAction groupAction = new GroupAction(editor);

        assertFalse(groupAction.isEnabled());

        when(view.getSelectionCount()).thenReturn(2);
        groupAction.updateEnabledState();
        assertTrue(groupAction.isEnabled());

        when(view.getSelectionCount()).thenReturn(0);
        groupAction.updateEnabledState();
        assertFalse(groupAction.isEnabled());
    }

    @Test
    public void testActionPerformed() {
        GroupFigure groupFigure = new GroupFigure();
        GroupAction groupAction = spy(new GroupAction(editor, groupFigure, groupingManager));

        when(view.getSelectionCount()).thenReturn(2);
        when(view.getSelectedFigures()).thenReturn(new HashSet<>());

        groupAction.actionPerformed(null);

        verify(view, times(1)).getSelectedFigures();
        verify(groupingManager, times(1)).groupFigures();
        verify(groupAction, times(1)).canGroup();
        verify(groupAction, times(1)).fireUndoableEditHappened(any());
    }

    @Test
    public void testActionPerformedWithInsufficientSelection() {
        GroupFigure groupFigure = new GroupFigure();
        GroupAction groupAction = new GroupAction(editor, groupFigure);

        when(view.getSelectionCount()).thenReturn(1);

        groupAction.actionPerformed(null);

        verify(view, never()).getSelectedFigures();
        verify(groupingManager, never()).groupFigures();
    }
}

