package bdbe.bdbd.keyword;

import bdbe.bdbd._core.errors.utils.KeywordTypeConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="keyword")
public class Keyword { //키워드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Column(name="name", length = 50, nullable = false)
    private String name;


    @Column(nullable = false)
    @Convert(converter = KeywordTypeConverter.class)
    private KeywordType type;

    @Builder
    public Keyword(Long id, String name, KeywordType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}