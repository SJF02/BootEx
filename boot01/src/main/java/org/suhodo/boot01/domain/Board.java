package org.suhodo.boot01.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity     // 테이블이 생성된다.
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
public class Board extends BaseEntity{
    @Id     // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long bno;

    @Column(length=500, nullable=false)
    private String title;

    @Column(length=2000, nullable = false)
    private String content;

    @Column(length=50, nullable = false)
    private String writer;

    
    // @OneToMany  // 불필요한 매핑 테이블이 생긴다

    /*
    BoardImage의 board와 1:n관계를 지정하면 
    Board와 BoardImage사이에 1:n관계이며 양방향 연결이 이루어진다.

    EAGER : Board를 조회할 때 BoardImage를 무조건 조회해서 imageSet에 읽어들인다.
    LAZY : Board를 조회할 때 BoardImage는 조회하지 않는다.
           imageSet을 접근하면 그때 BoardImage를 조회한다.

    @ToString(exclude = "imageSet") : Board객체 정보 조회시 일단 imageSet은 제외
     */
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY,
                cascade = {CascadeType.ALL}, orphanRemoval = true)
    @Builder.Default
    private Set<BoardImage> imageSet = new HashSet<>();

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void addImage(String uuid, String fileName){
        BoardImage boardImage = BoardImage.builder()
                    .uuid(uuid)
                    .fileName(fileName)
                    .board(this)
                    .ord(imageSet.size())
                    .build();
        imageSet.add(boardImage);
    }

    public void clearImages(){
        imageSet.forEach(boardImage -> boardImage.changeBoard(null));

        imageSet.clear();
    }
}
