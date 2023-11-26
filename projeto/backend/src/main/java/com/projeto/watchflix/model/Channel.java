package com.projeto.watchflix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CHANNEL")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "ID")
    private Long channelId;
    @Column(name = "PROFILE_IMAGE", columnDefinition = "VARBINARY(MAX)")
    private byte[] profileImage;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "FULL_NAME")
    private String fullName;
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ACTIVE")
    private boolean active;

}