package com.godsplayground;

public interface Player {
    public class Identifier {
        private long id;

        public Identifier(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Identifier that = (Identifier) o;

            if (id != that.id) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return (int) (id ^ (id >>> 32));
        }
    }

    public Identifier getId();

    public void sendNotification(Object notification);

}
