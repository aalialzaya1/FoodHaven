package ba.unsa.etf.nwt.ingredient_service.repos;

import ba.unsa.etf.nwt.ingredient_service.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PictureRepository extends JpaRepository<Picture, Integer> {
}
