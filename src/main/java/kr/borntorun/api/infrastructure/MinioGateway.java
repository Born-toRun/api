package kr.borntorun.api.infrastructure;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.thirdparty.ObjectStorageClient;
import kr.borntorun.api.adapter.out.thirdparty.model.Remove;
import kr.borntorun.api.adapter.out.thirdparty.model.RemoveAll;
import kr.borntorun.api.adapter.out.thirdparty.model.Upload;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MinioGateway {

	private final ObjectStorageClient objectStorageClient;

	public void removeObject(Remove remove) {
		objectStorageClient.remove(remove);
	}

	public void removeObjects(RemoveAll removeAll) {
		objectStorageClient.removeAll(removeAll);
	}

	public String uploadObject(Upload upload) {
		return objectStorageClient.upload(upload);
	}
}
