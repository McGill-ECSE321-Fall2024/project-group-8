package ca.mcgill.ecse321.gamemanager.dto;
import java.util.ArrayList;
import java.util.List;

public class ReviewListDto {

    private List<ReviewDto> reviews;

    public ReviewListDto() {
        this.reviews = new ArrayList<>();
    }
    public List<ReviewDto> getReviews() {
        return reviews;
    }
    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }


}
