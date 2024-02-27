package org.jhotdraw.draw.action.steps;

import com.tngtech.jgiven.Stage;

public class WhenGroupPushed extends Stage<WhenGroupPushed> {
    public WhenGroupPushed user_pushes_group_button() {
        return self();
    }
    /*@ProvidedScenarioState
    private DrawingEditor editor;

    public void user_pushes_group_button() {
        Action groupAction = new GroupAction(editor);
        groupAction.actionPerformed(null);
    }*/
}
