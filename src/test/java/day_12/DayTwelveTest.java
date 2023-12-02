package day_12;

import com.fasterxml.jackson.core.JsonProcessingException;
import day_12.DayTwelve.JsonUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DayTwelveTest {

    @ParameterizedTest
    @CsvSource(value = {
            "[1,2,3]                   #3",
            "{\"a\":2,\"b\":4}         #2",
            "[[[3]]]                   #1",
            "{\"a\":{\"b\":4},\"c\":-1}#2",
            "{\"a\":[-1,1]}            #2",
            "[-1,{\"a\":1}]            #2",
            "[]                        #0",
            "{}                        #0"
    }, delimiter = '#')
    void should_find_numbers(String input, int expectedListSize) {
        assertThat(JsonUtil.findNumbers(input)).hasSize(expectedListSize);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "[1,2,3]                   #6",
            "{\"a\":2,\"b\":4}         #6",
            "[[[3]]]                   #3",
            "{\"a\":{\"b\":4},\"c\":-1}#3",
            "{\"a\":[-1,1]}            #0",
            "[-1,{\"a\":1}]            #0",
            "[]                        #0",
            "{}                        #0"
    }, delimiter = '#')
    void should_sum(String input, int expectedSum) {
        List<Integer> numbers = JsonUtil.findNumbers(input);
        assertThat(numbers.stream().reduce(0, Integer::sum)).isEqualTo(expectedSum);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "[1,2,3]                                                                                         #3",
            "[1,{\"c\":\"red\",\"b\":2},3]                                                                   #2",
            "[{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5},2]                                                     #1",
            "[1,{\"b\":1,\"e\":{\"b\":2,\"c\":\"red\"}},3]                                                   #3",
            "[1,\"red\",5]                                                                                   #2",
            "[{\"a\":\"red\",\"b\":2},{\"a\":{\"c\":{\"e\":\"red\",\"f\":7,\"g\":[1,2,3]},\"d\":3},\"b\":2}] #2",
            "[{\"a\":[{\"a\":\"red\",\"b\":0},\"red\",{\"a\":\"green\",\"c\":0,\"d\":\"red\",\"f\":{\"a\":\"" +
                    "orange\",\"b\":0}},[1,{\"a\":\"orange\",\"b\":0,\"c\":\"red\",\"d\":0,\"e\":0},2,3,[\"o" +
                    "range\",\"blue\",\"orange\",\"yellow\",\"red\",\"violet\",4,5,6]]],\"b\":[7,[\"orange\"" +
                    ",{\"a\":8,\"b\":\"violet\"},\"violet\",9,10,\"green\"],11],\"c\":{\"a\":\"violet\",\"b" +
                    "\":12,\"c\":13,\"d\":{\"a\":{\"a\":0,\"b\":0,\"c\":0,\"d\":\"yellow\",\"e\":0,\"f\":\"" +
                    "green\",\"g\":0,\"h\":\"violet\",\"i\":0},\"b\":0,\"c\":\"orange\",\"d\":[171,\"green\"" +
                    ",\"orange\",1,-22,17,\"red\",\"orange\",\"green\",-33],\"e\":0,\"f\":\"red\"}},\"d\":14," +
                    "\"e\":\"blue\",\"f\":15},{\"a\":0,\"b\":\"red\",\"c\":0,\"d\":\"violet\",\"h\":\"red\"," +
                    "\"i\":{\"a\":\"violet\",\"b\":\"red\",\"c\":[{\"a\":\"green\",\"b\":0,\"c\":\"yellow\"," +
                    "\"d\":\"violet\"},[0,0,0],{\"a\":0,\"b\":0,\"c\":0,\"d\":\"violet\",\"e\":\"red\",\"f\"" +
                    ":0,\"g\":0,\"h\":0,\"i\":0},\"red\"],\"d\":\"yellow\",\"e\":0}}]                       #15",
    }, delimiter = '#')
    void should_find_non_red_numbers(String input, int expectedListSize) throws JsonProcessingException {
        assertThat(JsonUtil.findNonRedNumbers(input)).hasSize(expectedListSize);
    }
    
    @ParameterizedTest
    @CsvSource(value = {
            "[1,2,3]                                                                                         #3",
            "[1,{\"c\":\"red\",\"b\":2},3]                                                                   #2",
            "[{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5},2]                                                     #1",
            "[1,{\"b\":1,\"e\":{\"b\":2,\"c\":\"red\"}},3]                                                   #3",
            "[1,\"red\",5]                                                                                   #2",
            "[{\"a\":\"red\",\"b\":2},{\"a\":{\"c\":{\"e\":\"red\",\"f\":7,\"g\":[1,2,3]},\"d\":3},\"b\":2}] #2",
            "[{\"a\":[{\"a\":\"red\",\"b\":0},\"red\",{\"a\":\"green\",\"c\":0,\"d\":\"red\",\"f\":{\"a\":\"" +
                    "orange\",\"b\":0}},[1,{\"a\":\"orange\",\"b\":0,\"c\":\"red\",\"d\":0,\"e\":0},2,3,[\"o" +
                    "range\",\"blue\",\"orange\",\"yellow\",\"red\",\"violet\",4,5,6]]],\"b\":[7,[\"orange\"" +
                    ",{\"a\":8,\"b\":\"violet\"},\"violet\",9,10,\"green\"],11],\"c\":{\"a\":\"violet\",\"b" +
                    "\":12,\"c\":13,\"d\":{\"a\":{\"a\":0,\"b\":0,\"c\":0,\"d\":\"yellow\",\"e\":0,\"f\":\"" +
                    "green\",\"g\":0,\"h\":\"violet\",\"i\":0},\"b\":0,\"c\":\"orange\",\"d\":[171,\"green\"" +
                    ",\"orange\",1,-22,17,\"red\",\"orange\",\"green\",-33],\"e\":0,\"f\":\"red\"}},\"d\":14," +
                    "\"e\":\"blue\",\"f\":15},{\"a\":0,\"b\":\"red\",\"c\":0,\"d\":\"violet\",\"h\":\"red\"," +
                    "\"i\":{\"a\":\"violet\",\"b\":\"red\",\"c\":[{\"a\":\"green\",\"b\":0,\"c\":\"yellow\"," +
                    "\"d\":\"violet\"},[0,0,0],{\"a\":0,\"b\":0,\"c\":0,\"d\":\"violet\",\"e\":\"red\",\"f\"" +
                    ":0,\"g\":0,\"h\":0,\"i\":0},\"red\"],\"d\":\"yellow\",\"e\":0}}]                       #15",
    }, delimiter = '#')
    void should_find_non_red_numbers_without_jackson_library(String input, int expectedListSize) {
        assertThat(JsonUtil.findNonRedNumbersWithoutJacksonLibrary(input)).hasSize(expectedListSize);
    }
}