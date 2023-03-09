package com.sqy.guap.domain;

public record Task(
        long taskId,
        String name,
        TaskType type
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

        public static TaskType getProductListItemType(String name) {
            if (PRIMARY.getTypeName().equals(name)) {
                return PRIMARY;
            }
            return SECONDARY;
        }
    }

}
