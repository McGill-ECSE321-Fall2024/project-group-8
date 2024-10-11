package ca.mcgill.ecse321.gamemanager.repository;

import ca.mcgill.ecse321.gamemanager.model.Wishlist;
import org.springframework.data.repository.CrudRepository;

public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {
    public Wishlist findByWishlistId(int wishlistId);
}
