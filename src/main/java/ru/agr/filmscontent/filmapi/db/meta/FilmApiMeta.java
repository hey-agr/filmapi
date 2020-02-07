package ru.agr.filmscontent.filmapi.db.meta;

/**
 * Database metadata
 *
 * @author Arslan Rabadanov
 */
public final class FilmApiMeta {
    public static final String schema = "film_api";

    /**
     * Table Movie
     */
    public static final class movie {
        public static final String name = "movie";

        public static final class fld {
            public static final String id = "id";
        }
    }
}
