package ui.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import ui.pages.constants.BasicController;
import ui.pages.utilities.ObjectCacher;


import java.io.File;
import java.io.IOException;

public class UserProfile implements BasicController {

    @FXML
    Label lbl_username;
    @FXML
    Label lbl_password;
    @FXML
    ImageView user_image;
    @FXML
    Button btn_upload;

    @FXML
    AnchorPane root;

    String username,password;

    @FXML
    public void notinitialize(){
        try {
            String str = (String) ObjectCacher.getObjectCacher().get(String.class);
            username = str.substring(0, str.indexOf(">"));
            password = str.substring(str.indexOf(">") + 1);
            lbl_password.setText(password);
            lbl_username.setText(username);
            //if (imageExist(username)) {
              //  user_image.setImage(new Image(username + ".png"));
        //    }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    boolean imageExist(String username){
        if(new File(System.getProperty("user.dir")+"/"+username+".png").exists())
            return true;
        return false;
    }


    @FXML public void uploadImage(ActionEvent ae) throws IOException {

            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                System.out.println(file.getPath());
                user_image.setImage(new Image(file.toURL().toExternalForm()));
            }

    }


    @Override
    public Node getRoot() {
        notinitialize();
        return root;
    }

    @Override
    public void setPageKeeper(PageKeeper pg) {

    }

    @Override
    public void refreshPage() {

    }
}
