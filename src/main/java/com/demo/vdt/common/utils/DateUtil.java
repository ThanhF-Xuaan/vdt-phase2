package com.demo.vdt.common.utils;

import com.demo.vdt.common.exception.AppException;
import com.demo.vdt.common.exception.ErrorCode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@UtilityClass
public class DateUtil {

    // Danh sách các định dạng đầu vào có thể xuất hiện từ phía Client / Postman
    private static final List<DateTimeFormatter> INPUT_FORMATTERS = Arrays.asList(
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),   // 23/03/2005
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),   // 2005-03-23 (Chuẩn ISO / Database)
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),   // 23-03-2005
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),   // 2005/03/23
            DateTimeFormatter.ofPattern("MM/dd/yyyy")    // 03/23/2005 (Chuẩn Mỹ)
    );

    // Định dạng chuẩn đầu ra theo yêu cầu: dd/MM/yyyy
    private static final DateTimeFormatter VN_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Nhận vào chuỗi ngày tháng bất kỳ định dạng nào -> Chuyển thành LocalDate
     */
    public static LocalDate parseToLocalDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }

        String cleanDate = dateStr.trim();

        // Thử lần lượt các định dạng đầu vào
        for (DateTimeFormatter formatter : INPUT_FORMATTERS) {
            try {
                return LocalDate.parse(cleanDate, formatter);
            } catch (DateTimeParseException e) {
                // Không khớp thì tự động bỏ qua để thử format tiếp theo
            }
        }

        // Nếu chạy hết danh sách mà không khớp định dạng nào -> Báo lỗi
        log.error("Không thể giải mã ngày tháng với bất kỳ định dạng nào: {}", cleanDate);
        throw new AppException(ErrorCode.INVALID_DATETIME_FORMAT);
    }

}