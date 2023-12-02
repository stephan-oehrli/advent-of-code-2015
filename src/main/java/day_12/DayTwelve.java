package day_12;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DayTwelve {

    public static void main(String[] args) throws FileNotFoundException, JsonProcessingException {
        String jsonString = FileUtil.readToString("day_12.txt");
        int sumOfAllNumbers = JsonUtil.findNumbers(jsonString).stream().reduce(0, Integer::sum);
        System.out.println("Sum of all numbers is: " + sumOfAllNumbers);
        long start = System.currentTimeMillis();
        int sumOfNonRedNumbers = JsonUtil.findNonRedNumbers(jsonString).stream().reduce(0, Integer::sum);
        System.out.println("\nSum of all non red numbers is: " + sumOfNonRedNumbers);
        System.out.println("Duration: " + (System.currentTimeMillis() - start) + "ms");
        start = System.currentTimeMillis();
        int sumOfNonRedNumbersWithoutJacksonLibrary =
                JsonUtil.findNonRedNumbersWithoutJacksonLibrary(jsonString).stream().reduce(0, Integer::sum);
        System.out.println("\nSum of all non red numbers is: " + sumOfNonRedNumbersWithoutJacksonLibrary);
        System.out.println("Duration: " + (System.currentTimeMillis() - start) + "ms");
    }

    @UtilityClass
    protected class JsonUtil {

        public static List<Integer> findNumbers(String jsonString) {
            jsonString = jsonString.replace(",", "#");
            jsonString = jsonString.replace("]", "#");
            jsonString = jsonString.replace("}", "#");
            jsonString = jsonString.replaceAll("[^-#0-9]", "");
            return Arrays.stream(jsonString.split("#")).filter(StringUtils::isNotEmpty).map(Integer::parseInt).toList();
        }

        public static List<Integer> findNonRedNumbers(String jsonString) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode arrayNode = objectMapper.readValue(jsonString, ArrayNode.class);
            removeRedObjectsFrom(arrayNode);
            return findNumbers(arrayNode.toString());
        }

        private static void removeRedObjectsFrom(ArrayNode parent) {
            List<Integer> indexesToRemove = new ArrayList<>();
            for (int i = 0; i < parent.size(); i++) {
                JsonNode child = parent.get(i);
                if (child instanceof ObjectNode objectNode && isObjectContainingRed(objectNode)) {
                    indexesToRemove.add(i);
                } else if (child instanceof ObjectNode objectNode) {
                    removeObjectNodesContainingRed(objectNode);
                } else if (child instanceof ArrayNode array) {
                    removeRedObjectsFrom(array);
                }
            }
            indexesToRemove.forEach(index -> ((ObjectNode) parent.get(index)).removeAll());
        }

        private static void removeObjectNodesContainingRed(ObjectNode parent) {
            for (JsonNode child : parent) {
                if (child instanceof ObjectNode objectNode && isObjectContainingRed(objectNode)) {
                    ((ObjectNode) child).removeAll();
                } else if (child instanceof ObjectNode object) {
                    removeObjectNodesContainingRed(object);
                } else if (child instanceof ArrayNode arrayNode) {
                    removeRedObjectsFrom(arrayNode);
                }
            }
        }

        private static boolean isObjectContainingRed(ObjectNode parent) {
            for (JsonNode child : parent) {
                if (child instanceof TextNode textNode && textNode.textValue().equals("red")) {
                    return true;
                }
            }
            return false;
        }

        public static List<Integer> findNonRedNumbersWithoutJacksonLibrary(String jsonString) {
            List<FromTo> indexes = new ArrayList<>();
            for (int i = 0; i < jsonString.length(); i++) {
                if (jsonString.substring(i).startsWith(":\"red\"")) {
                    int from = findFromIndex(jsonString, i);
                    int to = findToIndex(jsonString, i);
                    indexes.add(new FromTo(from, to));
                    i = to;
                }
            }
            List<FromTo> indexesToRemove = new ArrayList<>();
            for (int i = 0; i < indexes.size(); i++) {
                for (FromTo index : indexes) {
                    if (indexes.get(i).contains(index)) {
                        indexesToRemove.add(index);
                    }
                }
            }
            for (FromTo fromTo : indexesToRemove) {
                indexes.remove(fromTo);
            }
            Collections.reverse(indexes);
            StringBuilder stringBuilder = new StringBuilder(jsonString);
            for (FromTo index : indexes) {
                stringBuilder.replace(index.from, index.to, "{}");
            }
            return findNumbers(stringBuilder.toString());
        }

        private static int findFromIndex(String jsonString, int index) {
            int from = 0;
            int otherBrackets = 0;
            for (int j = index; j >= 0; j--) {
                if (jsonString.charAt(j) == '{' && otherBrackets == 0) {
                    from = j;
                    break;
                }
                if (jsonString.charAt(j) == '{') {
                    otherBrackets--;
                }
                if (jsonString.charAt(j) == '}') {
                    otherBrackets++;
                }
            }
            return from;
        }

        private static int findToIndex(String jsonString, int index) {
            int to = 0;
            int otherBrackets = 0;
            for (int j = index; j < jsonString.length(); j++) {
                if (jsonString.charAt(j) == '}' && otherBrackets == 0) {
                    to = j;
                    break;
                }
                if (jsonString.charAt(j) == '{') {
                    otherBrackets++;
                }
                if (jsonString.charAt(j) == '}') {
                    otherBrackets--;
                }
            }
            return to;
        }

        private record FromTo(int from, int to) {

            boolean contains(FromTo fromTo) {
                return fromTo != this && fromTo.from() > this.from && fromTo.to() < this.to;
            }
        }
    }
}
