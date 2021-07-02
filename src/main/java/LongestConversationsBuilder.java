import io.github.redouane59.twitter.dto.collections.CollectionsResponse;
import io.github.redouane59.twitter.dto.endpoints.AdditionalParameters;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import io.github.redouane59.twitter.dto.tweet.TweetList;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LongestConversationsBuilder extends CollectionBuilder {

  public LongestConversationsBuilder(final CollectionConfiguration collectionConfiguration) throws IOException {
    super(collectionConfiguration);
  }

  public CollectionsResponse collect() {
    TweetList
        tweets =
        this.getTwitterClient().searchTweets(this.getConfig().getQuery(),
                                             AdditionalParameters.builder()
                                             .startTime(LocalDateTime.now().minusHours(this.getConfig().getHoursInPastStart()))
                                             .endTime(LocalDateTime.now().minusHours(this.getConfig().getHoursInPastEnd()).minusMinutes(1))
                                             .maxResults(100)
                                             .build());
    Map<String, Integer> conversationIds = new HashMap<>();
    for (Tweet tweet : tweets.getData()) {
      conversationIds.put(tweet.getConversationId(), conversationIds.getOrDefault(tweet.getConversationId(), 0) + 1);
    }

    conversationIds = conversationIds.entrySet()
                                     .stream()
                                     .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                     .filter(o -> o.getValue() >= this.getConfig().getMinAnswers())
                                     .collect(Collectors.toMap(
                                         Map.Entry::getKey,
                                         Map.Entry::getValue,
                                         (oldValue, newValue) -> oldValue, LinkedHashMap::new));

    System.out.println(new ArrayList<>(conversationIds.keySet()).size() + " tweets found (longest conversations)");
    return this.getTwitterClient().collectionsCurate(this.getConfig().getTimelineId(), new ArrayList<>(conversationIds.keySet()));
  }

}
