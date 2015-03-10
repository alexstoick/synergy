package synergy.newViews;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Josef on 07/03/2015.
 */

public class TaggingArea extends BorderPane {

    private GridPane gridLocation, gridDate, gridNorthern;
    private VBox vBoxChildren, vBoxSuggestion;
    private FlowPane childrenTags, childrenSuggestions;
    private HBox childrenPane, boxLocation;//This is an HBox pane with a textfield and an "add" button
    private Button button1, button2, addChildrenTagButton;
    private Text locationText, childrenText;
    private Label childrenSuggestionLabel;
    private TextField childrenTextField;
    final String[] array = {"Cham", "Mike", "Tobi", "Alex", "Sari", "Codrin", "Josef", "Amit"};

    public TaggingArea() {
        setCenter(returnGridPane(locationGridPane(), childrenVboxPane(), suggestionGRidPane(), dateGRidPane()));
        getStyleClass().setAll("button-bar");
    }


    private GridPane locationGridPane() {

        gridLocation = new GridPane();
        gridLocation.getStyleClass().add("grid");

        boxLocation = new HBox(5);
        button1 = new Button("LocationA");
        button2 = new Button("LocationB");
        button1.setStyle("-fx-text-fill: antiquewhite");
        button2.setStyle("-fx-text-fill: antiquewhite");
        DropShadow shadow1 = new DropShadow();
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                button1.setEffect(shadow1);
                button2.setEffect(null);
                gridLocation.setEffect(shadow1);
                vBoxChildren.setEffect(shadow1);
                vBoxSuggestion.setEffect(shadow1);
                gridDate.setEffect(shadow1);
            }
        });
        DropShadow shadow2 = new DropShadow();
        shadow2.setColor(Color.ANTIQUEWHITE);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                button1.setEffect(null);
                button2.setEffect(shadow2);
                gridLocation.setEffect(shadow2);
                vBoxChildren.setEffect(shadow2);
                vBoxSuggestion.setEffect(shadow2);
                gridDate.setEffect(shadow2);
            }
        });

        locationText = new Text(" Location:");
        locationText.setId("leftText");
        locationText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        boxLocation.getChildren().addAll(button1, button2);
        gridLocation.add(locationText, 0, 0);
        gridLocation.add(boxLocation, 1, 0);


        return gridLocation;
    }
    private VBox childrenVboxPane() {

        vBoxChildren = new VBox();

        gridNorthern = new GridPane();
        childrenTags = new FlowPane(10, 10);
        childrenTags.setPadding(new Insets(10, 10, 10, 10));
        childrenTags.setPrefWrapLength(4.0);
        childrenPane = new HBox(10);

        vBoxChildren.getStyleClass().add("grid");

        childrenTextField = new TextField();
        childrenTextField.setId("searching");
        addChildrenTagButton = new Button("+");
        addChildrenTagButton.setStyle("-fx-text-fill: antiquewhite");

        childrenText = new Text(" Children: ");
        childrenText.setId("leftText");
        childrenText.setFont(Font.font("Arial", FontWeight.BOLD, 20));


        EventHandler childrenEventHandler = new EventHandler() {
            public void handle(Event event) {
                HBox hBox = new HBox();
                String name = childrenTextField.getText();
                Button buttonNames = new Button(name + " -");
                buttonNames.setMinWidth(95.0);
                buttonNames.setStyle("-fx-text-fill: antiquewhite");
                hBox.getChildren().addAll(buttonNames);
                childrenTextField.setText("");
                childrenTags.getChildren().add(hBox);

                /*final Integer[] selectedIndexes = photosPanel.getSelectedIndexesAsArray ();
                Tag tag = new Tag(Tag.TagType.KID, (String) childrenComboBox.getSelectedItem());
                for ( int i = 0 ; i < selectedIndexes.length; ++ i ) {
                    photosPanel.getPhotos ().get (selectedIndexes[ i ]).addTag (tag);
                }
                updateChildrenTags();*/

            }
        };
        childrenTextField.setOnAction(childrenEventHandler);
        addChildrenTagButton.setOnAction(childrenEventHandler);

        childrenPane.getChildren().addAll(childrenTextField, addChildrenTagButton);

        gridNorthern.add(childrenText, 0, 0);
        gridNorthern.add(childrenPane, 1, 0);

        vBoxChildren.getChildren().addAll(gridNorthern, childrenTags);
        return vBoxChildren;
    }

    private VBox suggestionGRidPane() {

        vBoxSuggestion = new VBox();

        childrenSuggestionLabel = new Label(" Suggestions: ");
        childrenSuggestionLabel.setStyle("-fx-text-fill: antiquewhite");
        childrenSuggestionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        childrenSuggestions = new FlowPane(10, 10);
        childrenSuggestions.setPadding(new Insets(10, 10, 10, 10));
        childrenSuggestions.setPrefWrapLength(4.0);
        vBoxSuggestion.getStyleClass().add("grid");

        String[] suggestions = getSuggestions();
        for (int i = 0; i < suggestions.length; i++) {
            HBox hBox = new HBox();
            String suggestion = (suggestions[i] + " ");
            Button buttonName = new Button(suggestion + " +");
            buttonName.setStyle("-fx-text-fill: antiquewhite");
            buttonName.setOnAction(new EventHandler() {
                public void handle(Event event) {
                    childrenTextField.setText("");
                    HBox hBox = new HBox();
                    Button buttonNames = new Button(suggestion + " -");
                    buttonNames.setMinWidth(95.0);
                    buttonNames.setStyle("-fx-text-fill: antiquewhite");
                    hBox.getChildren().addAll(buttonNames);
                    childrenTags.getChildren().add(hBox);

                    /*final Integer[] selectedIndexes = photosPanel.getSelectedIndexesAsArray ();
                    Tag tag = new Tag(Tag.TagType.KID, (String) childrenComboBox.getSelectedItem());
                    for ( int i = 0 ; i < selectedIndexes.length; ++ i ) {
                        photosPanel.getPhotos ().get (selectedIndexes[ i ]).addTag (tag);
                    }
                    updateChildrenTags();*/
                }
            });
            hBox.getChildren().addAll(buttonName);
            childrenSuggestions.getChildren().add(hBox);
        }
        vBoxSuggestion.getChildren().addAll(childrenSuggestionLabel, childrenSuggestions);

        return vBoxSuggestion;

    }


    private GridPane dateGRidPane() {

        gridDate = new GridPane();
        gridDate.getStyleClass().add("grid");

        childrenSuggestionLabel = new Label("Date: ");
        childrenSuggestionLabel.setStyle("-fx-text-fill: antiquewhite");
        childrenSuggestionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gridDate.add(childrenSuggestionLabel, 1, 0);

        return gridDate;
    }


    private VBox returnGridPane(GridPane grid1, VBox box2, VBox box3, GridPane grid4) {

        VBox hb = new VBox(10);
        hb.getStyleClass().add("hbox");
        hb.getChildren().addAll(grid1, box2, box3, grid4);

        return hb;
    }


    //testing with sample suggestions by cham
    public String[] getSuggestions() {
        Random random = new Random();
        String[] suggestions = new String[random.nextInt(8)];
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));
        Collections.shuffle(arrayList);
        for (int i = 0; i < suggestions.length; i++) {
            suggestions[i] = arrayList.get(i);
        }
        return suggestions;
    }

    /**
     * Cham, this is your bit. I Just copy pasted the thing you have
     * commented out.
     */
//    public void setSuggestions(){
//        childrenSuggestion.getChildren().retainAll(childrenSuggestionLabel);
//        String[] suggestions = getSuggestions();
//        for(int i = 0; i < suggestions.length; i++){
//            HBox hBox = new HBox();
//            Label suggestion = new Label(suggestions[i]+ " ");
//            suggestion.setFont(new Font("Arial", 20));
//            suggestion.setTextFill(Color.GRAY);
//            Button addButton = new Button("+");
//            addButton.setOnAction(new EventHandler() {
//                public void handle(Event event) {
//                    HBox hBox = new HBox();
//                    Label label = new Label(suggestion.getText());
//                    label.setFont(new Font("Arial", 20));
//                    label.setTextFill(Color.GRAY);
//                    hBox.getChildren().add(label);
//                    hBox.getChildren().add(new Button("-"));
//                    childrenTags.getChildren().add(hBox);
//                    setSuggestions();
//
//                    /*final Integer[] selectedIndexes = photosPanel.getSelectedIndexesAsArray ();
//                    Tag tag = new Tag(Tag.TagType.KID, (String) childrenComboBox.getSelectedItem());
//                    for ( int i = 0 ; i < selectedIndexes.length; ++ i ) {
//                        photosPanel.getPhotos ().get (selectedIndexes[ i ]).addTag (tag);
//                    }
//                    updateChildrenTags();*/
//                }
//            });
//            hBox.getChildren().add(suggestion);
//            hBox.getChildren().add(addButton);
//            childrenSuggestion.getChildren().add(hBox);
//        }
//    }
    public void addLocationEventHandler(final Button location) {
       /* location.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                final Integer[] selectedIndexes = photosPanel.getSelectedIndexesAsArray();
                System.out.println(Arrays.toString(selectedIndexes) + " location");
                Tag tag = new Tag(Tag.TagType.PLACE, (String) location.getText());
                for (int i = 0; i < selectedIndexes.length; ++i) {
                    photosPanel.getPhotos().get(selectedIndexes[i]).addTag(tag);
                }
                updateLocationTags();
            }
        });*/
    }

    public void updateLocationTags() {
       /* locationTags.getChildren().clear();

        Set<Tag> tagSet = new HashSet<> ();
        final Integer[] selectedIndexes = photosPanel.getSelectedIndexesAsArray ();
        System.out.println ( "Selected Indexes: " + Arrays.toString(selectedIndexes));
        System.out.println( photosPanel.getPhotos ().get (0).getID ());
        for ( int i = 0 ; i < selectedIndexes.length; ++ i ) {
            tagSet.addAll (photosPanel.getPhotos ().get (selectedIndexes[i]).getLocationTags ());
        }
        System.out.println ("List of location tags: " + tagSet ) ;
        Tag[] tagArray = tagSet.toArray (new Tag[tagSet.size()]);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(0, 10, 0 ,0));
        if ( tagArray.length > 0 )  {
            for (int i = 0; i < tagArray.length; ++i) {
                final String tagValue= tagArray[i].getValue ();
                Label label = new Label();
                label.setText(tagValue);
                hBox.getChildren().add(label);
            }
        }
        locationTags.getChildren().add(hBox);*/
    }

    public void updateChildrenTags() {
        /*locationTags.getChildren().clear();
        Set<Tag> tagSet = new HashSet<>();
        final Integer[] selectedIndexes = photosPanel.getSelectedIndexesAsArray ();
        for ( int i = 0 ; i < selectedIndexes.length; ++ i ) {
            tagSet.addAll (photosPanel.getPhotos ().get (selectedIndexes[i]).getChildTags ());
        }
        System.out.println ("List of children tags: " + tagSet);
        Tag[] tagArray = tagSet.toArray (new Tag[tagSet.size()]);

        if ( tagArray.length > 0 )  {
            for (int i = 0; i < tagArray.length; ++i) {
                final String tagValue= tagArray[i].getValue ();

                HBox hBox = new HBox();
                Label label = new Label();
                label.setText(tagValue);

                Button removeButton = new Button("-");
                removeButton.setOnAction(new EventHandler(){
                    public void handle(Event e){
                        Tag tag = new Tag(Tag.TagType.KID, tagValue);
                        for(int i = 0; i < selectedIndex.length; ++i){
                            photosPanel.getPhotos ().get(selectedIndexes[i]).removeTag (tag);
                        }
                        updateChildrenTags();
                    }
                });
                hBox.getChildren().add(label);
                hBox.getChildren().add(removeButton);
                childrenTags.getChildren().add(hBox);

            }
        }*/
    }
}
