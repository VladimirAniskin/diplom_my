package com.example.library.repo;

import com.example.library.mod.BorrowRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRecordsRepository extends JpaRepository<BorrowRecords, Long> {
}
