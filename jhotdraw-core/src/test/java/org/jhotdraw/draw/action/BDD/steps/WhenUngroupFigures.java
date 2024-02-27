package org.jhotdraw.draw.action.BDD.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.UngroupAction;

public class WhenUngroupFigures extends Stage<WhenUngroupFigures> {
    @ExpectedScenarioState
    DrawingEditor editor;

    public WhenUngroupFigures user_ungroups_the_figures() {
        UngroupAction ungroupAction = new UngroupAction(editor);
        ungroupAction.actionPerformed(null);
        return self();
    }
}
