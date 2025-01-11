package com.example.library.maper;

import com.example.library.dto.BorrowRecordsDto;
import com.example.library.dto.BorrowRecordsReturnDto;
import com.example.library.mod.BorrowRecords;
import org.mapstruct.*;

/**
 * Интерфейс для преобразования между сущностями записей о заимствованиях и их DTO (Data Transfer Object).
 * Использует библиотеку MapStruct для автоматического генерации кода преобразования.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BorrowRecordsMapper {
    /**
     * Преобразует объект BorrowRecordsDto в сущность BorrowRecords.
     *
     * @param borrowRecordsDto объект DTO записей о заимствованиях, который необходимо преобразовать
     * @return преобразованный объект BorrowRecords
     */
    BorrowRecords toEntity(BorrowRecordsDto borrowRecordsDto);

    /**
     * Преобразует сущность BorrowRecords в объект BorrowRecordsDto.
     *
     * @param borrowRecords сущность записей о заимствованиях, которую необходимо преобразовать
     * @return преобразованный объект BorrowRecordsDto
     */

    BorrowRecordsDto toDto(BorrowRecords borrowRecords);

    /**
     * Обновляет существующую сущность BorrowRecords, используя данные из BorrowRecordsDto.
     * Игнорирует свойства, которые равны null в BorrowRecordsDto.
     *
     * @param borrowRecordsDto объект DTO записей о заимствованиях, содержащий данные для обновления
     * @param borrowRecords    сущность записей о заимствованиях, которую необходимо обновить
     * @return обновленная сущность BorrowRecords
     */

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BorrowRecords partialUpdate(BorrowRecordsDto borrowRecordsDto, @MappingTarget BorrowRecords borrowRecords);

    /**
     * Преобразует объект BorrowRecordsReturnDto в сущность BorrowRecords.
     *
     * @param borrowRecordsReturnDto объект DTO для возврата записей о заимствованиях, который необходимо преобразовать
     * @return преобразованный объект BorrowRecords
     */
    BorrowRecords toEntity(BorrowRecordsReturnDto borrowRecordsReturnDto);

    /**
     * Преобразует сущность BorrowRecords в объект BorrowRecordsReturnDto.
     *
     * @param borrowRecords сущность записей о заимствованиях, которую необходимо преобразовать
     * @return преобразованный объект BorrowRecordsReturnDto
     */
    BorrowRecordsReturnDto toDto1(BorrowRecords borrowRecords);

    /**
     * Обновляет существующую сущность BorrowRecords, используя данные из BorrowRecordsReturnDto.
     * Игнорирует свойства, которые равны null в BorrowRecordsReturnDto.
     *
     * @param borrowRecordsReturnDto объект DTO для возврата записей о заимствованиях, содержащий данные для обновления
     * @param borrowRecords          сущность записей о заимствованиях, которую необходимо обновить
     * @return обновленная сущность BorrowRecords
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BorrowRecords partialUpdate(BorrowRecordsReturnDto borrowRecordsReturnDto, @MappingTarget BorrowRecords borrowRecords);

    /**
     * Обновляет существующую сущность BorrowRecords, используя данные из BorrowRecordsReturnDto.
     * В отличие от partialUpdate, этот метод обновляет свойства, даже если они равны null.
     *
     * @param borrowRecordsReturnDto объект DTO для возврата записей о заимствованиях, содержащий данные для обновления
     * @param borrowRecords          сущность записей о заимствованиях, которую необходимо обновить
     * @return обновленная сущность BorrowRecords
     */
    BorrowRecords updateWithNull(BorrowRecordsReturnDto borrowRecordsReturnDto, @MappingTarget BorrowRecords borrowRecords);
}