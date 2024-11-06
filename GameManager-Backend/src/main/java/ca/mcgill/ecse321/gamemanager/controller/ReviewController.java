package ca.mcgill.ecse321.gamemanager.controller;


import ca.mcgill.ecse321.gamemanager.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamemanager.dto.ReviewResponseDto;
import ca.mcgill.ecse321.gamemanager.model.Review;
import ca.mcgill.ecse321.gamemanager.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews")
    public ReviewResponseDto createReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        int rating = reviewRequestDto.getRating();
        String description = reviewRequestDto.getDescription();
        String customerEmail = reviewRequestDto.getReviewerEmail();
        int reviewedGameId = reviewRequestDto.getReviewedGameId();
        Review createdReview = reviewService.createReview(rating, description, customerEmail, reviewedGameId);

        return new ReviewResponseDto(createdReview);
    }

    @PutMapping("/reviews/update/{rId}")
    public ReviewResponseDto updateReview(@PathVariable int rId ,@RequestBody ReviewRequestDto reviewRequestDto) {
        int rating = reviewRequestDto.getRating();
        String description = reviewRequestDto.getDescription();
        Review updatedReview = reviewService.updateReview(rId, rating, description);
        return new ReviewResponseDto(updatedReview);

    }



    @GetMapping("/reviews/all")
    public List<ReviewResponseDto> getAllReviews() {
        Iterable<Review> reviews = reviewService.findAllReviews();
        List<ReviewResponseDto> reviewResponseDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewResponseDtos.add(new ReviewResponseDto(review));
        }
        return reviewResponseDtos;
    }

    @GetMapping("/reviews/{rId}")
    public ReviewResponseDto getReviewById(@PathVariable int rId) {
        Review review = reviewService.findReviewById(rId);
        return new ReviewResponseDto(review);
    }

    @GetMapping("/reviews/{gId}")
    public List<ReviewResponseDto> getReviewByGameId(@PathVariable int gId) {
        List<Review> reviews = reviewService.findReviewsByGameId(gId);
        List<ReviewResponseDto> reviewResponseDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewResponseDtos.add(new ReviewResponseDto(review));
        }
        return reviewResponseDtos;
    }

    @GetMapping("/reviews/{cEmail}")
    public List<ReviewResponseDto> getReviewByEmail(@PathVariable String cEmail) {
        List<Review> reviews = reviewService.findReviewsByCustomerEmail(cEmail);
        List<ReviewResponseDto> reviewResponseDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewResponseDtos.add(new ReviewResponseDto(review));
        }
        return reviewResponseDtos;
    }

    @GetMapping("/reviews/{gId}/descending")
    public List<ReviewResponseDto> getReviewByGameIdDescending(@PathVariable int gId) {
        List<Review> reviews = reviewService.findReviewsByGameIdDescending(gId);
        List<ReviewResponseDto> reviewResponseDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewResponseDtos.add(new ReviewResponseDto(review));
        }
        return reviewResponseDtos;
    }




    @DeleteMapping("/reviews/{reviewId}")
    public void deleteReview(@PathVariable int reviewId) {
        reviewService.deleteReview(reviewId);
    }




}
