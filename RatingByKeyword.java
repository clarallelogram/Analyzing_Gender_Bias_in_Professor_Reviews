package finalproject;

import java.util.ArrayList;

public class RatingByKeyword extends DataAnalyzer {
    private MyHashTable<String, MyHashTable<String, Integer>> nestedHashTable;
    public RatingByKeyword(Parser p) {
        super(p);
    }

    @Override
    public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
        // ADD YOUR CODE BELOW THIS
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
            double rating = Double.parseDouble(line[parser.fields.get("student_star")]);
            String comment = line[parser.fields.get("comments")];
            comment = comment.toLowerCase().replaceAll("[^a-z']", " ");
            String[] words = comment.split("\\s+");
            String star;
            if (rating >= 1 && rating < 2) {
                star = "1";
            } else if (rating >= 2 && rating < 3) {
                star = "2";
            } else if (rating >= 3 && rating < 4) {
                star = "3";
            } else if (rating >= 4 && rating < 5) {
                star = "4";
            } else {
                star = "5";
            }
            for (String word : words) {
                if (uniqueReviews.get(word)==null) {
                    uniqueReviews.put(word, false);
                }
                MyHashTable<String, Integer> innerHashTable = nestedHashTable.get(word);
                if (innerHashTable == null) {
                    innerHashTable = new MyHashTable<String, Integer>();
                    innerHashTable.put("1", 0);
                    innerHashTable.put("2", 0);
                    innerHashTable.put("3", 0);
                    innerHashTable.put("4", 0);
                    innerHashTable.put("5", 0);
                    innerHashTable.put(star, innerHashTable.get(star) + 1);
                    nestedHashTable.put(word, innerHashTable);
                }
                else if (!uniqueReviews.get(word)) {
                    innerHashTable.put(star, innerHashTable.get(star) + 1);
                }
                uniqueReviews.put(word, true);
            }
        }
        //ADD YOUR CODE ABOVE THIS
    }
}
