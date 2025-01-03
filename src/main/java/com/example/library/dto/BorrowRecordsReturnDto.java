package com.example.library.dto;

import lombok.Value;

import java.time.LocalDate;

/**
 * DTO for {@link com.example.library.mod.BorrowRecords}
 */
@Value
public class BorrowRecordsReturnDto {
    private Long borrowRecordId;
    private LocalDate returnDate;

}