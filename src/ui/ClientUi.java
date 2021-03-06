/*@Author Dhananjay
* This will initiate the application on server side and will
* load the pagination(pageKeeper.fxml)*/
package ui;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ui.core.Comms;
import ui.pages.constants.ScreenSize;

public class ClientUi extends Application implements ScreenSize {

  //  static Comms client=new Comms();
    public void start(Stage primaryStage){
        try{
            Parent root;
            Scene scene;
            root = FXMLLoader.load(getClass().getResource("pages\\fxml\\pageKeeper.fxml"));
            System.out.println("Loaded pageKeeper.fxml");
            primaryStage.setTitle("StudyShare");
            System.out.println(ScreenSize.MAX_SCREEN_WIDTH);
            scene=new Scene(root,ScreenSize.MAX_SCREEN_WIDTH*0.6,ScreenSize.MAX_SCREEN_HEIGHT*0.91);
            System.out.println(ScreenSize.MAX_SCREEN_WIDTH*0.6);
            System.out.println(ScreenSize.MAX_SCREEN_HEIGHT*0.91);
            scene.getStylesheets().add(getClass().getResource("pages//css//applicationStyles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(Exception e){
            System.out.println("Oops Exception occured :"+e);
        }
    }

    public static void main(String args[]){
        launch();
    }
}
