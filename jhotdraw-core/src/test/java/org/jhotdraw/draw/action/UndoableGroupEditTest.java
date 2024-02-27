package org.jhotdraw.draw.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class UndoableGroupEditTest {

    private GroupingManager groupingManager;
    private UndoableGroupEdit undoableGroupEdit;

    @Before
    public void setUp() {
        groupingManager = mock(GroupingManager.class);
        undoableGroupEdit = new UndoableGroupEdit(groupingManager);
    }

    @Test
    public void testConstructor() {
        assertNotNull(undoableGroupEdit);
    }

    @Test
    public void shouldNotAcceptNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> new UndoableGroupEdit(null));
    }

    @Test
    public void testUndo() {
        undoableGroupEdit.undo();

        verify(groupingManager, times(1)).ungroupFigures();
    }

    @Test
    public void testRedo() {
        undoableGroupEdit.undo();
        undoableGroupEdit.redo();

        verify(groupingManager, times(1)).groupFigures();
    }
}

