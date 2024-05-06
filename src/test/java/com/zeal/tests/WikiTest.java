package com.zeal.tests;

import com.zeal.client.CustomHttpClient;
import com.zeal.client.WikiCustomHttpClient;
import com.zeal.steps.PageSteps;
import com.zeal.steps.SearchSteps;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class WikiTest {

    private final CustomHttpClient client = new WikiCustomHttpClient("https://api.wikimedia.org/core/v1/wikipedia/en/");
    private final SearchSteps searchSteps = new SearchSteps(client);
    private final PageSteps pageSteps = new PageSteps(client);

    String queryParam = "furry rabbits";
    String expectedTitle = "Sesame Street";

    @Test
    public void testPageTitleIsFoundInSearchResults() throws IOException, InterruptedException {
        searchSteps.verifyPageTitlePresentInSearchResults(queryParam, expectedTitle);
    }

    @Test
    public void testPageTimestampIsCorrect() throws IOException, InterruptedException {
        String expectedTimestamp = "2023-08-17T00:00:00Z";

        String expectedPageKey = searchSteps.searchForPageKeyWithQuery(queryParam, expectedTitle);

        pageSteps.verifyPageTsGreaterThanExpectedTs(expectedPageKey, expectedTimestamp);
    }
}

