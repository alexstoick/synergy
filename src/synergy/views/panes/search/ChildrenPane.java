package synergy.views.panes.search;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import synergy.models.Tag;
import synergy.views.panes.AutoCompleteComboBoxListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is a user interface that allows the users to search for a specific child tag.
 * It contains:
 * A list of queries from the user
 * A ComboBox with all the children's names taken from the database
 * A button that allows the users to add the query
 * Created by alexstoick on 3/21/15.
 */
public class ChildrenPane extends HBox {

    private ComboBox comboBox;
    private Button addButton;
    private HBox searchQueryButtons;
    private int height = 50;
    private Set<String> listOfSearchedKids = new HashSet<>();
    private List<String> childrenData;

    /**
     * Creates an instance of the class children pane with the basic features. It instantiates the values of the combobox
     */
    public ChildrenPane() {
        childrenData = new ArrayList<>();
        List<Tag> allTags = Tag.getAllChildrenTags();
        childrenData.addAll(allTags.stream().map(Tag::getValue).collect(Collectors.toList()));
        getStyleClass().add ("my-list-cell");
        setAlignment (Pos.CENTER);
        setUpTextFieldAndSearch ();
        comboBox.setMinHeight(height - 5);
        comboBox.getEditor().setId ("searching");
        comboBox.getEditor().setFont (Font.font ("Arial", FontPosture.ITALIC, 25));
        addButton.setMinHeight (height);
    }

    /**
     * Resets all the fields and clears all the queries
     */
    public void resetAll() {
        searchQueryButtons.getChildren().clear();
        listOfSearchedKids.clear();
	    comboBox.getEditor().setText(null);
    }

    /**
     *
     * @return a {@link java.util.Set} of strings that contains all of the queries
     */
    public Set<String> getListOfSearchedKids() {
        return listOfSearchedKids;
    }

    private void setUpTextFieldAndSearch() {
        searchQueryButtons = new HBox();
        searchQueryButtons.setAlignment(Pos.CENTER);
        comboBox = new ComboBox();
        comboBox.setMaxWidth(260);
        comboBox.setMinWidth(260);
        for (String childName : childrenData) {
            comboBox.getItems().add(childName);
        }
        AutoCompleteComboBoxListener autoComplete = new AutoCompleteComboBoxListener(comboBox);
        comboBox.setOnKeyReleased (autoComplete);
        setHgrow (this, Priority.ALWAYS);
        addButton = new Button("+");
        addButton.setFont (Font.font ("Arial", FontWeight.BOLD, 15));

        EventHandler eventHandler = event -> addAndUpdateChildren ();
        addButton.setOnAction (eventHandler);
        comboBox.getEditor().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent E) -> {
            if (E.getCode() == KeyCode.ENTER) {
                System.out.println("It worked!");
                addChildrenQuery();
                updateChildrenQueries();
            }
        });
        setSpacing(1);
        getChildren().addAll(searchQueryButtons, comboBox, addButton);
    }

    /**
     * Adds what's currently in the field and updates the queries if there are any changes
     */
	public void addAndUpdateChildren() {
		addChildrenQuery();
		updateChildrenQueries();
	}

    private void updateChildrenQueries() {
        searchQueryButtons.getChildren().clear();
        for (String query : listOfSearchedKids) {
            Button queryButton = new Button(query + " - ");
            queryButton.setOnAction(event -> {
                listOfSearchedKids.remove(query);
	            comboBox.getEditor().setText (null);
	            comboBox.setValue (null);
	            System.out.println (listOfSearchedKids.size ());
	            updateChildrenQueries();
            });
            queryButton.setMinHeight(height);
            queryButton.setStyle("-fx-text-fill: #ffffff");
            searchQueryButtons.getChildren().add(queryButton);
        }
    }

    private void addChildrenQuery() {
	    String addedQuery = (String) comboBox.getValue();
	    System.out.println ("$$$$$" + addedQuery);
	    Set<String> hashSet = new HashSet<String>(childrenData) {
            public boolean contains(Object o) {
                String paramStr = (String) o;
                for (String s : this) {
                    if (paramStr.equalsIgnoreCase(s)) return true;
                }
                return false;
            }
        };
        if (addedQuery != null && !listOfSearchedKids.contains(addedQuery) && hashSet.contains
                (addedQuery)) {
            String toAdd = null; // The String to add to the list of search
            for (String s : hashSet) {
                if (s.equalsIgnoreCase(addedQuery)) {
                    toAdd = s;
                    break;
                }
            }
            listOfSearchedKids.add(toAdd);
            comboBox.getEditor().setText (null);
	        comboBox.setValue (null);
        }
    }

}
