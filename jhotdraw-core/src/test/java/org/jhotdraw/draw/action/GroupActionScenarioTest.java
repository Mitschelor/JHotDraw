package org.jhotdraw.draw.action;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.draw.action.steps.ThenTwoFiguresGrouped;
import org.jhotdraw.draw.action.steps.GivenTwoSelectedFigures;
import org.jhotdraw.draw.action.steps.WhenGroupPushed;
import org.testng.annotations.Test;

public class GroupActionScenarioTest extends ScenarioTest<GivenTwoSelectedFigures, WhenGroupPushed, ThenTwoFiguresGrouped> {
    @Test
    public void testTwoFiguresGrouped() {
        given().user_has_selected_two_figures();
        when().user_pushes_group_button();
        then().two_figures_should_be_grouped();
    }
}
