package ru.agr.filmscontent.filmapi;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.agr.filmscontent.filmapi.db.entity.Movie;
import ru.agr.filmscontent.filmapi.service.MovieService;

/**
 * @author Arslan Rabadanov
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class MovieTests extends DatabaseTest {
    @Autowired
    private MovieService movieService;

    @Test
    public void test_save_movie_without_genres() {
        Movie newMovie = new Movie();
        newMovie.setTitle("Крутой фильм");
        newMovie.setTitleEn("Cool film");
        newMovie.setYear("2022");
        newMovie.setImdbID("tt3230854");
        newMovie.setType(Movie.MovieType.movie);
        newMovie.setPoster("https://m.media-amazon.com/images/M/MV5BMTQ2MTgxNTU3Ml5BMl5BanBnXkFtZTcwMzg4OTAzMQ@@._V1_.jpg");
        newMovie.setDescription("Some description");
        newMovie.setCountry("RU");
        newMovie.setVideo("ubPt0UeJe64&list=PLsQAG1V_t58AKvV5v4NVXxo68OyLdNX3j");

        newMovie = movieService.save(newMovie);
        Assert.assertNotNull(newMovie);
        Assert.assertNotNull(newMovie.getId());

        Movie movie = movieService.getById(newMovie.getId());
        Assert.assertEquals(newMovie, movie);
    }

}
