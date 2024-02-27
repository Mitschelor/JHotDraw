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
    private Figure selectedFigure;

    @Before
    public void setUp() {
        editor = mock(DrawingEditor.class);
        view = mock(DrawingView.class);
        prototype = mock(CompositeFigure.class);
        groupingManager = mock(GroupingManager.class);
        selectedFigure = mock(Figure.class);
        when(editor.getActiveView()).thenReturn(view);
    }

    @Test
    public void testConstructor() {
        UngroupAction ungroupAction = new UngroupAction(editor);

        assertNotNull(ungroupAction);
    }

    @Test
    public void testConstructorWithPrototype() {
        UngroupAction ungroupAction = new UngroupAction(editor, prototype);

        assertNotNull(ungroupAction);
    }

    @Test
    public void testConstructorWithPrototypeAndGroupingManager() {
        UngroupAction ungroupAction = new UngroupAction(editor, prototype, groupingManager);

        assertNotNull(ungroupAction);
    }

    @Test
    public void shouldCorrectlyUpdateEnabledState() {
        CompositeFigure prototype = mock(CompositeFigure.class);
        Set<Figure> selectedFigures = mock(HashSet.class);
        Iterator<Figure> iterator = mock(Iterator.class);
        Class<Figure> mockClass = mock(Class.class);

        when(view.getSelectedFigures()).thenReturn(selectedFigures);
        when(selectedFigures.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true, false);
        when(iterator.next()).thenReturn(selectedFigure);
        when(prototype.includes(selectedFigure)).thenReturn(true);
        when(selectedFigure.getClass()).thenReturn(mockClass);
        when(prototype.getClass()).thenReturn(mockClass);

        UngroupAction ungroupAction = new UngroupAction(editor, prototype);
        ungroupAction.updateEnabledState();
        assertTrue(ungroupAction.isEnabled());

        when(view.getSelectedFigures()).thenReturn(Collections.singleton(selectedFigure));
        when(prototype.includes(selectedFigure)).thenReturn(false);
        ungroupAction.updateEnabledState();
        assertFalse(ungroupAction.isEnabled());
    }


    @Test
    public void testActionPerformed() {
        UngroupAction ungroupAction = spy(new UngroupAction(editor, prototype, groupingManager));

        when(ungroupAction.canUngroup()).thenReturn(true);
        ungroupAction.actionPerformed(null);

        verify(groupingManager, times(1)).ungroupFigures();
    }
}
