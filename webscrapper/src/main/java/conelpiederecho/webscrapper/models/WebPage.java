package conelpiederecho.webscrapper.models;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Table(name = "webpage")
public class WebPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String domain;
    private String url;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String picture;
    private Integer rank;

}
