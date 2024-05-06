package com.zeal.steps;

import com.zeal.client.CustomHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PageSteps {

    private final CustomHttpClient client;

    String pageDetailsPath = "page/%s/bare";

    public PageSteps(CustomHttpClient client) {
        this.client = client;
    }

    public HttpResponse<String> getPageDetails(String pageTitle) throws IOException, InterruptedException {
        return client.sendRequestWithParam(pageDetailsPath, pageTitle);
    }

    public void verifyPageTsGreaterThanExpectedTs(String pageKey, String expectedTimestamp) throws IOException, InterruptedException {
        HttpResponse<String> response = getPageDetails(pageKey);

        boolean isTimestampCorrect = Stream.of(new JSONObject(response.body()).getJSONObject("latest").getString("timestamp"))
                .anyMatch(actualTimestamp ->
                        Instant.parse(actualTimestamp).isAfter(Instant.parse(expectedTimestamp)));
        assertTrue(isTimestampCorrect, String.format("Page with title '%s' should have timestamp greater than %s", pageKey,
                expectedTimestamp));
    }
}
