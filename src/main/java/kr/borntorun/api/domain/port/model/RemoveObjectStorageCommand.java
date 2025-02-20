package kr.borntorun.api.domain.port.model;

import kr.borntorun.api.domain.constant.Bucket;
import kr.borntorun.api.support.TokenDetail;

public record RemoveObjectStorageCommand(TokenDetail my,
										 long targetFileId,
                                         Bucket bucket) {

}
