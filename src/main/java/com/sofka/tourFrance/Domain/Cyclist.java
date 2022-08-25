package com.sofka.tourFrance.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cyclist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 30, nullable = false)
    private String name;
    @Column(length = 3, unique = true)
    private String competitorNumber;

    @ManyToOne
    @JoinColumn(name = "team", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "country",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Country country;




}
