package leetcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

class ApiRequestSample {

    /*
     * Complete the 'costliestChocolate' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts STRING brand as parameter.
     * API URL: https://jsonmock.hackerrank.com/api/chocolates?brand=<brand>
     */

    public static long costliestChocolate(String brand) {
        String baseFetchChocolateUrl = "";
        try {
            baseFetchChocolateUrl = "https://jsonmock.hackerrank.com/api/chocolates?" + "brand=" + URLEncoder.encode(brand, StandardCharsets.UTF_8.toString());
            System.out.println(baseFetchChocolateUrl);
        } catch (Exception exception) {
            System.out.println("Issues forming the base url");
        }

        ChocolateResponse firstFetchChocolateResponse = parseChocolateResponse(baseFetchChocolateUrl);

        // Return product number of the most expensive chocolate / gram
        long currentHighestPricedProductNumber = Integer.MAX_VALUE;
        double currentHighestPricedPerGram = -1.0;

        for (int page = 1; page <= firstFetchChocolateResponse.total_pages; page++) {
            String pagedFetchChocolateUrl = baseFetchChocolateUrl + "&page=" + page;
            ChocolateResponse resp = parseChocolateResponse(pagedFetchChocolateUrl);

            for (ChocolateData chocolateData : resp.data) {
                boolean isChocolate = chocolateData.type.toLowerCase().contains("chocolate");

                if (!chocolateData.brand.equals(brand) || !isChocolate) {
                    continue;
                }

                for (int idx = 0; idx < chocolateData.prices.size(); idx++) {
                    double currentPricePerGram = chocolateData.prices.get(idx) / chocolateData.weights.get(idx);
                    System.out.println(currentPricePerGram);
                    System.out.println("product number " + chocolateData.productNumber);
                    if (currentPricePerGram >= currentHighestPricedPerGram) {
                        currentHighestPricedPerGram = currentPricePerGram;
                        if (currentHighestPricedProductNumber != Integer.MAX_VALUE) {
                            currentHighestPricedProductNumber = chocolateData.productNumber;
                        }
                        if (chocolateData.productNumber < currentHighestPricedProductNumber) {
                            currentHighestPricedProductNumber = chocolateData.productNumber;
                        }
                    }
                }
            }
        }

        return currentHighestPricedProductNumber;
    }

    public static ChocolateResponse parseChocolateResponse(String urlString) {
        String data = getAPIResponse(urlString);
        ChocolateResponse mappedChocolateResponse = null;
//        ChocolateResponse mappedChocolateResponse = new Gson().fromJson(data, ChocolateResponse.class);
        return mappedChocolateResponse;
    }

    public static String getAPIResponse(String urlString) {
        try {
            URL url = new URL(urlString);
            // First query to get the information
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            return sb.toString();

        } catch (Exception exception) {
            System.out.println("Error forming the string");
            return "";
        }
    }

    public class ChocolateResponse {
        private int page;
        private int per_page;
        private int total;
        private int total_pages;
        private List<ChocolateData> data;
    }

    public class ChocolateData {
        private String type;
        private String brand;
        private long productNumber;
        private List<Long> prices;
        private List<Long> weights;
    }


}
