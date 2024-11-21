// package com.example.cafemanagement.repository;
//
// import com.example.cafemanagement.domain.Hashtag;
// import org.springframework.data.jpa.repository.JpaRepository;
//
// import java.util.Optional;
//
// public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
//     /**
//      * 해시태그 이름으로 해시태그를 검색합니다.
//      *
//      * @param tagName 검색할 해시태그 이름
//      * @return 검색된 해시태그의 Optional 객체
//      */
//     Optional<Hashtag> findByTagName(String tagName);
//
//     /**
//      * 특정 해시태그 이름이 이미 존재하는지 확인합니다.
//      *
//      * @param tagName 확인할 해시태그 이름
//      * @return 해당 이름이 존재하면 true, 그렇지 않으면 false
//      */
//     boolean existsByTagName(String tagName);
// }
