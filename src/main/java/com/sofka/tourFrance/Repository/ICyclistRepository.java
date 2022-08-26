package com.sofka.tourFrance.Repository;

import com.sofka.tourFrance.Domain.Cyclist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICyclistRepository extends JpaRepository<Cyclist,Long> {
}
