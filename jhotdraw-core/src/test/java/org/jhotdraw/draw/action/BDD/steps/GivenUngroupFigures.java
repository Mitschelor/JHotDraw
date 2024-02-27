package org.jhotdraw.draw.action.BDD.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.Figure;

import java.util.Set;

public class GivenUngroupFigures extends Stage<GivenUngroupFigures> {
    @ExpectedScenarioState
    DrawingEditor editor;

    public GivenUngroupFigures user_has_selected_figures(Set<Figure> figures) {
        editor = new DefaultDrawingEditor();
        editor.add(new DefaultDrawingView());
        Drawing drawing = new DefaultDrawing();
        drawing.addAll(figures);
        editor.getActiveView().setDrawing(drawing);
        for (Figure figure : figures) {
            editor.getActiveView().addToSelection(figure);
        }
        return self();
    }
}
