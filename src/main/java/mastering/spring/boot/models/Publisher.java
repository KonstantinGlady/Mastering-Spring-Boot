package mastering.spring.boot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String address;
}
