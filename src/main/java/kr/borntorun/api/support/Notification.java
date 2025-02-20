package kr.borntorun.api.support;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.thirdparty.DiscordAdapter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Notification {

	private final DiscordAdapter discordAdapter;

	public void send(final String message) {
		discordAdapter.send(message);
	}
}
