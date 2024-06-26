package meoipzi.meoipzi.shortform.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.common.config.S3Config;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.heart.repository.HeartRepository;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.login.service.UserService;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import meoipzi.meoipzi.shortform.dto.ShortformListResponseDTO;
import meoipzi.meoipzi.shortform.dto.ShortformRequestDTO;
import meoipzi.meoipzi.shortform.dto.ShortformResponseDTO;
import meoipzi.meoipzi.shortform.dto.ShortformUpdateRequestDTO;
import meoipzi.meoipzi.shortform.repository.ShortFormRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShortformService {
    private final ShortFormRepository shortformRepository;
    private final UserRepository userRepository;
    private final S3Config s3Config;
    private final HeartRepository heartRepository;

    // 숏폼 글 리스트 조회 -> 최신순 정렬
    @Transactional
    public Page<ShortformListResponseDTO> getLatestShortformList(Pageable pageable){
        try {
            Page<ShortForm> shortformPage = shortformRepository.findAllByOrderByIdDesc(pageable);
            List<ShortformListResponseDTO> shortformListResponseDTOS = shortformPage.stream()
                    .map(ShortformListResponseDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(shortformListResponseDTOS, pageable, shortformListResponseDTOS.size());
        } catch(Exception e){
            e.printStackTrace();
            return new PageImpl<>(Collections.emptyList());
        }
    }

    // 숏폼 글 리스트 조회 -> 좋아요순 정렬
    @Transactional
    public Page<ShortformListResponseDTO> getPopularShortformList(Pageable pageable){
        try {
            Page<ShortForm> shortformPage = shortformRepository.findAllByOrderByLikesCountDesc(pageable);
            List<ShortformListResponseDTO> shortformListResponseDTOS = shortformPage.stream()
                    .map(ShortformListResponseDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(shortformListResponseDTOS, pageable, shortformListResponseDTOS.size());
        } catch(Exception e){
            e.printStackTrace();
            return new PageImpl<>(Collections.emptyList());
        }
    }

    // 숏폼 글 등록
    @Transactional
    public ResponseEntity<?> saveShortform(ShortformRequestDTO shortformRequestDTO) {
        User user = userRepository.findByUsername(shortformRequestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + shortformRequestDTO.getUsername()));
        try {
            if (shortformRequestDTO.getImgUrl() != null) {
                String filePath = s3Config.upload(shortformRequestDTO.getImgUrl());
                ShortForm shortform = shortformRequestDTO.toEntity(user);
                shortform.setImgUrl(filePath);
                shortformRepository.save(shortform);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(shortformRequestDTO);
    }

    // 숏폼 글 삭제
    @Transactional
    public void deleteShortform(Long shortformId) {
        shortformRepository.deleteById(shortformId); // 글 삭제
    }

    // 숏폼 글 수정
    @Transactional
    public ResponseEntity<?> updateShortform(Long shortformId, ShortformUpdateRequestDTO shortformUpdateRequestDTO) throws IOException {

        User user = userRepository.findByUsername(shortformUpdateRequestDTO.getUserName())
                .orElseThrow(()-> new NotFoundMemberException("회원을 찾을 수 없습니다."));

        try {
            ShortForm originalShortform = shortformRepository.findById(shortformId)
                    .orElseThrow(() -> new RuntimeException("숏폼 게시글을 찾을 수 없습니다."));

            if (shortformUpdateRequestDTO.getImgUrl() != null) {
                String filePath = s3Config.upload(shortformUpdateRequestDTO.getImgUrl());
                originalShortform.setImgUrl(filePath);
            }
            originalShortform.setTitle(shortformUpdateRequestDTO.getTitle());
            originalShortform.setContents(shortformUpdateRequestDTO.getContents());
        } catch(IOException e) {
            throw new RuntimeException("숏폼 수정에 실패하였습니다." +e.getMessage(), e);
        }
        return ResponseEntity.ok(shortformUpdateRequestDTO);
    }

    // 숏폼 하나 상세 조회
    public ShortformResponseDTO viewShortform(Long communityId, String username){
        try {
            // 조회할 숏폼이 존재하는지 확인
            ShortForm shortform = shortformRepository.findById(communityId)
                    .orElseThrow(()-> new NoSuchElementException("조회할 숏폼이 존재하지 않습니다."));
            User user = userRepository.findByUsername(username)
                    .orElseThrow(()-> new NotFoundMemberException("회원을 찾을 수 없습니다."));

            ShortformResponseDTO shortformResponseDTO = new ShortformResponseDTO(shortform);
            if(heartRepository.findByUserAndShortForm(user, shortform).isPresent())
                shortformResponseDTO.setLikeOrNot(true);

            return shortformResponseDTO;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 숏폼이 존재하지 않습니다.");
        }
    }

}