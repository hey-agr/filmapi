package ru.agr.filmscontent.filmapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.agr.filmscontent.filmapi.db.entity.Movie;

import java.io.Serializable;
import java.util.List;

/**
 * {
 *      "Search": [
 *          {
 *              "Title": "Iron Man 3: The Prologue",
 *              "Year": "2013",
 *              "imdbID": "tt3107386",
 *              "Type": "movie",
 *              "Poster": "N/A"
 *          },
 *          {
 *              "Title": "Iron Man 3: The Official Game",
 *              "Year": "2013",
 *              "imdbID": "tt4111326",
 *              "Type": "game",
 *              "Poster": "N/A"
 *          },
 *          {
 *              "Title": "Iron Man 3: Advancing the Tech",
 *              "Year": "2013",
 *              "imdbID": "tt3455774",
 *              "Type": "movie",
 *              "Poster": "N/A"
 *          }],
 *      "totalResults": "3",
 *      "Response": "True"
 *}
 */
public class MovieDTO implements Serializable {
    private List<Movie> Search;

    private String totalResults;

    private String Response;

    public MovieDTO(List<Movie> search, String totalResults, String response) {
        Search = search;
        this.totalResults = totalResults;
        Response = response;
    }

    public List<Movie> getSearch() {
        return Search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return Response;
    }
}
