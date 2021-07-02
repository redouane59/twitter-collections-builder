import io.github.redouane59.twitter.dto.collections.CollectionsResponse;
import io.github.redouane59.twitter.dto.collections.TimeLineOrder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CollectionTest {

  @Test
  public void createCollection() throws IOException {
      BestTweetsBuilder   builder = new BestTweetsBuilder(new CollectionConfiguration());
      CollectionsResponse response = builder.getTwitterClient().collectionsCreate(
          "decathlon",
          "test for decathlon",
          "decathlon",
          TimeLineOrder.CHRONOLOGICAL_REVERSE);
    System.out.println(response.getResponse().getTimeLineId());
  }

  @Test
  public void deleteCollection() throws IOException {
    BestTweetsBuilder   builder = new BestTweetsBuilder(new CollectionConfiguration());
    CollectionsResponse response = builder.getTwitterClient().collectionsDestroy("custom-1370807121806254082");
    System.out.println(response.getResponse().getTimeLineId());
  }

}
