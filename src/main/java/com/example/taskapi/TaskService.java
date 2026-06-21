package com.example.taskapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

/**
 * In-memory task store. Deliberately framework-light so it can be unit tested
 * without spinning up the Spring context.
 */
@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public List<Task> findAll() {
        return List.copyOf(tasks);
    }

    public Optional<Task> findById(Long id) {
        return tasks.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public Task create(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
        Task task = new Task(sequence.incrementAndGet(), title.trim(), false);
        tasks.add(task);
        return task;
    }

    public Optional<Task> markDone(Long id) {
        Optional<Task> existing = findById(id);
        existing.ifPresent(t -> t.setDone(true));
        return existing;
    }

    public boolean delete(Long id) {
        return tasks.removeIf(t -> t.getId().equals(id));
    }

    /** Number of tasks not yet completed. */
    public long pendingCount() {
        return tasks.stream().filter(t -> !t.isDone()).count();
    }
}
