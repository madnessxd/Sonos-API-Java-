package sonosapi;

public class Song{
    String content;
    String res;
    String title;

    public Song(String content, String res, String title) {
        this.content = content;
        this.res = res;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Song{" + "content=" + content + ", res=" + res + ", title=" + title + '}';
    }

    public String getContent() {
        return content;
    }

    public String getRes() {
        return res;
    }

    public String getTitle() {
        return title;
    }
    
    
}
