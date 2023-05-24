package com.example.nasaimagesearch;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

                        Image image = new Image(link, true);
                        ImageView imageView = new ImageView();
                        imageView.setImage(image);
                        imageView.setX(10);
                        imageView.setY(10);
                        imageView.setFitWidth(575);
                        imageView.setPreserveRatio(true);

                        final Stage dialog = new Stage();
                        dialog.initOwner(stage);
                        Group root = new Group();
                        Scene dialogScene = new Scene(root, 595, 370);
                        final HBox pictureRegion = new HBox();

                        pictureRegion.getChildren().add(imageView);
                        Label labelTitle = new Label("Title: " + title);
                        labelTitle.setTranslateX(150);
                        labelTitle.setTranslateY(25);
                        labelTitle.setWrapText(true);
                        Label labelDescription = new Label("Description: " + description);
                        labelDescription.setTranslateY(100);
                        labelDescription.setWrapText(true);
                        Label labelDateCreated = new Label("Date Created: " + dateCreated);
                        labelDateCreated.setTranslateY(150);
                        labelDateCreated.setWrapText(true);
                        pictureRegion.getChildren().add(labelTitle);
                        pictureRegion.getChildren().add(labelDescription);
                        pictureRegion.getChildren().add(labelDateCreated);

                        root.getChildren().add(pictureRegion);


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