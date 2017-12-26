/*@Author Dhananjay
* this will show a page which will contain a page manager and dashboard options*/

package ui.pages;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.TranslateTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.Duration;
import ui.core.Comms;
import ui.core.interfaces.CommsMessages;
import ui.core.interfaces.ServerRequestConstants;
import ui.pages.constants.BasicController;
import ui.pages.constants.PageConstants;
import ui.pages.constants.PageContantsForDashboard;
import ui.pages.utilities.ObjectCacher;

public class Dashboard implements PageConstants,BasicController,CommsMessages{

    /*Required objects of pageKeeper.fxml*/
    @FXML
    StackPane dashboardRoot;/*the main pane which containes all the other pans*/

        @FXML
        BorderPane defaultHolder;/*the default page*/

            @FXML
            AnchorPane defaultHolderTop;/*this is used in borderpane top to hold logo and the button which will open dashboard */

        @FXML
        AnchorPane dashboardHolder;/*pane which conatines dashboard*/

            @FXML
            AnchorPane dashBoardControlsHolder;/*the original dashboard which will be shown*/

        @FXML
        AnchorPane pageHolder;/*pane which containas Pagination*/

            @FXML
            Pagination pageManager;/*pagination object inisde pageHolder*/


    /*Required object of fxml controllers*/
    Recent recent;
    ScrollableRecent scrollableRecent;
    Download download;
    UserProfile userProfile;

    //RecentsController recentsController;

    //ServerAccountController serverAccountController;

    DisplayYourIp yourIp;

    /*Supporting Objects*/
    TranslateTransition translateTransitionForDashboard;
    FXMLLoader downloadLoader,recentLoader,scrollableRecentLoader,userProfileLoader;
    String treeString;
    boolean showDownload=false;
    /*Supporting object for core part*/
    StringProperty commandProperty;
    BooleanProperty pathRecieved;



    /*this method will initialize other fxml files cotrollers and add them to pageManager*/
    @FXML protected void initialize() {
        try {

        /*initilize all the pages*/
            initControllers();
            /*Init top and add label and button of Borderpane*/
            initDefaultHolder();
            /*init the dashboard*/
            initDashboardHolder();
            /*inint pages in pagination*/
            initPageHolder();
            /*init Client core part*/
            //initClientCore();
        }catch(Exception e){
            System.out.println("Inside initiakize in pagekeeper"+e);
            e.printStackTrace();
        }
        System.out.println("End of PageKeeper");
    }

    /*This will init all the pages so that they can be added*/
    private void initControllers(){
        try{



            recentLoader=new FXMLLoader();
            recentLoader.setLocation(getClass().getResource("fxml//recent.fxml"));
            recentLoader.load();
            recent=(Recent) recentLoader.getController();

            downloadLoader=new FXMLLoader();
            downloadLoader.setLocation(getClass().getResource("fxml//download.fxml"));
            downloadLoader.load();
            download=(Download) downloadLoader.getController();
            System.out.println("*****************************"+download);

            userProfileLoader=new FXMLLoader();
            userProfileLoader.setLocation(getClass().getResource("fxml//userProfile.fxml"));
            userProfileLoader.load();
            userProfile=(UserProfile) userProfileLoader.getController();



            /*FOR MAIN DISPLAY*/
            scrollableRecentLoader=new FXMLLoader();
            scrollableRecentLoader.setLocation(getClass().getResource("fxml//scrollableRecent.fxml"));
            scrollableRecentLoader.load();
            scrollableRecent=(ScrollableRecent)scrollableRecentLoader.getController();

            yourIp=new DisplayYourIp();

        }catch(Exception e){
            System.out.println("Iniside Dashboard.java initnocntrollers exception"+e);
        }
    }

    private void initDefaultHolder(){
        /*MenuItem mu;

        FontAwesomeIconView item = new FontAwesomeIconView(FontAwesomeIcon.ASL_INTERPRETING);
        item.getStyleClass().add("close_icon");
        FontAwesomeIconView item2 = new FontAwesomeIconView(FontAwesomeIcon.AMERICAN_SIGN_LANGUAGE_INTERPRETING);
        FontAwesomeIconView item3 = new FontAwesomeIconView(FontAwesomeIcon.APPLE);
        item2.getStyleClass().add("close_icon");
        item3.getStyleClass().add("close_icon");
        mu=new MenuItem("Dhananjay",new Button("Hello"));
        circlePopupMenu=new CirclePopupMenu(defaultHolder,MouseButton.SECONDARY);
        circlePopupMenu.getItems().add(mu);
        circlePopupMenu.getItems().add(new MenuItem("Dhananjya1",item2));
        circlePopupMenu.getItems().add(new MenuItem("Dhananjya2",item3));*/
        //circlePopupMenu.getItems().add(new MenuItem("Dhananjya3",item));


        Button show_dashboard;
        Label defaultHolderTitle;
        FontAwesomeIconView show_dashboard_Icon;

        show_dashboard=new Button();
        defaultHolderTitle=new Label("STUDY SHARE");
        show_dashboard_Icon= new FontAwesomeIconView(FontAwesomeIcon.REORDER);

        defaultHolderTitle.setPrefSize(500,100);
        defaultHolderTitle.setFont(Font.font(27));
        show_dashboard.setGraphic(show_dashboard_Icon);
        //show_dashboard_Icon.setOpacity(0.1);
        show_dashboard_Icon.getStyleClass().add("show_icon");
        show_dashboard.getStyleClass().add("show_dashboard");

        show_dashboard.setPrefSize(50,50);
        show_dashboard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dashboardHolder.setVisible(true);
                animateIn();
            }
        });

        AnchorPane.setTopAnchor(show_dashboard, new Double("10"));
        AnchorPane.setLeftAnchor(show_dashboard,new Double("10"));
        AnchorPane.setTopAnchor(defaultHolderTitle,new Double("10"));
        AnchorPane.setLeftAnchor(defaultHolderTitle,new Double("340"));
        defaultHolderTop.getChildren().addAll(show_dashboard,defaultHolderTitle);
//        System.out.println("*********************************************"+scrollableRecent.getRoot());
       // defaultHolder.setBottom(scrollableRecent.getRoot());
        //defaultHolder.setCenter(yourIp.getRoot("192.767867"));
    }

    private void initDashboardHolder(){
        JFXButton hide_dashBoard;
        JFXButton showRecentPage;
        JFXButton showDownloadPage;
        JFXButton showUserProfile;
        FontAwesomeIconView hide_dashboard_icon;

        showDownloadPage=new JFXButton("Download");
        hide_dashBoard=new JFXButton();
        showRecentPage=new JFXButton("Recents");
        showUserProfile=new JFXButton("UserProfile");

        hide_dashboard_icon = new FontAwesomeIconView(FontAwesomeIcon.REMOVE);
        hide_dashboard_icon.getStyleClass().add("hide_icon");
        hide_dashBoard.getStyleClass().add("hide_dashboard");
        hide_dashBoard.setGraphic(hide_dashboard_icon);
        hide_dashBoard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animateOut();
            }
        });
        showRecentPage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dashboardHolder.setVisible(false);
                pageHolder.setVisible(true);
                pageManager.setCurrentPageIndex(PageContantsForDashboard.RECENT_PAGE);
            }
        });
        showUserProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dashboardHolder.setVisible(false);
                pageHolder.setVisible(true);
                pageManager.setCurrentPageIndex(PageContantsForDashboard.USER_PROFILE);
            }
        });
        showDownloadPage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(showDownload){
                    System.out.println("Inside call of download page");
                    dashboardHolder.setVisible(false);
                    pageHolder.setVisible(true);
                    pageManager.setCurrentPageIndex(PageContantsForDashboard.DOWNLOAD_PAGE);
                }
                else
                {
                    System.out.println("Files not received yet");
                }

            }
        });

        AnchorPane.setTopAnchor(hide_dashBoard,new Double("0"));
        AnchorPane.setLeftAnchor(hide_dashBoard,new Double("252"));
        AnchorPane.setTopAnchor(showRecentPage,new Double("100"));
        AnchorPane.setLeftAnchor(showRecentPage,new Double("50"));
        AnchorPane.setTopAnchor(showDownloadPage,new Double("200"));
        AnchorPane.setLeftAnchor(showDownloadPage,new Double("50"));
        AnchorPane.setTopAnchor(showUserProfile,new Double("300"));
        AnchorPane.setLeftAnchor(showUserProfile,new Double("50"));


        dashBoardControlsHolder.getChildren().addAll(hide_dashBoard,showRecentPage,showDownloadPage,showUserProfile);

        dashboardHolder.setVisible(false);
    }

    private void initPageHolder(){
         /*Calls and sets the reuired page to be shown*/
         Button hideCurrentPage;

         hideCurrentPage=new Button();
         hideCurrentPage.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.CLOSE));

        hideCurrentPage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pageHolder.setVisible(false);
            }
        });
        pageManager.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                try{
                    System.out.println("Showing page "+pageIndex);
                    return pageSelector(pageIndex);}
                catch (Exception e){
                    System.out.println("Hello inside call of exception"+e);
                }
                return null;
            }
        });
        AnchorPane.setLeftAnchor(hideCurrentPage,new Double("730"));
        AnchorPane.setTopAnchor(hideCurrentPage,new Double("50"));

        pageHolder.getChildren().add(hideCurrentPage);
        pageHolder.setVisible(false);
    }

    public void initClientCore(){
        try {
            Comms comms = (Comms) ObjectCacher.getObjectCacher().get(Comms.class);


            commandProperty = new SimpleStringProperty();
            pathRecieved = new SimpleBooleanProperty();

            commandProperty.addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                /*Code for command property*/
                }
            });

            pathRecieved.addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue == true) {
                        System.out.println("Path has been received");
                        treeString = commandProperty.getValue();
                        System.out.println("Path has been received"+treeString);
                        showDownload = true;
                    }
                }
            });

            System.out.println("Going to bind it"+comms);
            if(comms.getAccessibleFilePathsRecievedProperty()==null)
                System.out.println("OOPS it is NULL");
            pathRecieved.bindBidirectional(comms.getAccessibleFilePathsRecievedProperty());
            commandProperty.bindBidirectional(comms.getCommandProperty());
        }
        catch (Exception e){
            System.out.println("Inside Exception of the initCore");
            e.printStackTrace();
        }

    }

    private Node pageSelector(int pageIndex) {

        if(pageIndex==PageContantsForDashboard.RECENT_PAGE){
            return recent.getRoot();
        }else if(pageIndex==PageContantsForDashboard.DOWNLOAD_PAGE) {
            if(treeString==null)
                treeString="C:{A,b,c,}";
            return download.getRoot(treeString);
        }if(pageIndex==PageContantsForDashboard.USER_PROFILE){
            return userProfile.getRoot();}

        return new Button("Dhananjay");
    }


    public void setPageKeeper(PageKeeper pk){
        /*Not required here*/
    }

    public Node getRoot(){
        refreshPage();
        return dashboardRoot;
    }

    public void refreshPage(){

    }

    public void animateIn(){
        translateTransitionForDashboard=new TranslateTransition(Duration.millis(600),dashBoardControlsHolder);
        translateTransitionForDashboard.setFromX(-600.0);
        translateTransitionForDashboard.setToX(0.0);
        translateTransitionForDashboard.play();
    }

    public void animateOut() {
        translateTransitionForDashboard=new TranslateTransition(Duration.millis(600),dashBoardControlsHolder);
        translateTransitionForDashboard.setFromX(0.0);
        translateTransitionForDashboard.setToX(-600.0);
        translateTransitionForDashboard.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dashboardHolder.setVisible(false);
            }
        });
        translateTransitionForDashboard.play();
    }

}

class DisplayYourIp extends AnchorPane{
    public Node getRoot(String ip){
        this.getChildren().add(new Label("Your Ip is "+ip));
        return this;
    }
}


