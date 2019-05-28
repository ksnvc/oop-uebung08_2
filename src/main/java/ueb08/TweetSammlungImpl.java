package ueb08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Predicate;

public class TweetSammlungImpl implements TweetSammlung {

    private Map<String, Integer> words = new HashMap<>();
    private List<String> tweets = new LinkedList<>();

    private List<String> stopwords = new LinkedList<>();
    @Override
    public void setStopwords(File file) throws FileNotFoundException {
        Scanner sc  = new Scanner(file);
        while(sc.hasNext()) {
            stopwords.add(sc.next());
        }
    }

    @Override
    public void ingest(String tweet) {

        tweets.add(tweet);

     List<String> tw = TweetSammlung.tokenize(tweet);
      for(String s: tw){
          if(stopwords.contains(s))
              continue;
         if(words.containsKey(s)){

             words.put(s, words.get(s) +1);

      } else {
             words.put(s, 1);
         }
      }
    }

    @Override
    public Iterator<String> vocabIterator() {
        List<String> l = new ArrayList<>(words.keySet());

        l.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        return l.iterator();
    }

     private Iterator<String> filter(Predicate<String> pred) {

         List<Map.Entry<String,Integer>> l = new ArrayList<>();

         for(Map.Entry<String,Integer> s: words.entrySet()) {
             if(pred.test(s.getKey()))
                 l.add(s);
         }

         l.sort(new Comparator<Map.Entry<String, Integer>>() {
             @Override
             public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                 return o2.getValue().compareTo(o1.getValue());
             }
         });
         List <String> ls = new ArrayList<>();
         for(Map.Entry<String, Integer> e: l) {
             ls.add(e.getKey());
         }
         return ls.iterator();
    }

    @Override
    public Iterator<String> topHashTags() {
        return filter(s-> s.startsWith("#"));
    }

    @Override
    public Iterator<String> topWords() {
        return filter(s-> !s.startsWith("#"));
    }

    @Override
    public Iterator<Pair> topTweets() {
        List<Pair> result = new LinkedList<>();

        for(String s : tweets) {
            List<String> tweetWords = TweetSammlung.tokenize(s);
            int count = 0;
            for (String w : tweetWords) {
                if(words.containsKey(w))
                    count += words.get(w);
            }
            result.add(new Pair(s,count));
        }

        result.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
               return o2.getValue().compareTo(o1.getValue());
            }
        });
        return result.iterator();
    }
}
