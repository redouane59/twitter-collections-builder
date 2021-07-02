import io.github.redouane59.twitter.dto.collections.CollectionsResponse;
import io.github.redouane59.twitter.dto.endpoints.AdditionalParameters;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import io.github.redouane59.twitter.dto.tweet.TweetList;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BestTweetsBuilder extends CollectionBuilder {

  public BestTweetsBuilder(final CollectionConfiguration collectionConfiguration) throws IOException {
    super(collectionConfiguration);
  }

  public CollectionsResponse collect() {
    String query = this.getConfig().getQuery();
    if(this.getConfig()!=null){
      query += " lang:"+ this.getConfig().getLanguage();
    }
    TweetList tweets = this.getTwitterClient().searchTweets(query,
                                             AdditionalParameters.builder()
                                                                 .startTime(LocalDateTime.now().minusHours(this.getConfig().getHoursInPastStart()))
                                                                 .endTime(LocalDateTime.now().minusHours(this.getConfig().getHoursInPastEnd()).minusMinutes(1))
                                                                 .maxResults(100).build());
    System.out.println("API gave " + tweets.getData().size()+ " results");
    List<String> tweetIds = tweets.getData().stream()
                                  .filter(this::shouldBeTaken)
                                  .map(Tweet::getId)
                                  .collect(Collectors.toList());
    System.out.println(tweetIds.size() + " tweets found (best tweets " + this.getConfig().getQuery() + ")");
    return this.getTwitterClient().collectionsCurate(this.getConfig().getTimelineId(), tweetIds);
  }

  public boolean shouldBeTaken(Tweet t){
    return (t.getRetweetCount() > this.getConfig().getMinRetweets()
            || t.getLikeCount() > this.getConfig().getMinLikes()
            || t.getReplyCount() > this.getConfig().getMinAnswers());
  }
}
