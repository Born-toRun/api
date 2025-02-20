package kr.borntorun.api.infrastructure.model;

import org.springframework.web.multipart.MultipartFile;

import kr.borntorun.api.domain.constant.Bucket;
import kr.borntorun.api.support.TokenDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModifyObjectStorageQuery {
  private long targetFileId;
  private String cdnUri;
  private TokenDetail my;
  private MultipartFile file;
  private Bucket bucket;
}
