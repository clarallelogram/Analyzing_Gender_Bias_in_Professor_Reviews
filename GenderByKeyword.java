package finalproject;

import java.util.ArrayList;
import java.util.LinkedList;

public class GenderByKeyword extends DataAnalyzer {
    private MyHashTable<String, MyHashTable<String, Integer>> nestedHashTable;
    public GenderByKeyword(Parser p) {
        super(p);
    }

    @Override
    public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
        return nestedHashTable.get(keyword.trim().toLowerCase());
        //ADD YOUR CODE ABOVE THIS
    }

    @Override
    public void extractInformation() {
        // ADD YOUR CODE BELOW THIS
        nestedHashTable = new MyHashTable<String, MyHashTable<String, Integer>>();
        MyHashTable<String, Boolean> uniqueReviews = new MyHashTable<String, Boolean>();

        for (String[] line : parser.data) {
            uniqueReviews = new MyHashTable<String, Boolean>();
            String gender = line[parser.fields.get("gender")];
            String comment = line[parser.fields.get("comments")];
            comment = comment.toLowerCase().replaceAll("[^a-z']", " ");
            String[] words = comment.split("\\s+");
            for (String word : words) {
                if (uniqueReviews.get(word)==null) {
                    uniqueReviews.put(word, false);
                }
                MyHashTable<String, Integer> innerHashTable = nestedHashTable.get(word);
                if (innerHashTable == null) {
                    innerHashTable = new MyHashTable<String, Integer>();
                    innerHashTable.put("M", 0);
                    innerHashTable.put("F", 0);
                    innerHashTable.put("X", 0);
                    innerHashTable.put(gender, innerHashTable.get(gender) + 1);
                    nestedHashTable.put(word, innerHashTable);
                }
                else if (!uniqueReviews.get(word)) {
                    innerHashTable.put(gender, innerHashTable.get(gender) + 1);
                }
                uniqueReviews.put(word, true);
            }
        }
        //ADD YOUR CODE ABOVE THIS
    }
}
