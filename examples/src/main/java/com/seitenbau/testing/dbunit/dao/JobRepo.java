package com.seitenbau.testing.dbunit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<Job, Long>
{
}
