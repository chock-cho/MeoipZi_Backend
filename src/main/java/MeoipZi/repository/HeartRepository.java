package MeoipZi.repository;

import MeoipZi.domain.Community;
import MeoipZi.domain.Heart;
import MeoipZi.domain.ShortForm;
import MeoipZi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByUserAndShortForm(User user, ShortForm shortForm);
    Optional<Heart> findByUserAndCommunity(User user, Community community);
    //Optional<Heart> findByUserAndOutfit(User user, Outfit outfit);

    //List<Heart> findTop3ByUserAndOutfitNotNullOrderByCreatedAtDesc(User user);
    List<Heart> findTop3ByUserAndCommunityNotNullOrderByCreatedAtDesc(User user);
    List<Heart> findTop3ByUserAndShortFormNotNullOrderByCreatedAtDesc(User user);

}