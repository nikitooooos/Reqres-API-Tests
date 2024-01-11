package models.create_user;

import lombok.Data;

@Data
public class CreateUserResponseModel {
    String name, job, id, createdAt, updatedAt;
}