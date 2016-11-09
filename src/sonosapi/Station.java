/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonosapi;

public class Station {
    String title;
    String classObject;
    String ordinal;
    String uri;
    String albumArtURI;
    String type;
    String description;

    public Station(String title, String classObject, String ordinal, String uri, String albumArtURI, String type, String description) {
        this.title = title;
        this.classObject = classObject;
        this.ordinal = ordinal;
        this.uri = uri;
        this.albumArtURI = albumArtURI;
        this.type = type;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Station{" + "title=" + title + ", classObject=" + classObject + ", ordinal=" + ordinal + ", uri=" + uri + ", albumArtURI=" + albumArtURI + ", type=" + type + ", description=" + description + '}';
    }

    public String getTitle() {
        return title;
    }

    public String getClassObject() {
        return classObject;
    }

    public String getOrdinal() {
        return ordinal;
    }

    public String getUri() {
        return uri;
    }

    public String getAlbumArtURI() {
        return albumArtURI;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
    
    
}
