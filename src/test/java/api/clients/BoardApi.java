package api.clients;

import api.base.BaseApi;
import io.restassured.http.ContentType;

import java.util.List;

public class BoardApi extends BaseApi {

    // 1) Board oluştur
    public String createBoard(String boardName) {
        String boardId =
                request()
                        .contentType(ContentType.JSON)
                        .queryParam("name", boardName)
                        .queryParam("defaultLists", true)
                        .when()
                        .post("/boards/")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("id");

        System.out.println("✅ Board oluşturuldu: " + boardName + " | id=" + boardId);
        return boardId;
    }

    // 2) Board üzerindeki listeleri çek (id listesi)
    public List<String> getListIdsOnBoard(String boardId) {
        List<String> listIds =
                request()
                        .when()
                        .get("/boards/{id}/lists", boardId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .jsonPath()
                        .getList("id");

        System.out.println("📌 Board listeleri bulundu. count=" + listIds.size());
        return listIds;
    }

    // 3) Board sil
    public void deleteBoard(String boardId) {
        request()
                .when()
                .delete("/boards/{id}", boardId)
                .then()
                .statusCode(200);

        System.out.println("🧹 Board silindi: id=" + boardId);
    }

    // (Opsiyonel) Email ile member invite
    public void inviteMemberByEmail(String boardId, String email) {
        request()
                .queryParam("email", email)
                .queryParam("type", "normal")
                .when()
                .put("/boards/{id}/members", boardId)
                .then()
                .statusCode(200);

        System.out.println("📩 Davet gönderildi: " + email);
    }
}
