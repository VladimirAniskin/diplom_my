package com.example.library.control;
import com.example.library.dto.BorrowRecordsDto;
import com.example.library.service.BorrowRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
/**
 * Контроллер для управления записями о заимствовании книг.
 * Предоставляет REST API для аренды и возврата книг.
 */
@RestController
@Tag(name = "Borrow Record Controller", description = "Borrow Record Controller")
@RequiredArgsConstructor
@RequestMapping("/api")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;
    /**
     * Обрабатывает запрос на аренду книги.
     *
     * @param dto объект передачи данных, содержащий информацию о заимствовании книги
     * @return объект BorrowRecordsDto, представляющий запись о заимствовании
     */

    @PostMapping(value = "/borrow")
    public @ResponseBody BorrowRecordsDto bookRental(@RequestBody BorrowRecordsDto dto) {
        return borrowRecordService.bookRental(dto);
    }
    /**
     * Обрабатывает запрос на возврат книги по ее идентификатору.
     *
     * @param id идентификатор записи о заимствовании, которую нужно обновить
     * @return объект BorrowRecordsDto, представляющий обновленную запись о возврате книги
     */
    @PutMapping(value = "/returnBook/{id}")
    public @ResponseBody BorrowRecordsDto bookReturn(@PathVariable Long id) {
        return borrowRecordService.bookReturn(id);
    }
}
