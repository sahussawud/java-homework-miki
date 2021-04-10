package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.MovieSearchService;

@Component("invertedIndexMovieSearchService")
@DependsOn("movieDatabaseInitializer")
public class InvertedIndexMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieRepository movieRepository;

	private HashMap<String, HashSet<Long>> invertedIndexHashMap;

	private HashMap<Long, Movie> cacheMovies;


	public InvertedIndexMovieSearchService(){
		invertedIndexHashMap = new HashMap<String, HashSet<Long>>();
		cacheMovies = new HashMap<Long, Movie>();
	}

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 4 => Please implement in-memory inverted index to search movie by keyword.
		// You must find a way to build inverted index before you do an actual search.
		// Inverted index would looks like this:
		// -------------------------------
		// |  Term      | Movie Ids      |
		// -------------------------------
		// |  Star      |  5, 8, 1       |
		// |  War       |  5, 2          |
		// |  Trek      |  1, 8          |
		// -------------------------------
		// When you search with keyword "Star", you will know immediately, by looking at Term column, and see that
		// there are 3 movie ids contains this word -- 1,5,8. Then, you can use these ids to find full movie object from repository.
		// Another case is when you find with keyword "Star War", there are 2 terms, Star and War, then you lookup
		// from inverted index for Star and for War so that you get movie ids 1,5,8 for Star and 2,5 for War. The result that
		// you have to return can be union or intersection of those 2 sets of ids.
		// By the way, in this assignment, you must use intersection so that it left for just movie id 5.
		if(invertedIndexHashMap.isEmpty()){
			movieRepository.findAll().forEach(m->{
				// Add All movies to cache to optimize query performance
				cacheMovies.put(m.getId(),m);
				for (String word: m.getName().split("\\s")){
					word = word.toLowerCase();
					HashSet<Long> temp = invertedIndexHashMap.get(word);
					if(temp == null){
						temp = new HashSet<Long>();
						invertedIndexHashMap.put(word, temp);
					}
					temp.add(m.getId());
					invertedIndexHashMap.put(word, temp);
				}
			});
		}

		// Intersect All Movies Id Set with sentence or word

		HashSet<Long> MoviesId = new HashSet<Long>();

		boolean onceFlag = true;
		for(String queryWord: queryText.split("\\s")){
			queryWord = queryWord.toLowerCase();
				if(invertedIndexHashMap.get(queryWord) != null){
					if(onceFlag){
						MoviesId.addAll(invertedIndexHashMap.get(queryWord));
						onceFlag = false;
					}else if(!MoviesId.isEmpty()){
						HashSet<Long> MoviesId2 = new HashSet<Long>();
						MoviesId2.addAll(invertedIndexHashMap.get(queryWord));
						if(!MoviesId2.isEmpty()){
							MoviesId.retainAll(MoviesId2);
						}
					}
				}else{
					MoviesId.clear();
				}

		}

		List<Movie> result = new ArrayList<Movie>();

		if(MoviesId != null){
			MoviesId.forEach(mId->{
				result.add(cacheMovies.get(mId));
			});
		}

		return result;

	}
}
