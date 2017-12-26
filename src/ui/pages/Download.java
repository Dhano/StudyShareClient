package ui.pages;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import ui.core.Comms;
import ui.core.interfaces.CommsMessages;
import ui.core.interfaces.ServerRequestConstants;
import ui.pages.constants.BasicController;
import ui.pages.utilities.ObjectCacher;
import ui.resources.CustomDirectory;

import java.io.File;
import java.util.ArrayList;


public class Download implements BasicController {
    @FXML
    AnchorPane parent;

    @FXML
    ScrollPane scrollPane;

    @FXML
    Button downloadBtn;

    CustomDirectory customDirectory;
    String path="";


    @FXML protected void startDownload(ActionEvent actionEvent){
        try {
            System.out.println("Going to download");
            Comms comms = (Comms) ObjectCacher.getObjectCacher().get(Comms.class);
            comms.setSelectedFilesListToBeRequested(customDirectory.getFileList());
            SimpleStringProperty commandProperty = new SimpleStringProperty();
            commandProperty.bindBidirectional(comms.getCommandProperty());
            commandProperty.setValue(CommsMessages.START_DOWNLOADING);
            comms.setServerRequest(CommsMessages.START_DOWNLOADING);


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.showAndWait();
            commandProperty.addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if(newValue.equals(CommsMessages.DOWNLOAD_COMPLETED))
                        alert.close();
                    System.out.println("Done Downloading");
                }
            });
        }
        catch (Exception e){
            System.out.println("Exceptoin while downloading"+e);
        }
    }

    @FXML protected void initialize(){

    }

    void initIt(){
        if(customDirectory!=null)
            parent.getChildren().remove(customDirectory);
        try{
            System.out.println("***********The path is "+path);
            customDirectory=new CustomDirectory(path);
            scrollPane.setContent(customDirectory);
            parent.getChildren().addAll(customDirectory);
            customDirectory.setPrefSize(800,800);
            ArrayList<File> fileList=((Comms)ObjectCacher.getObjectCacher().get(Comms.class)).getDownloadedFiles();
            for(File f:fileList)
                System.out.println(f.getName());
        }
        catch(Exception e){
            System.out.println("Inside try of initpage "+e);
            e.printStackTrace();
        }
    }
    public Node getRoot(){
        initIt();
        return parent;
    }

    public Node getRoot(String path){
        this.path=path;
        initIt();
        return parent;
    }

    public void setPageKeeper(PageKeeper pg){
//        Not required here
    }

    public void refreshPage(){

    }
}
