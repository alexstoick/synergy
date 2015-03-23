package synergy.views.panes.search;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import synergy.models.Photo;
import synergy.views.factories.EndDateCellFactory;
import synergy.views.factories.InitialDateCellFactory;

/**
 * Created by alexstoick on 3/21/15.
 */
public class DatePane extends HBox {

	private String[] arrayMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private String[] arrayCategories = {"Date", "Month", "Period"};
    private ComboBox dateCategories, months, years;
    private DatePicker singleDatePicker,initialDatePicker, endDatePicker;
	private StackPane stackCategories;
	private HBox monthAndYear, periodPane;

	static final Object[] uniqueYears = Photo.getUniqueYears();

	public DatePane () {
		setUpDatePickers ();
		setAlignment (Pos.CENTER);
		getStyleClass().add("my-list-cell");
	}

	public void resetAll() {
		singleDatePicker.setValue(null);
		initialDatePicker.setValue(null);
		endDatePicker.setValue(null);
		months.setValue("");
	}

	public ComboBox getMonths () {
		return months;
	}

	public ComboBox getYears () {
		return years;
	}

	public DatePicker getSingleDatePicker () {
		return singleDatePicker;
	}

	public DatePicker getInitialDatePicker () {
		return initialDatePicker;
	}

	public DatePicker getEndDatePicker () {
		return endDatePicker;
	}

	public ComboBox getDateCategories () {
		return dateCategories;
	}

	private void setUpDatePickers() {
		singleDatePicker = new DatePicker ();
		setPadding (new Insets (0, 0, 0, 8));
		setSpacing(10);
		stackCategories = new StackPane ();

		dateCategories = new ComboBox ();
		dateCategories.getItems().addAll (arrayCategories);
		dateCategories.setValue (arrayCategories[ 0 ]);
		updateCategories ();
		dateCategories.setOnAction (event -> updateCategories ());
		dateCategories.setStyle("-fx-text-fill: #ffffff");

		monthAndYear = new HBox();
		monthAndYear.setAlignment (Pos.CENTER);
		months = new ComboBox();
		months.getItems().addAll(arrayMonths);
		years = new ComboBox();

		periodPane = new HBox();
		periodPane.setSpacing (5);
		periodPane.setAlignment(Pos.CENTER);
		initialDatePicker = new DatePicker();
		endDatePicker = new DatePicker();

        final Callback<DatePicker, DateCell> initialDateDayCellFactory = new InitialDateCellFactory ();
        final Callback<DatePicker, DateCell> endDateDayCellFactory = new EndDateCellFactory (initialDatePicker);

		singleDatePicker.setDayCellFactory (initialDateDayCellFactory);
		singleDatePicker.setShowWeekNumbers (false);
		singleDatePicker.setMaxWidth (200);

		formatDatePicker (initialDatePicker, initialDateDayCellFactory);
		formatDatePicker (endDatePicker, endDateDayCellFactory);

		Font font = new Font("Arial", 20);
		Label fromLabel = new Label("From: ");
		fromLabel.setStyle("-fx-text-fill: #ffffff");
		fromLabel.setFont(font);
		Label toLabel = new Label("To: ");
		toLabel.setStyle("-fx-text-fill: #ffffff");
		toLabel.setFont(font);

		periodPane.getChildren().addAll(fromLabel, initialDatePicker, toLabel, endDatePicker);
		getChildren ().addAll(dateCategories, stackCategories);
	}

	private void formatDatePicker(DatePicker datePicker, Callback<DatePicker, DateCell> factory){
		datePicker.setDayCellFactory(factory);
		datePicker.setMaxWidth(125);
		datePicker.setShowWeekNumbers(false);
		datePicker.setPromptText("dd/mm/yyyy");
	}

	private void updateCategories() {
		if (dateCategories.getValue().equals("Date")) {
			stackCategories.getChildren().clear();
			stackCategories.getChildren().add(singleDatePicker);
		} else if (dateCategories.getValue().equals("Month")) {
			stackCategories.getChildren().clear();
			if ( years.getItems ().size () == 0 ) {
				years.getItems ().addAll (uniqueYears);
			}
			monthAndYear.getChildren ().clear();
			monthAndYear.getChildren().addAll(months, years);
			stackCategories.getChildren().add(monthAndYear);
		} else if (dateCategories.getValue().equals("Period")) {
			stackCategories.getChildren().clear();
			stackCategories.getChildren().add(periodPane);
		}
	}
}

