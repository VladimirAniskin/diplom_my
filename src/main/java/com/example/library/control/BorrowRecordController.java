package com.example.library.control;
import com.example.library.dto.BorrowRecordsDto;
import com.example.library.service.BorrowRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Borrow Record Controller", description = "Borrow Record Controller")
@RequiredArgsConstructor
@RequestMapping("/api")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    @PostMapping(value = "/borrow")
    public @ResponseBody BorrowRecordsDto bookRental(@RequestBody BorrowRecordsDto dto) {
        return borrowRecordService.bookRental(dto);
    }

    @PutMapping(value = "/returnBook/{id}")
    public @ResponseBody BorrowRecordsDto bookReturn(@PathVariable Long id) {
        return borrowRecordService.bookReturn(id);
    }
}
