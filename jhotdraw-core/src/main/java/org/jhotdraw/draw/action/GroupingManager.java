package org.jhotdraw.draw.action;

import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.CompositeFigure;
import org.jhotdraw.draw.figure.Figure;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public class GroupingManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private final DrawingView view;
    private final CompositeFigure group;
    private final Collection<Figure> figures;

    public GroupingManager(DrawingView view, CompositeFigure group,
                           Collection<Figure> figures) {
        if (view == null || group == null || figures == null) {
            throw new IllegalArgumentException("Arguments must not be null.");
        }
        this.view = view;
        this.group = group;
        this.figures = figures;
    }


    public void groupFigures() {
        Collection<Figure> sorted = view.getDrawing().sort(figures);
        if (figures.isEmpty()) {
            return;
        }
        int index = view.getDrawing().indexOf(sorted.iterator().next());
        view.getDrawing().basicRemoveAll(figures);
        view.clearSelection();
        view.getDrawing().add(index, group);
        group.willChange();
        for (Figure f : sorted) {
            f.willChange();
            group.basicAdd(f);
        }
        group.changed();
        view.addToSelection(group);
    }

    public Collection<Figure> ungroupFigures() {
        LinkedList<Figure> groupChildren = new LinkedList<>(group.getChildren());
        if (groupChildren.isEmpty()) {
            return groupChildren;
        }
        view.clearSelection();
        group.basicRemoveAllChildren();
        view.getDrawing().basicAddAll(view.getDrawing().indexOf(group), groupChildren);
        view.getDrawing().remove(group);
        view.addToSelection(groupChildren);
        return groupChildren;
    }
}
