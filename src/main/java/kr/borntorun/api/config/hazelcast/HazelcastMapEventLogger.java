package kr.borntorun.api.config.hazelcast;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.map.AbstractIMapEvent;
import com.hazelcast.map.MapEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HazelcastMapEventLogger implements EntryListener<Object, Object> {

	@Override
	public void entryAdded(EntryEvent<Object, Object> event) {
		log(event);
	}

	@Override
	public void entryEvicted(EntryEvent<Object, Object> event) {
		log(event);
	}

	@Override
	public void entryRemoved(EntryEvent<Object, Object> event) {
		log(event);
	}

	@Override
	public void entryUpdated(EntryEvent<Object, Object> event) {
		log(event);
	}

	@Override
	public void mapCleared(MapEvent event) {
		log(event);
	}

	@Override
	public void mapEvicted(MapEvent event) {
		log(event);
	}

	private void log(AbstractIMapEvent event) {
		log.debug(event.toString());
	}

	@Override
	public void entryExpired(EntryEvent<Object, Object> event) {
		log(event);
	}
}
