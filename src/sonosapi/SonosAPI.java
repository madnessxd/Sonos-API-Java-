package sonosapi;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import static sonosapi.XMLParse.getString;
import static sonosapi.XMLParse.loadXMLFromString;

public class SonosAPI {
    private final String ip;
    
    public SonosAPI(String ip){
        this.ip = ip;
    }
    
    public String pauseSonos(){
        try {
            URLConnection connection = new URL("http://" + ip + ":1400" + "/MediaRenderer/AVTransport/Control").openConnection();
            connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("SOAPACTION", "\"urn:schemas-upnp-org:service:AVTransport:1#Pause\"");

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout( 2000 );  // long timeout, but not infinite
            connection.setReadTimeout( 2000 );
            connection.setUseCaches (false);
            connection.setDefaultUseCaches (false);

            connection.setRequestProperty ( "Content-Type", "text/xml" );

            OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );

            writer.write( "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                            "	<s:Body>\n" +
                            "		<u:Pause xmlns:u=\"urn:schemas-upnp-org:service:AVTransport:1\">\n" +
                            "			<InstanceID>0</InstanceID>\n" +
                            "			<Speed>1</Speed>\n" +
                            "		</u:Pause>\n" +
                            "	</s:Body>\n" +
                            "</s:Envelope>" );

            writer.flush();
            writer.close();

            InputStreamReader reader = new InputStreamReader( connection.getInputStream() );
            StringBuilder buf = new StringBuilder();
            char[] cbuf = new char[ 2048 ];
            int num;
            while ( -1 != (num=reader.read( cbuf )))
            {
                buf.append( cbuf, 0, num );
            }

            String result = buf.toString();
            return result;
        } catch (Exception e) {
            System.err.println(e);
            return e.toString();
        }
    }
    
    public String playSonos(){
        try {
            URLConnection connection = new URL("http://" + ip +":1400" + "/MediaRenderer/AVTransport/Control").openConnection();
            connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("SOAPACTION", "\"urn:schemas-upnp-org:service:AVTransport:1#Play\"");

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout( 2000 );  // long timeout, but not infinite
            connection.setReadTimeout( 2000 );
            connection.setUseCaches (false);
            connection.setDefaultUseCaches (false);

            connection.setRequestProperty ( "Content-Type", "text/xml" );

            OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );

            writer.write( "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                            "	<s:Body>\n" +
                            "		<u:Play xmlns:u=\"urn:schemas-upnp-org:service:AVTransport:1\">\n" +
                            "			<InstanceID>0</InstanceID>\n" +
                            "			<Speed>1</Speed>\n" +
                            "		</u:Play>\n" +
                            "	</s:Body>\n" +
                            "</s:Envelope>" );

            writer.flush();
            writer.close();

            InputStreamReader reader = new InputStreamReader( connection.getInputStream() );
            StringBuilder buf = new StringBuilder();
            char[] cbuf = new char[ 2048 ];
            int num;
            while ( -1 != (num=reader.read( cbuf )))
            {
                buf.append( cbuf, 0, num );
            }

            String result = buf.toString();
            return result;
        } catch (Exception e) {
            System.err.println(e);
            return e.toString();
        }
    }
    
    public Station[] getFavoriteStations(){
        try {
            URLConnection connection = new URL("http://" + ip +":1400" + "/MediaServer/ContentDirectory/Control").openConnection();
            connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("SOAPACTION", "\"urn:schemas-upnp-org:service:ContentDirectory:1#Browse\"");

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout( 2000 );  // long timeout, but not infinite
            connection.setReadTimeout( 2000 );
            connection.setUseCaches (false);
            connection.setDefaultUseCaches (false);

            connection.setRequestProperty ( "Content-Type", "text/xml" );

            OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );

            writer.write( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                        "   <s:Body>\n" +
                        "      <u:Browse xmlns:u=\"urn:schemas-upnp-org:service:ContentDirectory:1\">\n" +
                        "         <ObjectID>FV:2</ObjectID>\n" +
                        "         <BrowseFlag>BrowseDirectChildren</BrowseFlag>\n" +
                        "         <Filter>dc:title,res,dc:creator,upnp:artist,upnp:album,upnp:albumArtURI</Filter>\n" +
                        "         <StartingIndex>0</StartingIndex>\n" +
                        "         <RequestedCount>100</RequestedCount>\n" +
                        "         <SortCriteria />\n" +
                        "      </u:Browse>\n" +
                        "   </s:Body>\n" +
                        "</s:Envelope>" );

            writer.flush();
            writer.close();

            InputStreamReader reader = new InputStreamReader( connection.getInputStream() );
            StringBuilder buf = new StringBuilder();
            char[] cbuf = new char[ 2048 ];
            int num;
            while ( -1 != (num=reader.read( cbuf )))
            {
                buf.append( cbuf, 0, num );
            }

            String result = buf.toString();
            
            try{
                Document document = loadXMLFromString(result);
                Element rootElement = document.getDocumentElement();

                document = loadXMLFromString(getString("Result", rootElement));
                rootElement = document.getDocumentElement();
            
                NodeList nList = rootElement.getElementsByTagName("item");
                
                Station[] stations = new Station[nList.getLength()];
                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);
                    Element eElement = (Element) nNode;
                    
                    String title = eElement.getElementsByTagName("dc:title").item(0).getTextContent();
                    String classObject = eElement.getElementsByTagName("upnp:class").item(0).getTextContent();
                    String ordinal = eElement.getElementsByTagName("r:ordinal").item(0).getTextContent();
                    String uri = eElement.getElementsByTagName("res").item(0).getTextContent();
                    String albumArtURI = eElement.getElementsByTagName("upnp:albumArtURI").item(0).getTextContent();
                    String type = eElement.getElementsByTagName("r:type").item(0).getTextContent();
                    String description = eElement.getElementsByTagName("r:description").item(0).getTextContent();
                    
                    Station s = new Station(title, classObject, ordinal, uri, albumArtURI, type, description);
                    stations[i] = s;
                }
                return stations;
            } catch(Exception e){
            System.err.println(e);
            }
            
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }
    
    public String setVolume(int volume){
        try {
            URLConnection connection = new URL("http://" + ip + ":1400" + "/MediaRenderer/RenderingControl/Control").openConnection();
            connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("SOAPACTION", "urn:schemas-upnp-org:service:RenderingControl:1#SetVolume");

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout( 2000 );  // long timeout, but not infinite
            connection.setReadTimeout( 2000 );
            connection.setUseCaches (false);
            connection.setDefaultUseCaches (false);

            connection.setRequestProperty ( "Content-Type", "text/xml" );

            try (OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() )) {
                writer.write( "<s:Envelope \n" +
                                "	xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                                "	s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
                                "	>\n" +
                                "  <s:Body>\n" +
                                "    <u:SetVolume xmlns:u=\"urn:schemas-upnp-org:service:RenderingControl:1\">\n" +
                                "      <InstanceID>0</InstanceID>\n" +
                                "      <Channel>Master</Channel>\n" +
                                "      <DesiredVolume>" + volume + "</DesiredVolume>\n" +
                                "    </u:SetVolume>\n" +
                                "  </s:Body>\n" +
                                "</s:Envelope>" );

                writer.flush();
            }

            InputStreamReader reader = new InputStreamReader( connection.getInputStream() );
            StringBuilder buf = new StringBuilder();
            char[] cbuf = new char[ 2048 ];
            int num;
            while ( -1 != (num=reader.read( cbuf )))
            {
                buf.append( cbuf, 0, num );
            }

            String result = buf.toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }
    
    public Song getCurrentSongInfo(){
        Song song = null;
        
        try {
            URLConnection connection = new URL("http://" + ip + ":1400" + "/MediaRenderer/AVTransport/Control").openConnection();
            connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("SOAPACTION", "urn:schemas-upnp-org:service:AVTransport:1#GetPositionInfo");

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);  // long timeout, but not infinite
            connection.setReadTimeout(2000);
            connection.setUseCaches (false);
            connection.setDefaultUseCaches (false);

            connection.setRequestProperty ( "Content-Type", "text/xml" );

            try (OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() )) {
                writer.write( "<s:Envelope \n" +
                        "	xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                        "	s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
                        "	>\n" +
                        "  <s:Body>\n" +
                        "    <u:GetPositionInfo xmlns:u=\"urn:schemas-upnp-org:service:AVTransport:1\">\n" +
                        "      <InstanceID>0</InstanceID>\n" +
                        "    </u:GetPositionInfo>\n" +
                        "  </s:Body>\n" +
                        "</s:Envelope>\n" +
                        "<!--MediaRenderer/AVTransport/Control-->" );

                writer.flush();
            }

            InputStreamReader reader = new InputStreamReader( connection.getInputStream() );
            StringBuilder buf = new StringBuilder();
            char[] cbuf = new char[ 2048 ];
            int num;
            while ( -1 != (num=reader.read( cbuf )))
            {
                buf.append( cbuf, 0, num );
            }

            String result = buf.toString();
            try{
                Document document = loadXMLFromString(result);
                Element rootElement = document.getDocumentElement();

                if(!equals(getString("TrackMetaData", rootElement).equals("NOT_IMPLEMENTED"))){
                    Document document2 = loadXMLFromString(getString("TrackMetaData", rootElement));
                    Element rootElement2 = document2.getDocumentElement();

                    song = new Song(getString("r:streamContent", rootElement2), getString("res", rootElement2), getString("title", rootElement2));
                }
            } catch(Exception e){
            System.err.println(e);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return song;
    }
    
    public String playRadioStation(String uri){
        try {
            URLConnection connection = new URL("http://" + ip +":1400" + "/MediaRenderer/AVTransport/Control").openConnection();
            connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("SOAPACTION", "\"urn:schemas-upnp-org:service:AVTransport:1#SetAVTransportURI\"");

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout( 10000 );  // long timeout, but not infinite
            connection.setReadTimeout( 10000 );
            connection.setUseCaches (false);
            connection.setDefaultUseCaches (false);

            connection.setRequestProperty ( "Content-Type", "text/xml" );

            OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );
            
            writer.write( "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
            "<s:Body>\n" +
            "<u:SetAVTransportURI xmlns:u=\"urn:schemas-upnp-org:service:AVTransport:1\">\n" +
            "<InstanceID>0</InstanceID>\n" +
            "<CurrentURI>" + uri.replaceAll("&", "&amp;") + "</CurrentURI>\n" +
            "<CurrentURIMetaData>&lt;DIDL-Lite xmlns:dc=&quot;http://purl.org/dc/elements/1.1/&quot; xmlns:upnp=&quot;urn:schemas-upnp-org:metadata-1-0/upnp/&quot; xmlns:r=&quot;urn:schemas-rinconnetworks-com:metadata-1-0/&quot; xmlns=&quot;urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/&quot;&gt;&lt;item id=&quot;F00092020s9483&quot; parentID=&quot;F000c0008s9483&quot; restricted=&quot;true&quot;&gt;&lt;dc:title&gt;NPO Radio 2&lt;/dc:title&gt;&lt;upnp:class&gt;object.item.audioItem.audioBroadcast.sonos-favorite&lt;/upnp:class&gt;&lt;desc id=&quot;cdudn&quot; nameSpace=&quot;urn:schemas-rinconnetworks-com:metadata-1-0/&quot;&gt;SA_RINCON65031_&lt;/desc&gt;&lt;/item&gt;&lt;/DIDL-Lite&gt;</CurrentURIMetaData>\n" +
            "</u:SetAVTransportURI>\n" +
            "</s:Body>\n" +
            "</s:Envelope>" );

            writer.flush();
            writer.close();

            playSonos();
            InputStreamReader reader = new InputStreamReader( connection.getInputStream() );
            StringBuilder buf = new StringBuilder();
            char[] cbuf = new char[ 2048 ];
            int num;


            while ( -1 != (num=reader.read( cbuf )))
            {
                buf.append( cbuf, 0, num );
            }

            playSonos();
            
            String result = buf.toString();
            System.out.println(result);
            return result;
        } catch (Exception e) {
            System.err.println(e);
            return e.toString();
        }
    }
    
    /*public static void main(String[] args) {
        SonosAPI s = new SonosAPI("192.168.177.112");
        Song song = s.getCurrentSongInfo();
        System.out.println(song.toString());
        
        Station[] stations = s.getFavoriteStations();
        for(Station station : stations){
            System.out.println(station.toString());
        }
        s.playRadioStation(stations[2].uri);
    }*/
}