package dev.leeshuyun.Lifeguild.models;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
        String title;
        String previewUrl; // click to external site for preview
        String imgUrl; // hotlink to book cover img
        String description;
        String identifier; // can be ISBN, can be OCLC, UOM.. I didn't even know these existed

        @Override
        public String toString() {
                return "Book [title=%s, externalUrl=%s, imgUrl=%s, description=%s]"
                                .formatted(title, previewUrl, imgUrl, description);
        }

        /*
         * ISBN identifier
         * "industryIdentifiers": [
         * {
         * "type": "ISBN_10",
         * "identifier": "8173666024"
         * },
         * {
         * "type": "ISBN_13",
         * "identifier": "9788173666025"
         * }
         * ],
         * OTHER type of identifier
         * "industryIdentifiers": [
         * {
         * "type": "OTHER",
         * "identifier": "OCLC:1247857063" <- sometimes can be UOM
         * }
         * ],
         */
        public static Book createBookFromJsonObjWithOUTImages(JsonObject jsonObj) {
                // System.out.println("BIG PROBLEM >>>>>>> " +
                // jsonObj.getJsonArray("industryIdentifiers"));
                // checking for isbn,oclc, uom...
                JsonArray industryidentifiers = jsonObj.getJsonArray("industryIdentifiers");
                String identifier = "";
                String typeOfIdentifier = "";
                for (int j = 0; j < industryidentifiers.size() - 1; j++) {
                        JsonObject identifierJ = (JsonObject) industryidentifiers.get(j);
                        typeOfIdentifier = identifierJ.getString("type");
                        identifier = identifierJ.getString("identifier");
                        // log.info("type: {}, identifier: {}", typeOfIdentifier, identifier);

                        Json.createObjectBuilder().add(typeOfIdentifier, identifier);
                }
                String description = "";
                try {
                        description = jsonObj.getString("description");
                } catch (Exception e) {
                        log.error(e.getMessage());
                }
                Book book = Book.builder()
                                .title(jsonObj.getString("title"))
                                .previewUrl(jsonObj.getString("previewLink"))
                                .imgUrl("none")
                                .description(description)
                                .identifier(identifier)
                                .build();

                return book;
        }

        public JsonObjectBuilder toJSONObjBuilder() {
                return Json.createObjectBuilder()
                                .add("title", getTitle())
                                .add("externalUrl", getPreviewUrl())
                                .add("imgUrl", getImgUrl())
                                .add("description", getDescription());
        }

        public static Book createBookFromJsonObjWithImages(JsonObject jsonObj) {
                JsonObject isImagesJsonObj = (JsonObject) jsonObj.getJsonArray("industryIdentifiers")
                                .get(1);
                String identifier = isImagesJsonObj.getString("identifier");
                // log.info("identifier >>>> {}", identifier);
                String description = "";
                try {
                        description = jsonObj.getString("description");
                } catch (Exception e) {
                        log.error(e.getMessage());
                }
                Book book = Book.builder()
                                .title(jsonObj.getString("title"))
                                .previewUrl(jsonObj.getString("previewLink"))
                                .imgUrl(jsonObj.getJsonObject("imageLinks")
                                                .getString("smallThumbnail"))
                                .description(description)
                                .identifier(identifier)
                                .build();
                return book;
        }

}
