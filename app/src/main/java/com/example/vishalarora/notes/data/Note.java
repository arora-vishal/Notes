package com.example.vishalarora.notes.data;

/**
 * Created by vishalarora on 25/03/18.
 */

public class Note {

    private int id;
    private String title;
    private String body;

    public int getId() {
        return id;
    }

    public long getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public long getModifiedTimeStamp() {
        return modifiedTimeStamp;
    }

    private long createdTimeStamp;
    private long modifiedTimeStamp;

    private Note(Builder builder) {
        id = builder.id;
        title = builder.title;
        body = builder.body;
        createdTimeStamp = builder.createdTimeStamp == 0L ? System.currentTimeMillis() : builder.createdTimeStamp;
        modifiedTimeStamp = builder.modifiedTimeStamp == 0L ? System.currentTimeMillis() : builder.createdTimeStamp;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }


    public static final class Builder {
        private long modifiedTimeStamp;
        private long createdTimeStamp;
        private String body;
        private String title;
        public int id;

        public Builder() {
        }

        public Builder withId(int val) {
            id = val;
            return this;
        }

        public Builder withModifiedTimeStamp(long val) {
            modifiedTimeStamp = val;
            return this;
        }

        public Builder withCreatedTimeStamp(long val) {
            createdTimeStamp = val;
            return this;
        }

        public Builder withBody(String val) {
            body = val;
            return this;
        }

        public Builder withTitle(String val) {
            title = val;
            return this;
        }

        public Note build() {
            return new Note(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        return id == note.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
