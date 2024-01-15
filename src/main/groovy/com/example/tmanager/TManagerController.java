package com.example.tmanager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TManagerController {


    private ObservableList<ApplicationData> ObList;
    @FXML
    private ScrollPane Scroll;
    @FXML
    private GridPane grid;
    @FXML
    private Circle circ;
    @FXML
    private Pane TitleBar;

    @FXML
    private TextFlow text;
    @FXML
    private TableView<ApplicationData> table;
    @FXML
    private TableColumn<String,String> application;
    @FXML
    private TableColumn<String,String> memory;

    public void initialize() {
        // Access the Text nodes within the TextFlow
        Text textNode1 = new Text("Task Manager");

        //Set Up Columns In TableView
        application.setCellValueFactory(new PropertyValueFactory<>("AppName"));
        memory.setCellValueFactory(new PropertyValueFactory<>("AppMemory"));


        // Add Text nodes to the TextFlow
        text.getChildren().addAll(textNode1);
        text.setTextAlignment(TextAlignment.CENTER);
    }
    public ObservableList<ApplicationData> setApplications(HashMap<ApplicationData, ArrayList<ApplicationData>> AllApps){
        ArrayList<ApplicationData> PureApps = new ArrayList<>();
        for(Map.Entry<ApplicationData, ArrayList<ApplicationData>> entry : AllApps.entrySet()) {
            ApplicationData key = entry.getKey();
            PureApps.add(key);
        }
        ObList = FXCollections.observableList(PureApps);
        table.setItems(ObList);
        return ObList;
    }




    public TManagerController() {
    }
}
