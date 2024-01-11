package models.create_user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserModel {
    String name, job;
}