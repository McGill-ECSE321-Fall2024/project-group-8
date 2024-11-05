package ca.mcgill.ecse321.gamemanager.service;


import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.Review;
import ca.mcgill.ecse321.gamemanager.repository.CustomerRepository;
import ca.mcgill.ecse321.gamemanager.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Review createReview(int rating, String description, Customer customer, Game game) {
        Date now = Date.valueOf(LocalDate.now());
        Review review = new Review(rating, description, now, customer, game);

        return reviewRepository.save(review);
    }

    @Transactional
    public Review updateReview(int id,int rating, String description, Date date, Customer customer, Game game) {
        Review review = reviewRepository.findReviewByReviewId(id);
        if (review == null) {
            throw new IllegalArgumentException("Review not found");
        }
        if (review.getRating() > 5 || review.getRating() < 1) {
            throw new IllegalArgumentException("Review rating out of range");
        }
        review.setRating(rating);
        review.setDescription(description);
        review.setDate(date);

        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(int id) {
        Review review = reviewRepository.findReviewByReviewId(id);
        if (review == null) {
            throw new IllegalArgumentException("Review not found");
        }
        reviewRepository.delete(review);
    }



    public Review findReviewById(int id) {
        Review review = reviewRepository.findReviewByReviewId(id);
        if (review == null) {
            // TODO: maybe create a new exception type
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("There is no review with ID %d.", id));
        }
        return review;
    }


    public Iterable<Review> findAllReviews() {
        return reviewRepository.findAll();
    }


    public Iterable<Review> findReviewsByCustomerEmail(String customerEmail) {
        Customer customer = customerRepository.findCustomerByEmail(customerEmail);
        Iterable<Review> review = reviewRepository.findReviewByCreated(customer);
        if (review == null) {
            // TODO: maybe create a new exception type
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("There is no review from customer %s.", customerEmail));
        }
        return review;
    }

}
