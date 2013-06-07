package com.seitenbau.testing.personmanager;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long>
{
}
