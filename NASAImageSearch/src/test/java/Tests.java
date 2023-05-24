import com.example.nasaimagesearch.ImageSearchController;
import com.example.nasaimagesearch.ImageSearchModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Tests {
    Assertions Assert;
    ImageSearchController controller = new ImageSearchController();


    @Test
    public void basicQuerySizeOfOutputTest() throws Exception {
        String query = "mars";
        System.out.println("Query is " + query);
        String link = "https://images-api.nasa.gov/search?q=" + query + "&media_type=image";
        List<ImageSearchModel> list =  controller.getResults(link);
        Assert.assertTrue(list.size() <= 100);
    }

    @Test
    public void randomStringQuerySizeOfOutputTest() throws Exception {
        String query = RandomStringUtils.randomAlphabetic(10);
        System.out.println("Query is " + query);
        String link = "https://images-api.nasa.gov/search?q=" + query + "&media_type=image";
        List<ImageSearchModel> list =  controller.getResults(link);
        Assert.assertTrue(list.size() <= 100);
    }

    @Test
    public void emptyQueryTest() throws Exception {
        String query = "";
        String link = "https://images-api.nasa.gov/search?q=" + query + "&media_type=image";
        List<ImageSearchModel> list =  controller.getResults(link);
        Assert.assertTrue(list.size() <= 100);
    }

    @Test
    public void alphaNumericCharsQueryTest() throws Exception {
        String query = RandomStringUtils.randomAlphanumeric(10);
        System.out.println("Query is " + query);
        String link = "https://images-api.nasa.gov/search?q=" + query + "&media_type=image";
        List<ImageSearchModel> list =  controller.getResults(link);
        Assert.assertTrue(list.size() <= 100);
    }

    @Test
    public void onlyNumericCharsQueryTest() throws Exception {
        String query = RandomStringUtils.randomNumeric(10);
        System.out.println("Query is " + query);
        String link = "https://images-api.nasa.gov/search?q=" + query + "&media_type=image";
        List<ImageSearchModel> list =  controller.getResults(link);
        Assert.assertTrue(list.size() <= 100);
    }

    @Test
    public void longInput500CharsQueryTest() throws Exception {
        String query = RandomStringUtils.randomAlphabetic(500);
        System.out.println("Query is " + query);
        String link = "https://images-api.nasa.gov/search?q=" + query + "&media_type=image";
        List<ImageSearchModel> list =  controller.getResults(link);
        Assert.assertTrue(list.size() <= 100);
    }

    @Test
    public void specialCharsQueryTest() throws URISyntaxException, IOException, InterruptedException {
        String query = ":}V|[p^JC<:i>K\"#Ui%T";
        System.out.println("Query is " + query);
        String link = "https://images-api.nasa.gov/search?q=" + query + "&media_type=image";
        try {
            List<ImageSearchModel> list =  controller.getResults(link);
            Assert.fail("Exception should have hit when hitting the API as we input special characters");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
