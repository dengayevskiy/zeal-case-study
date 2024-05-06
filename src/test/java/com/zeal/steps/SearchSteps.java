package com.zeal.steps;

import com.zeal.client.CustomHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchSteps {

    private final CustomHttpClient client;

    String pageSearchPath = "search/page?q=%s";

    public SearchSteps(CustomHttpClient client) {
        this.client = client;
    }

    public void verifyPageTitlePresentInSearchResults(String queryParam, String expectedTitle) throws IOException, InterruptedException {
        boolean isTitleFound = new JSONObject(searchForPageWithQuery(queryParam).body())
                .getJSONArray("pages")
                .toList()
                .stream()
                .anyMatch(page -> ((Map<String, Object>) page).get("title").toString().equals(expectedTitle));
        assertTrue(isTitleFound, String.format("Page with title '%s' should be present in the search results", expectedTitle));
    }

    public String searchForPageKeyWithQuery(String queryParam, String expectedTitle) throws IOException, InterruptedException {
        return new JSONObject(searchForPageWithQuery(queryParam).body())
                .getJSONArray("pages")
                .toList()
                .stream()
                .filter(page -> ((Map<String, Object>) page).get("title").toString().equals(expectedTitle))
                .map(page -> ((Map<String, Object>) page).get("key").toString())
                .findFirst()
                .orElse("");
    }

    private HttpResponse<String> searchForPageWithQuery(String queryParam) throws IOException, InterruptedException {
        return client.sendRequestWithParam(pageSearchPath, queryParam);
    }
}