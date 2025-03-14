package kr.borntorun.api.domain.port;

import kr.borntorun.api.domain.port.model.ModifyUserPrivacyCommand;
import kr.borntorun.api.domain.port.model.UserPrivacy;

public interface PrivacyPort {

	void modifyUserPrivacy(ModifyUserPrivacyCommand command);

	UserPrivacy searchUserPrivacy(long userId);
}
