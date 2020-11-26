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
            /**
             * URL видео
             */
            public static final String video = "video";
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

    /**
     * Table user
     */
    public static final class user {
        public static final String name = "user";

        public static final class fld {
            /**
             * ID
             */
            public static final String id = "id";
            /**
             * Username
             */
            public static final String username = "username";
            /**
             * Password
             */
            public static final String password = "password";
            /**
             * Account date created
             */
            public static final String date_created = "date_created";
            /**
             * Account blocked
             */
            public static final String blocked = "blocked";
            /**
             * name
             */
            public static final String name = "name";
            /**
             * last name
             */
            public static final String last_name = "last_name";
            /**
             * middle name
             */
            public static final String middle_name = "middle_name";
            /**
             * avatar image data
             */
            public static final String avatar_data = "avatar_data";
            /**
             * avatar image filename
             */
            public static final String avatar_filename = "avatar_filename";
            /**
             * Gender
             */
            public static final String gender = "gender";
            /**
             * E-Mail
             */
            public static final String email = "email";
            /**
             * Theme
             */
            public static final String theme = "theme";
        }
    }

    /**
     * Table role
     */
    public static final class role {
        public static final String name = "role";

        public static final class fld {
            /**
             * ID
             */
            public static final String id = "id";
            /**
             * Name
             */
            public static final String name = "name";
            /**
             * Description
             */
            public static final String description = "description";
        }
    }

    /**
     * Table user_role
     */
    public static final class user_role {
        public static final String name = "user_role";

        public static final class fld {
            /**
             * User ID
             */
            public static final String user_id = "user_id";
            /**
             * Role ID
             */
            public static final String role_id = "role_id";
        }
    }

    /**
     * Table role_permission
     */
    public static final class role_permission {
        public static final String name = "role_permission";

        public static final class fld {
            /**
             * ID
             */
            public static final String id = "id";
            /**
             * Permission system name
             */
            public static final String authority = "authority";
            /**
             * Role ID
             */
            public static final String role_id = "role_id";
        }
    }
}
