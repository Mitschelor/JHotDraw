package org.jhotdraw.draw.tool;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.handle.Handle;
import org.junit.Test;
import org.mockito.Mock;

public class SelectionToolTest {

    @Mock
    private DrawingView drawingView;
    @Mock
    private DrawingEditor drawingEditor;

    @Test
    public void testShouldSelectBehind() {
        DrawingEditor mockEditor = mock(DrawingEditor.class);
        
        SelectionTool selectionTool = new SelectionTool();

        selectionTool.activate(mockEditor);

        MouseEvent ctrlEvent = mock(MouseEvent.class);
        when(ctrlEvent.isControlDown()).thenReturn(true);
        assertTrue(selectionTool.shouldSelectBehind(ctrlEvent));

        MouseEvent altEvent = mock(MouseEvent.class);
        when(altEvent.isAltDown()).thenReturn(true);
        assertFalse(selectionTool.shouldSelectBehind(altEvent));

        MouseEvent noModifierEvent = mock(MouseEvent.class);
        when(noModifierEvent.getModifiers()).thenReturn(0);
        assertFalse(selectionTool.shouldSelectBehind(noModifierEvent));
    }

    @Test
    public void testActivateAndDeactivate() {
        DrawingEditor mockEditor = mock(DrawingEditor.class);

        SelectionTool selectionTool = new SelectionTool();

        selectionTool.activate(mockEditor);
        assertTrue(selectionTool.isActive());
        selectionTool.deactivate(mockEditor);
        assertFalse(selectionTool.isActive());
    }

    @Test
    public void testCreateNewTrackerWithHandle() {

        SelectionTool selectionTool = new SelectionTool();

        MouseEvent mockEvent = mock(MouseEvent.class);

        drawingView = mock(DrawingView.class);
        when(drawingView.isEnabled()).thenReturn(true);

        drawingEditor = mock(DrawingEditor.class);
        when(drawingEditor.getActiveView()).thenReturn(drawingView);

        selectionTool.activate(drawingEditor);

        Handle mockHandle = mock(Handle.class);

        Point testPoint = new Point(10, 20);

        when(drawingView.findHandle(testPoint)).thenReturn(mockHandle);
        when(selectionTool.getView()).thenReturn(drawingView);

        Tool result = selectionTool.createNewTracker(mockHandle, mockEvent, drawingView);

        verify(drawingView).findHandle(testPoint);

        assertNotNull(result); // assuming createNewTracker returns a Tool
    }

    @Test
    public void testMousePressed_ClearSelectionWithoutShift() {

        SelectionTool selectionTool = new SelectionTool();

        drawingView = mock(DrawingView.class);

        drawingEditor = mock(DrawingEditor.class);
        when(drawingEditor.getActiveView()).thenReturn(drawingView);

        selectionTool.activate(drawingEditor);

        MouseEvent mouseEvent = mock(MouseEvent.class);

        when(mouseEvent.isShiftDown()).thenReturn(false);
        when(mouseEvent.getPoint()).thenReturn(new Point());
        when(drawingView.getSelectionCount()).thenReturn(1);

        when(selectionTool.getView()).thenReturn(drawingView);

        selectionTool.mousePressed(mouseEvent);

        verify(drawingView, times(1)).clearSelection();
        verify(drawingView, times(1)).setHandleDetailLevel(0);
    }

}
