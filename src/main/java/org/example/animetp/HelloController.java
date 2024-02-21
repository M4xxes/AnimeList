package org.example.animetp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class HelloController {
    public TextField getTitleTextField() {
        return titleTextField;
    }

    public void setTitleTextField(TextField titleTextField) {
        this.titleTextField = titleTextField;
    }

    public TextField getNbrEpisodesTextField() {
        return nbrEpisodesTextField;
    }

    public void setNbrEpisodesTextField(TextField nbrEpisodesTextField) {
        this.nbrEpisodesTextField = nbrEpisodesTextField;
    }

    public TextField getNoteTextField() {
        return noteTextField;
    }

    public void setNoteTextField(TextField noteTextField) {
        this.noteTextField = noteTextField;
    }

    public TextField getCreateurTextField() {
        return CreateurTextField;
    }

    public void setCreateurTextField(TextField createurTextField) {
        CreateurTextField = createurTextField;
    }

    public TextField getDescriptionTextField() {
        return DescriptionTextField;
    }

    public void setDescriptionTextField(TextField descriptionTextField) {
        DescriptionTextField = descriptionTextField;
    }

    public TextField getLienStreamTextField() {
        return LienStreamTextField;
    }

    public void setLienStreamTextField(TextField lienStreamTextField) {
        LienStreamTextField = lienStreamTextField;
    }
    @FXML
    private TextField titleTextField;

    @FXML
    private TextField nbrEpisodesTextField;

    @FXML
    private TextField noteTextField;
    @FXML
    private TextField CreateurTextField;
    @FXML
    private TextField DescriptionTextField;
    @FXML
    private TextField LienStreamTextField;

    @FXML
    private TableView<Anime> animeTableView;
    @FXML
    private TableColumn<Anime, String> titleColumn;
    @FXML
    private TableColumn<Anime, String> nbrEpisodesColumn;
    @FXML
    private TableColumn<Anime, String> noteColumn;
    @FXML
    private TableColumn<Anime, String> createurColumn;
    @FXML
    private TableColumn<Anime, String> descriptionColumn;
    @FXML
    private TableColumn<Anime, String> lienStreamColumn;

    private ObservableList<Anime> animeList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        nbrEpisodesColumn.setCellValueFactory(new PropertyValueFactory<>("nbrEpisodes"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        createurColumn.setCellValueFactory(new PropertyValueFactory<>("createur"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        lienStreamColumn.setCellValueFactory(new PropertyValueFactory<>("lienStream"));

        loadCSVData();
        animeTableView.setItems(animeList);
    }

    private void loadCSVData() {
        Path csvPath = Paths.get("anime_list.csv");
        if (Files.exists(csvPath)) {
            try (BufferedReader br = Files.newBufferedReader(csvPath)) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        Anime anime = new Anime(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                        animeList.add(anime);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Le fichier CSV n'existe pas : " + csvPath.toAbsolutePath());
        }
    }
    @FXML
    protected void handleEditButtonClick(ActionEvent event) {
        Anime selectedAnime = animeTableView.getSelectionModel().getSelectedItem();
        if (selectedAnime != null) {
            Anime updatedAnime = showEditForm(selectedAnime);

            if (updatedAnime != null) {
                int selectedIndex = animeList.indexOf(selectedAnime);
                animeList.set(selectedIndex, updatedAnime);

                // Refresh TableView
                animeTableView.refresh();

                // Save to CSV
                saveToCSV();
            }
        } else {
            // No item is selected
            // Handle this situation (e.g., show an alert to the user)
        }
    }

    // Hypothetical method that shows the edit form and returns the updated Anime object
    private Anime showEditForm(Anime animeToEdit) {
        return null;
    }
    @FXML
    protected void handleDeleteButtonClick(ActionEvent event) {
        Anime selectedAnime = animeTableView.getSelectionModel().getSelectedItem();
        if (selectedAnime != null) {
            animeList.remove(selectedAnime); // Remove from the observable list
            animeTableView.setItems(animeList); // Update the TableView
            saveToCSV(); // Update the CSV
        } else {
            // No item is selected
            // Handle this situation (e.g., show an alert to the user)
        }
    }
    @FXML
    protected void ButtonValider(ActionEvent event) {
        saveToCSV();
        loadCSVData();
        goToHomePage(event);
    }

    private void goToHomePage(ActionEvent event) {
        try {
            Parent pageAccueilParent = FXMLLoader.load(getClass().getResource("PageAccueil.fxml"));
            Scene pageAccueilScene = new Scene(pageAccueilParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(pageAccueilScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ValidePage() {
        String title = titleTextField.getText();
        String nbrEpisodes = nbrEpisodesTextField.getText();
        String note = noteTextField.getText();
        String createur = CreateurTextField.getText();
        String description = DescriptionTextField.getText();
        String lienStream = LienStreamTextField.getText();

        writeToCSV(new String[] {title, nbrEpisodes, note, createur,description,lienStream});
    }

    private void writeToCSV(String[] data) {
        String csvFile = "anime_list.csv";
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFile), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            String line = String.join(",", data);
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveToCSV() {
        String title = titleTextField.getText();
        String nbrEpisodes = nbrEpisodesTextField.getText();
        String note = noteTextField.getText();
        String createur = CreateurTextField.getText();
        String description = DescriptionTextField.getText();
        String lienStream = LienStreamTextField.getText();
        List<String> dataLines = List.of(title, nbrEpisodes, note, createur, description, lienStream);
        Path csvPath = Paths.get("anime_list.csv");

        try (BufferedWriter bw = Files.newBufferedWriter(csvPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            bw.newLine();
            bw.write(String.join(",", dataLines));
            bw.newLine();
            for (Anime anime : animeList) {
                String dataLine = String.join(",",
                        anime.getTitle(),
                        anime.getNbrEpisodes(),
                        anime.getNote(),
                        anime.getCreateur(),
                        anime.getDescription(),
                        anime.getLienStream());
                bw.write(dataLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void changePage(ActionEvent event) {
        try {
            Parent nextPageParent = FXMLLoader.load(getClass().getResource("AnimeList.fxml"));
            Scene nextPageScene = new Scene(nextPageParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(nextPageScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void ButtonQuitter(ActionEvent event) {
        try {
            Parent nextPageParent = FXMLLoader.load(getClass().getResource("PageAccueil.fxml"));
            Scene nextPageScene = new Scene(nextPageParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(nextPageScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void refreshPage(ActionEvent event) {
        animeList.clear();

        loadCSVData();
        animeTableView.refresh();
    }

}
