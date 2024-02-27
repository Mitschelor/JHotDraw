package org.jhotdraw.draw.action.BDD.scenarios;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.draw.action.BDD.steps.GivenUngroupFigures;
import org.jhotdraw.draw.action.BDD.steps.ThenGroupFigures;
import org.jhotdraw.draw.action.BDD.steps.WhenGroupFigures;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.RectangleFigure;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class GroupingTest extends ScenarioTest<GivenUngroupFigures, WhenGroupFigures, ThenGroupFigures> {
    @Test
    public void grouping_selected_figures() {
        Set<Figure> figures = new HashSet<>();
        figures.add(new RectangleFigure(10, 10, 100, 100));
        figures.add(new RectangleFigure(20, 20, 100, 100));
        figures.add(new RectangleFigure(20, 20, 100, 100));
        given().user_has_selected_figures(figures);
        when().user_groups_the_figures(figures);
        then().the_editor_should_have_$_figures(1);
    }

    @Test
    public void grouping_no_selected_figures() {
        Set<Figure> figures = new HashSet<>();
        given().user_has_selected_figures(figures);
        when().user_groups_the_figures(figures);
        then().the_editor_should_have_$_figures(0);
    }
}
