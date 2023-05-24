package com.example.nasaimagesearch;

import com.example.nasaimagesearch.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import javafx.scene.control.cell.PropertyValueFactory;

public class ImageSearchController {

    @FXML
    private Label numberOfEntriesLabel;
    @FXML
    private TextField searchText;
    @FXML
    private TableView tableView;
    private static final String URL = "https://images-api.nasa.gov/search?q=";

    @FXML
    private TableColumn nasaIdColumn;
    @FXML
    private TableColumn titleColumn;
    @FXML
    private TableColumn descriptionColumn;
    @FXML
    private TableColumn dateCreatedColumn;
    @FXML
    private TableColumn imageHrefColumn;

    @FXML
    protected void onGoButtonClick() {
        nasaIdColumn.setCellValueFactory(new PropertyValueFactory<>("nasaId"));

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));

        imageHrefColumn.setCellValueFactory(new PropertyValueFactory<>("link"));

        String query = searchText.getText();
        int i = 1;
        //while(true) {
        String link = "https://images-api.nasa.gov/search?q=" + query + "&media_type=image&page=" + i;
        i++;
        try {
            List<ImageSearchModel> list =  getResults(link);
            //if(list.isEmpty()) break;
            numberOfEntriesLabel.setText("Number of entries: " + list.size());
            tableView.getItems().addAll(list);
            for(ImageSearchModel image : list) {
                System.out.println("ID is " + image.getNasaId());
                System.out.println("Title is " + image.getTitle());
                System.out.println("Description is " + image.getDescription());
                System.out.println("Date Created is " + image.getDateCreated());
                System.out.println("Link is " + image.getLink());
                //tableView.getItems().add(image);
            }
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        tableView.setRowFactory(tv -> {
            TableRow<ImageSearchModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && !row.isEmpty()) {
                    ImageSearchModel rowData = row.getItem();
                    System.out.println("Double click on: "+rowData.getLink());
                }
            });
            return row;
        });
    }

    public static List<ImageSearchModel> getResults(String query) throws URISyntaxException, IOException, InterruptedException {
        System.out.println("Inside getResults");
        List<ImageSearchModel> list = new ArrayList<>();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(query))
                .GET().build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> httpResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        String response = httpResponse.body();
        Gson gson = new Gson();
        Image image =  gson.fromJson(response, Image.class);
        List<Items> items = image.getCollection().getItems();

        for(Items item : items) {
            List<Data> data = item.getData();
            List<Links> links = item.getLinks();
            String title = "";
            String description = "";
            String dateCreated = "";
            String id = "";
            String href = "";
            for(Data d : data) {
                title = d.getTitle();
                description = d.getDescription();
                dateCreated = d.getDate_created();
                id = d.getNasaId();
            }
            for(Links link : links) {
                href = link.getHref();
            }
            list.add(new ImageSearchModel(id, title, description, dateCreated, href));
        }
        return list;
    }
}