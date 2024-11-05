package ca.mcgill.ecse321.gamemanager.dto;

import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.Review;

import java.sql.Date;
import java.time.LocalDate;

public class ReviewResponseDto {
    private int reviewId;
    private int rating;
    private String description;
    private LocalDate creationDate;
    private String gameTitle;

    @SuppressWarnings("unused")
    private ReviewResponseDto() {}

    /*public ReviewResponseDto(int reviewId, int rating, String description, String gameTitle) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.description = description;
        this.gameTitle = gameTitle;
    }

     */
    public ReviewResponseDto(Review review) {
        this.reviewId = review.getReviewId();
        this.rating = review.getRating();
        this.description = review.getDescription();
        Game game = review.getGame();
        this.gameTitle = game.getTitle();
        this.creationDate = review.getDate().toLocalDate();
    }
    public int getReviewId() {
        return reviewId;
    }
    public int getRating() {
        return rating;
    }
    public String getDescription() {
        return description;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public String getGameTitle() {
        return gameTitle;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
    public void setRatingAndDescription(int rating, String description) {
        this.rating = rating;
        this.description = description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


}

