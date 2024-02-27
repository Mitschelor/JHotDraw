package org.jhotdraw.draw.action;

import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.CompositeFigure;
import org.jhotdraw.draw.figure.Figure;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;

public class GroupingManagerTest {
    @Mock
    private DrawingView drawingView;

    @Mock
    private CompositeFigure group;

    @Mock
    private Figure figure1;

    @Mock
    private Figure figure2;

    @Mock
    private Drawing drawing;

    @Before
    public void setUp() {
        drawingView = mock(DrawingView.class);
        group = mock(CompositeFigure.class);
        figure1 = mock(Figure.class);
        figure2 = mock(Figure.class);
        drawing = mock(Drawing.class);
    }

    @Test
    public void shouldCorrectlyGroupFigures() {
        List<Figure> figures = new ArrayList<>();
        figures.add(figure1);
        figures.add(figure2);

        when(drawingView.getDrawing()).thenReturn(drawing);
        when(drawingView.getDrawing().sort(figures)).thenReturn(figures);
        when(drawingView.getDrawing().indexOf(figure1)).thenReturn(0);

        GroupingManager groupingManager = new GroupingManager(drawingView, group, figures);
        groupingManager.groupFigures();
        verify(drawingView.getDrawing(), times(1)).sort(figures);

        for (Figure figure : figures) {
            verify(figure, times(1)).willChange();
        }
        InOrder inOrder = Mockito.inOrder(group);
        inOrder.verify(group).willChange();
        inOrder.verify(group).basicAdd(figure1);
        inOrder.verify(group).basicAdd(figure2);
        inOrder.verify(group).changed();

        InOrder inOrder2 = Mockito.inOrder(drawing);
        inOrder2.verify(drawing).sort(figures);
        inOrder2.verify(drawing).indexOf(figure1);
        inOrder2.verify(drawing).basicRemoveAll(figures);
        inOrder2.verify(drawing).add(0, group);

        verify(drawingView, times(1)).addToSelection(group);
    }

    @Test
    public void shouldCorrectlyUngroupFigures() {
        List<Figure> figures = new ArrayList<>();
        figures.add(figure1);
        figures.add(figure2);

        when(group.getChildren()).thenReturn(figures);
        when(drawing.indexOf(group)).thenReturn(0);
        when(drawingView.getDrawing()).thenReturn(drawing);
        when(drawingView.getDrawing().sort(figures)).thenReturn(figures);
        when(drawingView.getDrawing().indexOf(figure1)).thenReturn(0);

        GroupingManager groupingManager = new GroupingManager(drawingView, group, figures);
        Collection<Figure> returnedFigures = groupingManager.ungroupFigures();
        assertEquals(figures, returnedFigures);
        verify(group, times(1)).basicRemoveAllChildren();
        verify(drawing, times(1)).basicAddAll(0, figures);
        verify(drawing, times(1)).remove(group);
        verify(drawingView, times(1)).addToSelection(figures);
    }

    @Test
    public void shouldCorrectlyGroupFiguresWithEmptyList() {
        List<Figure> figures = new ArrayList<>();

        when(drawingView.getDrawing()).thenReturn(drawing);
        when(drawingView.getDrawing().sort(figures)).thenReturn(figures);

        GroupingManager groupingManager = new GroupingManager(drawingView, group, figures);
        groupingManager.groupFigures();

        verify(drawingView.getDrawing(), times(1)).sort(figures);
        verify(drawing, times(0)).indexOf(any(Figure.class));
        verify(drawing, times(0)).basicRemoveAll(figures);
        verify(drawingView, times(0)).addToSelection(group);
        verify(drawing, times(0)).add(anyInt(), any(Figure.class));
        verify(group, times(0)).willChange();
        verify(group, times(0)).basicAdd(any(Figure.class));
        verify(group, times(0)).changed();
        verify(drawingView, times(0)).addToSelection(group);
    }

    @Test
    public void shouldCorrectlyUngroupFiguresWithEmptyList() {
        List<Figure> figures = new ArrayList<>();

        when(group.getChildren()).thenReturn(figures);
        when(drawing.indexOf(group)).thenReturn(0);
        when(drawingView.getDrawing()).thenReturn(drawing);
        when(drawingView.getDrawing().sort(figures)).thenReturn(figures);

        GroupingManager groupingManager = new GroupingManager(drawingView, group, figures);
        Collection<Figure> returnedFigures = groupingManager.ungroupFigures();
        assertEquals(figures, returnedFigures);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowConstructingGroupingManagerWithNullView() {
        List<Figure> figures = new ArrayList<>();
        new GroupingManager(null, group, figures);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowConstructingGroupingManagerWithNullGroup() {
        List<Figure> figures = new ArrayList<>();
        new GroupingManager(drawingView, null, figures);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowConstructingGroupingManagerWithNullFigures() {
        new GroupingManager(drawingView, group, null);
    }
}
