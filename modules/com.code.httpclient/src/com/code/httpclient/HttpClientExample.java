package com.code.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class HttpClientExample {

    private static final System.Logger LOG = System.getLogger("HttpClientExample");

    public static void main(String[] args) throws Exception {
        httpGetRequest();
        httpPostRequest();
        asynchronousRequest();
        asynchronousMultipleRequests();
    }

    /**
     * httpGetRequest
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public static void httpGetRequest() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI httpURI = new URI("http://jsonplaceholder.typicode.com/posts/1");
        HttpRequest request = HttpRequest.newBuilder(httpURI).GET()
                .headers("Accept-Enconding", "gzip, deflate").build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        LOG.log(System.Logger.Level.DEBUG, "httpGetRequest");
        LOG.log(System.Logger.Level.DEBUG, responseBody);
    }

    /**
     * httpPostRequest
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public static void httpPostRequest() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient
                .newBuilder()
                .build();
        HttpRequest request = HttpRequest
                .newBuilder(new URI("http://jsonplaceholder.typicode.com/posts"))
                .POST(BodyPublishers.ofString("Sample Post Request"))
                .build();
        HttpResponse<String> response
                = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        LOG.log(System.Logger.Level.DEBUG, "httpPostRequest");
        LOG.log(System.Logger.Level.DEBUG, responseBody);
    }

    /**
     * asynchronousRequest
     *
     * @throws URISyntaxException
     */
    public static void asynchronousRequest() throws URISyntaxException, ExecutionException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI httpURI = new URI("http://jsonplaceholder.typicode.com/posts/1");
        HttpRequest request = HttpRequest.newBuilder(httpURI).GET().build();
        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request,
                HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> res = futureResponse.get();
        LOG.log(System.Logger.Level.DEBUG, "asynchronousRequest");
        LOG.log(System.Logger.Level.DEBUG, res.body());
    }

    /**
     * asynchronousMultipleRequests
     *
     * @throws URISyntaxException
     */
    public static void asynchronousMultipleRequests() throws URISyntaxException {
        List<URI> targets = Arrays.asList(new URI("http://jsonplaceholder.typicode.com/posts/1"), new URI("http://jsonplaceholder.typicode.com/posts/2"));
        HttpClient client = HttpClient.newHttpClient();
        List<CompletableFuture<HttpResponse<String>>> futures = targets
                .stream()
                .map(target -> client
                        .sendAsync(
                                HttpRequest.newBuilder(target)
                                        .GET()
                                        .build(),
                                HttpResponse.BodyHandlers.ofString()))
                .collect(Collectors.toList());
        LOG.log(System.Logger.Level.DEBUG, "asynchronousMultipleRequests");
        futures.forEach(future -> {
            try {
                HttpResponse<String> res = future.get();
                LOG.log(System.Logger.Level.DEBUG, res.body());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}
