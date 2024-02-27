package org.jhotdraw.draw.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UngroupActionTest {

    private DrawingEditor editor;
    private DrawingView view;
    private CompositeFigure prototype;
    private GroupingManager groupingManager;
    private CompositeFigure selectedFigure;

    private void setUpCanUngroup(boolean wantToBeTrue) {
        prototype = selectedFigure;
        when(view.getSelectedFigures()).thenReturn(Collections.singleton(selectedFigure));
        when(view.getSelectionCount()).thenReturn(wantToBeTrue ? 1 : 2);
    }

    @Before
    public void setUp() {
        editor = mock(DrawingEditor.class);
        view = mock(DrawingView.class);
        prototype = mock(CompositeFigure.class);
        groupingManager = mock(GroupingManager.class);
        selectedFigure = mock(CompositeFigure.class);
        when(editor.getActiveView()).thenReturn(view);
    }

    @Test
    public void testConstructor() {
        UngroupAction ungroupAction = new UngroupAction(editor);

        assertNotNull(ungroupAction);
    }

    @Test
    public void testConstructorWithPrototype() {
        setUpCanUngroup(true);
        UngroupAction ungroupAction = new UngroupAction(editor, prototype);

        assertNotNull(ungroupAction);
    }

    @Test
    public void testConstructorWithPrototypeAndGroupingManager() {
        setUpCanUngroup(true);
        UngroupAction ungroupAction = new UngroupAction(editor, prototype, groupingManager);

        assertNotNull(ungroupAction);
    }

    @Test
    public void shouldCorrectlyUpdateEnabledState() {
        setUpCanUngroup(true);
        UngroupAction ungroupAction = new UngroupAction(editor, prototype);
        ungroupAction.updateEnabledState();
        assertTrue(ungroupAction.isEnabled());

        setUpCanUngroup(false);
        ungroupAction.updateEnabledState();
        assertFalse(ungroupAction.isEnabled());
    }


    @Test
    public void shouldNotUnGroupWhenCanUngroupIsFalse() {
        setUpCanUngroup(false);
        UngroupAction ungroupAction = spy(new UngroupAction(editor, prototype, groupingManager));
        ungroupAction.actionPerformed(null);

        verify(ungroupAction, times(1)).canUngroup();
        verify(view, never()).getSelectedFigures();
        verify(groupingManager, never()).ungroupFigures();
        verify(ungroupAction, never()).fireUndoableEditHappened(any());
    }
}
