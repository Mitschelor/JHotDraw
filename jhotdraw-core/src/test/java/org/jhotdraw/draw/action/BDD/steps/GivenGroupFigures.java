package org.jhotdraw.draw.action.BDD.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.GroupAction;
import org.jhotdraw.draw.figure.Figure;

import java.util.Set;

public class GivenGroupFigures extends Stage<GivenGroupFigures> {
    @ExpectedScenarioState
    DrawingEditor editor;

    public GivenGroupFigures user_has_grouped_figures(Set<Figure> figures) {
        editor = new DefaultDrawingEditor();
        editor.add(new DefaultDrawingView());
        Drawing drawing = new DefaultDrawing();
        drawing.addAll(figures);
        editor.getActiveView().setDrawing(drawing);
        GroupAction groupAction = new GroupAction(editor);
        groupAction.actionPerformed(null);
        return self();
    }
}
