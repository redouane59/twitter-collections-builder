import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.redouane59.twitter.dto.collections.CollectionsResponse;
import io.github.redouane59.twitter.dto.collections.CollectionsResponse.Response.Error;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Launcher {

  public static void main(String[] args) throws IOException {

    List<CollectionBuilder> builders = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();

    builders.add(new BestTweetsBuilder(objectMapper.readValue(new File(Launcher.class.getClassLoader().getResource("best-from.json").getFile()),
                                                               CollectionConfiguration.class)));
    builders.add(new BestTweetsBuilder(objectMapper.readValue(new File(Launcher.class.getClassLoader().getResource("best-to.json").getFile()),
                                                               CollectionConfiguration.class)));
    builders.add(new LongestConversationsBuilder(objectMapper.readValue(new File(Launcher.class.getClassLoader().getResource("longest.json").getFile()),
                                                              CollectionConfiguration.class)));

    int duplicates = 0;
    int notFound = 0;
    int others = 0;
    for(CollectionBuilder builder : builders){
      CollectionsResponse response = builder.collect();
      if(response.hasErrors()){
        for(Error error : response.getResponse().getErrors()){
          if(error.getReason().equals("duplicate")){
            duplicates++;
          } else if(error.getReason().equals("not_found")){
            notFound++;
          } else{
            others++;
          }
        }
      }
    }
    System.out.println(duplicates + " duplicates found");
    System.out.println(notFound + " tweets not found");
    if(others>0){
      System.out.println(others + " other errors");
    }
  }

}
