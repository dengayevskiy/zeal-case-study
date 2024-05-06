package com.zeal.client;

import java.io.IOException;
import java.net.http.HttpResponse;

public interface CustomHttpClient {

    HttpResponse<String> sendRequestWithParam(String path, String param) throws IOException, InterruptedException;
}
