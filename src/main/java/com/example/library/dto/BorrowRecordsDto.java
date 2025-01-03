package com.example.library.dto;

import lombok.Value;

/**
 * DTO for {@link com.example.library.mod.BorrowRecords}
 */
@Value
public class BorrowRecordsDto {
    UserDtoBorrow userId;
    BookDtoBorrow bookId;


    /**
     * DTO for {@link com.example.library.mod.User}
     */
    @Value
    public static class UserDtoBorrow {
        Long id;
    }

    /**
     * DTO for {@link com.example.library.mod.Book}
     */
    @Value
    public static class BookDtoBorrow {
        Long id;
    }
}