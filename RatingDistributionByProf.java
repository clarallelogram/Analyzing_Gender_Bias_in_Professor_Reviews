package finalproject;

import java.util.LinkedList;

public class RatingDistributionByProf extends DataAnalyzer {

    private MyHashTable<String, MyHashTable<String, Integer>> nestedHashTable;

    public RatingDistributionByProf(Parser p) {
        super(p);
    }

    @Override
    public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
        // ADD YOUR CODE BELOW THIS
        return nestedHashTable.get(keyword.trim().toLowerCase());
    }
    //ADD YOUR CODE ABOVE THIS

    @Override
    public void extractInformation() {
        // ADD YOUR CODE BELOW THIS
        nestedHashTable = new MyHashTable<String, MyHashTable<String, Integer>>();
        for (String[] line : parser.data) {
            String profName = line[parser.fields.get("professor_name")].trim().toLowerCase();
            double rating = Double.parseDouble(line[parser.fields.get("student_star")]);
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
            MyHashTable<String, Integer> innerHashTable = nestedHashTable.get(profName);
            if (innerHashTable == null) {
                innerHashTable = new MyHashTable<String, Integer>();
                innerHashTable.put("1", 0);
                innerHashTable.put("2", 0);
                innerHashTable.put("3", 0);
                innerHashTable.put("4", 0);
                innerHashTable.put("5", 0);
                nestedHashTable.put(profName, innerHashTable);
            }
            innerHashTable.put(star, innerHashTable.get(star) + 1);
        }
    }
    //ADD YOUR CODE ABOVE THIS
}
