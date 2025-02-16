package com.myApp.TazkartiApp.Repositories;

import com.myApp.TazkartiApp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
