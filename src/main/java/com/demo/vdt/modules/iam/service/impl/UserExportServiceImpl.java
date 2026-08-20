package com.demo.vdt.modules.iam.service.impl;

import com.demo.vdt.modules.iam.entity.AppUser;
import com.demo.vdt.modules.iam.repository.AppUserRepository;
import com.demo.vdt.modules.iam.service.UserExportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserExportServiceImpl implements UserExportService {
    AppUserRepository appUserRepository;

    @Override
    public void exportUsersToExcel(OutputStream outputStream) {
        log.info("Entry exportUsersToExcel - UserExportService");

        List<AppUser> userList = appUserRepository.findAll();

        try(InputStream templateStream = new ClassPathResource("templates/users_template.xlsx").getInputStream()){
            Context context = new Context();
            context.putVar("users", userList);

            JxlsHelper.getInstance().processTemplate(templateStream, outputStream, context);

            outputStream.flush();
        } catch (Exception e) {
            log.error("Lỗi khi xuất file Excel User: ", e);
            throw new RuntimeException(e);
        }
    }
}
