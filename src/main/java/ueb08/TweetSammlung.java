package ueb08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public interface TweetSammlung {
	/**
	 * Stopwoerter werden beim Zaehlen nicht beruecksichtigt.
	 * @param file Fileobjekt in der die Stopwoerter gespeichert sind.
	 */
	void setStopwords(File file) throws FileNotFoundException;

	/**
	 * Einen weiteren Tweet indexieren.
	 * @param tweet lower-case, whitespace delimited text
	 */
	void ingest(String tweet);

	/**
	 * @return Ein Iterator über das Vokabular, alphabetisch aufsteigend.
	 */
	Iterator<String> vocabIterator();

	/**
	 * @return Ein Iterator über die am häufigsten verwendeten #hashtags, absteigend.
	 */
	Iterator<String> topHashTags();

	/**
	 * @return Ein Iterator über die am häufigsten verwendeten @mentions, absteigend.
	 */
	Iterator<String> topWords();


	/**
	 * @return Ein Iterator ueber die Tweets, welche insgesamt die meisten buzzwordigen woerter haben.
	 */
	Iterator<Pair> topTweets();

	/**
	 * Splittet einen String in seine einzelnen Wörter
	 * @param tweet um Satzzeichen und Kleinschreibung bereinigter Tweet
	 * @return Der Tweet als Liste von Wörtern
	 */
	static List<String> tokenize(String tweet) {
		List<String> list = new LinkedList<>();

		Scanner scanner = new Scanner(tweet);
		while (scanner.hasNext()) {
			list.add(scanner.next());
		}

		return list;
	}

	/**
	 * Erstellt eine neue Implementierung von TweetSammlung
	 */
	static TweetSammlung create() {
	   return new TweetSammlungImpl();
	}
}
