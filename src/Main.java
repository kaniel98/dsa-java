import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        List<String> titles = main.getArticleTitles("epaga");
        for (String title : titles) {
            System.out.println(title);
        }
    }

    public static List<String> getArticleTitles(String author) {
        List<String> store = new ArrayList<>();
        String url = String.format("https://jsonmock.hackerrank.com/api/articles?author=%s&page=", author);

        HttpClient client = HttpClient.newHttpClient();

        // Fire request to url and get output
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url + "1")) // For the first page
                .build();
        articlePageResponse response = null;

        do {
            try {
                // Get the response
                String responseString = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

                // Add the titles to the store
                for (articleResponse article : response.data) {
                    store.add(article.title);
                }

                // Increment the page number
                request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(url + (response.page + 1)))
                        .build();
            } catch (Exception e) {
                System.out.println("Error in fetching data");
            }
        } while (Objects.requireNonNull(response).page < response.total_pages);


        return store;
    }

    class articlePageResponse {
        int page;
        int per_page;
        int total;
        int total_pages;
        List<articleResponse> data;
    }

    class articleResponse {
        String title;
        String url;
        int num_comments;
        int story_id;
        int story_title;
        int story_url;
    }

}