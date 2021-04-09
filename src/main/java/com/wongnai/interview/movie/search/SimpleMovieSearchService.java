package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;


@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 2 => Implement this method by using data from MovieDataService
		// All test in SimpleMovieSearchServiceIntegrationTest must pass.
		// Please do not change @Component annotation on this class
		MoviesResponse movies = movieDataService.fetchAll();
		List<Movie> result = new ArrayList<Movie>();
		movies.forEach(m -> {
			String title = m.getTitle().toLowerCase();
			String query = queryText.toLowerCase();
			/*
			* check query are contain in movie title and whole sentence aren't exactly same whole query
			*
			* if query is a word and it is not match with word in title should not include
			*
			* */
			if((title.contains(query) && !title.equals(query)) ){
				int firstIndex = title.indexOf(query);
				int lastIndex = title.substring(firstIndex).indexOf(" ")+firstIndex;
				String title_contain = title.substring(firstIndex,lastIndex);
				if(title_contain.equals(query)){
					Movie new_movie = new Movie(m.getTitle());
					new_movie.setActors(m.getCast());
					result.add(new_movie);
				}
			}
		});
		return result;
	}
}
