package synergy.views;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import synergy.views.panes.base.BaseHorizontalPane;
import synergy.views.panes.base.BaseVerticalPane;
import synergy.views.panes.tagging.ChildrenPane;
import synergy.views.panes.tagging.DatePane;
import synergy.views.panes.tagging.LocationPane;
import synergy.views.panes.tagging.SuggestionsPane;

/**
 * Created by Josef on 07/03/2015.
 * The TaggingArea class includes the main features of tagging.
 * One can tag according to rooms and names.
 * The date and suggestions are displayed respectively to the pictures selected.
 */

public class TaggingArea extends BorderPane {

	private BaseVerticalPane childrenPane = new ChildrenPane (this);
	private BaseVerticalPane suggestionsPane = new SuggestionsPane (this);
	private BaseHorizontalPane locationPane = new LocationPane ();
	private BaseVerticalPane datePane = new DatePane (this);

    /**
     * updates all of the fields(children/locaiton/suggest/datePane) based on the data
     */
    public void update(){
        childrenPane.update();
        locationPane.update ();
	    suggestionsPane.update ();
	    datePane.update ();
        if(PhotoGrid.getSelectedImages().size() == 0){
	        SliderBar.hide();
        } else{
	        SliderBar.show ();
        }
    }

    /**
     *
     * @return the locationPane
     */
	public BaseHorizontalPane getLocationPane () {
		return locationPane;
	}

    /**
     * creates the UI for the tagging area
     */
	public TaggingArea() {
        setCenter (returnGridPane (locationPane, datePane, childrenPane, suggestionsPane));
    }

    private VBox returnGridPane(Pane box1, Pane box2, Pane box3, Pane box4) {
	    VBox hb = new VBox (10);
        hb.getStyleClass().add("hbox");
        hb.getChildren().addAll(box1, box2, box3, box4);
        return hb;
    }
}
