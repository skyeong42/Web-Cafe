 package com.example.cafemanagement.controller;

 import com.example.cafemanagement.dto.CafeDto;
 import com.example.cafemanagement.dto.HashtagDto;
 import io.swagger.v3.oas.annotations.Operation;
 import io.swagger.v3.oas.annotations.tags.Tag;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

 import java.util.List;

 @RestController
 @RequestMapping("/hashtags")
 @Tag(name = "해시태그 관리", description = "해시태그 추가, 삭제, 조회 등 관리 API")
 public class HashTagController {

     private final HashTagService hashTagService;

     public HashTagController(HashTagService hashTagService) {
         this.hashTagService = hashTagService;
     }

     @PostMapping
     @Operation(summary = "해시태그 생성", description = "새로운 해시태그를 생성합니다.")
     public ResponseEntity<HashtagDto> addHashTag(@RequestBody HashtagDto dto) {
         HashtagDto createdHashtag = hashTagService.createHashTag(dto);
         return ResponseEntity.ok(createdHashtag);
     }

     @PostMapping("/cafe/{cafeId}")
     @Operation(summary = "카페에 해시태그 추가", description = "특정 카페에 새로운 해시태그를 추가합니다.")
     public ResponseEntity<HashtagDto> addHashTagToCafe(@PathVariable Long cafeId, @RequestBody HashtagDto dto) {
         HashtagDto addedHashtag = hashTagService.addHashTagToCafe(cafeId, dto);
         return ResponseEntity.ok(addedHashtag);
     }

     @DeleteMapping("/cafe/{cafeId}/{tagId}")
     @Operation(summary = "카페에서 해시태그 삭제", description = "특정 카페에서 지정된 해시태그를 삭제합니다.")
     public ResponseEntity<Void> removeHashTag(@PathVariable Long cafeId, @PathVariable Long tagId) {
         hashTagService.removeHashTagFromCafe(cafeId, tagId);
         return ResponseEntity.noContent().build();
     }

     @GetMapping("/cafe/{cafeId}")
     @Operation(summary = "카페의 해시태그 조회", description = "특정 카페에 연결된 모든 해시태그를 조회합니다.")
     public ResponseEntity<List<HashtagDto>> getHashTagsForCafe(@PathVariable Long cafeId) {
         List<HashtagDto> hashtags = hashTagService.getHashTagsForCafe(cafeId);
         return ResponseEntity.ok(hashtags);
     }

     @GetMapping("/search")
     @Operation(summary = "해시태그로 카페 검색", description = "주어진 해시태그 이름으로 카페를 검색합니다.")
     public ResponseEntity<List<CafeDto>> searchCafesByHashTag(@RequestParam String tagName) {
         List<CafeDto> cafes = hashTagService.searchCafesByHashTag(tagName);
         return ResponseEntity.ok(cafes);
     }
 }
