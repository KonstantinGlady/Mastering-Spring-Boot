package mastering.spring.boot.chapter1.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(@Id long id, String name, String email) {
}
