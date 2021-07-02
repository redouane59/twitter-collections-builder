import com.fasterxml.jackson.annotation.JsonProperty;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionConfiguration {

  private String query;
  @JsonProperty("min_likes")
  private int minLikes;
  @JsonProperty("min_retweets")
  private int minRetweets;
  @JsonProperty("min_answers")
  private int minAnswers;
  @JsonProperty("timeline_id")
  private String timelineId;
  @JsonProperty("hours_in_past_start")
  private int    hoursInPastStart;
  @JsonProperty("hours_in_past_end")
  private int    hoursInPastEnd;
  private String language;
}
