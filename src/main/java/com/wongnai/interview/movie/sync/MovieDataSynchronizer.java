package com.wongnai.interview.movie.sync;

import javax.transaction.Transactional;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieDataService;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieDataSynchronizer {
	@Autowired
	private MovieDataService movieDataService;

	@Autowired
	private MovieRepository movieRepository;

	@Transactional
	public void forceSync() {
		//TODO: implement this to sync movie into repository
		MoviesResponse fetchMovie = movieDataService.fetchAll();
		List<Movie> movies = new ArrayList<Movie>();
		fetchMovie.forEach(m->{
			Movie new_movie = new Movie(m.getTitle());
			new_movie.setActors(m.getCast());
			movies.add(new_movie);
		});
		movieRepository.saveAll(movies);
	}
}
