package com.example.library.service;

import com.example.library.mod.Book;
import com.example.library.mod.BorrowRecords;
import com.example.library.mod.User;
import com.example.library.repo.BorrowRecordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrokerService {

    private final BorrowRecordsRepository borrowRecordsRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Метод, который запускается раз в сутки и проверяет записи о возврате книг.
     * Если срок возврата книги истекает через 3 дня, отправляет уведомление.
     */

    //@Scheduled(cron = "*/1 * * * * ?") // Каждую секунду
    @Scheduled(cron = "0 0 7 * * ?") // Каждое утро в 7:00
    public void returnBook() {
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = currentDate.plusDays(3); // Дата через 3 дня
        // Извлечение всех записей о выдаче книг
        List<BorrowRecords> borrowRecords = borrowRecordsRepository.findBorrowRecordsByReturnDate(targetDate);
        for (BorrowRecords record : borrowRecords) {
            User user = record.getUserId(); // Получение пользователя
            Book book = record.getBookId(); // Получение книги
            // Проверка на null перед отправкой уведомления
            if (book != null && user != null) {
                // Отправка уведомления через Kafka
                kafkaTemplate.send("return-book", "Напоминание: Срок сдачи книги с ID " +
                        book.getId() + " для пользователя с ID " + user.getId() + " истекает через 3 дня.");
            } else {
                // Логирование или обработка случая, когда book или user равен null
                System.out.println("Запись о выдаче книги содержит null для книги или пользователя.");
            }
        }
    }
}
