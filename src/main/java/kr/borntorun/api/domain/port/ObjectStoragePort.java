package kr.borntorun.api.domain.port;

import kr.borntorun.api.domain.port.model.ObjectStorage;
import kr.borntorun.api.domain.port.model.RemoveObjectStorageCommand;
import kr.borntorun.api.domain.port.model.UploadObjectStorageCommand;

public interface ObjectStoragePort {

	ObjectStorage upload(final UploadObjectStorageCommand command);

	void remove(final RemoveObjectStorageCommand command);
}
