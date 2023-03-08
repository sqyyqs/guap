package com.sqy.guap.domain;

public record Task(
        long taskId,
        String name,
        Type type
) {
    public enum Type {
        PRIMARY("primary"),
        SECONDARY("secondary");
        private final String typeName;

        Type(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeName() {
            return typeName;
        }

    }
}
