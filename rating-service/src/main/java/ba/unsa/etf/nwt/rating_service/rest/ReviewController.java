package ba.unsa.etf.nwt.rating_service.rest;

import ba.unsa.etf.nwt.rating_service.model.ReviewDTO;
import ba.unsa.etf.nwt.rating_service.service.ReviewService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(final ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable final Integer id) {
        return ResponseEntity.ok(reviewService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createReview(@RequestBody @Valid final ReviewDTO reviewDTO) {
        return new ResponseEntity<>(reviewService.create(reviewDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateReview(@PathVariable final Integer id,
            @RequestBody @Valid final ReviewDTO reviewDTO) {
        reviewService.update(id, reviewDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable final Integer id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
