package mastering.spring.boot.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "authors")
@Entity
public class Author {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String biography;
    @ManyToOne
    private Publisher publisher;
}
