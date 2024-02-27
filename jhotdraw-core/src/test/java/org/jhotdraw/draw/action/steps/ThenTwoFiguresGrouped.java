package org.jhotdraw.draw.action.steps;

import com.tngtech.jgiven.Stage;

import static org.testng.AssertJUnit.assertEquals;

public class ThenTwoFiguresGrouped extends Stage<ThenTwoFiguresGrouped> {
    public ThenTwoFiguresGrouped two_figures_should_be_grouped() {
        return self();
    }
    /*@ProvidedScenarioState
    private DrawingEditor editor;

    public void two_figures_should_be_grouped() {
        DrawingView view = editor.getActiveView();
        assertEquals(1, view.getSelectionCount());
    }*/
}
