package com.example.tmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import static java.lang.Runtime.getRuntime;


public class TaskManagerGUI extends Application {
    public static HashMap<String, ArrayList<ApplicationData>> ActSes;

    public static void main(String[] args) {
        ArrayList<ApplicationData> AppData = GrabData();
        for (int i = 0; i != AppData.size() - 1; i++) {
            System.out.println(AppData.get(i));
        };
        setActSes(Categorise(AppData));
        launch(args);
    }



    public TaskManagerGUI(){
    }


    public static void setActSes(HashMap<String, ArrayList<ApplicationData>> actSes) {
        ActSes = actSes;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            String path = "C:\\Users\\alexd\\IdeaProjects\\TManager\\src\\main\\groovy\\com\\example\\tmanager\\GUIFrame.fxml";
            File file = new File(path);
            //load fxml
            FXMLLoader loader = new FXMLLoader(file.toURI().toURL());
            Parent root = loader.load();
            //grabs controller id from fxml (TManagerController)
            TManagerController controller = loader.getController();
            //sets the applications from tasklist
            controller.setApplications(ActSes);
            //GUI Config
            primaryStage.setTitle("JavaFX Task Manager");
            primaryStage.setScene(new Scene(root, 750, 500));
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public static HashMap<String, ArrayList<ApplicationData>> Categorise(ArrayList<ApplicationData> x){//Condenses duplicate applications into one.
            HashMap<String, ArrayList<ApplicationData>> CategoriesResult = new HashMap<>();
            ArrayList<String> DifCategories = new ArrayList<>();
            for (int i = 0; i != x.size();i++){
                if(!(DifCategories.contains(x.get(i).getAppName()))){
                    ArrayList<ApplicationData> a = new ArrayList<>();
                    CategoriesResult.put(x.get(i).getAppName(),a);
                    CategoriesResult.get(x.get(i).getAppName()).add(x.get(i));
                    DifCategories.add(x.get(i).getAppName());
                }else{
                    CategoriesResult.get(x.get(i).getAppName()).add(x.get(i));
                }
            }

            return CategoriesResult;
        }

        public static ArrayList<ApplicationData> GrabData(){
            ArrayList<String> processes = new ArrayList<String>();
            try {
                String line;//
                Process p = getRuntime().exec("tasklist.exe /fo csv /nh");
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = input.readLine()) != null) {
                    processes.add(line);//grabs output from tasklist command
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
            ArrayList<ApplicationData> ActiveSessions = new ArrayList<>();
            for (int i = 0; i != processes.size() - 1; i++) {
                //splits the output into application and removes unnecessary information
                List<String> CurrentLine = new ArrayList<>(Arrays.asList(processes.get(i).split(",")));
                if ((!CurrentLine.get(3).equals("\"0\"")) && (CurrentLine.get(2).equals("\"Console\"") == true)) {
                    if(CurrentLine.size()!=5){
                    ActiveSessions.add(new ApplicationData(CurrentLine.get(0).replace("\"",""), Integer.parseInt((CurrentLine.get(4) + CurrentLine.get(5)).replace(" K","").replace("\"",""))));//removes quotation marks from both, and concats items 4 and 5 as the numbers have comas >= 10000, relies on data not being >1000000
                    }
                }
            }
            return ActiveSessions;
        }

    }

