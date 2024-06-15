package com.cm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PoemKidRepository extends JpaRepository<PoemKid, Long> {

}
