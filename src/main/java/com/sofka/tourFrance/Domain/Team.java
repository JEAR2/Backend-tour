package com.sofka.tourFrance.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 30, nullable = false, unique = true)
    private String name;
    @Column(name = "codeTeam", length = 30, nullable = false, unique = true)
    private String codeTeam;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Country.class)
    @JoinColumn(name = "country")
    @JsonBackReference
    private Country country;

    @OneToMany(mappedBy = "team")
    private Set<Cyclist> cyclistsList = new HashSet<>();

}
