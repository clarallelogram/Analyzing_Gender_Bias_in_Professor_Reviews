package finalproject;

import java.util.ArrayList;

public class RatingByGender extends DataAnalyzer{
    private MyHashTable<String, MyHashTable<String, Integer>> nestedHashTableQ;
    private MyHashTable<String, MyHashTable<String, Integer>> nestedHashTableD;
    public RatingByGender(Parser p) {
        super(p);
    }

    @Override
    public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
        // ADD YOUR CODE BELOW THIS

        MyHashTable<String, Integer> ratingByGender = new MyHashTable<String, Integer>();
        String[] which = keyword.trim().split(",");

        if (which[1].equalsIgnoreCase("difficulty") || which[1].equalsIgnoreCase(" difficulty")) {
            return nestedHashTableD.get(which[0].toUpperCase());
        }

        else if (which[1].equalsIgnoreCase("quality") || which[1].equalsIgnoreCase(" quality")) {
            return nestedHashTableQ.get(which[0].toUpperCase());
        }
        return null;
        //ADD YOUR CODE ABOVE THIS
    }

    @Override
    public void extractInformation() {
        // ADD YOUR CODE BELOW THIS
        nestedHashTableQ = new MyHashTable<String, MyHashTable<String, Integer>>();
        nestedHashTableD = new MyHashTable<String, MyHashTable<String, Integer>>();

        for (String[] line : parser.data) {
            String gender = line[parser.fields.get("gender")];
            double quality = Double.parseDouble(line[parser.fields.get("student_star")]);
            double difficulty = Double.parseDouble(line[parser.fields.get("student_difficult")]);
            String star;
            String diff;
            if (quality >= 1 && quality < 2) {
                star = "1";
            } else if (quality >= 2 && quality < 3) {
                star = "2";
            } else if (quality >= 3 && quality < 4) {
                star = "3";
            } else if (quality >= 4 && quality < 5) {
                star = "4";
            } else {
                star = "5";
            }
            if (difficulty >= 1 && difficulty < 2) {
                diff = "1";
            } else if (difficulty >= 2 && difficulty < 3) {
                diff = "2";
            } else if (difficulty >= 3 && difficulty < 4) {
                diff = "3";
            } else if (difficulty >= 4 && difficulty < 5) {
                diff = "4";
            } else {
                diff = "5";
            }

            MyHashTable<String, Integer> innerHashTableQ = nestedHashTableQ.get(gender);
            MyHashTable<String, Integer> innerHashTableD = nestedHashTableD.get(gender);

            if (innerHashTableQ == null) {
                innerHashTableQ = new MyHashTable<String, Integer>();
                innerHashTableQ.put("1", 0);
                innerHashTableQ.put("2", 0);
                innerHashTableQ.put("3", 0);
                innerHashTableQ.put("4", 0);
                innerHashTableQ.put("5", 0);
                if (!gender.equals("X")) {
                    nestedHashTableQ.put(gender, innerHashTableQ);
                }
            }
            innerHashTableQ.put(star, innerHashTableQ.get(star)+1);

            if (innerHashTableD == null) {
                innerHashTableD = new MyHashTable<String, Integer>();
                innerHashTableD.put("1", 0);
                innerHashTableD.put("2", 0);
                innerHashTableD.put("3", 0);
                innerHashTableD.put("4", 0);
                innerHashTableD.put("5", 0);
                if (!gender.equals("X")) {
                    nestedHashTableD.put(gender, innerHashTableD);
                }
            }
            innerHashTableD.put(diff, innerHashTableD.get(diff)+1);


            }
        }
        //ADD YOUR CODE ABOVE THIS
}
