package org.jhotdraw.draw.action.BDD.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DrawingEditor;

public class ThenGroupFigures extends Stage<ThenGroupFigures> {
    @ExpectedScenarioState
    DrawingEditor editor;

    public ThenGroupFigures the_editor_should_have_$_figures(int count) {
        if (editor.getActiveView().getDrawing().getChildren().size() != count) {
            throw new AssertionError("Expected " + count + " figures, but got " + editor.getActiveView().getDrawing().getChildren().size());
        }
        return self();
    }
}
