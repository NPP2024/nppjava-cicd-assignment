package com.example.taskapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskServiceTest {

    private TaskService service;

    @BeforeEach
    void setUp() {
        service = new TaskService();
    }

    @Test
    void createAssignsIncrementingIds() {
        Task first = service.create("write tests");
        Task second = service.create("ship feature");

        assertThat(first.getId()).isEqualTo(1L);
        assertThat(second.getId()).isEqualTo(2L);
        assertThat(first.isDone()).isFalse();
    }

    @Test
    void createTrimsWhitespaceInTitle() {
        Task task = service.create("  tidy up  ");
        assertThat(task.getTitle()).isEqualTo("tidy up");
    }

    @Test
    void createRejectsBlankTitle() {
        assertThatThrownBy(() -> service.create("   "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void markDoneFlipsTheFlag() {
        Task task = service.create("review PR");
        service.markDone(task.getId());

        assertThat(service.findById(task.getId()))
                .get()
                .extracting(Task::isDone)
                .isEqualTo(true);
    }

    @Test
    void deleteRemovesTask() {
        Task task = service.create("temporary");
        boolean removed = service.delete(task.getId());

        assertThat(removed).isTrue();
        assertThat(service.findAll()).isEmpty();
    }

    @Test
    void pendingCountIgnoresCompletedTasks() {
        Task a = service.create("a");
        service.create("b");
        service.markDone(a.getId());

        assertThat(service.pendingCount()).isEqualTo(1L);
    }
}
