package ui.pages;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.icons525.Icons525;
import de.jensd.fx.glyphs.icons525.Icons525View;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import ui.core.Comms;
import ui.pages.constants.BasicController;
import ui.pages.constants.PageConstants;
import ui.pages.utilities.ObjectCacher;

import javax.swing.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login implements PageConstants,BasicController{


    /*Objects fo local fxml*/
    @FXML
    AnchorPane ipPane;
    @FXML
    AnchorPane loginRoot;
    @FXML
    Button btnLogin, btnServerIP;
    @FXML
    CustomPasswordField password;
    @FXML
    CustomTextField username;
    @FXML
    Label userlbl;
    @FXML
    Label passwordlbl;
    @FXML
    TextField txtIpAddress;
    /*object of other fxml*/
    PageKeeper pageKeeper;

    private TranslateTransition userSwipe;
    private TranslateTransition passwordSwipe;
    private TranslateTransition translateTransitionForIPPane;
    /*object used for some use*/
    FXMLLoader fxmlLoader;


    @FXML protected void initialize(){
        System.out.println("The vlaue of the location is"+System.getProperty("user.dir"));
        Icons525View lockIcon = new Icons525View(Icons525.LOCK);
        //lockIcon.setStyle("-fx-fill: #8A0808;");
        lockIcon.setFill(Color.web("#4ec1e3"));
        lockIcon.setStyle("-glyph-size:28px;");
        FontAwesomeIconView userIcon = new FontAwesomeIconView(FontAwesomeIcon.USER);
        userIcon.setFill(Color.web("#4ec1e3"));
        userIcon.setStyle("-glyph-size:28px;");


        FontAwesomeIconView loginIcon = new FontAwesomeIconView(FontAwesomeIcon.SIGN_OUT);
        loginIcon.setFill(Color.web("#4ec1e3"));
        loginIcon.setStyle("-glyph-size:28px;");
        loginIcon.setId("loginIcon");
        JFXButton jfoenixButton = new JFXButton("JFoenix Button");
        password.setRight(lockIcon);
        username.setRight(userIcon);
        btnLogin.setGraphic(loginIcon);
        JFXButton button = new JFXButton("Raised Button".toUpperCase());
        button.getStyleClass().add("button-raised");
        System.out.println("Start of Login");

        userSwipe = new TranslateTransition(Duration.millis(300), userlbl);
        passwordSwipe = new TranslateTransition(Duration.millis(300), passwordlbl);

        username.focusedProperty().addListener((ov, oldV, newV)->{
            if(newV){
                usernameAnimate();
            }
            else if(!newV){
                if(username.getText().length()<1)
                {
                    username.setStyle("-fx-border-color: #ff0000");

                }

                else if(username.getText().length()>1)
                {
                    username.setStyle("-fx-border-color: transparent");
                }
                reverseUsernameAnimate();
            }
        });

        password.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnLogin.fire();
            }
        });

        password.focusedProperty().addListener((ov, oldV, newV)->{
            if(newV){
                if(password.getText().length()<1)
                {
                    password.setStyle("-fx-border-color: #d01f38");

                }
                else if(password.getText().length()>1)
                {
                    password.setStyle("-fx-border-color: transparent");
                }

                passwordAnimate();
            }
            else if(!newV){
                reversePasswordAnimate();
            }
        });
        try{
        /*No need to call fxmlLoader.load() as the file is already loaded*/}
        catch(Exception e){
            System.out.println("Initialize of login"+e);
        }
        /*CustomTextField customTextField=new CustomTextField();
        customTextField.setRight(icon);
        AnchorPane.setLeftAnchor(customTextField,new Double("100"));
        AnchorPane.setTopAnchor(customTextField,new Double("100"));
        loginRoot.getChildren().add(customTextField);*/
    }

    private void reverseUsernameAnimate() {
        if(username.getText().length() < 1) {
            userSwipe.setFromY(-45);
            userSwipe.setToY(0);
            userSwipe.setFromX(-10);
            userSwipe.setToX(0);
            userSwipe.play();
        }
    }

    private void usernameAnimate() {
        if(username.getText().length() < 1) {
            userSwipe.setFromY(0);
            userSwipe.setToY(-45);
            userSwipe.setFromX(0);
            userSwipe.setToX(-10);
            userSwipe.play();
        }
    }

    private void reversePasswordAnimate() {
        if(password.getText().length() < 1) {
            passwordSwipe.setFromY(-45);
            passwordSwipe.setToY(0);
            passwordSwipe.setFromX(-10);
            passwordSwipe.setToX(0);
            passwordSwipe.play();
        }
    }

    private void passwordAnimate() {
        if(password.getText().length() < 1) {
            passwordSwipe.setFromY(0);
            passwordSwipe.setToY(-45);
            passwordSwipe.setFromX(0);
            passwordSwipe.setToX(-10);
            passwordSwipe.play();
        }
    }
    @FXML protected void validateLogin(ActionEvent ae)
    {
        username.setText("Ashu");
        password.setText("helloWorld123");
        Comms c= (Comms) ObjectCacher.getObjectCacher().get(Comms.class);
        try {
            if (validateUsername(ae) && validatePassword(ae)) {
            /*Login sucessful*/

                System.out.println("Value of pagekeeper"+pageKeeper);
                if(c.checkAuthentication(username.getText(),password.getText())) {
                    ObjectCacher.getObjectCacher().put(String.class,username.getText()+">"+password.getText());

                    System.out.println("YOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                    pageKeeper.pageManager.setCurrentPageIndex(PageConstants.DASHBOARD_PAGE);
                    Dashboard dashboard=(Dashboard) ObjectCacher.getObjectCacher().get(Dashboard.class);
                    dashboard.initClientCore();
                }
            } else {
                System.out.println("Something is wrong plz check again");
                password.setText("");
                username.setText("");
            /*Plz write the code to show error message @Vishal*/
            }
        }catch(Exception e){
            System.out.println("inside call of button click of login button");
        }

    }
    /*chutya return type should be boolean*/
    private boolean validatePassword(ActionEvent ae)
    {
        if((password.getText().length() == 0)||(password.getText().length() < 8))
            return false;
        return true;

    }

    private boolean validateUsername(ActionEvent ae)
    {
        if(username.getText().length() == 0)
            return false;
        return true;
    }

    public Node getRoot(){
        refreshPage();
        return loginRoot;
    }

    public void animateOut() {
        translateTransitionForIPPane =new TranslateTransition(Duration.millis(600),ipPane);
        translateTransitionForIPPane.setFromY(0.0);
        translateTransitionForIPPane.setToY(-700.0);
        translateTransitionForIPPane.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ipPane.setVisible(false);
            }
        });
        translateTransitionForIPPane.play();
    }

    public void setPageKeeper(PageKeeper pg){
        pageKeeper=pg;
    }

    public void refreshPage(){
    }

    public void serverCheck(ActionEvent actionEvent) {

        btnServerIP.setDisable(true);
        new Thread(){
            public void run(){

                System.out.println("Inisde call of server check");
                if(validateAddress()) {
                    try {
                        System.out.println("Value of ipAddress is"+txtIpAddress.getText());
                        Socket user = new Socket(txtIpAddress.getText(), 44444);
                        System.out.println(user);
                        Comms c = new Comms(user.getInetAddress());
                        ObjectCacher.getObjectCacher().put(Comms.class, c);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new Alert(Alert.AlertType.INFORMATION, "Successfully Connnected").showAndWait();
                                animateOut();
                            }
                        });

                    } catch (ConnectException connect) {
                        System.out.println("Opps connect Exception");
                        System.out.println("Something is wrong");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new Alert(Alert.AlertType.ERROR, "ConnnectException, IP Maybe Incorrect").showAndWait();
                                txtIpAddress.setText("");
                                btnServerIP.setDisable(false);
                            }
                        });

                    } catch (Exception e) {
                        System.out.println("This is exception of someting inside server check");
                        e.printStackTrace();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new Alert(Alert.AlertType.ERROR, "Some other Kind Exception").showAndWait();
                                txtIpAddress.setText("");
                                btnServerIP.setDisable(false);
                            }
                        });

                    }

                }else{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new Alert(Alert.AlertType.ERROR, "IP Maybe Incorrect or Empty").showAndWait();
                            btnServerIP.setDisable(false);
                        }
                    });

                }



            }
        }.start();
    }

    private boolean validateAddress(){
        try {
            Pattern pattern = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
            Matcher matcher = pattern.matcher(txtIpAddress.getText());
            if (matcher.find())
                return true;
        }catch (Exception e){System.out.println("Plxz syop i am in validate address"+e.getStackTrace());}
        return false;
    }
}
