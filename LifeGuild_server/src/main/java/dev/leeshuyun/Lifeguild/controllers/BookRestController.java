package dev.leeshuyun.Lifeguild.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.leeshuyun.Lifeguild.models.Book;
import dev.leeshuyun.Lifeguild.services.BookApiService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
public class BookRestController {
    private final Logger logger = LoggerFactory.getLogger(BookRestController.class);

    @Autowired
    BookApiService bookSvc;

    @GetMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBookByName(@RequestParam String search,
            @RequestParam(defaultValue = "0") int startIndex,
            @RequestParam(defaultValue = "10") int maxResults) {
        logger.info("/api/book: search=%s, startIndex=%s, maxResults=%s".formatted(search, startIndex, maxResults));

        JsonArrayBuilder jsonArrB = Json.createArrayBuilder();

        List<Book> bookList = bookSvc.googleByTitle(search, startIndex, maxResults);

        bookList.stream()
                .forEach(book -> {
                    jsonArrB.add(book.toJSONObjBuilder());
                });
        return ResponseEntity.ok().body(jsonArrB.build().toString());
    }

    // @GetMapping(path = "/author")
    // public ResponseEntity<?> getBooksByAuthor(String authorName) {
    // logger.info("/api/book/author: query=%s".formatted(authorName));

    // // Optional<List<Book>> booksList = getBooksByAuthor(authorName);
    // return ResponseEntity
    // .status(HttpStatus.OK)
    // .contentType(MediaType.APPLICATION_JSON)
    // // .body(result.toString());
    // .body("");

    // }

}
