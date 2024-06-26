package meoipzi.meoipzi.outfit.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import meoipzi.meoipzi.genre.domain.Genre;
//import meoipzi.meoipzi.genreoutfit.domain.GenreOutfit;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.outfit.domain.Outfit;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OutfitRequestDTO {
    private String username;
    @JsonIgnore
    private MultipartFile imgUrl;
    private String content; // 피그마에 추가시키자! -> 코디에 대한 제목 같은게 있으면 필요할 수 도???
    //private Long modelHeight;
    //private Long modelWeight;
    //private String modelInstagramId;
    //private String modelGender; // 성별

    private List<Long> genreIds;
    public Outfit toEntity(User user){
        Outfit outfit = Outfit.builder()
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .content(content)
                .user(user)
                .build();

        /*return Outfit.builder()
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .content(content)
                //.modelHeight(modelHeight)
                //.modelWeight(modelWeight)
                //.modelInstagramId(modelInstagramId)
                //.modelGender(modelGender)
                .user(user)
                .build();*/
        return  outfit;
    }
}
