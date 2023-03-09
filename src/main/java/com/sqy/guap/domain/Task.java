package com.sqy.guap.domain;

public record Task(
        long taskId,
        String name,
        TaskType type,
        TaskStatus status
) {
    public enum TaskType {
        PRIMARY("primary"),
        SECONDARY("secondary");
        private final String typeName;

        TaskType(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeName() {
            return typeName;
        }

        public static TaskType getTaskType(String name) {
            if (PRIMARY.getTypeName().equals(name)) {
                return PRIMARY;
            }
            return SECONDARY;
        }
    }

    public enum TaskStatus {
        UNREAD("unread"),
        IN_PROGRESS("in progress"),
        COMPLETED("completed");
        private final String taskStatus;

        TaskStatus(String taskStatus) {
            this.taskStatus = taskStatus;
        }

        public String getTaskStatusName() {
            return taskStatus;
        }

        public static TaskStatus getTaskStatus(String name) {
            if (UNREAD.getTaskStatusName().equals(name)) {
                return UNREAD;
            } else if (IN_PROGRESS.getTaskStatusName().equals(name)) {
                return IN_PROGRESS;
            }
            return COMPLETED;
        }
    }
}
