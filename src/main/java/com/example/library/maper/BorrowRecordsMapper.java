package com.example.library.maper;

import com.example.library.dto.BorrowRecordsDto;
import com.example.library.dto.BorrowRecordsReturnDto;
import com.example.library.mod.BorrowRecords;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BorrowRecordsMapper {
    BorrowRecords toEntity(BorrowRecordsDto borrowRecordsDto);

    BorrowRecordsDto toDto(BorrowRecords borrowRecords);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BorrowRecords partialUpdate(BorrowRecordsDto borrowRecordsDto, @MappingTarget BorrowRecords borrowRecords);

    BorrowRecords toEntity(BorrowRecordsReturnDto borrowRecordsReturnDto);

    BorrowRecordsReturnDto toDto1(BorrowRecords borrowRecords);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BorrowRecords partialUpdate(BorrowRecordsReturnDto borrowRecordsReturnDto, @MappingTarget BorrowRecords borrowRecords);

    BorrowRecords updateWithNull(BorrowRecordsReturnDto borrowRecordsReturnDto, @MappingTarget BorrowRecords borrowRecords);
}