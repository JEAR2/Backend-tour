package com.sofka.tourFrance.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "country")
@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 30, nullable = false, unique = true)
    private String name;
    @Column(name = "code", length = 3, nullable = false,unique = true)
    private String codeCountry;

    @OneToMany(mappedBy = "country",cascade = CascadeType.ALL)
    private Set<Cyclist> cyclistList = new HashSet<>();

    @OneToMany(mappedBy = "country",cascade = CascadeType.ALL)
    private Set<Team> teamList = new HashSet<>();




}
