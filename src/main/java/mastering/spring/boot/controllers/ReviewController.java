package mastering.spring.boot.controllers;

import lombok.RequiredArgsConstructor;
import mastering.spring.boot.models.Review;
import mastering.spring.boot.repositories.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable String id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable String id, @RequestBody Review review) {
        return reviewRepository.findById(id)
                .map(existingReview -> {
                    existingReview.setBookId(review.getBookId());
                    existingReview.setReviewerName(review.getReviewerName());
                    existingReview.setComment(review.getComment());
                    existingReview.setRating(review.getRating());

                    Review updatedReview = reviewRepository.save(existingReview);
                    return ResponseEntity.ok(updatedReview);
                }).orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable String id) {
        return reviewRepository.findById(id)
                .map(review -> {
                    reviewRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElseGet(() ->
                        ResponseEntity.notFound().build());
    }
}
