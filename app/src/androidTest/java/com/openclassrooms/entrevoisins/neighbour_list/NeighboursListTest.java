
package com.openclassrooms.entrevoisins.neighbour_list;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourDetailActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.utils.MatcherUtils;
import com.openclassrooms.entrevoisins.utils.ShowNeighbourDetailsAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static com.openclassrooms.entrevoisins.utils.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public IntentsRule intentsRule = new IntentsRule();

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(MatcherUtils.withIndex(withId(R.id.list_neighbours),0))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(MatcherUtils.withIndex(withId(R.id.list_neighbours),0)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(MatcherUtils.withIndex(withId(R.id.list_neighbours),0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(MatcherUtils.withIndex(withId(R.id.list_neighbours),0)).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * When we click an item, the neighbour detail activity in start
     */
    @Test
    public void myNeighboursList_clickAction_shouldStartNeighbourDetailActivity() {

        onView(MatcherUtils.withIndex(withId(R.id.list_neighbours),0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new ShowNeighbourDetailsAction()));

        Intents.intended(IntentMatchers.hasComponent(NeighbourDetailActivity.class.getName()));
    }

    /**
     * When neighbour detail activity in start, the username isn't empty
     */
    @Test
    public void myNeighboursList_neighbourDetailActivity_usernameShouldNotBeEmpty() {

        onView(MatcherUtils.withIndex(withId(R.id.list_neighbours),0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new ShowNeighbourDetailsAction()));

        onView(withId(R.id.username)).check(matches(notNullValue()));
        onView(withId(R.id.username)).check(matches(not(withText(""))));
    }

    /**
     * We ensure that our favorite neighbours list only displaying favorite neighbours
     */
    @Test
    public void myNeighboursList_favoriteNeighbours_shouldOnlyDisplayFavoriteNeighbours() {
        onView(MatcherUtils.withIndex(withId(R.id.list_neighbours),1))
                .check(withItemCount(0));
        onView(MatcherUtils.withIndex(withId(R.id.list_neighbours),0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new ShowNeighbourDetailsAction()));
        onView(ViewMatchers.withId(R.id.addFavorites)).perform(click());
        pressBack();
        onView(MatcherUtils.withIndex(withId(R.id.list_neighbours),1))
                .check(withItemCount(1));

        //onView(withRecyclerView(R.id.list_neighbours).atPositionOnView(1, R.id.username))
       //         .check(matches(withText(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(1).getName())));
    }


}