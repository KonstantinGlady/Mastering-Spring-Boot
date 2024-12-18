package mastering.spring.boot.repositories;

import mastering.spring.boot.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {
}
