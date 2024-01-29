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
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;

public class TManagerController{


    private static ObservableList<ApplicationData> ObList;
    @FXML
    private ScrollPane Scroll;
    @FXML
    private GridPane grid;
    @FXML
    private Circle circ;
    @FXML
    private Pane TitleBar;
    private Timer timer;
    @FXML
    private TextFlow text;
    @FXML
    public TableView<ApplicationData> table;
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
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::StartThread, 0, 500, TimeUnit.MILLISECONDS);
        System.out.println("BFB");
    }



    public ObservableList<ApplicationData> setApplications(HashMap<String, ArrayList<ApplicationData>> AllApps) {
        ArrayList<ApplicationData> PureApps = new ArrayList<>();
        for(Map.Entry<String, ArrayList<ApplicationData>> entry : AllApps.entrySet()) {
            int Memory = 0;
            for (int i = 0; i < entry.getValue().size(); i++){
                int CurrMemory = entry.getValue().get(i).getAppMemory();
                Memory = Memory + CurrMemory;
            }
            String key = entry.getKey();
            PureApps.add(new ApplicationData(entry.getKey(),Memory));
        }
        ObList = FXCollections.observableList(PureApps);
        table.setItems(ObList);
        return ObList;
    }
    public void StartThread(){
        Platform.runLater(()-> { setApplications(TaskManagerGUI.Categorise(TaskManagerGUI.GrabData()));
    });
    }







}
