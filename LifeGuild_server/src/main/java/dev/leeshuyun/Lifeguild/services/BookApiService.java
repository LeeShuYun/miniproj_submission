package dev.leeshuyun.Lifeguild.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.spel.ast.Identifier;
import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import dev.leeshuyun.Lifeguild.models.Book;

@Service
public class BookApiService {

    // @Value("${openlibrary.api.url}")
    // private String openLibraryEndPt;

    private final Logger logger = LoggerFactory.getLogger(BookApiService.class);

    // adding this later. not now, I have points to get
    // // eg. https://openlibrary.org/isbn/9780140328721
    // public void getBookByISBN(String query) {
    // final String ISBN_API_URL = "isbn/";

    // // doing the query
    // String fullURL = ISBN_API_URL + query + ".json";
    // System.out.println("query is ISBN: %s".formatted(fullURL));

    // RequestEntity<Void> req = RequestEntity.get(fullURL)
    // .accept(MediaType.APPLICATION_JSON)
    // .build();

    // // client to do the request
    // RestTemplate template = new RestTemplate();

    // // init the Response to catch the response data
    // ResponseEntity<String> resp = null;

    // // send the request using the client
    // try {
    // // executes the req entity and returns a response with json payload body
    // resp = template.exchange(req, String.class);
    // } catch (RestClientException ex) {
    // logger.error("BookApiService searchISBN error: query=%s"
    // .formatted(query), ex);
    // // return Optional.empty();
    // }

    // // retrieval of payload data
    // String payload = resp.getBody();
    // System.out.println(payload);
    // JsonReader reader = Json.createReader(new StringReader(payload));
    // JsonObject bookResp = reader.readObject();

    // // catching error returns
    // // eg. {"error": "notfound", "key": "/works/OL45804"}
    // if (bookResp.getString("error") == "notfound") {
    // logger.info("error not found response" +
    // bookResp.getString("key"));
    // // return Optional.empty();
    // }

    // // REPONSE_JSON_OBJECT.getJSONObject("result")
    // // .getJSONObject("map")
    // // .getJSONArray("entry");
    // // return Optional.of(bookResult);
    // }

    @Value("${google.books.api.url}")
    private String googleBooksApiUrl;

    @Value("${google.api.key}")
    private String googleApiKey;

    // https://www.googleapis.com/books/v1/volumes?q=quilting&key=APIKEY
    // the APIKEY allows for google analytics
    public List<Book> googleByTitle(String query, int startIndex, int maxResults) {
        // public void googleByTitle(String query, int startIndex, int maxResults) {
        // doing the query
        String googleApiURI = UriComponentsBuilder
                .fromUriString(googleBooksApiUrl)
                .queryParam("q", query)
                .queryParam("key", googleApiKey)
                .queryParam("startIndex", startIndex)
                .queryParam("maxResults", maxResults)
                .toUriString();
        logger.info("googleByTitle>> query: %s".formatted(googleApiURI));

        RequestEntity<Void> req = RequestEntity.get(googleApiURI)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        List<Book> bookResult = new ArrayList<Book>();

        // https://www.googleapis.com/books/v1/volumes?q=Coraline&key=AIzaSyAa2mev6PCSd0h08C7t9km1ttDJPH9JsvA
        // send the request using the client
        try {
            // executes the req entity and returns a response with json payload body
            resp = template.exchange(req, String.class);
        } catch (RestClientException ex) {
            logger.error("BookApiService>>> search error=%s"
                    .formatted(query), ex);
            return bookResult;
        }

        // retrieval of payload data
        String payload = resp.getBody();
        // logger.info(">>>>> ALL RIGHT WE GOT THE INFO HOT OFF THE API HERE: " +
        // payload);
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject bookResp = reader.readObject();

        // catching no result case
        if (bookResp.getInt("totalItems") == 0) {
            logger.error("BookApiService>>> no books found");
            return bookResult;
        }

        // testing return values
        JsonArray booksJsonArray = bookResp.getJsonArray("items");
        // logger.info(">>>>>>>>>>googleByTitle>>> items:");

        // checking the array.
        for (int i = 0; i < booksJsonArray.size(); i++) {

            JsonObject bookJ = (JsonObject) booksJsonArray.get(i);
            // logger.info("jsonBook>>>>>>>>" + bookJson);
            JsonObject volumeInfoJ = bookJ.getJsonObject("volumeInfo");

            // logger.info(" VOLUME {} =============================================", i);
            // logger.info("volumeInfoJson>>>>>>>> {}", volumeInfoJ);

            // checking for images error
            JsonObject readingModesJ = volumeInfoJ.getJsonObject("readingModes");
            Boolean isImagesURLPresent = readingModesJ.getBoolean("image");
            if (!isImagesURLPresent) {
                Book book = Book.createBookFromJsonObjWithOUTImages(volumeInfoJ);
                // logger.info("Book without images>>>> " + book);
                bookResult.add(book);
            } else {
                Book book = Book.createBookFromJsonObjWithImages(volumeInfoJ);
                // logger.info("Book with images>>> " + book);
                bookResult.add(book);
            }

        }

        return bookResult;
    }

}
