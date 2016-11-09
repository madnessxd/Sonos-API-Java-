package sonostest;

import sonosapi.Song;
import sonosapi.SonosAPI;
import sonosapi.Station;

public class SonosTest {
    public static void main(String[] args) {
        //Connect to url
        SonosAPI s = new SonosAPI("192.168.177.112");
        
        //Get and print current playing song
        Song song = s.getCurrentSongInfo();
        System.out.println(song.toString());
        
        //Get and print Sonos favorites 
        Station[] stations = s.getFavoriteStations();
        for(Station station : stations){
            System.out.println(station.toString());
        }
        
        //Start favorite radiostation number 2
        s.playRadioStation(stations[1].getUri());
    }
    
}
