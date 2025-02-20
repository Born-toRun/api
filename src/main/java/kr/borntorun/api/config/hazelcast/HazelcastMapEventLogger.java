package kr.borntorun.api.config.hazelcast;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.map.AbstractIMapEvent;
import com.hazelcast.map.MapEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HazelcastMapEventLogger implements EntryListener<Object, Object> {

	@Override
	public void entryAdded(final EntryEvent<Object, Object> event) {
		log(event);
	}

	@Override
	public void entryEvicted(final EntryEvent<Object, Object> event) {
		log(event);
	}

	@Override
	public void entryRemoved(final EntryEvent<Object, Object> event) {
		log(event);
	}

	@Override
	public void entryUpdated(final EntryEvent<Object, Object> event) {
		log(event);
	}

	@Override
	public void mapCleared(final MapEvent event) {
		log(event);
	}

	@Override
	public void mapEvicted(final MapEvent event) {
		log(event);
	}

	private void log(final AbstractIMapEvent event) {
		log.debug(event.toString());
	}

	@Override
	public void entryExpired(EntryEvent<Object, Object> event) {
		log(event);
	}
}
