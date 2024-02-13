package com.example.tmanager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;

public class TManagerController{


    private static ObservableList<ApplicationData> ObList;
    @FXML
    private Pane TitleBar;
    @FXML
    private TextFlow text;
    @FXML
    public TableView<ApplicationData> table;
    @FXML
    private TableColumn<String,String> application;
    @FXML
    private TableColumn<String,String> memory;
    @FXML
    private String Sorting;
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
        ArrayList<ApplicationData> PureApps = new ArrayList<>();//Holds the required data from the hashmap
        for(Map.Entry<String, ArrayList<ApplicationData>> entry : AllApps.entrySet()) {
            int Memory = 0;
            for (int i = 0; i < entry.getValue().size(); i++){
                int CurrMemory = entry.getValue().get(i).getAppMemory();//Condenses The Memory of duplicate applications
                Memory = Memory + CurrMemory;
            }
            PureApps.add(new ApplicationData(entry.getKey(),Memory));
        }
        ArrayList<ApplicationData> ToBeSorted = PureApps;
        if( Sorting != null){//if sortapps or sortmem button is pressed
            ToBeSorted = SortingData(ToBeSorted);//It will either be merged alphanumerically (string for name of app) or by int (int memory value)
        }
        ObList = FXCollections.observableList(ToBeSorted);
        table.setItems(ObList);
        return ObList;
    }

    private ArrayList<ApplicationData> SortingData(ArrayList<ApplicationData> AppsArray) {
        switch (Sorting){
            case "M":
                if (AppsArray == null || AppsArray.size() <= 1) {
                    return AppsArray; // Already sorted
                }

                    int midData = AppsArray.size()/2;
                    ArrayList<ApplicationData> LeftHalf = new ArrayList<>(AppsArray.subList(0, midData));
                    ArrayList<ApplicationData> RightHalf = new ArrayList<>(AppsArray.subList(midData,AppsArray.size()));

                    SortingData(LeftHalf);
                    SortingData(RightHalf);
                    Merge(AppsArray,LeftHalf,RightHalf);
                    break;
            case "A":
                ArrayList JustNames = new ArrayList();
                for (int i = 0; i < AppsArray.size();i++){
                    JustNames.add(AppsArray.get(i).getAppName());
                }
                Collections.sort(JustNames);
                ArrayList<ApplicationData> FinalArray = new ArrayList();
                for (int i = 0; i < AppsArray.size();i++){
                    for (int j = 0; j < AppsArray.size();j++){
                        if (JustNames.get(i) == AppsArray.get(j).getAppName()){
                            FinalArray.add(AppsArray.get(j));
                            //FinalArray.set(i, new ApplicationData("FINAL",0));

                        }
                    }
                }
                AppsArray = FinalArray;
                break;
        }

    return AppsArray;
    }

    private ArrayList<ApplicationData> Merge(ArrayList<ApplicationData> AppsArray, ArrayList<ApplicationData> LeftHalf, ArrayList<ApplicationData> RightHalf) {//merges the subarrays
        int a,b,c;
        a = 0;
        b = 0;
        c = 0;
        while(a < LeftHalf.size() && b < RightHalf.size()) {
            if (LeftHalf.get(a).getAppMemory() <= RightHalf.get(b).getAppMemory()) {
                AppsArray.set(c++, LeftHalf.get(a++));
            }else {
                AppsArray.set(c++, RightHalf.get(b++));
            }
        }
        while (a < LeftHalf.size()) {
                AppsArray.set(c++, LeftHalf.get(a++));
        }
        while (b < RightHalf.size()) {
                AppsArray.set(c++, RightHalf.get(b++));
        }

    return AppsArray;
    }

    public void StartThread(){
        Platform.runLater(()-> { setApplications(TaskManagerGUI.Categorise(TaskManagerGUI.GrabData()));
    });
    }

    @FXML
    private void SortApps(ActionEvent event){
        Sorting = "A";
        event.consume();
    }

    @FXML
    private void SortMem(ActionEvent event){
        Sorting = "M";
        event.consume();
    }
}
