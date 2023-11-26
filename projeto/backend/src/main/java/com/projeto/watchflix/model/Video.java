package com.projeto.watchflix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "ID")
    private long videoId;
    @Lob
    @Column(name = "THUMBNAIL_URL")
    private byte[] thumbnailUrl;

    @Column(name = "VIDEO_URL")
    private String videoUrl;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANNEL_ID", referencedColumnName = "ID")
    private Channel channel;
    @Column(name = "LIKES")
    private int likes;
    @Column(name = "DISLIKES")
    private int disLikes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VIDEO_STATUS_ID", referencedColumnName = "ID")
    private VideoStatus videoStatus;

    @Column(name = "VIEW_COUNT")
    private int viewCount;
    @Column(name = "ACTIVE")
    private boolean active;
}