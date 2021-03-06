//@author: Ashutosh



package ui.pages;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import ui.pages.constants.BasicController;

import java.util.ArrayList;
////This class is the controller class for Share.fxml file and this is used to initialize the elements of Share.fxml
// which enables the server to send  files to groups by clicking on the group buttons
//The essentials of this class is
//1. This class displays all the groups (in form of buttons) for the current server
//2. By clicking on the desired group a new scene appears which displays all the users for that particular group



//NOTE- THIS CLASS AND ITS CORRESPONDING FXML WILL BE USED WHEN THE SERVER CLICKS Share from dashboard options
public class Recent implements BasicController{

    //Supporting objects
    FXMLLoader loader=new FXMLLoader(getClass().getResource("fxml//startSharing.fxml"));

    //Objects required for this file
    @FXML
    Button btn_share;
    @FXML
    GridPane Grid;
    @FXML
    StackPane parent;

    AnchorPane startSharingRoot;


    ArrayList myArray = new ArrayList();

    //this method is used to initialize all the necessary components before the fxml is displayed
    public void initialize()
    {
        //linking the startSharingController with ShareController
    }

    //method which will create Groups(button) which is accepting arraylist
    //will be displaying buttons in column of 3
    public void getNumberOfGroups(ArrayList arr) throws Exception {
        int arraySize = arr.size();
        int z = 0;

        Button[] btn = new Button[arraySize];
        while (z < arraySize) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    btn[z] = new Button();

                    //event handler functions which loads the the panel of users list when the groups(button) are clicked
                    btn[z].setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {

                            parent.getChildren().add(startSharingRoot);
                        }
                    });

                    //populating the grid layout which displays the group names
                    btn[z].setId("btn_style");
                    btn[z].setPrefHeight(150);
                    btn[z].setPrefWidth(250);
                    btn[z].setText(arr.get(z) + "");
                    btn[z].setAlignment(Pos.BOTTOM_CENTER);
                    Grid.add(btn[z], i, j);
                    z++;
                }
            }
        }
        Grid.setHgap(20);
        Grid.setVgap(20);
    }

    //event handler function for adding data to in the grid layout groups ********** TEMPORARY FUNCTIONS **********
    @FXML
    public void createButtons(ActionEvent ae) throws  Exception {

        // do your job of creating Buttons and pass the arraylist as function parameters
        for(int i=1;i<10;i++)
            myArray.add("Group Number : " + i);
        this.getNumberOfGroups(myArray);
        System.out.println("I was clicked");
    }

    //    ********** getters for this file **********
    public Button getBtn_share() {
        return btn_share;
    }

    public GridPane getMyGrid() {
        return Grid;
    }

    public StackPane getMyParent() {
        return parent;
    }

//    ********** setters for this file **********


    public void setBtn_share(Button btn_share) {
        this.btn_share = btn_share;
    }

    public void setMyGrid(GridPane grid) {
        Grid = grid;
    }

    public void setMyParent(StackPane parent) {
        this.parent = parent;
    }

    public Node getRoot(){
        return parent;
    }

    public void setPageKeeper(PageKeeper pg){
//        hey not required here
    }

    public void refreshPage(){
        /*Code for DHIREN*/
    }
}





