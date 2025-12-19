package api.clients;

import api.base.BaseApi;
import io.restassured.http.ContentType;

public class CardApi extends BaseApi {

    // 1️⃣ Kart oluştur
    public String createCard(String listId, String cardName) {

        String cardId =
                request()
                        .contentType(ContentType.JSON)
                        .queryParam("idList", listId)
                        .queryParam("name", cardName)
                        .when()
                        .post("/cards")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("id");

        System.out.println("📝 Kart oluşturuldu: " + cardName + " | id=" + cardId);
        return cardId;
    }

    // 2️⃣ Kart sil
    public void deleteCard(String cardId) {

        request()
                .when()
                .delete("/cards/{id}", cardId)
                .then()
                .statusCode(200);

        System.out.println("🗑 Kart silindi: id=" + cardId);
    }
}
