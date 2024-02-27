package org.jhotdraw.draw.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class UndoableUngroupEditTest {

    private GroupingManager groupingManager;
    private UndoableUngroupEdit undoableUngroupEdit;

    @Before
    public void setUp() {
        groupingManager = mock(GroupingManager.class);
        undoableUngroupEdit = new UndoableUngroupEdit(groupingManager);
    }

    @Test
    public void testConstructor() {
        assertNotNull(undoableUngroupEdit);
    }

    @Test
    public void shouldNotAcceptNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> new UndoableUngroupEdit(null));
    }

    @Test
    public void testUndo() {
        undoableUngroupEdit.undo();

        verify(groupingManager, times(1)).groupFigures();
    }

    @Test
    public void testRedo() {
        undoableUngroupEdit.undo();
        undoableUngroupEdit.redo();

        verify(groupingManager, times(1)).ungroupFigures();
    }
}

