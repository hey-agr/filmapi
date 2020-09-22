package ru.agr.filmscontent.filmapi.db.meta;

/**
 * Database metadata
 *
 * @author Arslan Rabadanov
 */
public final class FilmApiMetaUtils {
    public static final String SCHEMA = "main";

    private FilmApiMetaUtils() {
    }

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
             * Title (en)
             */
            public static final String title_en = "title_en";
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
            /**
             * Description
             */
            public static final String description = "description";
            /**
             * Country
             */
            public static final String country = "country";
        }
    }

    /**
     * Table Genre
     */
    public static final class genre {
        public static final String name = "genre";

        public static final class fld {
            /**
             * ID
             */
            public static final String id = "id";
            /**
             * Name
             */
            public static final String name = "name";
        }
    }

    /**
     * Table MovieGenre
     */
    public static final class movie_genre {
        public static final String name = "movie_genre";

        public static final class fld {
            /**
             * Movie ID
             */
            public static final String movie_id = "movie_id";
            /**
             * Genre ID
             */
            public static final String genre_id = "genre_id";
        }
    }
}
