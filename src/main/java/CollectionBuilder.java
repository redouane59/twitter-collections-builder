import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.collections.CollectionsResponse;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public abstract class CollectionBuilder {

  private TwitterClient           twitterClient = new TwitterClient(TwitterClient.OBJECT_MAPPER
                                                      .readValue(new File("C:/Users/Perso/Documents/GitHub/twitter-credentials.json"),
                                                                 TwitterCredentials.class));
  private CollectionConfiguration config;

  public CollectionBuilder(CollectionConfiguration collectionConfiguration) throws IOException {
    this.config = collectionConfiguration;
  }

  public abstract CollectionsResponse collect();

}
