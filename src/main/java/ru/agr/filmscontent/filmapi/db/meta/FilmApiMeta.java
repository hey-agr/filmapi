package ru.agr.filmscontent.filmapi.db.meta;

/**
 * Database metadata
 *
 * @author Arslan Rabadanov
 */
public final class FilmApiMeta {
    public static final String schema = "main";

    /**
     * Table Movie
     */
    public static final class movie {
        public static final String name = "movie";

        public static final class fld {
            /**
             * ID
             */
            public static final String id = "id";
            /**
             * Title
             */
            public static final String title = "title";
            /**
             * Year
             */
            public static final String year = "year";
            /**
             * IMDB ID
             */
            public static final String imdbID = "imdb_id";
            /**
             * Type
             */
            public static final String type = "type";
            /**
             * Poster (image address)
             */
            public static final String poster = "poster";
        }
    }
}
