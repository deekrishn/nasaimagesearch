package com.example.nasaimagesearch;

import com.example.nasaimagesearch.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    protected void onGoButtonClick() {
        String s = searchText.getText();
        String query = URL + s + "&media_type=image";

        TableColumn nasaIdColumn = new TableColumn("NasaID");
        nasaIdColumn.setCellValueFactory(new PropertyValueFactory<>("nasaId"));

        TableColumn titleColumn = new TableColumn("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn descriptionColumn = new TableColumn("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn dateCreatedColumn = new TableColumn("Date Created");
        dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));

        TableColumn imageHrefColumn = new TableColumn("Link");
        imageHrefColumn.setCellValueFactory(new PropertyValueFactory<>("link"));

        tableView.getColumns().addAll(nasaIdColumn, titleColumn, descriptionColumn, dateCreatedColumn, imageHrefColumn);
        try {
            List<ImageSearchModel> list =  getResults(query);
            numberOfEntriesLabel.setText(Integer.toString(list.size()));
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