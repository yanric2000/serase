package com.startup.repositories;

import com.startup.domain.event.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {

  List<Event> findByCityIgnoreCase(String city);
}
