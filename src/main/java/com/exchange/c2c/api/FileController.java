package com.exchange.c2c.api;

import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.annotation.Login;
import com.exchange.c2c.common.util.RandomUtils;
import com.exchange.c2c.common.util.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Api(tags = "文件接口")
@Validated
@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${file.dir}")
    private String dir;

    @Login
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", notes = "创建人: 李海峰")
    public Result<?> upload(@NotNull(message = "文件不能为空") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Objects.requireNonNull(originalFilename);
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = System.currentTimeMillis() + "_" + RandomUtils.randomString(4) + suffixName;
        String lastName = "/" + WebUtils.getUserId() + "/" + fileName;
        File target = new File(dir + lastName);
        File folder = target.getParentFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        file.transferTo(target);
        return Result.success(lastName);
    }
}
