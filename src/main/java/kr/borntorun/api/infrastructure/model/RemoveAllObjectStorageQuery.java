package kr.borntorun.api.infrastructure.model;

import java.util.List;

import kr.borntorun.api.domain.constant.Bucket;
import kr.borntorun.api.support.TokenDetail;

public record RemoveAllObjectStorageQuery(TokenDetail my,
                                          List<Integer> targetFileIds,
                                          Bucket bucket) {}
