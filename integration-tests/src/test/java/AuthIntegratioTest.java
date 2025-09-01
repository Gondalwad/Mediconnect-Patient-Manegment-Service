import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class AuthIntegratioTest {

    @BeforeAll
    public void setUp(){
        RestAssured.baseURI = "http://localhost:4044";
    }

    public void shouldReturnOkWithJwtToken(){
//        String payload = """
//                {
//                    "
//                }
//                """
    }
}
