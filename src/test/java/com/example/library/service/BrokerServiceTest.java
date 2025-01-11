package com.example.library.service;

import com.example.library.mod.BorrowRecords;
import com.example.library.mod.User;
import com.example.library.repo.BorrowRecordsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BrokerServiceTest {
    @Mock
    private BorrowRecordsRepository borrowRecordsRepository;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    @InjectMocks
    private BrokerService brokerService;

    public BrokerServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void returnBook_ShouldSendNotification_WhenReturnDateIsInThreeDays() {

        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = currentDate.plusDays(3);
        User user = new User();
        user.setId(1L);
        BorrowRecords record = new BorrowRecords();
        record.setUserId(user);
        record.setReturnDate(targetDate);
        when(borrowRecordsRepository.findBorrowRecordsByReturnDate(targetDate)).thenReturn(Collections.singletonList(record));
        brokerService.returnBook();
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate, times(1)).send(eq("return-book"), messageCaptor.capture());
        String expectedMessage = "Напоминание: Срок сдачи книги с ID null для пользователя с ID 1 истекает через 3 дня.";
        assertEquals(expectedMessage, messageCaptor.getValue());
    }
}