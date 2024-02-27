package org.jhotdraw.draw.action.steps;

import com.tngtech.jgiven.Stage;

public class GivenTwoSelectedFigures extends Stage<GivenTwoSelectedFigures> {
    // @ProvidedScenarioState
    // private DrawingEditor editor;

    /*public TwoSelectedFigures user_has_selected_two_figures() {
        editor = new DefaultDrawingEditor();
        editor.add(new DefaultDrawingView());
        Drawing drawing = new DefaultDrawing();
        drawing.add(new BezierFigure());
        drawing.add(new DiamondFigure());
        editor.getActiveView().setDrawing(drawing);
        editor.getActiveView().addToSelection(drawing.getChild(0));
        editor.getActiveView().addToSelection(drawing.getChild(1));
        return self();
    }*/

    public GivenTwoSelectedFigures user_has_selected_two_figures() {
        return self();
    }
}
