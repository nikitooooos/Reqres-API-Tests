package models.list_users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ListUsersResponseModel {
    Integer page, per_page, total;
    @JsonProperty("total_pages")
    int totalPages;
    Support support;
    List<DataInfo> data;

    @Data
    public static class DataInfo {
        Integer id;
        String email, first_name, last_name, avatar;
    }

    @Data
    public static class Support {
        String url, text;
    }
}