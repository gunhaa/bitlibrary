package bitcopark.library.entity.Board;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @GeneratedValue
    @Id
    @Column(name = "category_id")
    private Long id;
    private String categoryName;
    private String categoryEngName;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name ="parent_category_id")
    private Category parentCategory;

    @OneToMany (mappedBy = "parentCategory", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Category> subCategory = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<Board> boardList = new ArrayList<>();


}
