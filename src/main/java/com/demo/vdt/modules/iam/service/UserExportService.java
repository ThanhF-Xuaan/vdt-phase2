package com.demo.vdt.modules.iam.service;

import java.io.OutputStream;

public interface UserExportService {
    public void exportUsersToExcel(OutputStream outputStream);
}
