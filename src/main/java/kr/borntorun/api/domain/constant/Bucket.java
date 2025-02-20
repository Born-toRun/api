package kr.borntorun.api.domain.constant;

import lombok.Getter;

@Getter
public enum Bucket {

	PROFILE("profile", false),
	FEED("feed", false);

	private final String bucketName;
	private final boolean isEncryption;

	Bucket(String bucketName, boolean isEncryption) {
		this.bucketName = bucketName;
		this.isEncryption = isEncryption;
	}
}
