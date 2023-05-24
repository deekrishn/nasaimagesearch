package com.example.nasaimagesearch;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ImageSearchApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ImageSearchApplication.class.getResource("search-image.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 600);
        stage.setTitle("NASA Image Search Application");
        stage.setScene(scene);
        stage.show();

        ImageSearchController imageSearchController = fxmlLoader.getController();
        TableView tableView = imageSearchController.getTableView();

        tableView.setRowFactory(tv -> {
            TableRow<ImageSearchModel> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
                        ImageSearchModel rowData = row.getItem();
                        String link = rowData.getLink();
                        String title = rowData.getTitle();
                        String description = rowData.getDescription();
                        String dateCreated = rowData.getDateCreated();
                        System.out.println("Double click on: " + rowData.getLink());
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(stage);
                        VBox dialogBox = new VBox(200);
                        dialogBox.getChildren().add(new Text("Link is" + link));
                        dialogBox.getChildren().add(new Text("Title is" + title));
                        dialogBox.getChildren().add(new Text("Description is" + description));
                        dialogBox.getChildren().add(new Text("Date Created is" + dateCreated));
                        Scene dialogScene = new Scene(dialogBox);
                        dialog.setScene(dialogScene);
                        dialog.show();
                    }
                }
            });

            return row;
        });
    }

    public static void main(String[] args) {
        launch();
    }
}