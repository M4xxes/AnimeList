package org.example.animetp;

import javafx.beans.property.SimpleStringProperty;

public class Anime {
    private final SimpleStringProperty title;
    private final SimpleStringProperty nbrEpisodes;
    private final SimpleStringProperty note;
    private final SimpleStringProperty createur;
    private final SimpleStringProperty description;
    private final SimpleStringProperty lienStream;

    public Anime(String title, String nbrEpisodes, String note, String createur, String description, String lienStream) {
        this.title = new SimpleStringProperty(title);
        this.nbrEpisodes = new SimpleStringProperty(nbrEpisodes);
        this.note = new SimpleStringProperty(note);
        this.createur = new SimpleStringProperty(createur);
        this.description = new SimpleStringProperty(description);
        this.lienStream = new SimpleStringProperty(lienStream);
    }

    // Getters
    public String getTitle() { return title.get(); }
    public String getNbrEpisodes() { return nbrEpisodes.get(); }
    public String getNote() { return note.get(); }
    public String getCreateur() { return createur.get(); }
    public String getDescription() { return description.get(); }
    public String getLienStream() { return lienStream.get(); }

    // Setters
    public void setTitle(String value) { title.set(value); }
    public void setNbrEpisodes(String value) { nbrEpisodes.set(value); }
    public void setNote(String value) { note.set(value); }
    public void setCreateur(String value) { createur.set(value); }
    public void setDescription(String value) { description.set(value); }
    public void setLienStream(String value) { lienStream.set(value); }
}
