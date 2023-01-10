import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PhotoCollection {
    public static void main(String[] args)  {
        for (int day = 1; day <= 30; day++) {
            String page = null;
            DowloadWebPage downloadWebPage = new DowloadWebPage();
            // скачивает страницу в api
            page = downloadWebPage.downloadWebPage(" https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&date=2021-11-"+day);
            // находит ссылку на картинку скачивает и создаёт файл jpg
            int urlBegin = page.lastIndexOf("url");
            int urlEnd = page.lastIndexOf("}");
            String url = page.substring(urlBegin + 6, urlEnd - 1);
            try (InputStream in = new URL(url).openStream()) {
                Files.copy(in, Paths.get(day+".jpg"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("day"+day+" saved");
        }
        System.out.println("Picture saved");
    }
}
