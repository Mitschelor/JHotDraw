package org.jhotdraw.draw.action.BDD.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.GroupAction;
import org.jhotdraw.draw.figure.Figure;

import java.util.Set;

public class WhenGroupFigures extends Stage<WhenGroupFigures> {
    @ExpectedScenarioState
    DrawingEditor editor;

    public WhenGroupFigures user_groups_the_figures(Set<Figure> figures) {
        for (Figure figure : figures) {
            editor.getActiveView().addToSelection(figure);
        }
        GroupAction groupAction = new GroupAction(editor);
        groupAction.actionPerformed(null);
        return self();
    }
}
