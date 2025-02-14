package kr.borntorun.api.adapter.out.thirdparty.model;

import org.springframework.web.multipart.MultipartFile;

public record Upload(MultipartFile file,
                    int myUserId,
                    String bucket) {}
