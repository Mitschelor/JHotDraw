package org.jhotdraw.draw.action.BDD.scenarios;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.draw.action.BDD.steps.GivenGroupFigures;
import org.jhotdraw.draw.action.BDD.steps.ThenUngroupFigures;
import org.jhotdraw.draw.action.BDD.steps.WhenUngroupFigures;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.RectangleFigure;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class UngroupingTest extends ScenarioTest<GivenGroupFigures, WhenUngroupFigures, ThenUngroupFigures> {
    @Test
    public void ungrouping_selected_figures() {
        Set<Figure> figures = new HashSet<>();
        figures.add(new RectangleFigure(10, 10, 100, 100));
        figures.add(new RectangleFigure(20, 20, 100, 100));
        figures.add(new RectangleFigure(20, 20, 100, 100));

        given().user_has_grouped_figures(figures);
        when().user_ungroups_the_figures();
        then().the_editor_should_have_$_figures(3);
    }

    @Test
    public void ungrouping_no_selected_figures() {
        Set<Figure> figures = new HashSet<>();
        given().user_has_grouped_figures(figures);
        when().user_ungroups_the_figures();
        then().the_editor_should_have_$_figures(0);
    }
}
